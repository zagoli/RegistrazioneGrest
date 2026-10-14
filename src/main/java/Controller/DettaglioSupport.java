package Controller;

import DAOManager.DAOMan;
import Domain.Accompagnatore;
import Domain.AttivitaGen;
import Domain.ContattoUrgenze;
import Response.FreemarkerTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

final class DettaglioSupport {
    private DettaglioSupport() {
    }

    static void contacts(FreemarkerTemplate template, int registratoId, boolean accompagnatori) throws SQLException {
        emergencyContacts(template, registratoId, "cu");
        if (accompagnatori) {
            accompagnatori(template, registratoId);
        }
    }

    static void emergencyContacts(FreemarkerTemplate template, int registratoId, String key) throws SQLException {
        List<ContattoUrgenze> contatti = DAOMan.contattoUrgenzeDAO.findByRegistratoId(registratoId);
        if (!contatti.isEmpty()) {
            template.addObject(key, contatti);
        }
    }

    static void accompagnatori(FreemarkerTemplate template, int registratoId) throws SQLException {
        List<Accompagnatore> lista = DAOMan.accompagnatoreDAO.findByRegistratoId(registratoId);
        if (!lista.isEmpty()) {
            template.addObject("accompagnatori", lista);
        }
    }

    static void activities(FreemarkerTemplate template, int registratoId, boolean distinct) throws SQLException {
        List<AttivitaGen> activities = DAOMan.attivitaGenDAO.findByRegistratoId(registratoId);
        template.addObject("attgen", distinct ? activities.stream().distinct().collect(Collectors.toList()) : activities);
    }
}
