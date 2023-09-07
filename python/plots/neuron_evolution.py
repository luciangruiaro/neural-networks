import json
import matplotlib.pyplot as plt
import sys
import os

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

    for i, weight_list in enumerate(weight_lists, 1):
        plt.plot(weight_list, label=f"Weight {i}")

    # Plot bias weights
    plt.plot(bias_weights, label="Bias Weight", linestyle='--', color='black')  # Using dashed line for bias weights

    plt.legend()
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
