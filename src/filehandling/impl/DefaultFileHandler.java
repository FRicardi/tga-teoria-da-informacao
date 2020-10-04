package filehandling.impl;

import filehandling.FileHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class DefaultFileHandler implements FileHandler {

    private File rootDir;
    private File outputDir;
    private File inputDir;
    private Scanner scanner;

    private final String OUTPUT_PATH = "/output";
    private final String INPUT_PATH = "/input";
    private final String TXT_EXT = ".txt";
    private final String COD_EXT = ".cod";

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
    public String[] getTextFiles() {
        return getDirFilesByDirAndExtension(inputDir, TXT_EXT);
    }

    @Override
    public String[] getEncodedFiles() {
        String[] inputDirFiles = appendPathToFiles(getDirFilesByDirAndExtension(inputDir, COD_EXT), INPUT_PATH) ;
        String[] outputDirFiles = appendPathToFiles(getDirFilesByDirAndExtension(outputDir, COD_EXT), OUTPUT_PATH);

        return combineArrays(inputDirFiles, outputDirFiles);

    }

    private String[] appendPathToFiles(String[] files, String dirPath) {
        for (int i = 0; i < files.length; i ++) {
            files[i] = dirPath + "/" + files[i];
        }

        return files;
    }

    private String[] getDirFilesByDirAndExtension(File dir, String extension) {
        return Arrays.stream(Objects.requireNonNull(dir.list())).filter(s -> s.endsWith(extension)).toArray(String[]::new);
    }

    @Override
    public File selectEncodedFile() {
        String[] pathFiles = getEncodedFiles();

        return selectFileFromOptions(pathFiles, rootDir);
    }

    @Override
    public File selectTextFile() {
        String[] pathFiles = getTextFiles();

        return selectFileFromOptions(pathFiles, inputDir);
    }

    private File selectFileFromOptions(String[] files, File dir) {
        System.out.println("Files:");

        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + " - " + files[i] + "\n");
        }


        System.out.println("Choose a file.");
        int choice = scanner.nextInt();

        while(choice > files.length || choice < 1) {
            System.out.println("Please, select a valid file.");
            choice = scanner.nextInt();
        }


        return new File(dir.getAbsolutePath() + "/" + files[choice - 1]);

    }

    private static String[] combineArrays(String[] a, String[] b){
        int length = a.length + b.length;
        String[] result = new String[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

}
