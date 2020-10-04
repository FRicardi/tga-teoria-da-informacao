package pojos;

import encodings.Encoder;

public class EncoderData {
    private String name;
    private int code;
    private Encoder encoder;

    public EncoderData(String name, int code, Encoder encoder) {
        this.setName(name);
        this.setCode(code);
        this.setEncoder(encoder);
    }

    public EncoderData() {}

    public Encoder getEncoder() {
        return encoder;
    }

    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
