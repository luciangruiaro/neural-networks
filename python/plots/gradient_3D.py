import numpy as np
import matplotlib.pyplot as plt

def plot_3d_gradient():
    x = np.linspace(-10, 10, 400)
    y = np.linspace(-10, 10, 400)
    X, Y = np.meshgrid(x, y)
    Z = X**2 + Y**2

    fig = plt.figure(figsize=(10, 8))
    ax = fig.add_subplot(111, projection='3d')

    ax.plot_surface(X, Y, Z, cmap='viridis')

    ax.set_title("3D Function")
    ax.set_xlabel("x")
    ax.set_ylabel("y")
    ax.set_zlabel("f(x, y)")

    plt.show()

plot_3d_gradient()

def gradient_descent_3d(learning_rate=0.1, epochs=10):
    gradient = lambda x, y: (2*x, 2*y)

    # Start at a random point
    current_x, current_y = np.random.uniform(-10, 10, size=2)

    x = np.linspace(-10, 10, 400)
    y = np.linspace(-10, 10, 400)
    X, Y = np.meshgrid(x, y)
    Z = X**2 + Y**2

    fig = plt.figure(figsize=(10, 8))
    ax = fig.add_subplot(111, projection='3d')

    ax.plot_surface(X, Y, Z, cmap='viridis', alpha=0.5)

    for _ in range(epochs):
        ax.scatter(current_x, current_y, current_x**2 + current_y**2, color='red')
        grad_x, grad_y = gradient(current_x, current_y)
        current_x -= learning_rate * grad_x
        current_y -= learning_rate * grad_y

    ax.set_title("Gradient Descent in 3D")
    ax.set_xlabel("x")
    ax.set_ylabel("y")
    ax.set_zlabel("f(x, y)")

    plt.show()

gradient_descent_3d()
