package ro.luciangruia.neuralnetworks.models;

import org.springframework.stereotype.Component;

@Component
public class TrainingData {
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
