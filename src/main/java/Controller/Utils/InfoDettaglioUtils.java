package Controller.Utils;

import DAOManager.DAOMan;
import Domain.Accompagnatore;
import Domain.ContattoUrgenze;
import Response.FreemarkerTemplate;

import java.sql.SQLException;
import java.util.List;

public final class InfoDettaglioUtils {

    private InfoDettaglioUtils() {
    }

    public static void popolaCu(FreemarkerTemplate template, int registratoId) throws SQLException {
        List<ContattoUrgenze> contatti = DAOMan.contattoUrgenzeDAO.findByRegistratoId(registratoId);
        if (!contatti.isEmpty()) {
            template.addObject("cu", contatti);
        }
    }

    public static void popolaAccompagnatori(FreemarkerTemplate template, int registratoId) throws SQLException {
        List<Accompagnatore> accompagnatori = DAOMan.accompagnatoreDAO.findByRegistratoId(registratoId);
        if (!accompagnatori.isEmpty()) {
            template.addObject("accompagnatori", accompagnatori);
        }
    }
}
