package utility;

import java.io.File;

public class FileUtils {

    private static final String PATH_SEPARATOR = "/";
    private static final String EXT_SEPARATOR = ".";

    public static File createFileFromFileNamePathAndExt(File file, String pathName, String ext) {
        File path = new File("");
        StringBuilder sb = new StringBuilder();
        sb.append(path.getAbsolutePath());
        sb.append(PATH_SEPARATOR);
        sb.append(pathName);
        sb.append(PATH_SEPARATOR);
        String fileName = file.getName().substring(0, file.getName().indexOf(EXT_SEPARATOR));
        sb.append(fileName);
        sb.append(EXT_SEPARATOR);
        sb.append(ext);
        return new File(sb.toString());
    }


}
