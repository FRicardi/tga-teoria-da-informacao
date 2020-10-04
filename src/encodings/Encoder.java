package encodings;

import utility.StringUtils;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Encoder {

    public int getDivider() {
        return 0;
    }

    abstract public int getCode();

    public byte[] encodeText(String text) {
        List<Integer> asciiValue = text.chars().mapToObj(c -> (char) c).map(character -> (int) character).collect(Collectors.toList());

        List<String> encodedChars = asciiValue.stream().map(this::encodeNumber).collect(Collectors.toList());
        encodedChars.add(0, StringUtils.getByteFromNumber(this.getCode()));
        encodedChars.add(1, StringUtils.getByteFromNumber(this.getDivider()));
        return byteStringListToByteArray(encodedChars);
    }

    protected byte[] byteStringListToByteArray(List<String> byteStringList) {
        String joinedString = byteStringList.stream().reduce(String::concat).get();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        String[] str = joinedString.split("(?<=\\G.{8})");

        for(String cur : str) {
            byte b = 0b00000000;
            for(int i = 0; i < cur.length(); i++) {
                if(cur.charAt(i) == '1') {
                    b = (byte) ((0b1 << (7-i)) | b);
                }
            }
            buffer.write(Byte.toUnsignedInt(b));
        }

        return buffer.toByteArray();
    }

    abstract public String encodeNumber(int number);

    abstract public String decode(String text);

    abstract public String decode(byte[] buffer);
}
