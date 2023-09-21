package ro.luciangruia.neuralnetworks.models;

import lombok.Data;

import java.util.List;

@Data
public class ImageData {
    private List<List<Integer>> image; // 2D list for binarized image
    private int label; // Label associated with the image

    public ImageData(List<List<Integer>> currentImage, int label) {
        this.image = currentImage;
        this.label = label;
    }

    public void printImage() {
        System.out.println("Image:");
        for (List<Integer> row : image) {
            for (Integer pixel : row) {
                System.out.print(pixel + " ");
//                System.out.print(pixel == 1 ? 'X' : ' ' + " ");
            }
            System.out.println();
        }
        System.out.println("Label: " + label);
    }

    public double[][] toDoubleArray() {
        int numRows = image.size();
        int numCols = (numRows > 0) ? image.get(0).size() : 0;
        double[][] result = new double[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                result[i][j] = image.get(i).get(j).doubleValue();
            }
        }
        return result;
    }


}

