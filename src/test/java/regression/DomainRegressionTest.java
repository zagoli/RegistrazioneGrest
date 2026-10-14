package regression;

import Domain.*;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DomainRegressionTest {
    private static final Map<Class<?>, String> PROPERTIES = Map.of(
            Ragazzo.class, "id nome cognome dataNascita presenza laboratorio parrocchia registrato circolo entrataAnticipata richieste noteAlimentari mensa saNuotare fratelloIscritto scuola sezione classe NTessera squadra",
            TerzaMedia.class, "id nome cognome dataNascita presenza laboratorio parrocchia registrato circolo richieste noteAlimentari saNuotare festaPassaggio scuola sezione NTessera squadra cellulare mail",
            Animatore.class, "id nome cognome dataNascita presenza laboratorio parrocchia registrato circolo cellulare fasciaEtaRagazzi mail NTessera squadra codiceFiscale responsabileSquadra responsabileLaboratorio");

    public static Object fixture(Class<?> type) {
        if (type == Ragazzo.class) return Fixtures.ragazzo();
        if (type == TerzaMedia.class) return Fixtures.terzamedia();
        if (type == Animatore.class) return Fixtures.animatore();
        throw new IllegalArgumentException(type.getName());
    }

    public static Map<String, PropertyDescriptor> properties(Class<?> type) throws Exception {
        Map<String, PropertyDescriptor> result = new TreeMap<>();
        for (PropertyDescriptor p : Introspector.getBeanInfo(type, Object.class).getPropertyDescriptors()) {
            if (p.getReadMethod() != null) result.put(p.getName(), p);
        }
        return result;
    }

    public static String describe(Object bean) throws Exception {
        if (bean == null) return "<null>";
        if (bean instanceof Date date) return Long.toString(date.getTime());
        if (!bean.getClass().getPackageName().equals("Domain")) return bean.toString();
        StringBuilder result = new StringBuilder(bean.getClass().getSimpleName()).append("{");
        for (var entry : properties(bean.getClass()).entrySet()) {
            result.append(entry.getKey()).append("=").append(describe(entry.getValue().getReadMethod().invoke(bean))).append(";");
        }
        return result.append("}").toString();
    }

    @TestFactory
    Stream<DynamicTest> constructorsAndBeanContract() {
        return PROPERTIES.entrySet().stream().map(entry -> DynamicTest.dynamicTest(entry.getKey().getSimpleName(), () -> {
            Class<?> type = entry.getKey();
            Object value = fixture(type);
            Map<String, PropertyDescriptor> properties = properties(type);
            assertEquals(new java.util.TreeSet<>(List.of(entry.getValue().split(" "))), properties.keySet());
            Object empty = type.getConstructor().newInstance();
            for (PropertyDescriptor property : properties.values()) {
                assertNotNull(property.getWriteMethod(), property.getName());
                Object expected = property.getReadMethod().invoke(value);
                property.getWriteMethod().invoke(empty, expected);
                assertEquals(expected, property.getReadMethod().invoke(empty), property.getName());
            }
            assertEquals(value, empty);
            assertEquals(value.hashCode(), empty.hashCode());
            assertNotEquals(value, null);
            assertNotEquals(value, new Object());
            Method setter = type.getMethod("setNTessera", String.class);
            setter.invoke(empty, "replacement");
            assertEquals("replacement", type.getMethod("getNTessera").invoke(empty));
            setter.invoke(empty, new Object[]{null});
            assertNull(type.getMethod("getNTessera").invoke(empty));
            Baseline.verify("domains/" + type.getSimpleName(), describe(value));
        }));
    }

    @TestFactory
    Stream<DynamicTest> equalityIncludesEveryExistingField() {
        return PROPERTIES.entrySet().stream().flatMap(entry -> Arrays.stream(entry.getValue().split(" ")).map(name ->
                DynamicTest.dynamicTest(entry.getKey().getSimpleName() + "." + name, () -> {
                    Object original = fixture(entry.getKey());
                    Object changed = fixture(entry.getKey());
                    PropertyDescriptor p = properties(entry.getKey()).get(name);
                    Object current = p.getReadMethod().invoke(changed);
                    Object replacement = current instanceof Boolean b ? !b
                            : current instanceof Integer i ? i + 1
                            : current instanceof String s ? s + "-different"
                            : current instanceof Date d ? new Date(d.getTime() + 86400000) : null;
                    p.getWriteMethod().invoke(changed, replacement);
                    assertNotEquals(original, changed);
                    assertNotEquals(changed, original);
                    assertEquals(original, fixture(entry.getKey()));
                })));
    }

    @TestFactory
    Stream<DynamicTest> differentParticipantTypesAreNotEqual() {
        return Stream.of(Ragazzo.class, TerzaMedia.class, Animatore.class).map(type ->
                DynamicTest.dynamicTest("cross-type-" + type.getSimpleName(), () -> {
                    for (Class<?> other : PROPERTIES.keySet()) if (type != other) {
                        assertNotEquals(type.getConstructor().newInstance(), other.getConstructor().newInstance());
                    }
                }));
    }
}
