package ro.luciangruia.neuralnetworks.nn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class TestNN {

    public static void testSN(double[] inputsVector, NN nn) {
        log.info("Prediction for {} ==> {} [{}]", nn.predictResult(inputsVector), Arrays.toString(nn.networkOutputs(inputsVector)), Arrays.toString(inputsVector));
    }

}
