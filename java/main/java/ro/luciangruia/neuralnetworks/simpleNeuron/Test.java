package ro.luciangruia.neuralnetworks.simpleNeuron;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class Test {

    public static void testSN(double[] newInput, SNeuron sNeuron, String testContext) {
        log.info("Test context: {}", testContext);
        log.info("Prediction for {} ==> {} [{}]", Arrays.toString(newInput), sNeuron.classify(newInput), sNeuron.output(newInput));
        sNeuron.printState();
    }

}