package converter.musicxml;

import converter.music.Chord;
import converter.music.Measure;
import converter.music.Song;
import converter.music.Time;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * MusicXMLReader --- class for reading musicxml files and convert them to the Song class
 */
public class MusicXMLReader {

    /**
     * read a song from a musicxml file
     * @param path the path to the musicxml file
     * @return a new Song object
     */
    public Song readSong(String path) {
        try (InputStream in = this.getClass().getResourceAsStream("/resources/keys.properties")) {

            Properties keys = new Properties();
            keys.load(in);
            SAXBuilder builder = new SAXBuilder();

            // ignore deprecated dtd
            builder.setEntityResolver(new IgnoreDTDEntityResolver());

            Document musicDoc = builder.build(new File(path));

            String title = musicDoc.getRootElement().getChild("work").getChildText("work-title");
            String composer = musicDoc.getRootElement().getChild("identification").getChildText("creator");

            Time time = new Time(
                    Integer.parseInt(musicDoc.getRootElement().getChild("part").getChild("measure").getChild("attributes").getChild("time").getChildText("beats")),
                    Integer.parseInt(musicDoc.getRootElement().getChild("part").getChild("measure").getChild("attributes").getChild("time").getChildText("beat-type"))
            );

            String key = keys.getProperty(musicDoc.getRootElement().getChild("part").getChild("measure").getChild("attributes").getChild("key").getChildText("fifths"));

            List<Measure> measures = musicDoc.getRootElement().getChild("part").getChildren("measure").stream().map(measure -> {
                if (measure.getAttribute("implicit") != null) {
                    return new Measure(
                            measure.getChildren("harmony").stream().map(
                                    chord -> new Chord(chord.getChild("root").getChildText("root-step"), chord.getChildText("kind"))
                            ).toList(),
                            measure.getAttributeValue("implicit").equals("yes")
                    );
                } else {
                    return new Measure(measure.getChildren("harmony").stream().map(
                            chord -> new Chord(chord.getChild("root").getChildText("root-step"), chord.getChildText("kind"))
                    ).toList());
                }
            }).toList();

            return new Song(title, composer, time, key, measures);
        } catch (JDOMException | IOException e) {
            throw new RuntimeException("Could not parse the converter.musicxml file", e);
        }
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
