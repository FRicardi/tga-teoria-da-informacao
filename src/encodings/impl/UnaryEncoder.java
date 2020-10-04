package encodings.impl;

import encodings.Encoder;
import utility.StringUtils;

public class UnaryEncoder extends Encoder {
    private final static String REPEAT_BIT = "1";
    private final static String STOP_BIT = "0";

    @Override
    public int getCode() {
        return 3;
    }

    @Override
    public String encodeNumber(int number) {
        return StringUtils.createStringSequence(REPEAT_BIT, number) + STOP_BIT;
    }

    @Override
    public String decode(String text) {
        StringBuilder decodedBuilder = new StringBuilder();

        do {
            int firstStopBit = text.indexOf(STOP_BIT);
            decodedBuilder.append((char)firstStopBit);
            text = text.substring(firstStopBit + 1);
        } while (text.length() > 0 && text.contains(STOP_BIT));

        return decodedBuilder.toString();
    }

    @Override
    public String decode(byte[] buffer) {
        return decode(StringUtils.concatByteArrayWithOffset(buffer, 2));
    }
}
