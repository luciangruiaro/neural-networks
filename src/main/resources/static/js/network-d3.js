// Set neural network layout
const noInputNeurons = +document.getElementById('noInputNeurons').value; // number of inputs neurons
const noHiddenLayers = +document.getElementById('noHiddenLayers').value; // number of hidden layers
const noNeuronsPerHiddenLayer = +document.getElementById('noNeuronsPerHiddenLayer').value; // number of neurons per hidden layer
const noOutputNeurons = +document.getElementById('noOutputNeurons').value; // number of output neurons

// Set vaoues inside neurons
const neuronValues = document.getElementById('neuronValues').value.split(',').map(Number).filter(value => !isNaN(value));

console.log(neuronValues);
let neuronIndex = 0;

// Set SVG dimensions
const neuronRadius = 17;
const layerGap = 150;
const neuronGap = 40;

// Adjust vertical position for centering
const maxNeurons = Math.max(noInputNeurons, noNeuronsPerHiddenLayer, noOutputNeurons);
const centerY = maxNeurons * neuronGap / 2;

// Adjust the SVG width to ensure all layers are visible
const svgWidth = (noHiddenLayers + 3) * layerGap; // Increase the multiplier for more space
const svgHeight = maxNeurons * neuronGap + 4 * neuronRadius; // Add padding for topmost and bottommost neurons

// Create SVG
const svg = d3.select("#network")
        .append("svg")
        .attr("width", svgWidth)
        .attr("height", svgHeight)
    // .style("background-color", "rgb(45,182,201)")  // Temporary red background
;

// Draw Input Neurons
for (let i = 0; i < noInputNeurons; i++) {

    svg.append("circle")
        .attr("class", "neuron")
        .attr("cx", neuronRadius * 2)
        .attr("cy", 2 * neuronRadius + (i + 1) * neuronGap)  // Adjusted for padding
        .attr("r", neuronRadius);

}

// Draw Hidden Layers
for (let p = 0; p < noHiddenLayers; p++) {
    for (let m = 0; m < noNeuronsPerHiddenLayer; m++) {
        svg.append("circle")
            .attr("class", "neuron")
            .attr("cx", (p + 2) * layerGap)
            .attr("cy", 2 * neuronRadius + centerY + (m - noNeuronsPerHiddenLayer / 2) * neuronGap)  // Adjusted for padding
            .attr("r", neuronRadius);
    }
}

// Draw Output Neurons
for (let o = 0; o < noOutputNeurons; o++) {
    svg.append("circle")
        .attr("class", "neuron")
        .attr("cx", (noHiddenLayers + 2) * layerGap)
        .attr("cy", 2 * neuronRadius + centerY + (o - noOutputNeurons / 2) * neuronGap)  // Adjusted for padding
        .attr("r", neuronRadius);
}

// Log neuron positions
svg.selectAll(".neuron")
    .each(function () {
        const neuron = d3.select(this);
        console.log("Neuron position:", neuron.attr("cx"), neuron.attr("cy"));
    });


// Function to draw lines between two layers
function connectLayers(layer1, layer2) {
    layer1.each(function () {
        const fromNeuron = d3.select(this);
        layer2.each(function () {
            const toNeuron = d3.select(this);
            svg.append("line")
                .attr("class", "line")
                .attr("x1", fromNeuron.attr("cx"))
                .attr("y1", fromNeuron.attr("cy"))
                .attr("x2", toNeuron.attr("cx"))
                .attr("y2", toNeuron.attr("cy"));
        });
    });
}

// Draw Lines
const inputLayer = svg.selectAll(".neuron")
    .filter(function () {
        return d3.select(this).attr("cx") == neuronRadius * 2;
    });

let previousLayer = inputLayer;

for (let p = 0; p < noHiddenLayers; p++) {
    const currentHiddenLayer = svg.selectAll(".neuron")
        .filter(function () {
            return d3.select(this).attr("cx") == (p + 2) * layerGap;
        });
    connectLayers(previousLayer, currentHiddenLayer);
    previousLayer = currentHiddenLayer;
}

const outputLayer = svg.selectAll(".neuron")
    .filter(function () {
        return d3.select(this).attr("cx") == (noHiddenLayers + 2) * layerGap;
    });

connectLayers(previousLayer, outputLayer);

// Draw Neuron Values
svg.selectAll(".neuron")
    .each(function () {
        const neuron = d3.select(this);
        const x = parseFloat(neuron.attr("cx"));
        const y = parseFloat(neuron.attr("cy"));
        const neuronValue = neuronValues[neuronIndex];  // Use the value from the array

        svg.append("text")
            .attr("x", x)
            .attr("y", y)
            .attr("dy", "0.35em")  // to vertically center the text
            .attr("text-anchor", "middle")
            .text(neuronValue.toFixed(2))
            .attr("font-size", "10px")
            .attr("fill", "white");

        neuronIndex++;  // Increment the index for the next neuron
    });

// Identify the neuron in the output layer with the largest value
let maxOutputValue = -Infinity;
let maxOutputNeuronIndex = -1;

svg.selectAll("text")
    .each(function (d, i) {
        // If the text element is within the range of the output neurons
        if (i >= noInputNeurons + noHiddenLayers * noNeuronsPerHiddenLayer && i < noInputNeurons + noHiddenLayers * noNeuronsPerHiddenLayer + noOutputNeurons) {
            const value = parseFloat(d3.select(this).text());
            if (value > maxOutputValue) {
                maxOutputValue = value;
                maxOutputNeuronIndex = i;
            }
        }
    });

const neuronToChange = svg.select(`circle[cx='${(noHiddenLayers + 2) * layerGap}'][cy='${2 * neuronRadius + centerY + (maxOutputNeuronIndex - noInputNeurons - noHiddenLayers * noNeuronsPerHiddenLayer) * neuronGap}']`);
console.log(neuronToChange.node());
d3.selectAll(".neuron").attr("fill", "yellow");
d3.selectAll(".neuron")
    .filter((d, i) => i === maxOutputNeuronIndex)
    .style("fill", "red");
neuronToChange.attr("fill", "red");
console.log(neuronToChange.node());

// Update the color of the neuron with the largest value
if (maxOutputNeuronIndex !== -1) {
    const newColor = "#FF0000";  // Red color, for example
    console.log("Max Output Neuron Index:", maxOutputNeuronIndex);

    d3.selectAll(".neuron")
        .filter((d, i) => i === maxOutputNeuronIndex)
        .attr("fill", newColor);
}

const textOffset = 30;  // Adjust this value as needed

svg.selectAll(".neuron")
    .filter(function () {
        return d3.select(this).attr("cx") == (noHiddenLayers + 2) * layerGap;
    })
    .each(function (d, i) {
        const neuron = d3.select(this);
        const x = parseFloat(neuron.attr("cx")) + textOffset;
        const y = parseFloat(neuron.attr("cy"));

        svg.append("text")
            .attr("x", x)
            .attr("y", y)
            .attr("dy", "0.35em")  // to vertically center the text
            .attr("text-anchor", "start")
            .text(i)  // The number corresponding to the neuron
            .attr("font-size", "14px")
            .attr("fill", "black");
    });
