import converter.ireal.Document;
import converter.musicxml.MusicXMLConverter;

public class Main {

    public static void main(String[] args) {
        MusicXMLConverter converter = new MusicXMLConverter();
        for (String path : args) {
            // parse the converter.musicxml file and output it to an converter.ireal pro html doc
            Document doc = converter.convert(path);
            doc.build(path);
        }
    }
}
