package converter.musicxml;

import converter.ireal.Document;
import converter.music.Chord;
import converter.music.Measure;
import converter.music.Song;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

// TODO: add support for    - staff text
//                          - styles?
//                          - bar lines
//                          - chord extensions and alterations

public class MusicXMLConverter {

    private final MusicXMLReader reader = new MusicXMLReader();

    public Document convert(String path) {
        Song song = reader.readSong(path);
        return new Document(songToURL(song), song.getTitle());
    }

    public String songToURL(Song song) {

        try (InputStream in = this.getClass().getResourceAsStream("/resources/time.properties")) {

            Properties time = new Properties();
            time.load(in);

            if (!time.containsKey(song.getTime().toString())) {
                throw new RuntimeException("This time signature is invalid for IReal Pro");
            }

            return "irealbook://" +
                    song.getTitle() + "=" +                                                 // title
                    song.getComposerLastName() + " " + song.getComposerFirstName() + "=" +  // composer
                    "undefined" + "=" +                                                     // style
                    song.getKey() + "=" + "n=" +                                            // key
                    time.getProperty(song.getTime().toString()) +                           // time signature
                    translate(song.getMeasures());                                          // chord progression

        } catch (IOException e) {
            throw new RuntimeException("Could not translate the song into a valid IReal Pro url", e);
        }
    }

    private String translate(List<Measure> measures) throws IOException {
        try (InputStream in = this.getClass().getResourceAsStream("/resources/chords.properties")) {

            Properties chords = new Properties();
            StringBuilder builder = new StringBuilder();
            chords.load(in);

            for (int i = 0; i < measures.size(); i++) {
                Measure measure = measures.get(i);

                if (!measure.isImplicit()) {
                    builder.append("|");
                    if (measure.getChords().isEmpty()) {
                        // if measure is empty, repeat previous chord
                        // TODO: change to play last chord
                        builder.append(" x ");
                    }

                    for (Chord chord : measure.getChords()) {
                        builder.append(chord.getRoot());
                        builder.append(chords.getProperty(chord.getKind()));
                        builder.append(" ");
                    }
                }
            }
            builder.append("Z");
            return builder.toString();
        }
    }
}
