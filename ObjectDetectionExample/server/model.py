import tensorflow as tf
import cv2
import numpy as np
from tensorflow.keras import datasets, layers, models


def TensorObjectModel():
    model = models.Sequential()
    model.add(layers.Conv2D(32, (3, 3), activation='relu', input_shape=(32, 32, 3)))
    model.add(layers.MaxPooling2D((2, 2)))
    model.add(layers.Conv2D(64, (3, 3), activation='relu'))
    model.add(layers.MaxPooling2D((2, 2)))
    model.add(layers.Conv2D(64, (3, 3), activation='relu'))
    model.add(layers.Flatten())
    model.add(layers.Dense(64, activation='relu'))
    model.add(layers.Dense(10))
    return  model;

def trainModel():
    (train_images, train_labels), (test_images, test_labels) = datasets.cifar10.load_data()
    # Normalize pixel values to be between 0 and 1
    train_images, test_images = train_images / 255.0, test_images / 255.0

    model= TensorObjectModel()
    model.compile(optimizer='adam',
              loss=tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True),
              metrics=['accuracy'])
    model.fit(train_images, train_labels, epochs=50, 
                    validation_data=(test_images, test_labels))
    model.save_weights("model_objects.h5")

def predictImage(imageUri):
    data= []
    src = cv2.imread(imageUri)
    output = cv2.resize(src, (32,32))
    data.append(output)
    testImages= np.array(data)
    testImages= testImages / 255.0

    model=  TensorObjectModel()
    model.load_weights("model_objects.h5")
    result= model.predict(testImages)
    return result[0]

if __name__ == "__main__":
    trainModel();