package ro.luciangruia.neuralnetworks.helpers;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.SN_LEARNING_RATE;

public class MathHelpers {
    public static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public static double sigmoidDerivativeOfActivatedValue(double x) {
        return x * (1 - x); // Assuming x is already sigmoid-activated
    }

    public static double gradient(double inputValue, double expected, double prediction) {
        return inputValue * rawLoss(expected, prediction) * sigmoidDerivativeOfActivatedValue(prediction);
    }

    public static double gradientDescent(double gradient) {
        return SN_LEARNING_RATE * gradient;
    }

    public static double rawLoss(double expected, double prediction) {
        return expected - prediction;
    }

    public static double meanSquaredLoss(double expected, double prediction) {
        return (1 / 2) * Math.pow(expected - prediction, 2);
    }

    public static double generateRandomWeight() {
        return Math.random() - 0.5;
    }
}
