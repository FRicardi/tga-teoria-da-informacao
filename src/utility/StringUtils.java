package utility;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringUtils {

    static private final int BYTE_SIZE = 8;
    static private final String ZERO = "0";

    public static String createStringSequence(String character, int sequence) {
        return IntStream.range(0, sequence).mapToObj(i -> character).collect(Collectors.joining(""));
    }

    public static String concatByteArrayWithOffset(byte[] byteArray, int offset) {
        StringBuilder builder = new StringBuilder();
        for (int i = offset; i < byteArray.length; i++) {
            builder.append(String.format("%8s", Integer.toBinaryString(byteArray[i] & 0xFF)).replace(' ', '0'));
        }
        return builder.toString();
    }

    public static String getByteFromNumber(int number) {
        String binaryString = Integer.toBinaryString(number);
        int length = binaryString.length();
        if (length < BYTE_SIZE) {
            binaryString = createStringSequence(ZERO, 8 - length) + binaryString;
        }
        return binaryString;
    }
}
