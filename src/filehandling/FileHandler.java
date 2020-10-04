package filehandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileHandler {

    void writeToFile(File file, byte[] buffer) throws IOException;

    void writeToFile(File file, String text) throws IOException;

    byte[] readByteArrayFromFile(File file) throws IOException;

    String readStringFromFile(File file) throws IOException;

    String[] getTextFiles();

    String[] getEncodedFiles();

    File selectEncodedFile();

    File selectTextFile();

}
