package encodings.impl;

import encodings.Encoder;

import enumerators.EncodersEnum;
import mappings.EncoderMapping;
import utility.StringUtils;
import utility.MathUtils;

public class GolombEncoder extends Encoder {

    private final int DIVIDER = 64;
    private final String REPEAT_BIT = "1";
    private final String STOP_BIT = "0";



    @Override
    public int getDivider() {
        return DIVIDER;
    }

    @Override
    public int getCode() {
        EncoderMapping em = new EncoderMapping();
        return em.getEncoderData(EncodersEnum.GOLOMB).getCode();
    }

    @Override
    public String encodeNumber(int number) {

        int suffixLength = (int) MathUtils.customLog(2, DIVIDER);

        int suffix = number % DIVIDER;

        int prefix = number / DIVIDER;

        String prefixBinary = StringUtils.createStringSequence(REPEAT_BIT, prefix) + STOP_BIT;

        String suffixBinary = Integer.toBinaryString(suffix);
        if (suffixBinary.length() < suffixLength) {
            suffixBinary = StringUtils.createStringSequence("0", suffixLength - suffixBinary.length()) + suffixBinary;
        }

        return prefixBinary + suffixBinary;

    }

    @Override
    public String decode(String text) {
        return this.decode(text, 0);
    }

    private String decode(String text, int divider) {
        StringBuilder decoded = new StringBuilder();

        if (divider == 0) {
            divider = DIVIDER;
        }

        while(!text.isEmpty() && text.contains("1")) {
            int prefixCounter = 0;
            char[] splitString = text.toCharArray();

            int suffixLength = (int) MathUtils.customLog(2, divider);

            while (splitString[prefixCounter] == (REPEAT_BIT.charAt(0))) {
                prefixCounter++;
            }

            int prefix = prefixCounter * divider;

            int suffixStart = text.indexOf(STOP_BIT) + 1;
            int x = Integer.parseInt(text.substring(suffixStart, suffixStart + suffixLength), 2);
            text = text.substring(suffixStart + suffixLength);
            decoded.append(Character.toChars(prefix + x));
            System.out.print(Character.toChars(prefix + x));
        }

        return decoded.toString();
    }

    @Override
    public String decode(byte[] buffer) {
        int divider = buffer[0];
        return decode(StringUtils.concatByteArrayWithOffset(buffer, CODE_START_INDEX), divider);
    }


}
