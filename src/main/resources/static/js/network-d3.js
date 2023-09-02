const N = 3;
const P = 2;
const M = 4;
const neuronRadius = 15;
const layerGap = 150;
const neuronGap = 40;
// Adjust vertical position for centering
const maxNeurons = Math.max(N * N, M, 10);
const centerY = maxNeurons * neuronGap / 2;

// Adjust the SVG width to ensure all layers are visible
const svgWidth = (P + 3) * layerGap; // Increase the multiplier for more space
const svgHeight = maxNeurons * neuronGap + 4 * neuronRadius; // Add padding for topmost and bottommost neurons


const svg = d3.select("#network")
        .append("svg")
        .attr("width", svgWidth)
        .attr("height", svgHeight)
    // .style("background-color", "rgba(72,185,218,0.2)")  // Temporary red background
;


// Draw Input Neurons
for (let i = 0; i < N * N; i++) {
    svg.append("circle")
        .attr("class", "neuron")
        .attr("cx", neuronRadius * 2)
        .attr("cy", 2 * neuronRadius + (i + 1) * neuronGap)  // Adjusted for padding
        .attr("r", neuronRadius);
}

// Draw Hidden Layers
for (let p = 0; p < P; p++) {
    for (let m = 0; m < M; m++) {
        svg.append("circle")
            .attr("class", "neuron")
            .attr("cx", (p + 2) * layerGap)
            .attr("cy", 2 * neuronRadius + centerY + (m - M / 2) * neuronGap)  // Adjusted for padding
            .attr("r", neuronRadius);
    }
}

// Draw Output Neurons
for (let o = 0; o < 10; o++) {
    svg.append("circle")
        .attr("class", "neuron")
        .attr("cx", (P + 2) * layerGap)
        .attr("cy", 2 * neuronRadius + centerY + (o - 10 / 2) * neuronGap)  // Adjusted for padding
        .attr("r", neuronRadius);
}

svg.selectAll(".neuron")
    .each(function () {
        const neuron = d3.select(this);
        console.log("Neuron position:", neuron.attr("cx"), neuron.attr("cy"));
    });


// Adjust line endpoints to connect to neuron edges
function getLineEndpoints(fromNeuron, toNeuron) {
    const x1 = parseFloat(fromNeuron.attr("cx"));
    const y1 = parseFloat(fromNeuron.attr("cy"));
    const x2 = parseFloat(toNeuron.attr("cx"));
    const y2 = parseFloat(toNeuron.attr("cy"));

    const angle = Math.atan2(y2 - y1, x2 - x1);
    return {
        x1: x1 + Math.cos(angle) * neuronRadius,
        y1: y1 + Math.sin(angle) * neuronRadius,
        x2: x2 - Math.cos(angle) * neuronRadius,
        y2: y2 - Math.sin(angle) * neuronRadius
    };
}

// Draw Lines (for simplicity, only between input and first hidden layer)
svg.selectAll(".neuron")
    .each(function (d, i) {
        const fromNeuron = d3.select(this);
        if (fromNeuron.attr("cx") < layerGap) {
            svg.selectAll(".neuron")
                .each(function () {
                    const toNeuron = d3.select(this);
                    if (toNeuron.attr("cx") == layerGap * 2) {
                        svg.append("line")
                            .attr("class", "line")
                            .attr("x1", fromNeuron.attr("cx"))
                            .attr("y1", fromNeuron.attr("cy"))
                            .attr("x2", toNeuron.attr("cx"))
                            .attr("y2", toNeuron.attr("cy"));
                    }
                });
        }
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

for (let p = 0; p < P; p++) {
    const currentHiddenLayer = svg.selectAll(".neuron")
        .filter(function () {
            return d3.select(this).attr("cx") == (p + 2) * layerGap;
        });
    connectLayers(previousLayer, currentHiddenLayer);
    previousLayer = currentHiddenLayer;
}

const outputLayer = svg.selectAll(".neuron")
    .filter(function () {
        return d3.select(this).attr("cx") == (P + 2) * layerGap;
    });

connectLayers(previousLayer, outputLayer);

// Add random numbers inside neurons
svg.selectAll(".neuron")
    .each(function () {
        const neuron = d3.select(this);
        const x = parseFloat(neuron.attr("cx"));
        const y = parseFloat(neuron.attr("cy"));
        const randomValue = (Math.random()).toFixed(2);

        svg.append("text")
            .attr("x", x)
            .attr("y", y)
            .attr("dy", "0.35em")  // to vertically center the text
            .attr("text-anchor", "middle")
            .text(randomValue)
            .attr("font-size", "10px")
            .attr("fill", "white");
    });