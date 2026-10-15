package DAOManager;

import DAOManager.Mappers.IscrittoDAOMapper;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import regression.Baseline;
import regression.DomainRegressionTest;

import java.lang.reflect.Proxy;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/** These mapper calls do not initialize DAOMan or open a connection. */
public class JdbcRowRegressionTest {
    @FunctionalInterface
    interface Mapper { Object read(ResultSet rs) throws Exception; }

    @TestFactory
    Stream<DynamicTest> participantRowMapping() {
        return Stream.of("ragazzo", "terzamedia", "animatore").flatMap(kind ->
                Stream.of(false, true).map(nullable -> DynamicTest.dynamicTest(kind + "-nullable-" + nullable, () -> {
                    List<String> calls = new ArrayList<>();
                    ResultSet rs = (ResultSet) Proxy.newProxyInstance(getClass().getClassLoader(),
                            new Class<?>[]{ResultSet.class}, (proxy, method, args) -> {
                                String column = args == null ? "" : String.valueOf(args[0]);
                                calls.add(method.getName() + "(" + column + ")");
                                boolean missing = nullable && (column.startsWith("sq") || column.endsWith("Tessera")
                                        || column.endsWith("richieste") || column.endsWith("noteAlimentari")
                                        || column.endsWith("cellulare"));
                                return switch (method.getName()) {
                                    case "getInt" -> missing ? 0 : 1 + Math.floorMod(column.hashCode(), 100);
                                    case "getString" -> missing ? null : "fixture-" + column;
                                    case "getBoolean" -> !nullable;
                                    case "getDate" -> java.sql.Date.valueOf("2010-01-02");
                                    case "wasNull" -> missing;
                                    default -> throw new UnsupportedOperationException(method.getName());
                                };
                            });
                    Object mapped = switch (kind) {
                        case "ragazzo" -> IscrittoDAOMapper.mapRowToRagazzo(rs);
                        case "terzamedia" -> IscrittoDAOMapper.mapRowToTerzamedia(rs);
                        default -> IscrittoDAOMapper.mapRowToAnimatore(rs);
                    };
                    Baseline.verify("jdbc/" + kind + "-" + nullable, DomainRegressionTest.describe(mapped));
                })));
    }
}
