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
import ro.luciangruia.neuralnetworks.model.TrainingData;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class NeuralNetworkController {

    @Autowired
    DataHelper dataHelper;

    @GetMapping("/train")
    public ModelAndView train() {
        return new ModelAndView("train");
    }

    @PostMapping("/train/submit")
    @ResponseBody
    public ResponseEntity<?> submitTrainingData(@RequestBody TrainingData data) {
        dataHelper.print1Dto2D(data.getInput());
        System.out.println("Expected Output: " + data.getExpectedOutput());
        return ResponseEntity.ok("Data received successfully!");
    }


    @GetMapping("/network-d3")
    public String viewNetworkD3(Model model) {
        int noInputNeurons = 32;
        int noHiddenLayers = 3;
        int noNeuronsPerHiddenLayer = 6;
        int noOutputNeurons = 10;
        model.addAttribute("noInputNeurons", noInputNeurons);
        model.addAttribute("noHiddenLayers", noHiddenLayers);
        model.addAttribute("noNeuronsPerHiddenLayer", noNeuronsPerHiddenLayer);
        model.addAttribute("noOutputNeurons", noOutputNeurons);

        Random rand = new Random();
        int totalNeurons = noInputNeurons + noHiddenLayers * noNeuronsPerHiddenLayer + noOutputNeurons;
        double[] neuronValuesArray = IntStream.range(0, totalNeurons).mapToDouble(i -> Math.round(rand.nextDouble() * 100.0) / 100.0).toArray();

        String neuronValues = Arrays.stream(neuronValuesArray).mapToObj(Double::toString).collect(Collectors.joining(","));

        model.addAttribute("neuronValues", neuronValues);

        return "network-d3";
    }


}