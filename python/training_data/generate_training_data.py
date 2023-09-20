import argparse
import random
import tensorflow as tf
from skimage.transform import resize


def generate_nxn_digit(n=28):
    # Load MNIST data
    mnist = tf.keras.datasets.mnist
    (train_images, train_labels), _ = mnist.load_data()

    # Normalize the images
    train_images = train_images / 255.0

    # Randomly select an image and its label
    idx = random.randint(0, len(train_images) - 1)
    img = train_images[idx]
    label = train_labels[idx]

    # Resize the image to n x n
    img_resized = resize(img, (n, n), mode='reflect', anti_aliasing=True)

    # Binarize the image
    threshold = 0.5
    binarized_img = (img_resized > threshold).astype(int)

    print(binarized_img)
    print(f"\nThis is a {label}")

    return binarized_img, label


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Generate n x n digit.')
    parser.add_argument('--n', type=int, default=28,
                        help='The value for n in generate_nxn_digit.')

    args = parser.parse_args()

    if args.n == 0:
        args.n = 28

    generate_nxn_digit(args.n)
