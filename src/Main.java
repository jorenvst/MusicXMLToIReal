import converter.ireal.Document;
import converter.musicxml.MusicXMLConverter;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        MusicXMLConverter converter = new MusicXMLConverter();
        for (String path : args) {
            List<Document> docs = converter.convert(path);
            for (Document doc : docs) {
                doc.build(path);
            }
        }
    }
}
