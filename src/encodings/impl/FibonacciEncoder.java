package encodings.impl;

import encodings.Encoder;
import utility.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FibonacciEncoder extends Encoder {
    private static final int MAX_FIBO = 25;
    private List<Integer> fibonaccis;
    private static final String ONE_SEQUENCE = "11";

    public FibonacciEncoder() {
        startFibonacci();
    }

    @Override
    public int getCode() {
        return 2;
    }

    @Override
    public String decode(String text) {
        StringBuilder decodedText = new StringBuilder();
        while (text.contains(ONE_SEQUENCE)) {
            String nextCodeword = getNextCodeword(text);
            text = text.substring(nextCodeword.length() + 1);

            decodedText.append((char) decodeCodeword(nextCodeword));
        }


        return decodedText.toString();

    }


    private int decodeCodeword(String codeword) {

        int sum = 0;

        for(int i = 0; i < codeword.length(); i++) {
            sum += codeword.charAt(i) == '1' ? fibonaccis.get(i) : 0;
        }

        return sum;

    }

    private String getNextCodeword(String text ) {
        int i = 0;
        StringBuilder codewordBuilder = new StringBuilder();
        codewordBuilder.append(" ");
        while (!(codewordBuilder.charAt(codewordBuilder.length() - 1) == '1' && text.charAt(i) == '1')) {
            codewordBuilder.append(text.charAt(i));
            i++;
        }
        codewordBuilder.deleteCharAt(0);
        return codewordBuilder.toString();
    }

    @Override
    public String decode(byte[] buffer) {

        return decode(StringUtils.concatByteArrayWithOffset(buffer, 2));

    }

    private void startFibonacci() {
        fibonaccis = new ArrayList<>();
        fibonaccis.add(0, 1);
        fibonaccis.add(1, 2);
        int i;
        for (i=2; i < MAX_FIBO; i++)
            fibonaccis.add(i, fibonaccis.get(i-1) + fibonaccis.get(i - 2));
    }

    @Override
    public String encodeNumber(int number)
    {
        List<Integer> indexesList = new ArrayList<>();
        for (int i = MAX_FIBO - 1; i >= 0; i--) {
            if(fibonaccis.get(i) <= number) {
                number = number - (fibonaccis.get(i));
                indexesList.add(i);
            }
            if (number == 0) {
                break;
            }
        }

        StringBuilder codewordBuilder = new StringBuilder();

        for(int i = 0; i <= indexesList.get(0); i++) {
            codewordBuilder.append(indexesList.contains(i) ? '1' : '0');
        }

        return codewordBuilder.toString() + '1';
    }

}
