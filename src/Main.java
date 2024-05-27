import ireal.Document;
import musicxml.MusicXMLConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        MusicXMLConverter converter = new MusicXMLConverter();
        for (String path : args) {
            // parse the musicxml file and output it to an ireal pro html doc
            Document doc = converter.convert(path);
            doc.build(path);
        }
    }
}
