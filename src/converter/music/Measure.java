package converter.music;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Measure --- represents a measure in music
 * created when musicxml file is read
 */
public class Measure {

    private final List<Chord> chords;
    private final boolean implicit;

    public Measure(Collection<Chord> chords) {
        this(chords, false);
    }

    public Measure(Collection<Chord> chords, boolean implicit) {
        this.chords = new ArrayList<>(chords);
        this.implicit = implicit;
    }

    public List<Chord> getChords() {
        return chords;
    }

    public boolean isImplicit() {
        return implicit;
    }

    @Override
    public String toString() {
        return chords.toString();
    }
}
