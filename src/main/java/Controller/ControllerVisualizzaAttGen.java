package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import Domain.RelCollabora;
import ModelAndView.FreemarkerTemplate;
import ModelAndView.Response;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ControllerVisualizzaAttGen implements ControllerInterface {

    @Override
    public Response handleRequest(HttpServletRequest request, HttpServletResponse response) {
        FreemarkerTemplate template = new FreemarkerTemplate();
        try {
            template.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            template.setView("ammseg/visualizzaattgen.html");
            template.addObject("TITOLOPAGINA", "Attivita genitori");
            List<RelCollabora> allRelCollabora = DAOMan.relCollaboraDAO.findAll();
            List<Object[]> parametri = new LinkedList<>();
            for (RelCollabora relCollabora : allRelCollabora) {
                Registrato r = DAOMan.registratoDAO.findById(relCollabora.getRegistratoId());
                String nome = r.getNome();
                String cognome = r.getCognome();
                String data = relCollabora.getData();
                String descrizione = DAOMan.attivitaGenDAO.findById(relCollabora.getAttivitaGenId()).getDescrizione();
                Object[] datiAttivita = {nome, cognome, data, descrizione};
                parametri.add(datiAttivita);
            }
            template.addObject("attivita", parametri);
        } catch (final RuntimeException | SQLException e) {
            template = Utils.getErrorPageAndLogException(e, ControllerVisualizzaAttGen.class.getName());
        }
        return template;
    }

}
