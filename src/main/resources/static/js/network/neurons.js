function drawNeuronValues() {
    // Draw values for input neurons
    svg.selectAll(".inputNeuron")
        .each(function (d, i) {
            const neuron = d3.select(this);
            const value = inputNeuronValues[i];
            drawValueForNeuron(neuron, value);
        });

    // Draw values for hidden neurons
    svg.selectAll(".hiddenNeuron")
        .each(function (d, i) {
            const neuron = d3.select(this);
            const value = hiddenNeuronValues[i];
            drawValueForNeuron(neuron, value);
        });

    // Draw values for output neurons
    svg.selectAll(".outputNeuron")
        .each(function (d, i) {
            const neuron = d3.select(this);
            const value = outputNeuronValues[i];
            drawValueForNeuron(neuron, value);
        });
}

function drawValueForNeuron(neuron, value) {
    const x = parseFloat(neuron.attr("cx"));
    const y = parseFloat(neuron.attr("cy"));
    svg.append("text")
        .attr("x", x)
        .attr("y", y)
        .attr("dy", "0.35em")  // to vertically center the text
        .attr("text-anchor", "middle")
        .text(value.toFixed(2))
        .attr("font-size", "10px")
        .attr("fill", "white");
}


function drawOutputNeuronsLabels() {
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
}

function highlightOutputNeuron() {
    // Find the max output neuron value and its index
    const maxOutputValue = Math.max(...outputNeuronValues);
    const maxOutputValueIndex = outputNeuronValues.indexOf(maxOutputValue);
    const selectedNeuron = svg.selectAll(".outputNeuron")
        .filter((d, i) => i === maxOutputValueIndex);
    selectedNeuron.style("fill", "red");
}