package ro.luciangruia.neuralnetworks.helpers;

public class MathHelpers {
    public static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public static double sigmoidDerivative(double x) {
        return x * (1 - x); // Assuming x is already sigmoid-activated
    }

    public static double gradient(double inputValue, double expected, double prediction) {
        return inputValue * meanSquaredLoss(expected, prediction) * sigmoidDerivative(prediction);
    }

    public static double gradientDescent(double gradient, double learningRate) {
        return learningRate * gradient;
    }

    public static double rawLoss(double expected, double prediction) {
        return expected - prediction;
    }

    public static double meanSquaredLoss(double expected, double prediction) {
        return Math.pow(expected - prediction, 2);
    }

}
