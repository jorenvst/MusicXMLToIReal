package music;

import java.util.List;

// TODO move time signature to measure so it can vary during the song
public class Song {

    private final String title;
    private final String composer;
    private final Time time;
    private final String key;
    private final List<Measure> measures;

    public Song(String title, String composer, Time time, String key, List<Measure> measures) {
        this.title = title;
        this.composer = composer;
        this.time = time;
        this.key = key;
        this.measures = measures;
    }

    public String getTitle() {
        return title;
    }

    public String getComposer() {
        return composer;
    }

    public String getComposerFirstName() {
        return composer.substring(0, composer.indexOf(" ")).trim();
    }

    public String getComposerLastName() {
        return composer.substring(composer.indexOf(" ")).trim();
    }

    public Time getTime() {
        return time;
    }

    public String getKey() {
        return key;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nComposer: " + composer + "\nTime: " + time + "\nKey: " + key + "\n" + measures;
    }
}
