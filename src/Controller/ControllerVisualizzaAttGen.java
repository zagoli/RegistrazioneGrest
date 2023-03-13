package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import Domain.RelCollabora;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerVisualizzaAttGen implements ControllerInterface{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        mv.setView("ammseg/visualizzaattgen.html");
        mv.addObject("TITOLOPAGINA", "Attivita genitori");
        try {
            List<RelCollabora> allRelCollabora = DAOMan.relCollaboraDAO.findAll();
            List<Object[]> parametri = new LinkedList<>();
            for (RelCollabora relCollabora : allRelCollabora) {
                Registrato r = DAOMan.registratoDAO.findById(relCollabora.getRegistratoId());
                String nome = r.getNome();
                String cognome = r.getCognome();
                String data = relCollabora.getData();
                String descrizione = DAOMan.attivitaGenDAO.findById(relCollabora.getAttivitaGenId()).getDescrizione();
                Object[] datiAttivita = {nome,cognome,data,descrizione};
                parametri.add(datiAttivita);
            }
            mv.addObject("attivita", parametri);
        } catch (NullPointerException | SQLException ex) {

            mv.addObject("eccezione", ex);
            Logger.getLogger(ControllerVisualizzaAttGen.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer tipoUt = (Integer) request.getSession().getAttribute("tipoUtente");
        mv.addObject("tipoUt", tipoUt);
        return mv;
    }
    
}
