document.addEventListener("DOMContentLoaded", function () {
    const svgWidth = 800;
    const svgHeight = 400;
    const neuronRadius = 60;
    const inputRadius = 20;
    const intermediaryRadius = 15;
    const outputRadius = 20;
    const neuronX = 3 * svgWidth / 4 + 40;
    const neuronY = svgHeight / 2;

    const svg = d3.select("#neuronVisual")
        .append("svg")
        .attr("width", svgWidth)
        .attr("height", svgHeight);

    // Input circles and animations
    const inputGap = 80;
    const inputStartY = neuronY - inputGap;
    const inputX = svgWidth / 6;

    const inputCircles = svg.selectAll(".input-circle")
        .data(inputValues)
        .enter()
        .append("circle")
        .attr("cx", inputX)
        .attr("cy", (d, i) => inputStartY + i * inputGap)
        .attr("r", inputRadius)
        .attr("class", "input-circle");

    inputCircles.each(function (d) {
        if (d === 1) {
            d3.select(this)
                .transition()
                .duration(2000)
                .style("fill", "#00ffbe");
        }
    });

    svg.selectAll(".input-text")
        .data(inputValues)
        .enter()
        .append("text")
        .attr("x", inputX)
        .attr("y", (d, i) => inputStartY + i * inputGap)
        .attr("dy", "0.35em")
        .attr("text-anchor", "middle")
        .text(d => d);

    // Intermediary circle and animation
    const intermediaryX = neuronX - 2.5 * neuronRadius;
    const intermediaryY = neuronY;

    const aggregatorCircle = svg.append("circle")
        .attr("cx", intermediaryX)
        .attr("cy", intermediaryY)
        .attr("r", intermediaryRadius)
        .attr("class", "intermediary-circle");

    if (inputValues.some(val => val !== 0)) {
        aggregatorCircle.transition()
            .delay(2000)  // Wait for input animations to finish
            .duration(1000)
            .style("fill", "black");
    }

    // Line from input to intermediary circle
    for (let i = 0; i < 3; i++) {
        const inputY = inputStartY + i * inputGap;

        svg.append("line")
            .attr("x1", inputX + inputRadius)
            .attr("y1", inputY)
            .attr("x2", intermediaryX - intermediaryRadius)
            .attr("y2", inputY)
            .attr("class", "line")
            .transition()
            .duration(1000)
            .attr("y2", intermediaryY);

        svg.append("text")
            .attr("x", (inputX + intermediaryX) / 2)
            .attr("y", inputY - 10)
            .attr("text-anchor", "middle")
            .text(`W: ${weights[i].toFixed(2)}`);
    }

    // Line from intermediary circle to neuron
    svg.append("line")
        .attr("x1", intermediaryX + intermediaryRadius)
        .attr("y1", intermediaryY)
        .attr("x2", neuronX - neuronRadius)
        .attr("y2", neuronY)
        .attr("class", "line");

    // Bias weight
    svg.append("text")
        .attr("x", intermediaryX)
        .attr("y", neuronY + 30)
        .attr("text-anchor", "middle")
        .text(`Bias W: ${biasWeight.toFixed(2)}`);

    // Neuron circle and animation
    const neuronCircle = svg.append("circle")
        .attr("cx", neuronX)
        .attr("cy", neuronY)
        .attr("r", neuronRadius)
        .style("fill", "#0020bb");
    const neuronOutputText = svg.append("text")
        .attr("fill", activated ? "black" : "white")
        .attr("x", neuronX)
        .attr("y", neuronY)
        .attr("dy", "-0.35em")
        .attr("text-anchor", "middle")
        .attr("opacity", 0)  // Initially set to invisible
        .text(`Output: ${output.toFixed(2)}`);
    neuronOutputText.transition()
        .delay(3000)  // Wait for aggregator animation to finish
        .duration(3000)
        .attr("opacity", 1);  // Make the text visible
    if (activated) {
        neuronCircle.transition()
            .delay(3000)  // Wait for aggregator animation to finish
            .duration(3000)
            .style("fill", "#00ffbe");
    }

    // Output line and circle
    const outputX = neuronX + neuronRadius + 50;
    svg.append("line")
        .attr("x1", neuronX + neuronRadius)
        .attr("y1", neuronY)
        .attr("x2", outputX)
        .attr("y2", neuronY)
        .attr("class", "line");

    const outputCircle = svg.append("circle")
        .attr("cx", outputX)
        .attr("cy", neuronY)
        .attr("r", outputRadius)
        .attr("class", "input-circle");  // Using the same class as input circle for consistent styling

    const outputText = svg.append("text")
        .attr("x", outputX)
        .attr("y", neuronY)
        .attr("dy", "0.35em")
        .attr("text-anchor", "middle")
        .attr("opacity", 0)  // Initially set to invisible
        .text(outputClassified);
    outputText.transition()
        .delay(7000)  // Wait for neuron animation to finish
        .duration(2000)
        .attr("opacity", 1);  // Make the text visible
    if (outputClassified === 1) {
        outputCircle.transition()
            .delay(7000)  // Wait for neuron animation to finish
            .duration(2000)
            .style("fill", "#00ffbe");
    }


});
