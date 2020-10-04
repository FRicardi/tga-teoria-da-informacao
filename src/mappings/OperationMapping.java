package mappings;

import enumerators.OperationsEnum;

import java.util.HashMap;
import java.util.Map;

public class OperationMapping {

    private static final String ERROR_VALUE = "ERROR";
    private Map<OperationsEnum, String> operationsNameMap;

    public OperationMapping() { initOperationMap(); }

    private void initOperationMap() {
        operationsNameMap = new HashMap<>();

        operationsNameMap.put(OperationsEnum.ENCODE, "Encode file");
        operationsNameMap.put(OperationsEnum.DECODE, "Decode file");
    }

    public String getOperationName(OperationsEnum operationEnum) {
        return operationsNameMap.getOrDefault(operationEnum, ERROR_VALUE);
    }

}
