package ro.luciangruia.neuralnetworks.helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.luciangruia.neuralnetworks.api.PythonApi;
import ro.luciangruia.neuralnetworks.models.ImageData;
import ro.luciangruia.neuralnetworks.nn.NN;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_INPUT_NEURONS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_NO_HIDDEN_LAYERS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_NO_NEURONS_PER_HIDDEN_LAYERS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_OUTPUT_NEURONS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_TRAINING_DATA;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_TRAINING_REQ_LABEL;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_TRAINING_SET_SIZE;

@Component
@Slf4j
public class DataHelper {

    // Print inputs vector friendly
    public void print1Dto2D(int[] input) {
        int size = (int) Math.sqrt(input.length); // Assuming the input is always NxN
        // Reshape and print the 1D array as a 2D matrix
        System.out.println("Input matrix for handwriting number: ");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(input[i * size + j] + " ");
            }
            System.out.println(); // Move to the next line after printing each row
        }
    }

    // Convert inputs vector to matrix
    public double[][] transform1Dto2D(int[] input) {
        int n = (int) Math.sqrt(input.length); // Assuming input length is always n*n
        if (n * n != input.length) {
            throw new IllegalArgumentException("The input array's length is not a perfect square.");
        }
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = input[i * n + j];
            }
        }
        return result;
    }

    // convert double[] to String csv
    public static String arrayToCSV(double[] array) {
        if (array == null) {
            return "";
        }
        return Arrays.stream(array).mapToObj(Double::toString).collect(Collectors.joining(","));
    }

    // generate random input values
    public static double[] generateRandTestValues() {
//        return IntStream.range(0, NN_INPUT_NEURONS).mapToDouble(i -> Math.round(new Random().nextDouble() * 100.0) / 100.0).toArray();
        return IntStream.range(0, NN_INPUT_NEURONS).mapToDouble(i -> 0).toArray();
    }

    // get hidden layer outputs
    public static String getHiddenLayerOutputs(NN nn) {
        if (nn.hiddenLayerOutputs == null) {
            return "";
        }
        return Arrays.stream(nn.hiddenLayerOutputs).flatMapToDouble(Arrays::stream).mapToObj(Double::toString).collect(Collectors.joining(","));
    }

    // generate random hidden layer outputs
    public static double[][] generateRandHiddenLayerOutputs() {
        return IntStream.range(0, NN_NO_HIDDEN_LAYERS).mapToObj(i -> IntStream.range(0, NN_NO_NEURONS_PER_HIDDEN_LAYERS)
//                        .mapToDouble(j -> Math.round(new Random().nextDouble() * 100.0) / 100.0).toArray())
                .mapToDouble(j -> 0).toArray()).toArray(double[][]::new);
    }


    // get output layer outputs
    public static String getOutputLayerOutputs(NN nn) {
        if (nn.outputLayerOutputs == null) {
            return "";
        }
        return Arrays.stream(nn.outputLayerOutputs).mapToObj(Double::toString).collect(Collectors.joining(","));
    }

    // generate output layer outputs
    public static double[] generateRandOutputLayerOutputs() {
        return IntStream.range(0, NN_OUTPUT_NEURONS)
//                .mapToDouble(i -> Math.round(new Random().nextDouble() * 100.0) / 100.0)
                .mapToDouble(i -> 0).toArray();
    }

    // save training data to file
    public static void saveTrainingData(List<ImageData> imageDataList) throws IOException {
        try {
            new ObjectMapper().writeValue(new File(NN_TRAINING_DATA), imageDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // load training data from file
    public static List<ImageData> loadTrainingData() throws IOException {
        List<ImageData> loadedList = new ArrayList<>();
        try {
            loadedList = new ObjectMapper().readValue(new File(NN_TRAINING_DATA), new TypeReference<List<ImageData>>() {
            });
        } catch (IOException e) {
            log.info("No training data found. Generating new training data...");
//            e.printStackTrace();
        }
        if (loadedList.isEmpty()) {
            loadedList = PythonApi.pyGenerateTrainingData(NN_INPUT_NEURONS, NN_TRAINING_SET_SIZE, NN_TRAINING_REQ_LABEL);
            saveTrainingData(loadedList);
        }
        return loadedList;
    }


}
