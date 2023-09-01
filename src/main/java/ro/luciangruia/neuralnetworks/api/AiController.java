package ro.luciangruia.neuralnetworks.api;

import org.springframework.ai.client.AiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.luciangruia.neuralnetworks.openAI.Completion;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final AiClient aiClient;

    @Autowired
    public AiController(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    @GetMapping("/prompt")
    public Completion completion(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return new Completion(aiClient.generate(message));
    }

    @GetMapping("/hello")
    public String hi() {
        return "sasa";
    }
}