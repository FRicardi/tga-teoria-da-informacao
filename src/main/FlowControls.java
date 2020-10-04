package main;

import enumerators.EncodersEnum;
import enumerators.OperationsEnum;
import mappings.EncoderMapping;
import mappings.OperationMapping;
import pojos.EncoderData;

import java.util.Scanner;

public class FlowControls {

    private EncoderMapping encoderMapping;
    private EncodersEnum[] encodersEnums;
    private OperationMapping operationMapping;
    private OperationsEnum[] operationsEnums;
    private Scanner scanner;

    public FlowControls() {
        encoderMapping = new EncoderMapping();
        encodersEnums = EncodersEnum.values();
        operationMapping = new OperationMapping();
        operationsEnums = OperationsEnum.values();
        scanner = new Scanner(System.in);
    }

    public OperationsEnum selectOperation() {
        displayOperationOptions();
        int selectedOption = -1;
        while (selectedOption < 0 || selectedOption > operationsEnums.length - 1) {
            System.out.println("Choose which operation you would like to perform:");
            selectedOption = scanner.nextInt();
        }

        return operationsEnums[selectedOption];
    }

    private void displayOperationOptions() {
        for (int i = 0; i < operationsEnums.length; i++)
            System.out.println("(" + i + ")" + operationMapping.getOperationName(operationsEnums[i]));
    }

    public EncoderData selectEncoder() {
        displayEncoderOptions();
        int selectedOption = -1;
        while (selectedOption < 0 || selectedOption > encodersEnums.length - 1) {
            System.out.println("Choose the encoder you want to use:");
            selectedOption = scanner.nextInt();
        }

        return encoderMapping.getEncoderData(encodersEnums[selectedOption]);
    }


    private void displayEncoderOptions() {
        for (int i = 0; i< encodersEnums.length; i++) {
            System.out.println("(" + i + ") " + encoderMapping.getEncoderData(encodersEnums[i]).getName());
        }
    }

}
