package Controller;

import Controller.Utils.SalvataggioLaboratoriSquadreUtils;
import Controller.Utils.IscrizioniUtils;
import Controller.Utils.PresenzeUtils;
import Domain.*;
import Response.FreemarkerTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ControllerDrySupportTest {
    private static HttpServletRequest request(Map<String, String[]> parameters) {
        return (HttpServletRequest) Proxy.newProxyInstance(HttpServletRequest.class.getClassLoader(),
                new Class<?>[]{HttpServletRequest.class}, (proxy, method, arguments) -> switch (method.getName()) {
                    case "getParameterMap" -> parameters;
                    case "getParameterValues" -> parameters.get(arguments[0]);
                    default -> throw new UnsupportedOperationException(method.getName());
                });
    }

    @Test
    void errorStatusPagesKeepTitlesRolesAndHttpStatus() {
        HttpSession session = (HttpSession) Proxy.newProxyInstance(HttpSession.class.getClassLoader(),
                new Class<?>[]{HttpSession.class}, (proxy, method, arguments) -> {
                    assertEquals("getAttribute", method.getName());
                    assertEquals("tipoUtente", arguments[0]);
                    return 2;
                });
        HttpServletRequest request = (HttpServletRequest) Proxy.newProxyInstance(HttpServletRequest.class.getClassLoader(),
                new Class<?>[]{HttpServletRequest.class}, (proxy, method, arguments) -> session);
        AtomicInteger status = new AtomicInteger();
        HttpServletResponse response = (HttpServletResponse) Proxy.newProxyInstance(HttpServletResponse.class.getClassLoader(),
                new Class<?>[]{HttpServletResponse.class}, (proxy, method, arguments) -> {
                    assertEquals("setStatus", method.getName());
                    status.set((Integer) arguments[0]);
                    return null;
                });
        FreemarkerTemplate forbidden = assertInstanceOf(FreemarkerTemplate.class,
                new Controller403().handleRequest(request, response));
        assertEquals(403, status.get());
        assertEquals("403 - Non autorizzato", forbidden.getMap().get("TITOLOPAGINA"));
        assertEquals(2, forbidden.getMap().get("tipoUt"));
        FreemarkerTemplate missing = assertInstanceOf(FreemarkerTemplate.class,
                new Controller404().handleRequest(request, response));
        assertEquals(404, status.get());
        assertEquals("404 - pagina non trovata", missing.getMap().get("TITOLOPAGINA"));
        assertEquals(2, missing.getMap().get("tipoUt"));
    }

    @Test
    void calendarSelectionsKeepOriginalTreeMapOrderingAndEquality() {
        Calendario first = new Calendario(1, new Date(1), new Date(2));
        Calendario second = new Calendario(2, new Date(3), new Date(4));
        Map<Calendario, Boolean> selected = IscrizioniUtils.getSettimaneSelezionate(
                List.of(first, second), List.of(new Calendario(1, new Date(1), new Date(2))));
        assertEquals(List.of(second, first), new ArrayList<>(selected.keySet()));
        assertFalse(selected.get(second));
        assertTrue(selected.get(first));
    }

    @Test
    void replacingPresencesDeletesFirstAndKeepsCalendarOrderAndConstructorDifferences() throws SQLException {
        List<RelPresenzaRag> previous = List.of(new RelPresenzaRag(3, 17), new RelPresenzaRag(1, 17));
        List<String> operations = new ArrayList<>();
        PresenzeUtils.replace(request(Map.of("cal", new String[]{"2", "1"})), previous,
                rel -> operations.add("delete " + rel.getCalendarioId()),
                id -> new RelPresenzaRag(id, 17),
                rel -> operations.add("insert " + rel.getCalendarioId() + ":" + rel.getRagazzoId()));
        assertEquals(List.of("delete 3", "delete 1", "insert 2:17", "insert 1:17"), operations);
        List<RelPresenzaAn> animatori = new ArrayList<>();
        PresenzeUtils.insert(request(Map.of("cal", new String[]{"2", "1"})),
                id -> new RelPresenzaAn(17, id), animatori::add);
        assertEquals(List.of(new RelPresenzaAn(17, 2), new RelPresenzaAn(17, 1)), animatori);
        assertEquals("2 1 ", PresenzeUtils.period(animatori, RelPresenzaAn::getCalendarioId));
        assertEquals("", PresenzeUtils.period(List.<RelPresenzaAn>of(), RelPresenzaAn::getCalendarioId));
    }

    @Test
    void presenceSqlFailuresPropagateUnchangedWithoutFurtherWrites() {
        SQLException expected = new SQLException("write failed");
        AtomicInteger writes = new AtomicInteger();
        SQLException actual = assertThrows(SQLException.class, () -> PresenzeUtils.insert(
                request(Map.of("cal", new String[]{"1", "2"})), id -> new RelPresenzaTer(id, 17),
                rel -> {
                    writes.incrementAndGet();
                    throw expected;
                }));
        assertSame(expected, actual);
        assertEquals(1, writes.get());
    }

    @Test
    void printRowsKeepOriginalTwoElementShapeAndPeriodOrder() throws SQLException {
        Animatore first = new Animatore();
        first.setId(17);
        Animatore second = new Animatore();
        second.setId(18);
        List<Object[]> rows = PresenzeUtils.printRows(List.of(first, second),
                id -> List.of(new RelPresenzaAn(id, 3), new RelPresenzaAn(id, 1)),
                RelPresenzaAn::getCalendarioId, false);
        assertArrayEquals(new Object[]{first, "3 1 "}, rows.get(0));
        assertArrayEquals(new Object[]{second, "3 1 "}, rows.get(1));
    }

    @Test
    void assignmentsPreserveOptionalTeamsAndRequiredLabs() throws SQLException {
        List<String> assignments = new ArrayList<>();
        SalvataggioLaboratoriSquadreUtils.salvataggio(new String[]{"17,2", "18,"}, true,
                (id, assignment) -> assignments.add(id + ":" + assignment));
        assertEquals(List.of("17:2", "18:null"), assignments);
        SalvataggioLaboratoriSquadreUtils.salvataggio(null, false, (id, assignment) -> fail("No submitted assignments"));
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> SalvataggioLaboratoriSquadreUtils.salvataggio(new String[]{"18,"}, false, (id, assignment) -> fail()));
    }

    @Test
    void animatorResponsibilitiesKeepTheirSeparateValidationRules() throws SQLException {
        Map<String, String[]> parameters = new LinkedHashMap<>();
        parameters.put("target", new String[]{"an"});
        parameters.put("17", new String[]{"2", "responsabile"});
        parameters.put("18", new String[]{""});
        List<String> assignments = new ArrayList<>();
        SalvataggioLaboratoriSquadreUtils.salvataggioAnimatori(request(parameters), true,
                (id, assignment, responsible) -> assignments.add(id + ":" + assignment + ":" + responsible));
        assertEquals(List.of("17:2:true", "18:null:false"), assignments);
        assertThrows(IllegalArgumentException.class,
                () -> SalvataggioLaboratoriSquadreUtils.salvataggioAnimatori(request(Map.of("18", new String[]{"", "responsabile"})),
                        true, (id, assignment, responsible) -> fail()));
        assertThrows(NumberFormatException.class,
                () -> SalvataggioLaboratoriSquadreUtils.salvataggioAnimatori(request(Map.of("18", new String[]{""})),
                        false, (id, assignment, responsible) -> fail()));
    }

    @Test
    void paymentRenderingKeepsMixedValueTypesOrderAndSkipsPaidQuoteCalculation() throws Exception {
        PagamentoRiepilogo paid = new PagamentoRiepilogo(1, 2, new Date(3), 4.5f, "Nome", "Cognome");
        List<DatiPagamento<String, PagamentoRiepilogo>> input = List.of(
                new DatiPagamento<>("paid", paid, 1), new DatiPagamento<>("unpaid", null, 3));
        FreemarkerTemplate template = new FreemarkerTemplate();
        AtomicInteger quotes = new AtomicInteger();
        List<Object[]> dati1 = new ArrayList<>(input.size());
        if (!input.isEmpty()) {
            for (DatiPagamento<String, PagamentoRiepilogo> dato1 : input) {
                PagamentoRiepilogo pagamento1 = dato1.getPagamento();
                dati1.add(new Object[]{dato1.getIscritto(), pagamento1 != null,
                        pagamento1 != null ? pagamento1 : ((PagamentiUtils.Quota<String>) (iscritto, weeks) -> {
                            Assertions.assertEquals("unpaid", iscritto);
                            quotes.incrementAndGet();
                            return weeks * 10.5f;
                        }).calculate(dato1.getIscritto(), dato1.getSettimane())});
            }
            template.addObject("ragazzi", dati1);
        }
        template.setView("unused");
        List<?> rows = assertInstanceOf(List.class, template.getMap().get("ragazzi"));
        assertArrayEquals(new Object[]{"paid", true, paid}, assertInstanceOf(Object[].class, rows.get(0)));
        assertArrayEquals(new Object[]{"unpaid", false, 31.5f}, assertInstanceOf(Object[].class, rows.get(1)));
        assertEquals(1, quotes.get());
        FreemarkerTemplate empty = new FreemarkerTemplate();
        List<DatiPagamento<Object, PagamentoRiepilogo>> iscritti = List.of();
        List<Object[]> dati = new ArrayList<>(iscritti.size());
        if (!iscritti.isEmpty()) {
            for (DatiPagamento<Object, PagamentoRiepilogo> dato : iscritti) {
                PagamentoRiepilogo pagamento = dato.getPagamento();
                dati.add(new Object[]{dato.getIscritto(), pagamento != null,
                        pagamento != null ? pagamento : ((PagamentiUtils.Quota<Object>) (iscritto, weeks) -> {
                            Assertions.fail("No quote for an empty list");
                            return 0;
                        }).calculate(dato.getIscritto(), dato.getSettimane())});
            }
            empty.addObject("ragazzi", dati);
        }
        empty.setView("unused");
        assertFalse(empty.getMap().containsKey("ragazzi"));
        List<Object[]> rows1 = new ArrayList<>();
        for (DatiPagamento<String, PagamentoRiepilogo> dato : input) {
            rows1.add(new Object[]{dato.getIscritto(), dato.getPagamento()});
        }
        empty.addObject("ragazzi", rows1);
        List<?> raw = assertInstanceOf(List.class, empty.getMap().get("ragazzi"));
        assertArrayEquals(new Object[]{"paid", paid}, assertInstanceOf(Object[].class, raw.get(0)));
        assertArrayEquals(new Object[]{"unpaid", null}, assertInstanceOf(Object[].class, raw.get(1)));
    }

    @Test
    void duplicatePaymentRerendersButOtherSqlErrorsPropagate() throws Exception {
        FreemarkerTemplate template = new FreemarkerTemplate();
        AtomicInteger renders = new AtomicInteger();
        boolean result = true;
        Logger logger1 = LoggerFactory.getLogger(getClass());
        try {
            ((PagamentiUtils.Insert) () -> {
                throw new SQLException("duplicate", "23505");
            }).apply();
        } catch (SQLException e1) {
            if (e1.getSQLState().equals("23505")) {
                logger1.warn("original message");
                template.addObject("errorePagamento", "original message");
                ((PagamentiUtils.Render) ignored -> renders.incrementAndGet()).apply(template);
                result = false;
            } else {
                throw e1;
            }
        }
        assertFalse(result);
        assertEquals("original message", template.getMap().get("errorePagamento"));
        assertEquals(1, renders.get());
        SQLException expected = new SQLException("failure", "other");
        assertSame(expected, assertThrows(SQLException.class,
                () -> {
                    Logger logger = LoggerFactory.getLogger(getClass());
                    try {
                        ((PagamentiUtils.Insert) () -> {
                            throw expected;
                        }).apply();
                    } catch (SQLException e) {
                        if (e.getSQLState().equals("23505")) {
                            logger.warn("unused");
                            template.addObject("errorePagamento", "unused");
                            ((PagamentiUtils.Render) ignored -> Assertions.fail()).apply(template);
                            return;
                        }
                        throw e;
                    }
                }));
    }
}
