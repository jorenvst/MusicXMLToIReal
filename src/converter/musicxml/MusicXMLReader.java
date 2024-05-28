package converter.musicxml;

import converter.music.Chord;
import converter.music.Measure;
import converter.music.Song;
import converter.music.Time;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * MusicXMLReader --- class for reading musicxml files and convert them to the Song class
 */
public class MusicXMLReader {

    /**
     * read parts from a partwise musicxml file
     * @param path the path to the musicxml file
     * @return a list of Song objects
     */
    public List<Song> readSong(String path) {
        try (InputStream in = this.getClass().getResourceAsStream("/resources/keys.properties")) {

            Properties keys = new Properties();
            keys.load(in);
            SAXBuilder builder = new SAXBuilder();

            // ignore deprecated dtd
            builder.setEntityResolver(new IgnoreDTDEntityResolver());

            Document musicDoc = builder.build(new File(path));

            String title = musicDoc.getRootElement().getChild("work").getChildText("work-title");
            String composer = musicDoc.getRootElement().getChild("identification").getChildText("creator");

            List<Song> songs = new ArrayList<>();
            for (Element part : musicDoc.getRootElement().getChildren("part")) {
                Time time = new Time(
                        Integer.parseInt(part.getChild("measure").getChild("attributes").getChild("time").getChildText("beats")),
                        Integer.parseInt(part.getChild("measure").getChild("attributes").getChild("time").getChildText("beat-type"))
                );

                String key = keys.getProperty(part.getChild("measure").getChild("attributes").getChild("key").getChildText("fifths"));

                List<Measure> measures = part.getChildren("measure").stream().map(this::buildMeasure).toList();

                // only add the song if it has harmony
                if (measures.stream().anyMatch(measure -> !measure.getChords().isEmpty())) {
                    songs.add(new Song(title, composer, time, key, measures));
                }
            }
            return songs;

        } catch (JDOMException | IOException e) {
            throw new RuntimeException("Could not parse the converter.musicxml file", e);
        }
    }

    /**
     * build a measure from musicxml
     * @param measure the element that needs to be converted into a Measure
     * @return a new Measure
     */
    private Measure buildMeasure(Element measure) {
        if (measure.getAttribute("implicit") != null) {
            return new Measure(
                    measure.getChildren("harmony").stream().map(this::buildChord).toList(),
                    measure.getAttributeValue("implicit").equals("yes")
            );
        } else {
            return new Measure(measure.getChildren("harmony").stream().map(this::buildChord).toList());
        }
    }

    /**
     * build a chord from musicxml
     * @param chord the element that needs to be converted into a Chord
     * @return a new Chord
     */
    private Chord buildChord(Element chord) {

        String root = chord.getChild("root").getChildText("root-step");
        String bass = null;
        List<String> alterations = new ArrayList<>();

        try (InputStream in2 = this.getClass().getResourceAsStream("/resources/chords.properties")) {
            Properties chords = new Properties();
            chords.load(in2);

            if (chord.getChild("root").getChild("root-alter") != null) {
                root += chords.getProperty(chord.getChild("root").getChildText("root-alter"));
            }

            if (chord.getChild("bass") != null) {
                bass = chord.getChild("bass").getChildText("bass-step");
                if (chord.getChild("bass").getChild("bass-alter") != null) {
                    bass += chords.getProperty(chord.getChild("bass").getChildText("bass-alter"));
                }
            }

            if (chord.getChild("degree") != null) {
                for (Element degree : chord.getChildren("degree")) {
                    alterations.add(chords.getProperty(degree.getChildText("degree-alter")) + degree.getChildText("degree-value"));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not load chords.properties", e);
        }

        return new Chord(root, chord.getChildText("kind"), alterations, bass);
    }

    /**
     * IgnoreDTDEntityResolver --- class to ignore deprecated links for dtd
     * e.g. when exporting a musescore file to musicxml, the included dtd is nonexistent and deprecated
     */
    static class IgnoreDTDEntityResolver implements EntityResolver {
        @Override
        public InputSource resolveEntity(String publicId, String systemId) {
            if (systemId.contains("partwise.dtd")) {
                return new InputSource(new StringReader(""));
            } else {
                return null;
            }
        }
    }
}
