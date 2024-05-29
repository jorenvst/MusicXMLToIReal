package converter.tests;

import converter.ireal.IRealProDocument;
import converter.musicxml.MusicXMLConverter;
import org.junit.Test;

public class MusicXMLTest {

    private final MusicXMLConverter converter = new MusicXMLConverter();

    @Test
    public void testConverter() {
        for (IRealProDocument doc : converter.convert("/home/joren/Documents/MuseScore4/Scores/Cissy Strut.musicxml")) {
            System.out.println(doc);
        }
    }

    @Test
    public void testConverterPart() {
        System.out.println(converter.convertPart("/home/joren/Documents/MuseScore4/Scores/Cissy Strut.musicxml"));
        System.out.println(converter.convertPart("/home/joren/Documents/MuseScore4/Scores/Cissy Strut.musicxml", 0));
    }
}
