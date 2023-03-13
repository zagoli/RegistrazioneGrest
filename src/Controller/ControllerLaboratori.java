package Controller;

import DAOManager.DAOMan;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerLaboratori implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        //devo fare controlli aggiuntivi, e mettere l'hidden input submitted, visto che in questo caso passo sempre nei parametri anche target, a differenza delle squadre dove ci sono 3 controller separati perchè sono pigro
        ModelAndView mv = new ModelAndViewStandard();
        mv.addObject("TITOLOPAGINA", "Assegnazione Laboratori");
        try {
            //preparo gli oggetti per la pagina
            if (!request.getParameterMap().containsKey("submitted")) {
                switch (request.getParameter("target")) {
                    case "rag":
                        mv.addObject("ragazzi", DAOMan.ragazzoDAO.findAll());
                        mv.addObject("laboratori", DAOMan.laboratorioDAO.findNonRiservato());
                        mv.setView("ammseg/labragazzi.html");
                        break;
                    case "an":
                        mv.addObject("animatori", DAOMan.animatoreDAO.findAll());
                        mv.addObject("laboratori", DAOMan.laboratorioDAO.findAll());
                        mv.setView("ammseg/labanimatori.html");
                        break;
                    case "ter":
                        mv.addObject("terzamedia", DAOMan.terzamediaDAO.findAll());
                        mv.addObject("laboratori", DAOMan.laboratorioDAO.findAll());
                        mv.setView("ammseg/labterzamedia.html");
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
                                DAOMan.ragazzoDAO.updateLaboratorio(idRagazzo,idLaboratorio);
                            }
                        }
                        response.sendRedirect("/RegistrazioneGrest/App/Laboratori?target=rag");
                        break;
                    }
                    case "an": {
                        Set<Map.Entry<String, String[]>> parameterSet = request.getParameterMap().entrySet();  //è un insieme di entry della mappa con chiave stringa e valore array di stringhe
                        for (Map.Entry<String, String[]> entry : parameterSet) {
                            String key = entry.getKey();
                            // faccio la modifica solo se la chiave è un numero (controllato dal matches, copiato da Stack Overflow). Avrei eliminato quelle entry, ma purtroppo il set è immutabile
                            // in alternativa, potrei copiare il set non modificabile in un nuovo set modificabile, ed eliminare le entry che non interessano, ma fa anche stesso
                            if (key.matches("\\d+")) {
                                String[] value = entry.getValue();
                                //il secondo elemento è presente solo se è a true, come nei checkbox
                                //qua sotto: il primo parametro è l'id che sarebbe la chiave della entry, il secondo è il laboratorio cioè il primo elemento di value e il secondo è sapere se c'è il valore "responsabile"
                                DAOMan.animatoreDAO.updateLaboratorio(Integer.parseInt(key),Integer.parseInt(value[0]),value.length == 2); //scrittura fighetta ispirata da Netbeans
                            }
                        }
                        response.sendRedirect("/RegistrazioneGrest/App/Laboratori?target=an");
                        break;
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
                        response.sendRedirect("/RegistrazioneGrest/App/Laboratori?target=ter");
                        break;
                    }
                }
            }
        } catch (NullPointerException | SQLException | IOException ex) {

            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerLaboratori.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }
}
