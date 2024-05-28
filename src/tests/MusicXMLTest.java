package tests;

import converter.ireal.IRealProDocument;
import converter.musicxml.MusicXMLConverter;
import converter.musicxml.MusicXMLReader;
import org.junit.*;

public class MusicXMLTest {

    private final MusicXMLReader reader = new MusicXMLReader();
    private final MusicXMLConverter converter = new MusicXMLConverter();

    @Test
    public void testReader() {
        System.out.println(reader.readSong("/home/joren/Documents/MuseScore4/Scores/Funkallero.musicxml"));
    }

    @Test
    public void testConverter1() {
        System.out.println(converter.convert("/home/joren/Documents/MuseScore4/Scores/Funkallero.musicxml"));
    }

    @Test
    public void testConverter2() {
        System.out.println(converter.convert("/home/joren/Documents/MuseScore4/Scores/Cissy Strut.musicxml"));
    }

    @Test
    public void testConverterMultipleParts() {
        for (IRealProDocument doc : converter.convert("/home/joren/Documents/Music/Sheet music/JamZ/mscz/3. Footprints Eb.musicxml")) {
            System.out.println(doc);
        }
    }

    @Test
    public void testConverter3() {
        for (IRealProDocument doc : converter.convert("/home/joren/Documents/Music/Sheet music/JamZ/mscz/14. There is no greater love Eb.musicxml")) {
            System.out.println(doc);
        }
    }

}
