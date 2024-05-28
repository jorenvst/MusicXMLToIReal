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

public class MusicXMLReader {

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
                            MusicXMLBoolean.parseBoolean(measure.getAttributeValue("implicit"))
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
