package converter.musicxml;

import converter.ireal.IRealProDocument;
import converter.music.Chord;
import converter.music.Measure;
import converter.music.Song;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * MusicXMLConverter --- class for converting a song to an IReal Pro document
 */
public class MusicXMLConverter {

    private final MusicXMLReader reader = new MusicXMLReader();
    private final Set<String> validQualities;
    private final Properties time;
    private final Properties chords;

    public MusicXMLConverter() {
        try {
            Document document = new SAXBuilder().build(this.getClass().getResourceAsStream("/resources/valid-alterations.xml"));
            validQualities = document.getRootElement().getChildren("element")
                    .stream().map(Element::getText).collect(Collectors.toSet());
        } catch (IOException | JDOMException e) {
            throw new RuntimeException("Could not read valid-alterations.xml", e);
        }

        try (InputStream in = this.getClass().getResourceAsStream("/resources/time.properties")) {
            time = new Properties();
            time.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Could not read time.properties", e);
        }

        try (InputStream in = this.getClass().getResourceAsStream("/resources/chords.properties")) {
            chords = new Properties();
            chords.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Could not read chords.properties", e);
        }
    }

    /**
     * convert a file to an IReal Pro document
     * @param path the path to the musicxml file that needs to be converted
     * @return a new IReal Pro Document
     */
    public List<IRealProDocument> convert(String path) {
        // read the song from the musicxml file
        List<Song> songs = reader.readSong(path);
        List<IRealProDocument> documents = new ArrayList<>();
        for (Song song : songs) {
            documents.add(new IRealProDocument(songToURL(song), song.getTitle()));
        }
        return documents;
    }

    /**
     * convert a song to a valid IReal Pro url
     * @param song the song that needs to be converted
     * @return the valid IReal Pro url
     */
    private String songToURL(Song song) {
        // if the key signature doesn't exist in IReal Pro, throw an exception
        if (!time.containsKey(song.getTime().toString())) {
            throw new RuntimeException("This time signature is invalid for IReal Pro");
        }
        // build the url
        return "irealbook://" +
                song.getTitle() + "=" +                                                 // title
                song.getComposerLastName() + " " + song.getComposerFirstName() + "=" +  // composer
                "Undefined" + "=" +                                                     // style
                song.getKey() + "=" + "n=" +                                            // key
                time.getProperty(song.getTime().toString()) +                           // time signature
                translate(song.getMeasures());                                          // chord progression
    }

    /**
     * translates measures of a song into the IReal Pro format
     * @param measures the measures of the song that need translating
     * @return a String of the measures in IReal Pro format
     */
    private String translate(List<Measure> measures) {

        StringBuilder builder = new StringBuilder();

        for (Measure measure : measures) {
            if (!measure.isImplicit()) {

                builder.append("|");
                if (measure.getChords().isEmpty()) {
                    builder.append("x ");
                }

                for (Chord chord : measure.getChords()) {
                    StringBuilder iRealChord = new StringBuilder();
                    iRealChord.append(chord.root());

                    StringBuilder quality = new StringBuilder();
                    quality.append(chords.getProperty(chord.kind()));
                    for (String alteration : chord.alterations()) {
                        quality.append(alteration);
                    }

                    // TODO: checks if this exact order is valid, but you need to check all the orders
                    if (isValid(quality.toString())) {
                        iRealChord.append(quality);
                    } else {
                        throw new RuntimeException(quality + "is an invalid quality for IReal Pro");
                    }

                    if (chord.hasBass()) {
                        iRealChord.append("/").append(chord.bass());
                    }

                    builder.append(iRealChord);
                    builder.append(" ");
                }
            }
        }
        builder.append("Z");
        return builder.toString();
    }

    private boolean isValid(String quality) {
        return validQualities.contains(quality);
    }
}
