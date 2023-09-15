package ro.luciangruia.neuralnetworks.models;

import lombok.Data;

@Data
public class TrainingData {
    private int[] input;
    private int expectedOutput;

}
