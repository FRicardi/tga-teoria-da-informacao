package main;

import encodings.Encoder;
import encodings.impl.EliasGammaEncoder;
import filehandling.FileHandler;
import filehandling.impl.DefaultFileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class main {

    public static void main(String[] args) throws IOException {
        FileHandler fileHandler = new DefaultFileHandler();
        Encoder encoder = new EliasGammaEncoder();

        String aliceBook = "";
        File path = new File("");
        File inputFile = fileHandler.selectInputFile();
        String fileText = fileHandler.readStringFromFile(inputFile);

        byte[] encodedText = encoder.encodeText(fileText);
        File output = new File(path.getAbsolutePath() + "/output/alice29.cod");

        fileHandler.writeToFile(output, encodedText);


        byte[] array = fileHandler.readByteArrayFromFile(new File(path.getAbsolutePath() + "/output/alice29.cod"));

        String decodedText = encoder.decode(array);

        File decodedOutput = new File(path.getAbsolutePath() + "/output/alice29.dec");
        fileHandler.writeToFile(decodedOutput, decodedText);
    }
}
