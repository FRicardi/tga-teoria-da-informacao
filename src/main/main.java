package main;

import encodings.Encoder;
import encodings.impl.DeltaEncoder;
import encodings.impl.EliasGammaEncoder;
import encodings.impl.FibonacciEncoder;
import encodings.impl.GolombEncoder;
import enumerators.EncodersEnum;
import enumerators.OperationsEnum;
import filehandling.FileHandler;
import filehandling.impl.DefaultFileHandler;
import mappings.EncoderMapping;
import pojos.EncoderData;
import utility.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static enumerators.OperationsEnum.*;

public class main {



    public static void main(String[] args) throws IOException {
//        StringBuilder a = new StringBuilder();
//        a.append((char) 0);
//        a.append((char) 250);
//        a.append((char) );
//        File path = new File("");
//
//        FileHandler fileHandler = new DefaultFileHandler();
//
//        Encoder encoder = new DeltaEncoder();
//
//        byte[] encodedText = encoder.encodeText(a.toString());
//        File output = new File(path.getAbsolutePath() + "/output/test.cod");
//
//        fileHandler.writeToFile(output, encodedText);
//
//        encoder.decode(encoder.encodeText(a.toString()));
//

        FlowControls fc = new FlowControls();
        OperationsEnum option = fc.selectOperation();
        EncoderMapping em = new EncoderMapping();
        FileHandler fileHandler = new DefaultFileHandler();

        switch (option) {
            case DECODE:
                File encodedFile = fileHandler.selectEncodedFile();
                byte[] fileByteArray = fileHandler.readByteArrayFromFile(encodedFile);
                EncoderData decoder = em.getEncoderFromCode(fileByteArray[0]);
                File decodedOutputFile = FileUtils.createFileFromFileNamePathAndExt(encodedFile, "output", "dec");
                fileHandler.writeToFile(decodedOutputFile, decoder.getEncoder().decode(fileByteArray));
                break;
            case ENCODE:
                EncoderData encoder = fc.selectEncoder();
                File textFile = fileHandler.selectTextFile();
                File encodedOutputFile = FileUtils.createFileFromFileNamePathAndExt(textFile, "output", "cod");
                byte[] encodedText = encoder.getEncoder().encodeText(fileHandler.readStringFromFile(textFile));
                fileHandler.writeToFile(encodedOutputFile, encodedText);
                break;
        }

//
//
////        Encoder encoder = new EliasGammaEncoder();
//        Encoder encoder = new GolombEncoder();
//
//
//
//        String aliceBook = "";
//        File path = new File("");
//        File inputFile = fileHandler.selectInputFile();
//        String fileText = fileHandler.readStringFromFile(inputFile);
//
//        byte[] encodedText = encoder.encodeText(fileText);
//        File output = new File(path.getAbsolutePath() + "/output/alice29.cod");
//
//        fileHandler.writeToFile(output, encodedText);
//
//        byte[] array = fileHandler.readByteArrayFromFile(new File(path.getAbsolutePath() + "/output/alice29.cod"));
//
////        String decodedText = encoder.decode(array);
//
//        String a = "";
//        for (byte b : array) {
//            a += "" + String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
//        }
////
//        String decodedText = encoder.decode(a);
////
//        File decodedOutput = new File(path.getAbsolutePath() + "/output/alice29.dec");
//        fileHandler.writeToFile(decodedOutput, decodedText);
    }
}
