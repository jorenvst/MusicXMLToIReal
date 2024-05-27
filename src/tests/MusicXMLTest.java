package tests;

import musicxml.MusicXMLConverter;
import musicxml.MusicXMLReader;
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

}
