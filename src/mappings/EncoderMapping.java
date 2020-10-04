package mappings;

import encodings.impl.*;
import enumerators.EncodersEnum;
import pojos.EncoderData;

import java.util.HashMap;
import java.util.Map;

public class EncoderMapping {

    private Map<EncodersEnum, EncoderData> encoderDataMap;

    public EncoderMapping() {
        initEncoderDataMap();
    }

    private void initEncoderDataMap() {
        encoderDataMap = new HashMap<>();

        encoderDataMap.put(EncodersEnum.GOLOMB, new EncoderData("Golomb", 0, new GolombEncoder()));
        encoderDataMap.put(EncodersEnum.ELIAS_GAMMA, new EncoderData("Elias Gamma", 1, new EliasGammaEncoder()));
        encoderDataMap.put(EncodersEnum.FIBONACCI, new EncoderData("Fibonacci", 2, new FibonacciEncoder()));
        encoderDataMap.put(EncodersEnum.UNARY, new EncoderData("Unary", 3, new UnaryEncoder()));
        encoderDataMap.put(EncodersEnum.DELTA, new EncoderData("Delta", 4, new DeltaEncoder()));
    }

    public EncoderData getEncoderData(EncodersEnum encodersEnum) {
        return encoderDataMap.getOrDefault(encodersEnum, new EncoderData());
    }

    public EncoderData getEncoderFromCode(int code) {
        return encoderDataMap.entrySet().stream()
                .filter(encodersEnumEncoderDataEntry -> encodersEnumEncoderDataEntry.getValue().getCode() == code)
                .findFirst()
                .get()
                .getValue();
    }


}
