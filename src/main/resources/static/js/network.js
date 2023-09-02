const N = 16; // input neurons
const P = 1; // hidden layers count
const M = 6; // neurons per hidden layer

// Generate Input Neurons
const inputNeurons = document.getElementById('inputNeurons');
for (let i = 0; i < N; i++) {
    const neuron = document.createElement('div');
    neuron.classList.add('neuron');
    inputNeurons.appendChild(neuron);
}

// Generate Hidden Layers
const hiddenLayers = document.getElementById('hiddenLayers');
for (let i = 0; i < P; i++) {
    const layer = document.createElement('div');
    layer.classList.add('layer');
    for (let j = 0; j < M; j++) {
        const neuron = document.createElement('div');
        neuron.classList.add('neuron');
        layer.appendChild(neuron);
    }
    hiddenLayers.appendChild(layer);
}

// Generate Output Neurons
const outputNeurons = document.getElementById('outputNeurons');
for (let i = 0; i < 10; i++) {
    const neuron = document.createElement('div');
    neuron.classList.add('neuron');
    outputNeurons.appendChild(neuron);
}

drawLines();

function drawLines() {
    const svg = document.querySelector('.lines-container');
    const inputNeurons = document.querySelectorAll('.input-layer .neuron');
    const hiddenLayers = document.querySelectorAll('.hidden-layers .layer');
    const outputNeurons = document.querySelectorAll('.output-layer .neuron');

    // Draw lines from input to first hidden layer
    connectLayers(inputNeurons, hiddenLayers[0].querySelectorAll('.neuron'), svg);

    // Draw lines between hidden layers
    for (let i = 0; i < hiddenLayers.length - 1; i++) {
        const currentLayerNeurons = hiddenLayers[i].querySelectorAll('.neuron');
        const nextLayerNeurons = hiddenLayers[i + 1].querySelectorAll('.neuron');
        connectLayers(currentLayerNeurons, nextLayerNeurons, svg);
    }

    // Draw lines from last hidden layer to output
    const lastHiddenLayerNeurons = hiddenLayers[hiddenLayers.length - 1].querySelectorAll('.neuron');
    connectLayers(lastHiddenLayerNeurons, outputNeurons, svg);
}

function connectLayers(fromNeurons, toNeurons, svg) {
    const svgBounds = svg.getBoundingClientRect();

    fromNeurons.forEach(fromNeuron => {
        const fromPos = fromNeuron.getBoundingClientRect();
        toNeurons.forEach(toNeuron => {
            const toPos = toNeuron.getBoundingClientRect();
            const line = document.createElementNS('http://www.w3.org/2000/svg', 'line');
            line.setAttribute('x1', fromPos.left + fromPos.width / 2 - svgBounds.left);
            line.setAttribute('y1', fromPos.top + fromPos.height / 2 - svgBounds.top);
            line.setAttribute('x2', toPos.left + toPos.width / 2 - svgBounds.left);
            line.setAttribute('y2', toPos.top + toPos.height / 2 - svgBounds.top);
            line.setAttribute('stroke', 'black');
            svg.appendChild(line);
        });
    });
}

