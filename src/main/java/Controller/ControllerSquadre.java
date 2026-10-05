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

public class ControllerSquadre implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("TITOLOPAGINA", "Assegnazione Squadre");
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            switch (request.getParameter("target")) {
                case "rag":
                    if (!request.getParameterMap().containsKey("submitted")) {
                        //preparo i dati iniziali per visualizzare la pagina
                        template.addObject("ragazzi", DAOMan.ragazzoDAO.findAll());
                        template.addObject("squadre", DAOMan.squadraDAO.findAll());
                        template.setView("ammseg/squadreragazzi.html");
                    } else {
                        //salvo le modifiche
                        String[] listSquadreParam = request.getParameterValues("squadra");
                        if (listSquadreParam != null) {
                            for (String value : listSquadreParam) {
                                String[] ragSquadraArray = value.split(",");
                                int idRagazzo = Integer.parseInt(ragSquadraArray[0]);
                                if (ragSquadraArray.length == 2) {
                                    DAOMan.ragazzoDAO.updateSquadra(idRagazzo, Integer.parseInt(ragSquadraArray[1]));
                                } else {
                                    DAOMan.ragazzoDAO.updateSquadra(idRagazzo, null);
                                }
                            }
                        }
                        return new RedirectResult("/RegistrazioneGrest/App/Squadre?target=rag");
                    }
                    break;
                case "an":
                    if (!request.getParameterMap().containsKey("submitted")) {
                        //preparo i dati iniziali per visualizzare la pagina
                        template.addObject("animatori", DAOMan.animatoreDAO.findAll());
                        template.addObject("squadre", DAOMan.squadraDAO.findAll());
                        template.setView("ammseg/squadreanimatori.html");
                    } else {
                        //salvo le modifiche
                        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
                            if (entry.getKey().matches("\\d+")) { //vedi controller laboratori per spiegazione
                                Integer squadra;
                                String[] value = entry.getValue();
                                //imposto la squadra, se non c'è metto null
                                if (value[0].isEmpty()) {
                                    if (value.length == 2) {
                                        throw new IllegalArgumentException("Impossibile impostare come responsabile un animatore che non ha nessuna squadra!");
                                    }
                                    squadra = null;
                                } else {
                                    squadra = Integer.parseInt(value[0]);
                                }
                                //il secondo elemento è presente solo se è a true, come nei checkbox
                                DAOMan.animatoreDAO.updateSquadra(Integer.parseInt(entry.getKey()), squadra, value.length == 2); //figata di netbeans
                            }
                        }
                        return new RedirectResult("/RegistrazioneGrest/App/Squadre?target=an");
                    }
                    break;
                case "ter":
                    if (!request.getParameterMap().containsKey("submitted")) {
                        //preparo i dati iniziali per visualizzare la pagina
                        template.addObject("terzamedia", DAOMan.terzamediaDAO.findAll());
                        template.addObject("squadre", DAOMan.squadraDAO.findAll());
                        template.setView("ammseg/squadreterzamedia.html");
                    } else {
                        //salvo le modifiche
                        String[] listSquadreParam = request.getParameterValues("squadra");
                        if (listSquadreParam != null) {
                            for (String value : listSquadreParam) {
                                String[] terSquadraArray = value.split(",");
                                int id = Integer.parseInt(terSquadraArray[0]);
                                if (terSquadraArray.length == 2) {
                                    DAOMan.terzamediaDAO.updateSquadra(id, Integer.parseInt(terSquadraArray[1]));
                                } else {
                                    DAOMan.terzamediaDAO.updateSquadra(id, null);
                                }
                            }
                        }
                        return new RedirectResult("/RegistrazioneGrest/App/Squadre?target=ter");
                    }
                    break;
            }
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerSquadre.class.getName());
        }
        return template;
    }
}
