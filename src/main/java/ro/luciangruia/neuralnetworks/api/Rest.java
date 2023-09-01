package ro.luciangruia.neuralnetworks.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;

@RestController
public class Rest {

    private static final Logger logger = LoggerFactory.getLogger(Rest.class);

    @GetMapping("/hello")
    public String hello() {
        System.out.println("hello");
        return "Hello World!";
    }




}
