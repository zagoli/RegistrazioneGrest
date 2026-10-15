package DAOManager;

import DAOManager.Mappers.ExportDAOMapper;
import DAOManager.Mappers.PagamentoDAOMapper;
import Domain.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapperExtractionTest {
    @Test
    void paymentsPreserveParticipantIdsAndRegistrationPrefixes() throws Exception {
        Map<String, Object> columns = new HashMap<>(Map.of(
                "pid", 11, "pdata", Date.valueOf("2026-06-01"), "pquota", 51.5f,
                "pragid", 12, "pterid", 13, "pordineArrivo", 14,
                "customid", 15, "customnome", " Nome "));
        Registrato registrato = new Registrato(15, null, null, " Nome ", null,
                null, null, null, null, 0);
        assertEquals(new Pagamento(11, Date.valueOf("2026-06-01"), 51.5f, 12, registrato, 14),
                PagamentoDAOMapper.mapRowToPagamento(row(columns), "custom"));
        assertEquals(new PagamentoTerzamedia(11, Date.valueOf("2026-06-01"), 51.5f, 13, registrato, 14),
                PagamentoDAOMapper.mapRowToPagamentoTerzamedia(row(columns), "custom"));
        columns.put("reid", 15);
        columns.put("renome", " Nome ");
        assertEquals(PagamentoDAOMapper.mapRowToPagamento(row(columns), "custom"),
                PagamentoDAOMapper.mapRowToPagamento(row(columns)));
        assertEquals(PagamentoDAOMapper.mapRowToPagamentoTerzamedia(row(columns), "custom"),
                PagamentoDAOMapper.mapRowToPagamentoTerzamedia(row(columns)));
    }

    @Test
    void exportsPreserveEachRecordComponentForPopulatedAndNullableRows() throws Exception {
        for (boolean populated : List.of(true, false)) {
            Map<String, Object> columns = new HashMap<>();
            columns.put("presenza", " M ");
            columns.put("fasciaEtaRagazzi", " TER ");
            if (populated) {
                columns.putAll(Map.of("nome", " Nome ", "cognome", "Cognome",
                        "dataNascita", Date.valueOf("2012-02-03"), "settimane", "1,3",
                        "nTessera", " tessera ", "sezione", " A ", "classe", " 3 ",
                        "pagamento_id", 7, "pagamento_quota", new BigDecimal("52.50")));
                columns.putAll(Map.of("codiceFiscale", "CF", "mail", "mail@test.invalid",
                        "cellulare", " 123 ", "cellularePersonale", " 456 ",
                        "entrataAnticipata", true, "mensa", true, "saNuotare", true,
                        "fratelloIscritto", true, "festaPassaggio", true,
                        "isResponsabileLaboratorio", true));
                columns.put("isResponsabileSquadra", true);
            }
            String nome = populated ? " Nome " : "";
            String cognome = populated ? "Cognome" : "";
            Date date = populated ? Date.valueOf("2012-02-03") : null;
            String flag = populated ? "si" : "no";
            String tessera = populated ? "tessera" : "";
            String sezione = populated ? "A" : "";
            String mail = populated ? "mail@test.invalid" : "";
            List<Integer> settimane = populated ? List.of(1, 3) : List.of();
            BigDecimal quota = populated ? new BigDecimal("52.50") : BigDecimal.ZERO;
            Laboratorio laboratorio = new Laboratorio(0, "", false);
            Parrocchia parrocchia = new Parrocchia(0, "", "");
            Circolo circolo = new Circolo(0, "", "");
            Scuola scuola = new Scuola(0, "", "");
            Squadra squadra = new Squadra(0, "", "");
            Registrato registrato = new Registrato(0, "", null, "", "", "", "", "", "", null);
            assertEquals(new RagazzoExport(nome, cognome, date, "mattina", laboratorio,
                    parrocchia, registrato, circolo, flag, "", "", flag, flag, flag, scuola,
                    tessera, sezione, populated ? "3" : "", squadra, settimane, flag, quota),
                    ExportDAOMapper.mapRowToRagazzoExport(row(columns)));
            assertEquals(new TerzaMediaExport(nome, cognome, date, "mattina", laboratorio,
                    scuola, parrocchia, circolo, populated ? "123" : "", "", "", flag, flag,
                    sezione, tessera, mail, squadra, settimane, registrato, flag, quota),
                    ExportDAOMapper.mapRowToTerzamediaExport(row(columns)));
            assertEquals(new AnimatoreExport(nome, cognome, date, populated ? "CF" : "",
                    populated ? "456" : "", mail, parrocchia, circolo, tessera, settimane,
                    "mattina", "terza media", laboratorio, flag, squadra, flag, registrato),
                    ExportDAOMapper.mapRowToAnimatoreExport(row(columns)));
        }
    }

    private static ResultSet row(Map<String, Object> columns) {
        return (ResultSet) Proxy.newProxyInstance(ResultSet.class.getClassLoader(),
                new Class<?>[]{ResultSet.class}, (proxy, method, arguments) -> {
                    Object value = arguments == null ? null : columns.get(arguments[0]);
                    return switch (method.getName()) {
                        case "getString" -> (String) value;
                        case "getInt" -> value == null ? 0 : ((Number) value).intValue();
                        case "getFloat" -> value == null ? 0f : ((Number) value).floatValue();
                        case "getBoolean" -> value != null && (Boolean) value;
                        case "getDate", "getBigDecimal", "getObject" -> value;
                        default -> throw new UnsupportedOperationException(method.getName());
                    };
                });
    }
}
