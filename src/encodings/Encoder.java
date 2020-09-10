package encodings;

public interface Encoder {

    byte[] encodeText(String text);

    String encodeNumber(int number);

    String decode(String text);

    String decode(byte[] buffer);
}
