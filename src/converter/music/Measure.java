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
    private final Time time;
    private final boolean implicit;

    public Measure(Collection<Chord> chords) {
        this(chords, null, false);
    }

    public Measure(Collection<Chord> chords, boolean implicit) {
        this(chords, null, implicit);
    }

    public Measure(Collection<Chord> chords, Time time, boolean implicit) {
        this.chords = new ArrayList<>(chords);
        this.implicit = implicit;
        this.time = time;
    }

    public List<Chord> getChords() {
        return chords;
    }

    public Time getTime() {
        return time;
    }

    public boolean hasTime() {
        return time != null;
    }

    public boolean isImplicit() {
        return implicit;
    }

    @Override
    public String toString() {
        return chords.toString();
    }
}
