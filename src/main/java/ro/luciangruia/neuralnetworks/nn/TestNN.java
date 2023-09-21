package ro.luciangruia.neuralnetworks.nn;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class TestNN {

    public static void testSN(double[] inputsVector, NN nn) {
        log.info("Prediction: {} ==> {}", nn.predictResult(inputsVector), Arrays.toString(nn.networkOutputs(inputsVector)));
    }

}
