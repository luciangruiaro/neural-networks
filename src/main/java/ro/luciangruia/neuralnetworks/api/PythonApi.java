package ro.luciangruia.neuralnetworks.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NEURAL_NETWORK_INPUT_RESOLUTION;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.RES_PYTHON_PATH;

public class PythonApi {

    public static void main(String[] args) {
        invokePythonScript(NEURAL_NETWORK_INPUT_RESOLUTION);
    }

    private static void invokePythonScript(int n) {
        // invoke the python script
        ProcessBuilder processBuilder = new ProcessBuilder("python", RES_PYTHON_PATH + "/generate_training_data.py", "--n", String.valueOf(n)).redirectErrorStream(true);

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
}
