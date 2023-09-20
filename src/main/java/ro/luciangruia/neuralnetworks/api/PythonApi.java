package ro.luciangruia.neuralnetworks.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ro.luciangruia.neuralnetworks.simpleNeuron.SNState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_INPUT_NEURONS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.PYTHON_VENV_COMMAND;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.RES_PYTHON_PATH;

@Service
public class PythonApi {

    public static void main(String[] args) {
        pyGenerateTrainingData(NN_INPUT_NEURONS);
    }

    private static void pyGenerateTrainingData(int n) {
        // invoke the python script
        ProcessBuilder processBuilder = new ProcessBuilder(PYTHON_VENV_COMMAND, RES_PYTHON_PATH + "/training_data/generate_training_data.py", "--n", String.valueOf((int) Math.sqrt(n))).redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            handleExitCode(process);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
