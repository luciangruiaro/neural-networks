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

import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_INPUT_NEURONS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_NO_HIDDEN_LAYERS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_NO_NEURONS_PER_HIDDEN_LAYERS;
import static ro.luciangruia.neuralnetworks.config.GlobalConfig.NN_OUTPUT_NEURONS;
import static ro.luciangruia.neuralnetworks.helpers.DataHelper.arrayToCSV;
import static ro.luciangruia.neuralnetworks.helpers.DataHelper.getHiddenLayerOutputs;
import static ro.luciangruia.neuralnetworks.helpers.DataHelper.getOutputLayerOutputs;

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
        return nnService.trainNN(nn);
    }

    @GetMapping("/test")
    public ModelAndView test() {
        return new ModelAndView("test");
    }

    @PostMapping("/test/submit")
    @ResponseBody
    public ResponseEntity<?> submitTestData(@RequestBody TrainingData data) {
        dataHelper.print1Dto2D(data.getInput());
        nnService.testNN(dataHelper.transform1Dto2D(data.getInput()));
        return ResponseEntity.ok("Data received successfully!");
    }


    @GetMapping("/network-d3")
    public String viewNetworkD3(Model model) {

        // draw network layout
        model.addAttribute("noInputNeurons", NN_INPUT_NEURONS);
        model.addAttribute("noHiddenLayers", NN_NO_HIDDEN_LAYERS);
        model.addAttribute("noNeuronsPerHiddenLayer", NN_NO_NEURONS_PER_HIDDEN_LAYERS);
        model.addAttribute("noOutputNeurons", NN_OUTPUT_NEURONS);

        // neurons outputs
        model.addAttribute("inputNeuronValues", arrayToCSV(nn.inputsVector));
        model.addAttribute("hiddenNeuronValues", getHiddenLayerOutputs(nn));
        model.addAttribute("outputNeuronValues", getOutputLayerOutputs(nn));

        return "network-d3";
    }

}