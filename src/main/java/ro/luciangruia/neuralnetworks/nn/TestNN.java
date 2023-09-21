package ro.luciangruia.neuralnetworks.nn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class TestNN {

    @Autowired
    NN nn;

    public void go() {
        log.info("Prediction: {}", nn.predictResult());
        log.info("Outputs: {}", Arrays.stream(nn.outputLayerOutputs).toArray());
    }

}
