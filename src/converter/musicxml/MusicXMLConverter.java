package converter.musicxml;

import converter.ireal.Document;
import converter.music.Chord;
import converter.music.Measure;
import converter.music.Song;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * MusicXMLConverter --- class for converting a song to an IReal Pro document
 */
public class MusicXMLConverter {

    private final MusicXMLReader reader = new MusicXMLReader();

    /**
     * convert a file to an IReal Pro document
     * @param path the path to the musicxml file that needs to be converted
     * @return a new IReal Pro Document
     */
    public Document convert(String path) {
        // read the song from the musicxml file
        Song song = reader.readSong(path);
        return new Document(songToURL(song), song.getTitle());
    }

    /**
     * convert a song to a valid IReal Pro url
     * @param song the song that needs to be converted
     * @return the valid IReal Pro url
     */
    private String songToURL(Song song) {
        try (InputStream in = this.getClass().getResourceAsStream("/resources/time.properties")) {

            Properties time = new Properties();
            time.load(in);

            // if the key signature doesn't exist in IReal Pro, throw an exception
            if (!time.containsKey(song.getTime().toString())) {
                throw new RuntimeException("This time signature is invalid for IReal Pro");
            }

            // build the url
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

    /**
     * translates measures of a song into the IReal Pro format
     * @param measures the measures of the song that need translating
     * @return a String of the measures in IReal Pro format
     * @throws IOException when chords.properties can not be read
     */
    private String translate(List<Measure> measures) throws IOException {
        try (InputStream in = this.getClass().getResourceAsStream("/resources/chords.properties")) {

            Properties chords = new Properties();
            StringBuilder builder = new StringBuilder();
            chords.load(in);

            for (Measure measure : measures) {
                if (!measure.isImplicit()) {
                    builder.append("|");
                    if (measure.getChords().isEmpty()) {
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
