package Controller;

import DAOManager.DAOMan;
import Domain.Pagamento;
import Domain.Ragazzo;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.Checker;
import Utility.ConfigProperties;
import Utility.ConfigPropertyException;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ControllerPagamentiRagazzi implements ControllerInterface {

    protected static float calcolaQuota(Ragazzo r) throws SQLException, ConfigPropertyException, IOException {
        int[][][] tabellaQuotaBase = new int[][][]{
                // normale
                {   //senza mensa   |   con mensa
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_1_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_1_MENSA_RAGAZZI"))},    // una settimana
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_2_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_2_MENSA_RAGAZZI"))},   // due settimane
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_3_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_3_MENSA_RAGAZZI"))},   // tre settimane
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_4_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_4_MENSA_RAGAZZI"))}    // quattro settimane
                },
                // fratello iscritto
                {   //senza mensa   |   con mensa
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_1_FRATELLI_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_1_FRATELLI_MENSA_RAGAZZI"))},    // una settimana
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_2_FRATELLI_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_2_FRATELLI_MENSA_RAGAZZI"))},   // due settimane
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_3_FRATELLI_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_3_FRATELLI_MENSA_RAGAZZI"))},   // tre settimane
                        {Integer.parseInt(ConfigProperties.getProperty("PREZZO_4_FRATELLI_RAGAZZI")), Integer.parseInt(ConfigProperties.getProperty("PREZZO_4_FRATELLI_MENSA_RAGAZZI"))}    // quattro settimane
                }
        };
        int supplementoFuoriComune = Integer.parseInt(ConfigProperties.getProperty("SUPPLEMENTO_FUORI_COMUNE_RAGAZZI"));
        int supplementoEntrataAnticipataRagazzi = Integer.parseInt(ConfigProperties.getProperty("SUPPLEMENTO_ENTRATA_ANTICIPATA_RAGAZZI"));
        int nSettimane = DAOMan.relPresenzaRagDAO.findByRagazzoId(r.getId()).size();
        return tabellaQuotaBase[r.getFratelloIscritto() ? 1 : 0][nSettimane - 1][r.getMensa() ? 1 : 0] +
                nSettimane * (r.getEntrataAnticipata() ? supplementoEntrataAnticipataRagazzi : 0) +
                nSettimane * (Checker.checkIsFromPescantina(r.getRegistrato().getLocalita()) ? 0 : supplementoFuoriComune);
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("tipoUt", (Integer) request.getSession().getAttribute("tipoUtente"));
            mv.addObject("TITOLOPAGINA", "Gestisci pagamenti ragazzi");
            if (request.getParameterMap().isEmpty()) {
                // pagamento contiene solo l'id del ragazzo, quindi sono stupido e non si può migliorare
                List<Ragazzo> listRagazzo = DAOMan.ragazzoDAO.findAll();
                List<Pagamento> pagamenti = DAOMan.pagamentoDAO.findAll();
                Set<Object[]> datiRagazzi = new HashSet<>();
                if (!listRagazzo.isEmpty()) {
                    for (Ragazzo ragazzo : listRagazzo) {
                        Optional<Pagamento> p = pagamenti.parallelStream().filter(pag -> pag.getRagazzoId() == ragazzo.getId()).findFirst();
                        if (p.isPresent()) {
                            Object[] o = {ragazzo, true, p.get()};
                            datiRagazzi.add(o);
                        } else {
                            Object[] o = {ragazzo, false, ControllerPagamentiRagazzi.calcolaQuota(ragazzo)};
                            datiRagazzi.add(o);
                        }
                    }
                    mv.addObject("ragazzi", datiRagazzi);
                }
                mv.setView("ammseg/gestiscipagamenti.html");
            } else if (request.getParameterMap().containsKey("addPagamento")) {
                float quota = Float.parseFloat(request.getParameter("quota").replace(',', '.'));
                int idRagazzo = Integer.parseInt(request.getParameter("addPagamento"));
                int idUt = (int) request.getSession().getAttribute("idUtente");
                DAOMan.pagamentoDAO.insert(Integer.parseInt(request.getParameter("ordineArrivo")), quota, idRagazzo, idUt);
                response.sendRedirect("/RegistrazioneGrest/App/GestisciPagamenti");
            } else if (request.getParameterMap().containsKey("deletePagamento")) {
                int id = Integer.parseInt(request.getParameter("deletePagamento"));
                DAOMan.pagamentoDAO.delete(id);
                response.sendRedirect("/RegistrazioneGrest/App/GestisciPagamenti");
            }
        } catch (final RuntimeException | IOException | SQLException | ConfigPropertyException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerPagamentiRagazzi.class.getName());
        }
        return mv;
    }

}
