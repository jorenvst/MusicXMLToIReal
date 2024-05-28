import converter.ireal.IRealProDocument;
import converter.musicxml.MusicXMLConverter;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        MusicXMLConverter converter = new MusicXMLConverter();
        for (String path : args) {
            List<IRealProDocument> docs = converter.convert(path);
            for (IRealProDocument doc : docs) {
                doc.build(path);
            }
        }
    }
}
