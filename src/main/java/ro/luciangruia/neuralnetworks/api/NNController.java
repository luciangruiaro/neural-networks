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
import ro.luciangruia.neuralnetworks.nn.NN;
import ro.luciangruia.neuralnetworks.nn.NNService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_INPUT_NEURONS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_NO_HIDDEN_LAYERS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_NO_NEURONS_PER_HIDDEN_LAYERS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_OUTPUT_NEURONS;

@Controller
public class NNController {

    @Autowired
    DataHelper dataHelper;

    @Autowired
    NNService nnService;

    @Autowired
    private NN nn;


    @GetMapping("/visualizeInputImage")
    public String visualizeGrid() {
        return "visualizeInputImage";
    }


    @GetMapping("/train")
    @ResponseBody
    public String train() throws IOException {
        nnService.trainNN(nn);

        return "Training done...";
    }

    @PostMapping("/train/submit")
    @ResponseBody
    public ResponseEntity<?> submitTrainingData(@RequestBody TrainingData data) {
        dataHelper.print1Dto2D(data.getInput());
        System.out.println("Expected Output: " + data.getExpectedOutput());
        return ResponseEntity.ok("Data received successfully!");
    }

    @GetMapping("/test")
    public ModelAndView test() {
        return new ModelAndView("test");
    }

    @PostMapping("/test/submit")
    @ResponseBody
    public ResponseEntity<?> submitTestData(@RequestBody TrainingData data) {
        dataHelper.print1Dto2D(data.getInput());
        nnService.testNN(dataHelper.transform1Dto2D(data.getInput()), nn);
        return ResponseEntity.ok("Data received successfully!");
    }


    @GetMapping("/network-d3")
    public String viewNetworkD3(Model model) {


        model.addAttribute("noInputNeurons", NN_INPUT_NEURONS);
        model.addAttribute("noHiddenLayers", NN_NO_HIDDEN_LAYERS);
        model.addAttribute("noNeuronsPerHiddenLayer", NN_NO_NEURONS_PER_HIDDEN_LAYERS);
        model.addAttribute("noOutputNeurons", NN_OUTPUT_NEURONS);

        // values for input layer
        double[] inputNeuronsValuesArray = IntStream.range(0, NN_INPUT_NEURONS).mapToDouble(i -> Math.round(new Random().nextDouble() * 100.0) / 100.0).toArray();
        String inputNeuronValues = Arrays.stream(inputNeuronsValuesArray).mapToObj(Double::toString).collect(Collectors.joining(","));
        model.addAttribute("inputNeuronValues", inputNeuronValues);

        // values for hidden layer
        String hiddenNeuronValues = Arrays.stream(nn.hiddenLayerOutputs)
                .flatMapToDouble(Arrays::stream)
                .mapToObj(Double::toString)
                .collect(Collectors.joining(","));
        model.addAttribute("hiddenNeuronValues", hiddenNeuronValues);
        // values for output layer
        String outputNeuronValues = Arrays.stream(nn.outputLayerOutputs)
                .mapToObj(Double::toString)
                .collect(Collectors.joining(","));
        model.addAttribute("outputNeuronValues", outputNeuronValues);

        return "network-d3";
    }


}