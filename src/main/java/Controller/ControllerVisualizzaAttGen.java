package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import Domain.RelCollabora;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ControllerVisualizzaAttGen implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("tipoUt", (Integer) request.getSession().getAttribute("tipoUtente"));
            mv.setView("ammseg/visualizzaattgen.html");
            mv.addObject("TITOLOPAGINA", "Attivita genitori");
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
            mv.addObject("attivita", parametri);
        } catch (final RuntimeException | SQLException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerVisualizzaAttGen.class.getName());
        }
        return mv;
    }

}
