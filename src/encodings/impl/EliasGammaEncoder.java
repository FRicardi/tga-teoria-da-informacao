package encodings.impl;

import encodings.Encoder;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import utility.StringUtils;
import utility.MathUtils;
public class EliasGammaEncoder extends Encoder {
    private final String STOP_BIT = "1";
    private final String UNARY_BIT = "0";

    @Override
    public byte[] encodeText(String text) {
        List<Integer> asciiValue = text.chars().mapToObj(c -> (char) c).map(character -> (int) character).collect(Collectors.toList());

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        List<String> encodedChars = asciiValue.stream().map(this::encodeNumber).collect(Collectors.toList());

        for (String encodedChar : encodedChars) {
            byte[] bytes = (new BigInteger(encodedChar, 2).toByteArray());
            for(byte b : bytes) {
                buffer.write(b);
            }
        }

        return buffer.toByteArray();
    }

    @Override
    public String encodeNumber(int number) {
        String encoded = "";

        int unaryPart = (int)Math.floor(MathUtils.customLog(2, number));

        encoded += StringUtils.createStringSequence(UNARY_BIT, unaryPart);
        encoded += STOP_BIT;

        int binaryPart = number - (int)Math.pow(2,unaryPart);

        String binaryEncoded = Integer.toBinaryString(binaryPart);

        int leadingZeroes = unaryPart - binaryEncoded.length();

        if (leadingZeroes > 0) {
            encoded += StringUtils.createStringSequence("0", leadingZeroes);
        }

        encoded += binaryEncoded;

        return encoded;
    }

    @Override
    public String decode(String text) {
        return decodeText(text, "");
    }

    @Override
    public String decode(byte[] buffer) {
        StringBuilder decoded = new StringBuilder();

        for (byte b : buffer) {
            decoded.append((char) b);
        }

        return decoded.toString();
    }

    public String decodeText(String text, String decoded) {
        if (text.isEmpty()) {
            return decoded;
        }
        int unaryPart = text.indexOf(STOP_BIT);
        int binaryPart = Integer.parseInt(text.substring(unaryPart + 1, unaryPart * 2 + 1), 2);

        decoded += new String(Character.toChars(binaryPart + (int) Math.pow(2, unaryPart)));

        return decodeText(text.substring(unaryPart*2 + 1), decoded);
    }

}
