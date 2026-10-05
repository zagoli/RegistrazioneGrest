package Controller;

import DAOManager.DAOMan;
import ModelAndView.FreemarkerTemplate;
import ModelAndView.RedirectResult;
import ModelAndView.Response;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public class ControllerLaboratori implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
            template.addObject("tipoUt", tipoUt);
            template.addObject("TITOLOPAGINA", "Assegnazione Laboratori");
            //preparo gli oggetti per la pagina
            if (!request.getParameterMap().containsKey("submitted")) {
                switch (request.getParameter("target")) {
                    case "rag":
                        template.addObject("ragazzi", DAOMan.ragazzoDAO.findAll());
                        template.addObject("laboratori", DAOMan.laboratorioDAO.findNonRiservato());
                        template.setView("ammseg/labragazzi.html");
                        break;
                    case "an":
                        template.addObject("animatori", DAOMan.animatoreDAO.findAll());
                        template.addObject("laboratori", DAOMan.laboratorioDAO.findAll());
                        template.setView("ammseg/labanimatori.html");
                        break;
                    case "ter":
                        template.addObject("terzamedia", DAOMan.terzamediaDAO.findAll());
                        template.addObject("laboratori", DAOMan.laboratorioDAO.findAll());
                        template.setView("ammseg/labterzamedia.html");
                        break;
                }
            } else {
                //salvo le modifiche
                switch (request.getParameter("target")) {
                    case "rag": {
                        String[] listLabParam = request.getParameterValues("laboratorio");
                        if (listLabParam != null) {
                            for (String value : listLabParam) {
                                String[] ragLabArray = value.split(",");
                                int idRagazzo = Integer.parseInt(ragLabArray[0]), idLaboratorio = Integer.parseInt(ragLabArray[1]);
                                DAOMan.ragazzoDAO.updateLaboratorio(idRagazzo, idLaboratorio);
                            }
                        }
                        return new RedirectResult("/RegistrazioneGrest/App/Laboratori?target=rag");
                    }
                    case "an": {
                        Set<Map.Entry<String, String[]>> parameterSet = request.getParameterMap().entrySet();  //è un insieme di entry della mappa con chiave stringa e valore array di stringhe
                        for (Map.Entry<String, String[]> entry : parameterSet) {
                            String key = entry.getKey();
                            // faccio la modifica solo se la chiave è un numero (controllato dal matches, copiato da Stack Overflow). Avrei eliminato quelle entry, ma purtroppo il set è immutabile
                            // in alternativa, potrei copiare il set non modificabile in un nuovo set modificabile, ed eliminare le entry che non interessano, ma fa anche stesso
                            if (key.matches("\\d+")) {
                                String[] value = entry.getValue();
                                // il secondo elemento è presente solo se è a true, come nei checkbox
                                // qua sotto: il primo parametro è l'id che sarebbe la chiave della entry, il secondo è il laboratorio cioè il primo elemento di value e il secondo è sapere se c'è il valore "responsabile"
                                DAOMan.animatoreDAO.updateLaboratorio(Integer.parseInt(key), Integer.parseInt(value[0]), value.length == 2); //scrittura fighetta ispirata da Netbeans
                            }
                        }
                        return new RedirectResult("/RegistrazioneGrest/App/Laboratori?target=an");
                    }
                    case "ter": {
                        String[] listLabParam = request.getParameterValues("laboratorio");
                        if (listLabParam != null) {
                            for (String value : listLabParam) {
                                String[] terLabArray = value.split(",");
                                int id = Integer.parseInt(terLabArray[0]);
                                DAOMan.terzamediaDAO.updateLaboratorio(id, Integer.parseInt(terLabArray[1]));
                            }
                        }
                        return new RedirectResult("/RegistrazioneGrest/App/Laboratori?target=ter");
                    }
                }
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerLaboratori.class.getName());
        }
        return template;
    }
}
