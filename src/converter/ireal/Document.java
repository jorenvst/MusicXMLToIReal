package converter.ireal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Document {

    private final String content;

    public Document(String url, String songTitle) {
        this.content = "<a href=\"" + url + "\">" + songTitle + "</a>";
    }

    public void build(String path) {
        try {
            String fileName = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf(".")) + ".html";
            File irealFile = new File(fileName);

            if (irealFile.createNewFile()) {
                try (FileWriter writer = new FileWriter(irealFile)) {
                    writer.write(content);
                    System.out.println(fileName + " created successfully");
                }
            } else {
                System.out.println(fileName + " already exists in this directory");
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create file", e);
        }
    }

    @Override
    public String toString() {
        return this.content;
    }
}
