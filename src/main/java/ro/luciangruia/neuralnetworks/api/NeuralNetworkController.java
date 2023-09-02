package ro.luciangruia.neuralnetworks.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ro.luciangruia.neuralnetworks.helpers.DataHelper;
import ro.luciangruia.neuralnetworks.model.TrainingData;

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

    @GetMapping("/network")
    public String viewNetwork() {
        return "network";
    }

    @GetMapping("/network-d3")
    public String viewNetworkD3() {
        return "network-d3";
    }


}