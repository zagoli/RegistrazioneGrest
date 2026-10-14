package Domain;

import org.junit.jupiter.api.Test;

import java.beans.Introspector;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IscrittoInheritanceTest {
    @Test
    void commonPropertiesBelongToAnAbstractSuperclass() throws Exception {
        assertTrue(Modifier.isAbstract(Iscritto.class.getModifiers()));
        for (Class<?> type : List.of(Ragazzo.class, TerzaMedia.class, Animatore.class)) {
            assertEquals(Iscritto.class, type.getSuperclass());
            for (String field : List.of("id", "nome", "cognome", "dataNascita", "presenza",
                    "laboratorio", "parrocchia", "registrato", "circolo", "nTessera", "squadra")) {
                assertNotNull(Iscritto.class.getDeclaredField(field));
                assertTrue(Arrays.stream(type.getDeclaredFields()).noneMatch(f -> f.getName().equals(field)));
            }
        }
    }

    @Test
    void inheritedAccessorsExposeTheSameValuesThroughEachSubtype() {
        Date birth = new Date(1_000);
        for (Iscritto person : List.of(new Ragazzo(), new TerzaMedia(), new Animatore())) {
            person.setId(7);
            person.setNome("Nome");
            person.setCognome("Cognome");
            person.setDataNascita(birth);
            person.setPresenza("M");
            person.setNTessera("12345678901");
            assertEquals(7, person.getId());
            assertEquals("Nome", person.getNome());
            assertEquals("Cognome", person.getCognome());
            assertSame(birth, person.getDataNascita());
            assertEquals("M", person.getPresenza());
            assertEquals("12345678901", person.getNTessera());
            person.setNTessera(null);
            assertNull(person.getNTessera());
        }
    }

    @Test
    void equalityIncludesCommonFieldsAndKeepsSubtypesDistinct() {
        Ragazzo left = new Ragazzo();
        Ragazzo right = new Ragazzo();
        left.setNome("Nome");
        right.setNome("Nome");
        assertEquals(left, right);
        assertEquals(left.hashCode(), right.hashCode());
        right.setNTessera("12345678901");
        assertNotEquals(left, right);
        right.setNTessera(null);
        right.setMensa(true);
        assertNotEquals(left, right);

        TerzaMedia thirdYear = new TerzaMedia();
        thirdYear.setNome("Nome");
        Animatore animator = new Animatore();
        animator.setNome("Nome");
        assertNotEquals(left, thirdYear);
        assertNotEquals(thirdYear, left);
        assertNotEquals(thirdYear, animator);
        assertNotEquals(animator, thirdYear);
    }

    @Test
    void tesseraKeepsItsJavaBeanPropertyName() throws Exception {
        for (Class<?> type : List.of(Ragazzo.class, TerzaMedia.class, Animatore.class)) {
            var descriptor = Arrays.stream(Introspector.getBeanInfo(type).getPropertyDescriptors())
                    .filter(property -> property.getName().equals("NTessera")).findFirst().orElseThrow();
            assertEquals("getNTessera", descriptor.getReadMethod().getName());
            assertEquals("setNTessera", descriptor.getWriteMethod().getName());
        }
    }
}
