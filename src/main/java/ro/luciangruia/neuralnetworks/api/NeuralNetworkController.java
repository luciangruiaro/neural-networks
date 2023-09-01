package ro.luciangruia.neuralnetworks.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class NeuralNetworkController {
    @GetMapping("/train")
    public Map<String, Object> train(Model model) {
        Map<String, Object> responseData = new HashMap<>();

        // Mocked data
        responseData.put("inputLayerSize", 9 * 9);
        responseData.put("hiddenLayers", "100, 50, 25"); // Example values
        responseData.put("weights", "5000"); // Example value

        return responseData;
    }

    @PostMapping("/train/submit")
    @ResponseBody
    public ResponseEntity<?> submitTrainingData(@RequestBody TrainingData data) {
        int[] input = data.getInput();
        int size = (int) Math.sqrt(input.length); // Assuming the input is always NxN

        // Reshape and print the 1D array as a 2D matrix
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(input[i * size + j] + " ");
            }
            System.out.println(); // Move to the next line after printing each row
        }

        System.out.println("Expected Output: " + data.getExpectedOutput());

        return ResponseEntity.ok("Data received successfully!");
    }


    public static class TrainingData {
        private int[] input;
        private int expectedOutput;

        // Getters and setters
        public int[] getInput() {
            return input;
        }

        public void setInput(int[] input) {
            this.input = input;
        }

        public int getExpectedOutput() {
            return expectedOutput;
        }

        public void setExpectedOutput(int expectedOutput) {
            this.expectedOutput = expectedOutput;
        }
    }
}