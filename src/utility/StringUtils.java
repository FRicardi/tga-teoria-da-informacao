package utility;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringUtils {

    public static String createStringSequence(String character, int sequence) {
        return IntStream.range(0, sequence).mapToObj(i -> character).collect(Collectors.joining(""));
    }
}
