package Controller;

import DAOManager.DAOMan;
import Domain.DatiPagamento;
import Domain.PagamentoRiepilogo;
import Domain.Ragazzo;
import Domain.RagazzoPagamento;
import ModelAndView.ControllerResult;
import ModelAndView.ModelAndView;
import ModelAndView.RedirectResult;
import ModelAndView.ModelAndViewStandard;
import Utility.Checker;
import Utility.ConfigProperties;
import Utility.ConfigPropertyException;
import Utility.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerPagamentiRagazzi implements ControllerInterface {

    private final static Logger logger = LoggerFactory.getLogger(ControllerPagamentiRagazzi.class);

    protected static float calcolaQuota(Ragazzo r) throws SQLException, ConfigPropertyException, IOException {
        return calcolaQuota(r.getFratelloIscritto(), r.getMensa(), r.getEntrataAnticipata(),
                r.getRegistrato().getLocalita(), DAOMan.relPresenzaRagDAO.findByRagazzoId(r.getId()).size());
    }

    private static float calcolaQuota(RagazzoPagamento r, int nSettimane) throws ConfigPropertyException, IOException {
        return calcolaQuota(r.getFratelloIscritto(), r.getMensa(), r.getEntrataAnticipata(),
                r.getLocalita(), nSettimane);
    }

    private static float calcolaQuota(boolean fratelloIscritto, boolean mensa, boolean entrataAnticipata,
                                     String localita, int nSettimane) throws ConfigPropertyException, IOException {
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
        return tabellaQuotaBase[fratelloIscritto ? 1 : 0][nSettimane - 1][mensa ? 1 : 0] +
                nSettimane * (entrataAnticipata ? supplementoEntrataAnticipataRagazzi : 0) +
                nSettimane * (Checker.checkIsFromPescantina(localita) ? 0 : supplementoFuoriComune);
    }

    @Override
    public ControllerResult handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("tipoUt", request.getSession().getAttribute("tipoUtente"));
            mv.addObject("TITOLOPAGINA", "Gestisci pagamenti ragazzi");
            if (request.getParameterMap().isEmpty()) {
                List<DatiPagamento<RagazzoPagamento, PagamentoRiepilogo>> iscritti = DAOMan.ragazzoDAO.findAllConPagamenti();
                List<Object[]> datiRagazzi = new ArrayList<>(iscritti.size());
                if (!iscritti.isEmpty()) {
                    for (DatiPagamento<RagazzoPagamento, PagamentoRiepilogo> dato : iscritti) {
                        PagamentoRiepilogo pagamento = dato.getPagamento();
                        datiRagazzi.add(new Object[]{
                                dato.getIscritto(),
                                pagamento != null,
                                pagamento != null ? pagamento : calcolaQuota(dato.getIscritto(), dato.getSettimane())
                        });
                    }
                    mv.addObject("ragazzi", datiRagazzi);
                }
                mv.setView("ammseg/gestiscipagamenti.html");
            } else if (request.getParameterMap().containsKey("addPagamento")) {
                float quota = Float.parseFloat(request.getParameter("quota").replace(',', '.'));
                int idRagazzo = Integer.parseInt(request.getParameter("addPagamento"));
                int idUt = (int) request.getSession().getAttribute("idUtente");
                DAOMan.pagamentoDAO.insert(Integer.parseInt(request.getParameter("ordineArrivo")), quota, idRagazzo, idUt);
                logger.info("Pagamento di {} euro aggiunto per il ragazzo con id {}. Ordine arrivo: {}", quota, idRagazzo, request.getParameter("ordineArrivo"));
                return new RedirectResult("/RegistrazioneGrest/App/GestisciPagamenti");
            } else if (request.getParameterMap().containsKey("deletePagamento")) {
                int id = Integer.parseInt(request.getParameter("deletePagamento"));
                DAOMan.pagamentoDAO.delete(id);
                logger.info("Pagamento con id {} eliminato.", id);
                return new RedirectResult("/RegistrazioneGrest/App/GestisciPagamenti");
            }
        } catch (final RuntimeException | IOException | SQLException | ConfigPropertyException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerPagamentiRagazzi.class.getName());
        }
        return mv;
    }

}
