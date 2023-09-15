package ro.luciangruia.neuralnetworks.helpers;

import org.springframework.stereotype.Component;

@Component
public class DataHelper {

    public void print1Dto2D(int[] input) {
        int size = (int) Math.sqrt(input.length); // Assuming the input is always NxN
        // Reshape and print the 1D array as a 2D matrix
        System.out.println("Input matrix for handwriting number: ");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(input[i * size + j] + " ");
            }
            System.out.println(); // Move to the next line after printing each row
        }
    }
}
