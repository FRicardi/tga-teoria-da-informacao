package encodings.impl;

import encodings.Encoder;
import utility.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeltaEncoder extends Encoder {

    private final static int ENCODED_LENGTH = 8;

    @Override
    public int getCode() {
        return 4;
    }
    @Override
    public byte[] encodeText(String text) {
        List<Integer> asciiValue = text.chars().mapToObj(c -> (char) c).map(character -> (int) character).collect(Collectors.toList());

        List<String> encodedChars = new ArrayList<>();

        int last = 0;

        for (int i = 0; i < asciiValue.size(); i ++) {
            encodedChars.add(this.encodeNumber(asciiValue.get(i) - last));
            last = asciiValue.get(i);
        }

        encodedChars.add(0, StringUtils.getByteFromNumber(this.getCode()));
        encodedChars.add(1, StringUtils.getByteFromNumber(this.getDivider()));
        return super.byteStringListToByteArray(encodedChars);
    }

    @Override
    public String encodeNumber(int number) {
        String encoded = Integer.toBinaryString(number & 0xFF);
        int length = encoded.length();

        return length < ENCODED_LENGTH ? StringUtils.createStringSequence("0", ENCODED_LENGTH - length) + encoded : encoded;
    }

    @Override
    public String decode(String text) {
        byte[] buffer = text.getBytes();

        return this.decode(buffer);
    }

    @Override
    public String decode(byte[] buffer) {

        StringBuilder decodedBuffer = new StringBuilder();

        int last = 0;
        decodedBuffer.append((char) buffer[CODE_START_INDEX]);
        for (int i = CODE_START_INDEX; i < buffer.length; i++) {
            int delta =  buffer[i] + last;
            decodedBuffer.append((char) (delta < 0 ? delta + 256 : delta));
            last = buffer[i] + last;
        }
        return decodedBuffer.toString();
    }
}
