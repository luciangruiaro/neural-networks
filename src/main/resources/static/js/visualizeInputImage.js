document.addEventListener("DOMContentLoaded", function () {
    const svgWidth = 1200;
    const svgHeight = 1200;
    const cellSize = 20;
    const gridSize = 8;
    const transitionDuration = 4000;

    const svg = d3.select("#gridVisual")
        .append("svg")
        .attr("width", svgWidth)
        .attr("height", svgHeight);

    // const binaryMatrix = [
    //     [0, 1, 1, 1, 0],
    //     [0, 0, 0, 1, 0],
    //     [0, 1, 1, 1, 0],
    //     [0, 1, 0, 0, 0],
    //     [0, 1, 1, 1, 0]
    // ];

    const binaryMatrix = [
        [0, 0, 1, 1, 1, 1, 0, 0],
        [0, 0, 0, 0, 0, 1, 0, 0],
        [0, 0, 0, 0, 0, 1, 0, 0],
        [0, 0, 1, 1, 1, 1, 0, 0],
        [0, 0, 1, 0, 0, 0, 0, 0],
        [0, 0, 1, 0, 0, 0, 0, 0],
        [0, 0, 1, 0, 0, 0, 0, 0],
        [0, 0, 1, 1, 1, 1, 0, 0]
    ];

    const cells = [];
    for (let row = 0; row < gridSize; row++) {
        for (let col = 0; col < gridSize; col++) {
            cells.push({
                row: row,
                col: col,
                color: binaryMatrix[row][col] ? "#00ffbe" : "#e0e0e0"
            });
        }
    }

    const rects = svg.selectAll("rect")
        .data(cells)
        .enter()
        .append("rect")
        .attr("x", d => d.col * cellSize)
        .attr("y", d => d.row * cellSize)
        .attr("width", cellSize)
        .attr("height", cellSize)
        .attr("fill", d => d.color)
        .attr("stroke", "#333");

    let isAnimated = false;

    svg.on("click", function() {
        if (!isAnimated) {
            isAnimated = true;

            // Animation: Convert the grid into a row vector
            rects.transition()
                .duration(transitionDuration)
                .attr("x", (d, i) => i * cellSize)
                .attr("y", svgHeight / 2 - cellSize / 2)
                .on("end", function () {
                    // After the row vector animation, transpose it to a column vector
                    d3.selectAll("rect").transition()
                        .duration(transitionDuration)
                        .attr("x", svgWidth / 2 - cellSize / 2)
                        .attr("y", (d, i) => i * cellSize);
                });
        }
    });
});
