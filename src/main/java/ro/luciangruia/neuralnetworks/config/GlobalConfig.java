package ro.luciangruia.neuralnetworks.config;

import org.springframework.stereotype.Component;

@Component
public class GlobalConfig {

    // NEURAL NETWORK ARCHITECTURE
    public static int NEURAL_NETWORK_INPUT_RESOLUTION = 9;

    // PATHS
    public static final String DYNAMIC_PATH = System.getProperty("user.dir");
    public static final String RES_PYTHON_PATH = DYNAMIC_PATH + "/python/training_data";
    public static final String RES_JS_PATH = DYNAMIC_PATH + "/static/js";
    public static final String RES_CSS_PATH = DYNAMIC_PATH + "/static/css";


}
