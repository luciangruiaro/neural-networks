import argparse
import random
import tensorflow as tf
from skimage.transform import resize


def generate_nxn_digit(n=28, label=-1):
    # Load MNIST data
    mnist = tf.keras.datasets.mnist
    (train_images, train_labels), _ = mnist.load_data()

    # Normalize the images
    train_images = train_images / 255.0

    if label != -1:
        # Filter images by the given label
        filtered_indices = [i for i, lbl in enumerate(train_labels) if lbl == label]
        idx = random.choice(filtered_indices)
    else:
        idx = random.randint(0, len(train_images) - 1)

    img = train_images[idx]
    label = train_labels[idx]

    # Resize the image to n x n
    img_resized = resize(img, (n, n), mode='reflect', anti_aliasing=True)

    # Binarize the image
    threshold = 0.5
    binarized_img = (img_resized > threshold).astype(int)

    print("BEGIN_IMAGE")
    for row in binarized_img:
        print(" ".join(map(str, row)))
    print("END_IMAGE")
    print(f"LABEL: {label}")


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Generate n x n digit.')
    parser.add_argument('--n', type=int, default=28,
                        help='The value for n in generate_nxn_digit.')
    parser.add_argument('--count', type=int, default=1,
                        help='Number of results to be generated.')
    parser.add_argument('--label', type=int, default=-1,
                        help='Label of the digit to be generated. -1 for any number.')

    args = parser.parse_args()

    if args.n == 0:
        args.n = 28

    for _ in range(args.count):
        generate_nxn_digit(args.n, args.label)
