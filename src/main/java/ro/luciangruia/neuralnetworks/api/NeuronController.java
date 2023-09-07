package ro.luciangruia.neuralnetworks.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.luciangruia.neuralnetworks.simpleNeuron.SimpleNeuronService;

@RestController
@RequestMapping("/neuron")
public class NeuronController {

    @Autowired
    SimpleNeuronService simpleNeuron;

    @GetMapping("/main")
    public String neuronMain() {
        simpleNeuron.main();
        return "simple neuron";
    }
}
