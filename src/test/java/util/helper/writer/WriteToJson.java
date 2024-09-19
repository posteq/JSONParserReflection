package util.helper.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToJson {
    public static void write(String pathName , String file){
        try {
            File outputGson = new File("src/test/resources/"+pathName);
            FileWriter fileWriterGson = new FileWriter(outputGson);
            fileWriterGson.write(file);
            fileWriterGson.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
