package converter.music;

import converter.musicxml.Repetition;

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
    private final String barLineType;
    private final boolean implicit;
    private final Repetition repetition;

    public Measure(Collection<Chord> chords, Time time, String barLineType, boolean implicit, Repetition repetition) {
        this.chords = new ArrayList<>(chords);
        this.time = time;
        if (repetition == Repetition.NONE) {
            this.barLineType = barLineType;
        } else {
            this.barLineType = "regular";
        }
        this.implicit = implicit;
        this.repetition = repetition;
    }

    public List<Chord> getChords() {
        return chords;
    }

    public String getBarLineType() {
        return barLineType;
    }

    public Repetition getRepetition() {
        return repetition;
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
