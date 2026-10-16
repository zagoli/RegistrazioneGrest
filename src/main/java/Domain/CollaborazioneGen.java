package Domain;

import java.util.Comparator;
import java.util.Objects;

public record CollaborazioneGen(int id, AttivitaGen attivitaGen,
                                String periodo) implements Comparable<CollaborazioneGen> {

    @Override
    public int compareTo(CollaborazioneGen other) {
        var thisDescrizione = this.attivitaGen.getDescrizione();
        var otherDescrizione = other.attivitaGen.getDescrizione();
        if (thisDescrizione.compareTo(otherDescrizione) != 0)
            return this.attivitaGen.getDescrizione().compareTo(other.attivitaGen.getDescrizione());
        return Objects.compare(this.periodo, other.periodo, Comparator.nullsLast(Comparator.naturalOrder()));
    }

}
