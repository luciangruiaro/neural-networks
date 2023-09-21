package ro.luciangruia.neuralnetworks.models;

import lombok.Data;

import java.util.List;

@Data
public class Pair<T, U> {
    public final T images;
    public final U labels;

    public Pair(T images, U labels) {
        this.images = images;
        this.labels = labels;
    }

    public static Pair<double[][], double[][]> transformListToTrainingData(List<ImageData> imageDataList) {
        double[][] trainingInputs = imageDataList.stream()
                .map(imgData -> imgData.getImage().stream()
                        .flatMapToInt(row -> row.stream().mapToInt(Integer::intValue))
                        .asDoubleStream()
                        .toArray())
                .toArray(double[][]::new);

        double[][] expectedOutputs = imageDataList.stream()
                .map(imgData -> oneHotEncode(imgData.getLabel()))
                .toArray(double[][]::new);

        return new Pair<>(trainingInputs, expectedOutputs);
    }

    private static double[] oneHotEncode(int label) {
        double[] encoding = new double[10]; // Assuming there are 10 categories (0-9)
        encoding[label] = 1.0;
        return encoding;
    }
}
