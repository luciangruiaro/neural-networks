package ro.luciangruia.neuralnetworks.nn;

import org.springframework.stereotype.Service;
import ro.luciangruia.neuralnetworks.api.PythonApi;
import ro.luciangruia.neuralnetworks.helpers.MathHelpers;
import ro.luciangruia.neuralnetworks.models.ImageData;
import ro.luciangruia.neuralnetworks.models.Pair;

import java.io.IOException;
import java.util.List;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_INPUT_NEURONS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_TRAINING_REQ_LABEL;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_TRAINING_SET_SIZE;

@Service
public class NNService {


    public static void main(String[] args) {
        NN nn = new NN();
        nn.saveToJSON("nn.json");
        System.out.println("Saved to JSON");

    }


    public void trainNN(NN nn) throws IOException {
        List<ImageData> trainingImages = PythonApi.pyGenerateTrainingData(NN_INPUT_NEURONS, NN_TRAINING_SET_SIZE, NN_TRAINING_REQ_LABEL);
        List<double[]> predictedOutputs = TrainNN.train(Pair.transformListToTrainingData(trainingImages).images,
                Pair.transformListToTrainingData(trainingImages).labels,
                nn);
        PythonApi.pyPlotPredictedOutputs(predictedOutputs, NN_TRAINING_REQ_LABEL);
        nn.saveToJSON("nn.json");
        nn.loadFromJSON("nn.json");
    }

    public void testNN(double[][] inputsMatrix, NN nn) {
        TestNN.testSN(MathHelpers.flattenMatrix(inputsMatrix), nn);
    }

}
