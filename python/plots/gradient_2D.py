import numpy as np
import matplotlib.pyplot as plt

def plot_gradient():
    x = np.linspace(-10, 10, 400)
    y = x**2
    y_prime = 2*x

    plt.figure(figsize=(8, 6))

    plt.plot(x, y, label="f(x) = x^2")
    plt.plot(x, y_prime, label="f'(x) = 2x", linestyle='--')

    plt.title("Function and its Gradient")
    plt.xlabel("x")
    plt.ylabel("y")
    plt.legend()
    plt.grid(True)

    plt.show()

plot_gradient()

def gradient_descent(learning_rate=0.1, epochs=10):
    # Gradient of f(x) = x^2 is f'(x) = 2x
    gradient = lambda x: 2*x

    # Start at a random point
    current_x = np.random.uniform(-10, 10)

    plt.figure(figsize=(8, 6))
    x = np.linspace(-10, 10, 400)
    plt.plot(x, x**2, label="f(x) = x^2")

    # Iteratively move towards the minimum
    for _ in range(epochs):
        plt.scatter(current_x, current_x**2, color='red')
        current_x -= learning_rate * gradient(current_x)

    plt.title("Gradient Descent on f(x) = x^2")
    plt.xlabel("x")
    plt.ylabel("y")
    plt.legend()
    plt.grid(True)

    plt.show()

gradient_descent()
