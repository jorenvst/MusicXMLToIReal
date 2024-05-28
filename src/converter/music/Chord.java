package converter.music;

import java.util.ArrayList;
import java.util.List;

/**
 * Chord --- represents a chord in music
 * created when musicxml file is read
 */
public class Chord {

    private final String root;
    private final String kind;
    private final String bass;
    private final List<String> alterations;

    /**
     *
     * @param root required, the root note of the chord
     * @param kind required, the quality of the chord, e.g. maj7
     * @param alterations optional, alterations or extensions to the chord e.g. #11, b5
     * @param bass optional, the inversion of the chord or over what bass note the chord should be played
     */
    public Chord(String root, String kind, List<String> alterations, String bass) {
        this.root = root;
        this.kind = kind;
        this.bass = bass;
        this.alterations = alterations;
    }

    public Chord(String root, String kind, List<String> alterations) {
        this(root, kind, alterations, null);
    }

    public Chord(String root, String kind, String bass) {
        this(root, kind, new ArrayList<>(), bass);
    }

    public Chord(String root, String kind) {
        this(root, kind, new ArrayList<>(), null);
    }

    public String getRoot() {
        return root;
    }

    public String getKind() {
        return kind;
    }

    public String getBass() {
        return bass;
    }

    public List<String> getAlterations() {
        return new ArrayList<>(alterations);
    }

    @Override
    public String toString() {
        if (bass == null) {
            return root + " " + kind;
        } else {
            return root + " " + kind + "/" + bass;
        }
    }
}
