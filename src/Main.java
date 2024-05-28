import converter.ireal.Document;
import converter.musicxml.MusicXMLConverter;

public class Main {

    public static void main(String[] args) {
        MusicXMLConverter converter = new MusicXMLConverter();
        for (String path : args) {
            Document doc = converter.convert(path);
            doc.build(path);
        }
    }
}
