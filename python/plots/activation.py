import numpy as np
import matplotlib.pyplot as plt

x = np.linspace(-10, 10, 400)
sigmoid = 1 / (1 + np.exp(-x))
relu = np.maximum(0, x)
tanh = np.tanh(x)

plt.figure(figsize=(10, 8))
plt.plot(x, sigmoid, label='Sigmoid')
plt.plot(x, relu, label='ReLU')
plt.plot(x, tanh, label='Tanh')
plt.title("Activation Functions")
plt.xlabel("Input value")
plt.ylabel("Output value")
plt.legend()
plt.grid(True)
plt.show()

# Overfitting vs Underfitting
epochs = np.arange(1, 21)
training_loss = np.random.rand(20) * 0.1  # Random values for illustration
validation_loss = training_loss + np.random.rand(20) * 0.05  # Adding some noise

plt.figure(figsize=(10, 8))
plt.plot(epochs, training_loss, label='Training Loss')
plt.plot(epochs, validation_loss, label='Validation Loss')
plt.title("Overfitting vs Underfitting")
plt.xlabel("Epochs")
plt.ylabel("Loss")
plt.legend()
plt.grid(True)
plt.show()

# Learning Rate Schedules
# Assuming learning rate decay
initial_lr = 0.1
decay_rate = 0.9
epochs = np.arange(1, 51)
learning_rates = initial_lr * (decay_rate ** (epochs // 10))

plt.figure(figsize=(10, 8))
plt.plot(epochs, learning_rates)
plt.title("Learning Rate Decay")
plt.xlabel("Epochs")
plt.ylabel("Learning Rate")
plt.grid(True)
plt.show()
