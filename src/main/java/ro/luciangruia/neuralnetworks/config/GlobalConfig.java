package ro.luciangruia.neuralnetworks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalConfig {

    public static final String DYNAMIC_TARGET_PATH = System.getProperty("user.dir") + "/target/classes";
    public static final String RES_PYTHON_PATH = DYNAMIC_TARGET_PATH + "/static/python";
    public static final String RES_JS_PATH = DYNAMIC_TARGET_PATH + "/static/js";
    public static final String RES_CSS_PATH = DYNAMIC_TARGET_PATH + "/static/css";

    @Value("${neural.network.input.resolution.numbers.handwriting}")
    public static int NEURAL_NETWORK_INPUT_RESOLUTION;
}
