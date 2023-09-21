package ro.luciangruia.neuralnetworks.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ro.luciangruia.neuralnetworks.models.ImageData;
import ro.luciangruia.neuralnetworks.simpleNeuron.SNState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_INPUT_NEURONS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_TRAINING_REQ_LABEL;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_TRAINING_SET_SIZE;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.PYTHON_VENV_COMMAND;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.RES_PYTHON_PATH;

@Service
public class PythonApi {

    // use this method visualize the training data
    public static void main(String[] args) throws IOException {
        List<ImageData> trainingImages = pyGenerateTrainingData(NN_INPUT_NEURONS, NN_TRAINING_SET_SIZE, NN_TRAINING_REQ_LABEL);
        for (ImageData image : trainingImages) {
            image.printImage();
        }
        System.out.println("Done!");

        // Generating some sample data
//        Random random = new Random();
//        List<double[]> predictedOutputs = new ArrayList<>();
        // Generate 10 data samples
//        for (int i = 0; i < 10; i++) {
//            double[] probabilities = new double[10];
//            for (int j = 0; j < 10; j++) {
//                probabilities[j] = random.nextDouble();
//            }
//            predictedOutputs.add(probabilities);
//        }
        // Call the plotting function
//        pyPlotPredictedOutputs(predictedOutputs, NN_TRAINING_REQ_LABEL);
    }

    public static List<ImageData> pyGenerateTrainingData(int noInputNeurons, int trainingSetSize, int reqLabel) {
        List<ImageData> results = new ArrayList<>();

        // invoke the python script
        ProcessBuilder processBuilder = new ProcessBuilder(PYTHON_VENV_COMMAND,
                RES_PYTHON_PATH + "/training_data/generate_training_data.py",
                "--n", String.valueOf((int) Math.sqrt(noInputNeurons)),
                "--count", String.valueOf(trainingSetSize),
                "--label", String.valueOf(reqLabel))
                .redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            List<List<Integer>> currentImage = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("BEGIN_IMAGE")) {
                    currentImage = new ArrayList<>();
                } else if (line.startsWith("END_IMAGE")) {
                    int label = Integer.parseInt(reader.readLine().replace("LABEL: ", "").trim());
                    results.add(new ImageData(currentImage, label));
                } else {
                    currentImage.add(Arrays.stream(line.split(" "))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList()));
                }
            }

            handleExitCode(process);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    private static void handleExitCode(Process process) {
        int exitCode;
        try {
            exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println("Python script encountered an error.");
            }
        } catch (InterruptedException e) {
            // Re-interrupt the thread to indicate the interruption
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void pyPlotNeuronStates(List<SNState> neuronStates) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Path filePath = Paths.get(RES_PYTHON_PATH + "/plots/neuronStates.json");
        String neuronStatesJson = objectMapper.writeValueAsString(neuronStates);
        Files.write(filePath, neuronStatesJson.getBytes(StandardCharsets.UTF_8));

        // invoke the python script
        ProcessBuilder processBuilder = new ProcessBuilder(PYTHON_VENV_COMMAND, RES_PYTHON_PATH + "/plots/neuron_evolution.py ", filePath.toString());
        processBuilder.start();
    }

    public static void pyPlotPredictedOutputs(List<double[]> predictedOutputs, int reqLabel) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Assuming this is the path where you want to store the JSON file
        Path filePath = Paths.get(RES_PYTHON_PATH + "/plots/predictedOutputs.json");

        String predictedOutputsJson = objectMapper.writeValueAsString(predictedOutputs);
        Files.write(filePath, predictedOutputsJson.getBytes(StandardCharsets.UTF_8));

        // invoke the python script
        ProcessBuilder processBuilder = new ProcessBuilder(PYTHON_VENV_COMMAND, RES_PYTHON_PATH + "/plots/network_evolution.py",
                filePath.toString(),
                String.valueOf(reqLabel)
        );
        processBuilder.start();
    }

}
