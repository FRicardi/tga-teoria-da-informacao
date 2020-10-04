package ecc.impl;

import ecc.Crc;
import utility.StringUtils;

import java.io.ByteArrayOutputStream;

public class Crc8 implements Crc {
    private static final int CRC8CODE = 263;

    @Override
    public byte checksumResult(byte[] buffer) {
        String code = Integer.toBinaryString(CRC8CODE);
        byte codeByte = (byte) Integer.parseInt(code, 2);
        String bytes = StringUtils.concatByteArrayWithOffset(buffer, 0);
        bytes = bytes.concat(StringUtils.createStringSequence("0", 8));
        int sum = 0;
        int needs = code.length();
        while(needs <= bytes.length()) {
            int firstOneIndex = bytes.indexOf('1');
            String usingBytes = bytes.substring(firstOneIndex, firstOneIndex + needs);
            bytes = bytes.substring(firstOneIndex + needs);
            byte byteUsed = (byte) Integer.parseInt(usingBytes, 2);
            sum = byteUsed ^ codeByte;
            bytes = Integer.toBinaryString(sum & 0xff).concat(bytes);
        }
        String sumString = Integer.toBinaryString(sum);
        String finalSum = sumString.length() == 8 ? sumString : StringUtils.createStringSequence("0", 8 - sumString.length()).concat(sumString);
        int finalSumByte = Integer.parseInt(finalSum, 2);
        return (byte) (finalSumByte < 0 ? finalSumByte + 256 : finalSumByte) ;
    }

    @Override
    public boolean checksum(byte[] buffer) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for(int i = 0; i < buffer.length - 1; i++) {
            baos.write(buffer[i]);
        }


        return buffer[buffer.length-1] == checksumResult(baos.toByteArray());
    }
}
