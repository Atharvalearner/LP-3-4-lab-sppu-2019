import cv2
import numpy as np
import matplotlib.pyplot as plt

def import_image(image_path):
    image = cv2.imread(image_path)    # Load the image using OpenCV
    return image

def create_histogram_with_opencv(image, channel):
    hist = cv2.calcHist([image], [channel], None, [256], [0, 256])
    plt.figure(figsize=(10, 5))
    plt.plot(hist, color='gray' if channel == 0 else ('b' if channel == 0 else 'r' if channel == 2 else 'g'))
    plt.title(f'Histogram for Channel {channel} (using OpenCV)')
    plt.xlabel('Pixel Intensity')
    plt.ylabel('Frequency')
    plt.xlim([0, 256])
    plt.show()

def display_images(original, gray, blurred):    # Display the images
    plt.figure(figsize=(15, 5))

    plt.subplot(1, 3, 1)    # display original image
    plt.imshow(cv2.cvtColor(original, cv2.COLOR_BGR2RGB))
    plt.title('Original RGB Image')
    plt.axis('off')

    plt.subplot(1, 3, 2)    # display grey image
    plt.imshow(gray, cmap='gray')
    plt.title('Grayscale Image')
    plt.axis('off')

    plt.subplot(1, 3, 3)    # display blur grey image
    plt.imshow(blurred, cmap='gray')
    plt.title('Blurred Grayscale Image')
    plt.axis('off')

    plt.show()

def main(image_path):
    image = import_image(image_path)
    gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)   # Step C: Converting the Image to a 2-D Matrix
    blurred_image = cv2.GaussianBlur(gray_image, (11, 11), 0)   # Step D: Blurring the Grayscale Image

    for i in range(3):  # 0: Blue, 1: Green, 2: Red
        create_histogram_with_opencv(image, i)

    display_images(image, gray_image, blurred_image)

if __name__ == "__main__":
    image_path = '/content/images.jpg'  # Change this to your image file
    main(image_path)