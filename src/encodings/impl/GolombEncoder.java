package encodings.impl;

import encodings.Encoder;

import utility.StringUtils;
import utility.MathUtils;

public class GolombEncoder extends Encoder {

    private final int DIVIDER = 10;
    private final String REPEAT_BIT = "0";
    private final String STOP_BIT = "1";

    @Override
    public String encodeNumber(int number) {
        int quocient = (int) number/DIVIDER;
        int remainder = number % DIVIDER;

        String quocientBinary = StringUtils.createStringSequence(REPEAT_BIT, quocient) + STOP_BIT;

        int b = (int) MathUtils.customLog(2, DIVIDER);

        int remainderBinaryLength = b;
        int lengthEquation = (int) Math.pow(2, b+1) - DIVIDER;
        if (b >= lengthEquation) {
            remainderBinaryLength += 1;
            remainder += lengthEquation;
        }

        String remainderBinary = Integer.toBinaryString(remainder);

        if(remainderBinary.length() < remainderBinaryLength) {
            remainderBinary = StringUtils.createStringSequence("0", remainderBinaryLength - remainderBinary.length()) + remainderBinary;
        }

        return quocientBinary + remainderBinary;
    }

    @Override
    public String decode(String text) {
        int repeatCounter = 0;
        char[] splitString = text.toCharArray();
        if(text.isEmpty() || text.indexOf("1") == -1) {
            return "";
        }

        double bits = (Math.log(DIVIDER) / Math.log(2.0));
        int treshNumber = (int)(Math.pow(2, Math.floor(bits) + 1) - DIVIDER);
        boolean powerTwo = Math.floor(bits) == bits;

        int b = (int) MathUtils.customLog(2, DIVIDER);
        int remainderBinaryLength = b;
        int remainder = 0;
        int lengthEquation = (int) Math.pow(2, b+1) - DIVIDER;
        if (b >= lengthEquation) {
            remainderBinaryLength += 1;
        }
        if (remainder >= 10) {
            remainder -= 10;
            repeatCounter++;
        }
        while(splitString[repeatCounter] == (REPEAT_BIT.charAt(0))){
            repeatCounter++;
        }
//
        int startingIndex = text.indexOf(STOP_BIT) + 1;
        String currentSubstring = text.substring(startingIndex, startingIndex + remainderBinaryLength);

//        String test = text.substring(0, startingIndex + remainderBinaryLength);

        remainder += Integer.parseInt(currentSubstring, 2);

        String nextString = text.substring(startingIndex + remainderBinaryLength + 1);
        return new String(Character.toChars(Integer.parseInt(repeatCounter + "" + remainder) + DIVIDER)) + decode(nextString);
    }

    @Override
    public String decode(byte[] buffer) {
        StringBuilder decoded = new StringBuilder();
        for (byte b : buffer) {
            double bits = (Math.log(DIVIDER) / Math.log(2.0));
            int treshNumber = (int)(Math.pow(2, Math.floor(bits) + 1) - DIVIDER);
            boolean powerTwo = Math.floor(bits) == bits;

            int qPart = 0;
            int rPart = 0;
            int j = 1;
            while ((j <= b) && ((b & j) == 1)) {
                qPart++;
                j <<= 1;
            }

            for (int x = 0; x < (Math.floor(bits)); x++) {
                j <<= 1;
                rPart = rPart << 1 | b & j;
            }

            if (!powerTwo && rPart >= treshNumber) {
                j <<= 1;
                rPart = rPart << 1 | b & j;
                rPart = (int) (rPart - treshNumber);
            }


            decoded.append((char) (qPart * DIVIDER + rPart));
        }


        return decoded.toString();
    }


}
