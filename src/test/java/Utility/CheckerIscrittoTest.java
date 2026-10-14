package Utility;

import Domain.Animatore;
import Domain.Iscritto;
import Domain.Ragazzo;
import Domain.Registrato;
import Domain.TerzaMedia;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckerIscrittoTest {
    @Test
    void permissionPolicyIsTheSameForEachSubtype() {
        Registrato owner = new Registrato();
        owner.setId(12);
        for (Iscritto person : List.of(new Ragazzo(), new TerzaMedia(), new Animatore())) {
            person.setRegistrato(owner);
            assertTrue(Checker.canModifyIscritto(person, 99, 0));
            assertTrue(Checker.canModifyIscritto(person, 99, 1));
            assertFalse(Checker.canModifyIscritto(person, 12, 2));
            assertTrue(Checker.canModifyIscritto(person, 12, 3));
            assertFalse(Checker.canModifyIscritto(person, 99, 3));
            assertFalse(Checker.canModifyIscritto(person, 12, 4));
        }
    }

    @Test
    void missingUserOrRecordDoesNotCreateAnOwnerPermission() {
        assertThrows(NullPointerException.class, () -> Checker.canModifyIscritto(null, 12, 3));
        assertThrows(NullPointerException.class, () -> Checker.canModifyIscritto(new Ragazzo(), 12, 3));
        assertThrows(NullPointerException.class, () -> Checker.canModifyIscritto(new Ragazzo(), 12, null));
    }

    @Test
    void optionalLocalitaClearedIsEquivalentToEmptyLocalitaForFees() {
        assertFalse(Checker.checkIsFromPescantina(null));
        assertFalse(Checker.checkIsFromPescantina(""));
        assertFalse(Checker.checkIsFromPescantina("Verona"));
        assertTrue(Checker.checkIsFromPescantina("  BALCONI  "));
        assertTrue(Checker.checkIsFromPescantina("Arcè"));
    }
}
