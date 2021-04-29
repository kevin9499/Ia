from model import predictImage
from flask import Flask, request, jsonify
from PIL import Image
import numpy as np
import os

app = Flask(__name__)
size = 32, 32

@app.route("/upload", methods=["POST"])
def process_image():
    print(request.files)
    file = request.files['image']

    # Read the image via file.stream
    img = Image.open(file.stream)
    img.thumbnail(size)
    img.save(file.filename, "JPEG")

    result= predictImage(file.filename)
    return jsonify({'message': 'success', 'result': int(np.argmax(result))})


if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0")