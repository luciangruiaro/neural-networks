package ro.luciangruia.neuralnetworks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {

    // NEURAL NETWORK ARCHITECTURE
    public static int NEURAL_NETWORK_INPUT_RESOLUTION = 28;
    public static int NEURAL_NETWORK_NO_HIDDEN_LAYERS = 2;
    public static int NEURAL_NETWORK_NO_NEURONS_PER_HIDDEN_LAYERS = 16;
    public static int NEURAL_NETWORK_NO_NEURONS_OUTPUT = 10;

    // PATHS
    public static final String DYNAMIC_PATH = System.getProperty("user.dir");
    public static final String RES_PYTHON_PATH = DYNAMIC_PATH + "/python";
    public static final String RES_JS_PATH = DYNAMIC_PATH + "/static/js";
    public static final String RES_CSS_PATH = DYNAMIC_PATH + "/static/css";

    // SINGLE NEURON
    public static final double SN_OUTPUT_THRESHOLD = 0.5;
    public static final double SN_LEARNING_RATE = 0.1; // Controls the step size during weight updates in training.
    public static final int SN_INPUT_SIZE = 3;
    public static final int SN_EPOCHS = 50;

    @Bean
    public int inputSize() {
        return SN_INPUT_SIZE;
    }
}