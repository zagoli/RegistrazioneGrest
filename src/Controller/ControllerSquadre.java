package Controller;

import DAOManager.DAOMan;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class ControllerSquadre implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("TITOLOPAGINA", "Assegnazione Squadre");
            mv.addObject("tipoUt", (Integer) request.getSession().getAttribute("tipoUtente"));
            switch (request.getParameter("target")) {
                case "rag":
                    if (!request.getParameterMap().containsKey("submitted")) {
                        //preparo i dati iniziali per visualizzare la pagina
                        mv.addObject("ragazzi", DAOMan.ragazzoDAO.findAll());
                        mv.addObject("squadre", DAOMan.squadraDAO.findAll());
                        mv.setView("ammseg/squadreragazzi.html");
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
                        response.sendRedirect("/RegistrazioneGrest/App/Squadre?target=rag");
                    }
                    break;
                case "an":
                    if (!request.getParameterMap().containsKey("submitted")) {
                        //preparo i dati iniziali per visualizzare la pagina
                        mv.addObject("animatori", DAOMan.animatoreDAO.findAll());
                        mv.addObject("squadre", DAOMan.squadraDAO.findAll());
                        mv.setView("ammseg/squadreanimatori.html");
                    } else {
                        //salvo le modifiche
                        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
                            if (entry.getKey().matches("\\d+")) { //vedi controller laboratori per spiegazione
                                Integer squadra;
                                String[] value = entry.getValue();
                                //imposto la squadra, se non c'è metto null
                                if (value[0].length() == 0) {
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
                        response.sendRedirect("/RegistrazioneGrest/App/Squadre?target=an");
                    }
                    break;
                case "ter":
                    if (!request.getParameterMap().containsKey("submitted")) {
                        //preparo i dati iniziali per visualizzare la pagina
                        mv.addObject("terzamedia", DAOMan.terzamediaDAO.findAll());
                        mv.addObject("squadre", DAOMan.squadraDAO.findAll());
                        mv.setView("ammseg/squadreterzamedia.html");
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
                        response.sendRedirect("/RegistrazioneGrest/App/Squadre?target=ter");
                    }
                    break;
            }
        } catch (final RuntimeException | IOException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerSquadre.class.getName());
        }
        return mv;
    }
}
