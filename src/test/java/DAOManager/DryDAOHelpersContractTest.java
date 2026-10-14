package DAOManager;

import Domain.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class DryDAOHelpersContractTest {
    @Test
    void commonMappingRetainsRawNullableValuesAndMissingLeftJoinObject() throws Exception {
        Map<String, Object> columns = new HashMap<>();
        columns.put("raid", 17);
        columns.put("ranome", " Nome ");
        columns.put("racognome", "Cognome");
        columns.put("radataNascita", Date.valueOf("2014-02-03"));
        columns.put("rapresenza", "M");
        columns.put("laid", 2);
        columns.put("ladescrizione", "Laboratorio");
        columns.put("lariservato", true);
        columns.put("paid", 3);
        columns.put("ciid", 4);
        columns.put("reid", 5);
        columns.put("retipoUt", 0);
        columns.put("revia", "");
        Ragazzo ragazzo = RagazzoDAOImpl.mapRowToRagazzo(row(columns));

        assertEquals(17, ragazzo.getId());
        assertEquals(" Nome ", ragazzo.getNome());
        assertEquals(Date.valueOf("2014-02-03"), ragazzo.getDataNascita());
        assertEquals("M", ragazzo.getPresenza());
        assertEquals(2, ragazzo.getLaboratorio().getId());
        assertTrue(ragazzo.getLaboratorio().getRiservato());
        assertEquals(3, ragazzo.getParrocchia().getId());
        assertEquals(4, ragazzo.getCircolo().getId());
        assertEquals(5, ragazzo.getRegistrato().getId());
        assertEquals("", ragazzo.getRegistrato().getVia());
        assertNull(ragazzo.getRegistrato().getTelefono());
        assertNull(ragazzo.getNTessera());
        assertNull(ragazzo.getRichieste());
        assertNull(ragazzo.getNoteAlimentari());
        assertNotNull(ragazzo.getSquadra());
        assertEquals(0, ragazzo.getSquadra().getId());
        assertNull(ragazzo.getSquadra().getNome());
        assertFalse(ragazzo.getEntrataAnticipata());
    }

    @Test
    void tesseraAliasAndSubtypeFieldsRemainDistinct() throws Exception {
        TerzaMedia ter = TerzamediaDAOImpl.mapRowToTerzamedia(row(Map.of(
                "terid", 11, "terTessera", " ter ", "tercellulare", "",
                "terfestaPassaggio", true, "tersaNuotare", true, "scid", 31)));
        Animatore an = AnimatoreDAOImpl.mapRowToAnimatore(row(Map.of(
                "anid", 12, "annTessera", " an ", "ancodiceFiscale", "CF",
                "anisResponsabileSquadra", true, "anisResponsabileLaboratorio", false)));

        assertEquals(" ter ", ter.getNTessera());
        assertEquals("", ter.getCellulare());
        assertTrue(ter.getFestaPassaggio());
        assertTrue(ter.getSaNuotare());
        assertEquals(31, ter.getScuola().getId());
        assertEquals(" an ", an.getNTessera());
        assertEquals("CF", an.getCodiceFiscale());
        assertTrue(an.isResponsabileSquadra());
        assertFalse(an.isResponsabileLaboratorio());
    }

    @Test
    void registratoMappingSupportsAllExistingPrefixesWithoutDisplayNormalization() throws Exception {
        for (String prefix : List.of("", "re", "se", "custom")) {
            Registrato registrato = IscrittoDAOMapper.registrato(row(Map.of(
                    prefix + "id", 7, prefix + "nome", " Nome ",
                    prefix + "password", "password", prefix + "telefono", " 123 ")), prefix);
            assertEquals(7, registrato.getId());
            assertEquals(" Nome ", registrato.getNome());
            assertEquals("password", registrato.getPassword());
            assertEquals(" 123 ", registrato.getTelefono());
            assertNull(registrato.getLocalita());
        }
    }

    @Test
    void ragazzoBindsEveryColumnAndClearsNullableText() throws Exception {
        Ragazzo ragazzo = requiredFields(new Ragazzo());
        ragazzo.setEntrataAnticipata(false);
        ragazzo.setMensa(true);
        ragazzo.setSaNuotare(false);
        ragazzo.setFratelloIscritto(true);
        ragazzo.setScuola(new Scuola(15, null, null));
        ragazzo.setSezione("A");
        ragazzo.setClasse("3");
        ragazzo.setRichieste("old");
        ragazzo.setNoteAlimentari("old");
        ragazzo.setNTessera("old");
        Bindings bindings = new Bindings();
        RagazzoDAOImpl.bindRagazzo(bindings.statement(), ragazzo);
        ragazzo.setRichieste(null);
        ragazzo.setNoteAlimentari(null);
        ragazzo.setNTessera(null);
        bindings.calls.clear();
        RagazzoDAOImpl.bindRagazzo(bindings.statement(), ragazzo);

        assertCommonBindings(bindings);
        bindings.assertBinding(9, "setBoolean", false);
        bindings.assertBinding(10, "setString", null);
        bindings.assertBinding(11, "setString", null);
        bindings.assertBinding(12, "setBoolean", true);
        bindings.assertBinding(13, "setBoolean", false);
        bindings.assertBinding(14, "setBoolean", true);
        bindings.assertBinding(15, "setInt", 15);
        bindings.assertBinding(16, "setString", "A");
        bindings.assertBinding(17, "setString", "3");
        bindings.assertBinding(18, "setString", null);
        assertEquals(18, bindings.calls.size());
    }

    @Test
    void terzamediaBindsNullRequestsNotesTesseraAndCellulareRatherThanSkippingThem() throws Exception {
        TerzaMedia ter = requiredFields(new TerzaMedia());
        ter.setSaNuotare(false);
        ter.setFestaPassaggio(false);
        ter.setScuola(new Scuola(15, null, null));
        ter.setSezione("B");
        ter.setMail("mail");
        Bindings bindings = new Bindings();
        TerzamediaDAOImpl.bindTerzamedia(bindings.statement(), ter);

        assertCommonBindings(bindings);
        for (int index : new int[]{9, 10, 14, 15}) {
            bindings.assertBinding(index, "setString", null);
        }
        bindings.assertBinding(11, "setBoolean", false);
        bindings.assertBinding(12, "setInt", 15);
        bindings.assertBinding(13, "setString", "B");
        bindings.assertBinding(16, "setBoolean", false);
        bindings.assertBinding(17, "setString", "mail");
        assertEquals(17, bindings.calls.size());
    }

    @Test
    void animatoreBindsOriginalIndicesAndNullableTessera() throws Exception {
        Animatore an = requiredFields(new Animatore());
        an.setCellulare("123");
        an.setFasciaEtaRagazzi("PIC");
        an.setMail("mail");
        an.setCodiceFiscale("CF");
        an.setResponsabileSquadra(true);
        Bindings bindings = new Bindings();
        AnimatoreDAOImpl.bindAnimatore(bindings.statement(), an);

        assertCommonBindings(bindings);
        bindings.assertBinding(9, "setString", "123");
        bindings.assertBinding(10, "setString", "PIC");
        bindings.assertBinding(11, "setString", "mail");
        bindings.assertBinding(12, "setString", null);
        bindings.assertBinding(13, "setString", "CF");
        bindings.assertBinding(14, "setBoolean", true);
        bindings.assertBinding(15, "setBoolean", false);
        assertEquals(15, bindings.calls.size());
    }

    @Test
    void nullableContactNumbersAreAlwaysBound() throws Exception {
        ContattoUrgenze contatto = new ContattoUrgenze();
        Registrato registrato = new Registrato();
        registrato.setId(8);
        contatto.setRegistrato(registrato);
        contatto.setNome("Nome");
        contatto.setCognome("Cognome");
        contatto.setRelazione("madre");
        Bindings bindings = new Bindings();
        ContattoUrgenzeDAOImpl.bindContatto(bindings.statement(), contatto);

        bindings.assertBinding(1, "setString", null);
        bindings.assertBinding(2, "setString", null);
        bindings.assertBinding(3, "setString", "Nome");
        bindings.assertBinding(4, "setString", "Cognome");
        bindings.assertBinding(5, "setString", "madre");
        bindings.assertBinding(6, "setInt", 8);
        assertEquals(6, bindings.calls.size());
    }

    @Test
    void accountOptionalValuesBindNullAtBothInsertAndUpdateOffsets() throws Exception {
        Registrato registrato = new Registrato();
        registrato.setNome("Nome");
        registrato.setCognome("Cognome");
        registrato.setTipoUt(0);
        for (int start : new int[]{2, 3}) {
            Bindings bindings = new Bindings();
            RegistratoDAOImpl.bindDatiPersonali(bindings.statement(), registrato, start);
            bindings.assertBinding(start, "setString", "Nome");
            bindings.assertBinding(start + 1, "setString", "Cognome");
            for (int index = start + 2; index <= start + 5; index++) {
                bindings.assertBinding(index, "setString", null);
            }
            bindings.assertBinding(start + 6, "setInt", 0);
            assertEquals(7, bindings.calls.size());
        }
    }

    @Test
    void squadraUsesSqlIntegerNullAndDoesNotConfuseZeroWithAbsence() throws Exception {
        Bindings bindings = new Bindings();
        IscrittoDAOMapper.bindSquadra(bindings.statement(), null);
        bindings.assertBinding(1, "setNull", Types.INTEGER);
        IscrittoDAOMapper.bindSquadra(bindings.statement(), 0);
        bindings.assertBinding(1, "setInt", 0);
    }

    @Test
    void missingPaymentDoesNotReadOtherColumnsAndPresentZeroIdIsNotAbsence() throws Exception {
        ResultSet missing = proxy(ResultSet.class, (p, method, args) -> {
            assertEquals("getObject", method.getName());
            assertEquals("pid", args[0]);
            return null;
        });
        assertNull(PagamentoDAOMapper.riepilogo(missing));
        PagamentoRiepilogo pagamento = PagamentoDAOMapper.riepilogo(row(Map.of(
                "pid", 0, "pordineArrivo", 22, "pdata", Date.valueOf("2026-06-01"),
                "pquota", 15.5f, "prenome", "Segretario", "precognome", "Cognome")));
        assertEquals(new PagamentoRiepilogo(0, 22, Date.valueOf("2026-06-01"), 15.5f,
                "Segretario", "Cognome"), pagamento);
    }

    @Test
    void paymentInsertRetainsFloatAndIntegerBindingOrder() throws Exception {
        Bindings bindings = new Bindings();
        PagamentoDAOMapper.bindInsert(bindings.statement(), 31, 12.5f, 41, 51);
        bindings.assertBinding(1, "setFloat", 12.5f);
        bindings.assertBinding(2, "setInt", 41);
        bindings.assertBinding(3, "setInt", 51);
        bindings.assertBinding(4, "setInt", 31);
    }

    @Test
    void rowCollectorPreservesOrderAndLeavesResourceOwnershipWithCaller() throws Exception {
        AtomicInteger index = new AtomicInteger();
        AtomicBoolean closed = new AtomicBoolean();
        ResultSet rs = proxy(ResultSet.class, (p, method, args) -> switch (method.getName()) {
            case "next" -> index.incrementAndGet() <= 3;
            case "getInt" -> index.get() * 10;
            case "close" -> { closed.set(true); yield null; }
            default -> throw new UnsupportedOperationException(method.getName());
        });
        assertEquals(List.of(10, 20, 30), DAORows.readAll(rs, r -> r.getInt(1)));
        assertFalse(closed.get());
    }

    @Test
    void rowCollectorPreservesMapperSQLExceptionIdentity() {
        SQLException failure = new SQLException("mapping failure");
        ResultSet rs = proxy(ResultSet.class, (p, method, args) -> true);
        assertSame(failure, assertThrows(SQLException.class,
                () -> DAORows.readAll(rs, r -> { throw failure; })));
    }

    @Test
    void weeklyCountsKeepColumnOrderAndSqlNullIntegerSemantics() throws Exception {
        AtomicInteger index = new AtomicInteger();
        ResultSet rs = proxy(ResultSet.class, (p, method, args) -> switch (method.getName()) {
            case "next" -> index.incrementAndGet() <= 1;
            case "getInt" -> (int) args[0] == 1 ? 9 : 0;
            default -> throw new UnsupportedOperationException(method.getName());
        });
        List<Integer[]> counts = DAORows.weeklyCounts(rs);
        assertEquals(1, counts.size());
        assertArrayEquals(new Integer[]{9, 0}, counts.get(0));
    }

    @Test
    void generatedKeysAreClosedAndMissingKeysLeaveExistingIdUnchanged() throws Exception {
        for (boolean present : new boolean[]{false, true}) {
            AtomicBoolean closed = new AtomicBoolean();
            AtomicInteger id = new AtomicInteger(73);
            ResultSet keys = proxy(ResultSet.class, (p, method, args) -> switch (method.getName()) {
                case "next" -> present;
                case "getInt" -> 0;
                case "close" -> { closed.set(true); yield null; }
                default -> throw new UnsupportedOperationException(method.getName());
            });
            PreparedStatement pst = proxy(PreparedStatement.class, (p, method, args) -> {
                assertEquals("getGeneratedKeys", method.getName());
                return keys;
            });
            DAORows.assignGeneratedId(pst, id::set);
            assertEquals(present ? 0 : 73, id.get());
            assertTrue(closed.get());
        }
    }

    @Test
    void generatedKeyReadFailureStillClosesKeysAndPropagatesOriginalException() {
        AtomicBoolean closed = new AtomicBoolean();
        SQLException failure = new SQLException("key read failure");
        ResultSet keys = proxy(ResultSet.class, (p, method, args) -> switch (method.getName()) {
            case "next" -> true;
            case "getInt" -> throw failure;
            case "close" -> { closed.set(true); yield null; }
            default -> throw new UnsupportedOperationException(method.getName());
        });
        PreparedStatement pst = proxy(PreparedStatement.class, (p, method, args) -> keys);
        assertSame(failure, assertThrows(SQLException.class, () -> DAORows.assignGeneratedId(pst, id -> fail())));
        assertTrue(closed.get());
    }

    @Test
    void presenceMappersKeepTheirDifferentConstructorArgumentOrder() throws Exception {
        ResultSet rs = row(Map.of("Calendario_idSettimana", 13, "Animatore_id", 23,
                "Ragazzo_id", 33, "Terzamedia_id", 43));
        RelPresenzaAn an = new RelPresenzaAnDAOImpl().mapRowToRelPresenzaAn(rs);
        RelPresenzaRag rag = new RelPresenzaRagDAOImpl().mapRowToRelPresenzaRag(rs);
        RelPresenzaTer ter = new RelPresenzaTerDAOImpl().mapRowToRelPresenzaTer(rs);
        assertEquals(13, an.getCalendarioId());
        assertEquals(23, an.getAnimatoreId());
        assertEquals(13, rag.getCalendarioId());
        assertEquals(33, rag.getRagazzoId());
        assertEquals(13, ter.getCalendarioId());
        assertEquals(43, ter.getTerzamediaId());
    }

    private static <T extends Iscritto> T requiredFields(T iscritto) {
        iscritto.setNome("Nome");
        iscritto.setCognome("Cognome");
        iscritto.setDataNascita(Date.valueOf("2014-02-03"));
        iscritto.setPresenza("M");
        iscritto.setLaboratorio(new Laboratorio(5, null, false));
        iscritto.setParrocchia(new Parrocchia(6, null, null));
        Registrato registrato = new Registrato();
        registrato.setId(7);
        iscritto.setRegistrato(registrato);
        iscritto.setCircolo(new Circolo(8, null, null));
        return iscritto;
    }

    private static void assertCommonBindings(Bindings bindings) {
        bindings.assertBinding(1, "setString", "Nome");
        bindings.assertBinding(2, "setString", "Cognome");
        bindings.assertBinding(3, "setDate", Date.valueOf("2014-02-03"));
        bindings.assertBinding(4, "setString", "M");
        for (int index = 5; index <= 8; index++) {
            bindings.assertBinding(index, "setInt", index);
        }
    }

    private static ResultSet row(Map<String, Object> columns) {
        return proxy(ResultSet.class, (p, method, args) -> {
            Object value = columns.get(args[0]);
            return switch (method.getName()) {
                case "getInt" -> value == null ? 0 : value;
                case "getFloat" -> value == null ? 0f : value;
                case "getBoolean" -> value != null && (boolean) value;
                case "getString", "getDate", "getObject" -> value;
                default -> throw new UnsupportedOperationException(method.getName());
            };
        });
    }

    private static <T> T proxy(Class<T> type, InvocationHandler handler) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, handler));
    }

    private record Binding(String method, Object value) {
    }

    private static final class Bindings {
        private final Map<Integer, Binding> values = new HashMap<>();
        private final List<Integer> calls = new ArrayList<>();

        PreparedStatement statement() {
            return proxy(PreparedStatement.class, (p, method, args) -> {
                if (!method.getName().startsWith("set")) {
                    throw new UnsupportedOperationException(method.getName());
                }
                int index = (int) args[0];
                calls.add(index);
                values.put(index, new Binding(method.getName(), args[1]));
                return null;
            });
        }

        void assertBinding(int index, String method, Object value) {
            assertEquals(new Binding(method, value), values.get(index), "parameter " + index);
            assertTrue(calls.contains(index), "parameter was not rebound: " + index);
        }
    }
}
