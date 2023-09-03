// Set neural network layout
const noInputNeurons = +document.getElementById('noInputNeurons').value; // number of inputs neurons
const noHiddenLayers = +document.getElementById('noHiddenLayers').value; // number of hidden layers
const noNeuronsPerHiddenLayer = +document.getElementById('noNeuronsPerHiddenLayer').value; // number of neurons per hidden layer
const noOutputNeurons = +document.getElementById('noOutputNeurons').value; // number of output neurons
const noMaxNeuronsDisplay = 13; // number of inputs neurons that can be displayed

// Set values inside neurons (coming from the neural network)
const inputNeuronValues = document.getElementById('inputNeuronValues').value.split(',').map(Number).filter(value => !isNaN(value));
const hiddenNeuronValues = document.getElementById('hiddenNeuronValues').value.split(',').map(Number).filter(value => !isNaN(value));
const outputNeuronValues = document.getElementById('outputNeuronValues').value.split(',').map(Number).filter(value => !isNaN(value));
let neuronIndex = 0;

// Set SVG dimensions
const neuronRadius = 17;
const layerGap = 150;
const neuronGap = 40;

// Adjust vertical position for centering
const maxNeurons = Math.max(Math.min(noInputNeurons, noMaxNeuronsDisplay), Math.min(noNeuronsPerHiddenLayer, noMaxNeuronsDisplay), Math.min(noOutputNeurons, noMaxNeuronsDisplay));
const centerY = maxNeurons * neuronGap / 2;

// Adjust the SVG width to ensure all layers are visible
const svgWidth = (noHiddenLayers + 3) * layerGap; // Increase the multiplier for more space
const svgHeight = maxNeurons * neuronGap + 4 * neuronRadius; // Add padding for topmost and bottommost neurons

// Create SVG
const svg = d3.select("#network")
    .append("svg")
    .attr("width", svgWidth)
    .attr("height", svgHeight);

// Layers
drawLayers();
// Synapses
drawSynapses();
// Neurons
drawNeuronValues();
drawOutputNeuronsLabels();
highlightOutputNeuron();
