package ro.luciangruia.neuralnetworks.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.luciangruia.neuralnetworks.simpleNeuron.NeuronVisualiser;
import ro.luciangruia.neuralnetworks.simpleNeuron.SimpleNeuronService;

@Controller
@RequestMapping("/neuron")
public class SNController {

    @Autowired
    SimpleNeuronService simpleNeuronService;

    @GetMapping("/visualize/initial")
    public String initial(Model model) {
        NeuronVisualiser neuronVisualiser = simpleNeuronService.getJustCreatedNeuron();
        setModelAttributes(model, neuronVisualiser);
        return "simpleNeuron";
    }

    @GetMapping("/visualize/after")
    public String after(Model model) {
        NeuronVisualiser neuronVisualiser = simpleNeuronService.getEducatedNeuron();
        setModelAttributes(model, neuronVisualiser);
        return "simpleNeuron";
    }

    private void setModelAttributes(Model model, NeuronVisualiser neuronVisualiser) {
        model.addAttribute("weights", neuronVisualiser.weights);
        model.addAttribute("noInputs", neuronVisualiser.noInputs);
        model.addAttribute("activated", neuronVisualiser.isActivated());
        model.addAttribute("output", neuronVisualiser.output);
        model.addAttribute("outputClassified", neuronVisualiser.outputClassified);
        model.addAttribute("biasWeight", neuronVisualiser.biasWeight);
        model.addAttribute("inputValues", neuronVisualiser.inputs);
    }
}
