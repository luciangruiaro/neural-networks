package ro.luciangruia.neuralnetworks.helpers;

import org.springframework.stereotype.Component;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.SN_LEARNING_RATE;

@Component
public class MathHelpers {

    private MathHelpers() {
    }

    // Neuron helpers -----------------------------------------------
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
        return 0.5 * Math.pow(expected - prediction, 2);
    }

    public static double generateRandomWeight() {
        return Math.random() - 0.5;
    }


    // Network helpers -----------------------------------------------
    public static double[] softmax(double[] logits) {
        double[] result = new double[logits.length];
        double sum = 0.0;
        for (int i = 0; i < logits.length; i++) {
            sum += Math.exp(logits[i]);
        }
        if (sum == 0.0) {
            throw new ArithmeticException("Denominator 'sum' is zero in softmax computation.");
        }
        for (int i = 0; i < logits.length; i++) {
            result[i] = Math.exp(logits[i]) / sum;
        }
        return result;
    }

    public static int getMaxIndex(double[] softmaxOutput) {
        int maxIndex = 0;
        for (int i = 0; i < softmaxOutput.length; i++) {
            if (softmaxOutput[i] > softmaxOutput[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    // compute delta for output layer
    public static double computeDeltaForOutput(double expected, double prediction) {
        return (expected - prediction) * sigmoidDerivativeOfActivatedValue(prediction);
    }

    public static double computeDeltaForHidden(double activatedInput, double[] nextLayerDeltas, double[] nextLayerWeights) {
        double weightedDeltaSum = 0;
        for (int i = 0; i < nextLayerDeltas.length; i++) {
            weightedDeltaSum += nextLayerDeltas[i] * nextLayerWeights[i];
        }
        double derivative = sigmoidDerivativeOfActivatedValue(activatedInput);
        return weightedDeltaSum * derivative;
    }


    public static double[] gradient(double[] inputValues, double[] expected, double[] predictions) {
        if (inputValues.length != predictions.length || predictions.length != expected.length) {
            throw new IllegalArgumentException("Mismatch in length of inputValues, predictions, and expected.");
        }
        double[] gradients = new double[inputValues.length];
        for (int i = 0; i < inputValues.length; i++) {
            gradients[i] = gradient(inputValues[i], expected[i], predictions[i]);
        }
        return gradients;
    }

    public static double[] gradientDescent(double[] gradients) {
        double[] adjustedGradients = new double[gradients.length];
        for (int i = 0; i < gradients.length; i++) {
            adjustedGradients[i] = SN_LEARNING_RATE * gradients[i];
        }
        return adjustedGradients;
    }

    public static double crossEntropyLoss(double[] expected, double[] predicted) {
        double loss = 0.0;
        for (int i = 0; i < expected.length; i++) {
            loss -= expected[i] * Math.log(predicted[i]);
        }
        return loss;
    }

    public static double[] flattenMatrix(double[][] inputsMatrix) {
        double[] inputsVector = new double[inputsMatrix.length * inputsMatrix[0].length];
        int index = 0;
        for (double[] row : inputsMatrix) {
            for (double value : row) {
                inputsVector[index++] = value;
            }
        }
        return inputsVector;
    }

}
