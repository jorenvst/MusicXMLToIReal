package converter.music;

/**
 * Chord --- represents a chord in music
 * created when musicxml file is read
 */
public class Chord {

    private final String root;
    private final String kind;

    public Chord(String root, String kind) {
        this.root = root;
        this.kind = kind;
    }

    public String getRoot() {
        return root;
    }

    public String getKind() {
        return kind;
    }

    @Override
    public String toString() {
        return root + " " + kind;
    }
}
