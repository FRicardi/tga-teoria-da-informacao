package filehandling.impl;

import filehandling.FileHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class DefaultFileHandler implements FileHandler {

    private File rootDir;
    private File outputDir;
    private File inputDir;
    private Scanner scanner;

    private final String OUTPUT_PATH = "/output";
    private final String INPUT_PATH = "/input";

    public DefaultFileHandler() {
        rootDir = (new File("")).getAbsoluteFile();
        outputDir = (new File(rootDir.getAbsolutePath() + OUTPUT_PATH));
        inputDir = (new File(rootDir.getAbsolutePath() + INPUT_PATH));
        scanner = new Scanner(System.in);
    }

    @Override
    public void writeToFile(File file, byte[] buffer) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file, false)) {
            fos.write(buffer);
        }
    }

    @Override
    public void writeToFile(File file, String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        }
    }

    @Override
    public byte[] readByteArrayFromFile(File file) throws IOException {
       return Files.readAllBytes(Paths.get(file.getAbsolutePath()));
    }

    @Override
    public String readStringFromFile(File file) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
        return lines.stream().reduce("", (s, s2) -> s + "\n" + s2);
    }

    @Override
    public String[] getInputFiles() {
        return inputDir.list();
    }

    @Override
    public File selectInputFile() {
        String[] pathFiles = getInputFiles();
        System.out.println("Output files:");

        for (int i = 0; i < pathFiles.length; i++) {
            System.out.println((i + 1) + " - " + pathFiles[i] + "\n");
        }


        System.out.println("Choose a file.");
        int choice = scanner.nextInt();

        while(choice > pathFiles.length || choice < 1) {
            System.out.println("Please, select a valid file.");
            choice = scanner.nextInt();
        }


        return new File(inputDir.getAbsolutePath() + "/" + pathFiles[choice - 1]);
    }

}
