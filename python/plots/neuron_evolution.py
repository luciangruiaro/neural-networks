import json
import matplotlib.pyplot as plt
import os
import sys

# Predefined filename
FILENAME = "neuronStates.json"


def process_json_data(data):
    # Convert the JSON string to a Python list
    neuron_states = json.loads(data)

    # Extract weights from neuron_states
    weights = [state['weights'] for state in neuron_states]
    bias_weights = [state['biasWeight'] for state in neuron_states]

    # Split weights into separate lists (assuming each NeuronState has an equal number of weights)
    weight_lists = list(zip(*weights))

    # Create a new figure with a specified size (width, height)
    plt.figure(figsize=(10, 6))

    # Plotting weights
    for i, weight_list in enumerate(weight_lists, 1):
        plt.plot(weight_list, label=f"Weight {i}")

    # Plotting bias weights
    plt.plot(bias_weights, color='black', label="Bias Weight")

    # Setting labels, title, grid, and legend
    plt.xlabel('Epochs')
    plt.ylabel('Weight Value')
    plt.title('Evolution of Neuron Weights and Bias Over Epochs')
    plt.grid(True, which='both', linestyle='--', linewidth=0.5)
    plt.legend()
    plt.tight_layout()
    plt.show()


def main():
    # Check if we have a command line argument
    if len(sys.argv) > 1:
        input_arg = sys.argv[1]

        # Check if input_arg is a file path
        if os.path.isfile(input_arg):
            with open(input_arg, 'r') as file:
                data = file.read()
            process_json_data(data)
        else:
            # Treat as direct JSON data
            process_json_data(input_arg)
    else:
        # If no command line arguments, read from the predefined file
        with open(FILENAME, 'r') as file:
            data = file.read()
        process_json_data(data)


if __name__ == "__main__":
    main()
