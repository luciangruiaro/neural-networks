package ro.luciangruia.neuralnetworks.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ro.luciangruia.neuralnetworks.helpers.DataHelper;
import ro.luciangruia.neuralnetworks.models.TrainingData;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_INPUT_RESOLUTION;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_NO_HIDDEN_LAYERS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_NO_NEURONS_OUTPUT;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_NO_NEURONS_PER_HIDDEN_LAYERS;

@Controller
public class NNController {

    @Autowired
    DataHelper dataHelper;

    @GetMapping("/test")
    public ModelAndView train() {
        return new ModelAndView("test");
    }

    @PostMapping("/test/submit")
    @ResponseBody
    public ResponseEntity<?> submitTrainingData(@RequestBody TrainingData data) {
        dataHelper.print1Dto2D(data.getInput());
        System.out.println("Expected Output: " + data.getExpectedOutput());
        return ResponseEntity.ok("Data received successfully!");
    }

    @GetMapping("/visualizeInputImage")
    public String visualizeGrid() {
        return "visualizeInputImage";
    }


    @GetMapping("/network-d3")
    public String viewNetworkD3(Model model) {
        int noInputNeurons = (int) Math.pow(NN_INPUT_RESOLUTION, 2);
        int noHiddenLayers = NN_NO_HIDDEN_LAYERS;
        int noNeuronsPerHiddenLayer = NN_NO_NEURONS_PER_HIDDEN_LAYERS;
        int noOutputNeurons = NN_NO_NEURONS_OUTPUT;
        model.addAttribute("noInputNeurons", noInputNeurons);
        model.addAttribute("noHiddenLayers", noHiddenLayers);
        model.addAttribute("noNeuronsPerHiddenLayer", noNeuronsPerHiddenLayer);
        model.addAttribute("noOutputNeurons", noOutputNeurons);

        Random rand = new Random(); // TODO replace with NN weights
        int totalNeurons = noInputNeurons + noHiddenLayers * noNeuronsPerHiddenLayer + noOutputNeurons;
        // values for input layer
        double[] inputNeuronsValuesArray = IntStream.range(0, noInputNeurons).mapToDouble(i -> Math.round(rand.nextDouble() * 100.0) / 100.0).toArray();
        String inputNeuronValues = Arrays.stream(inputNeuronsValuesArray).mapToObj(Double::toString).collect(Collectors.joining(","));
        model.addAttribute("inputNeuronValues", inputNeuronValues);
        // values for input layer
        double[] hiddenNeuronsValuesArray = IntStream.range(0, noHiddenLayers * noNeuronsPerHiddenLayer).mapToDouble(i -> Math.round(rand.nextDouble() * 100.0) / 100.0).toArray();
        String hiddenNeuronValues = Arrays.stream(hiddenNeuronsValuesArray).mapToObj(Double::toString).collect(Collectors.joining(","));
        model.addAttribute("hiddenNeuronValues", hiddenNeuronValues);
        // values for input layer
        double[] outputNeuronsValuesArray = IntStream.range(0, noOutputNeurons).mapToDouble(i -> Math.round(rand.nextDouble() * 100.0) / 100.0).toArray();
        String outputNeuronValues = Arrays.stream(outputNeuronsValuesArray).mapToObj(Double::toString).collect(Collectors.joining(","));
        model.addAttribute("outputNeuronValues", outputNeuronValues);

        return "network-d3";
    }


}