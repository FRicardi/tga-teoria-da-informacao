package encodings.impl;

import encodings.Encoder;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import enumerators.EncodersEnum;
import mappings.EncoderMapping;
import utility.StringUtils;
import utility.MathUtils;
public class EliasGammaEncoder extends Encoder {
    private final String STOP_BIT = "1";
    private final String UNARY_BIT = "0";

    @Override
    public int getCode() {
        EncoderMapping em = new EncoderMapping();
        return em.getEncoderData(EncodersEnum.ELIAS_GAMMA).getCode();
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
        return decodeText(StringUtils.concatByteArrayWithOffset(buffer, 2), "");
    }

    public String decodeText(String text, String decoded) {
        if (text.isEmpty() || !text.contains(STOP_BIT)) {
            return decoded;
        }
        int unaryPart = text.indexOf(STOP_BIT);

        int binaryPart = Integer.parseInt(text.substring(unaryPart + 1, unaryPart * 2 + 1), 2);

        decoded += new String(Character.toChars(binaryPart + (int) Math.pow(2, unaryPart)));

        return decodeText(text.substring(unaryPart*2 + 1), decoded);
    }

}
