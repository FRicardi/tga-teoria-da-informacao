package encodings;

import ecc.Crc;
import ecc.impl.Crc8;
import utility.StringUtils;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Encoder {
    protected final static int CODE_START_INDEX = 3;

    public int getDivider() {
        return 0;
    }

    abstract public int getCode();

    public byte[] encodeText(String text) {
        List<Integer> asciiValue = text.chars().mapToObj(c -> (char) c).map(character -> (int) character).collect(Collectors.toList());
        List<String> encodedChars = asciiValue.stream().map(this::encodeNumber).collect(Collectors.toList());
        return byteStringListToByteArray(addHeadersToByteList(encodedChars));
    }

    protected List<String> addHeadersToByteList(List<String> byteList) {
        Crc crc = new Crc8();
        String code = StringUtils.getByteFromNumber(this.getCode());
        String divider = StringUtils.getByteFromNumber(this.getDivider());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(this.getCode() & 0xFF);
        baos.write(this.getDivider() & 0xFF);
        byteList.add(0, code);
        byteList.add(1, divider);
        byteList.add(2, StringUtils.getByteFromNumber(crc.checksumResult(baos.toByteArray())));
        return byteList;
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

    public String decode(byte[] buffer) {
        return decode(StringUtils.concatByteArrayWithOffset(buffer, CODE_START_INDEX));
    }
}
