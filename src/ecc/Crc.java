package ecc;

public interface Crc {
    byte checksumResult(byte[] buffer);

    boolean checksum(byte[] buffer);
}
