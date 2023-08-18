"""
Run a rest API exposing the yolov5s object detection model
"""
import argparse
import io
from PIL import Image
import json
import torch
from flask import Flask, request, send_from_directory, jsonify
from flask_cors import CORS
from flask import make_response
import datetime
DATETIME_FORMAT = "%Y-%m-%d_%H-%M-%S-%f"
app = Flask(__name__)
CORS(app, supports_credentials=True, origins='*',allow_headers=["Content-Type", "Authorization", "Access-Control-Allow-Credentials"])

# S3 시작
import boto3
from botocore.exceptions import NoCredentialsError
from werkzeug.utils import secure_filename
#
S3_BUCKET = 'sh-bucket'
S3_ACCESS_KEY = 'AKIA5JRABH7N5QGWEFWN'
S3_SECRET_KEY = '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'

s3 = boto3.client('s3', aws_access_key_id=S3_ACCESS_KEY, aws_secret_access_key=S3_SECRET_KEY)

DETECTION_URL = "/v1/object-detection/yolov5"
IMAGE_DIRECTORY = "static"

@app.route(DETECTION_URL, methods=["POST"])
def predict():
    if not request.method == "POST":
        return "Only POST requests are allowed."
    # else:
    #     file = request.files["file"]
    #     img_bytes2 = file.read()
    #     img2 = Image.open(io.BytesIO(img_bytes2))  
    #     results2 = model([img2])
    #     results2.render()
    #     filename='이름'
    #     s3.upload_fileobj(results2, S3_BUCKET, 'filename')
    #     image_url = f'https://{S3_BUCKET}.s3.amazonaws.com/{filename}'
    if request.files.get("image"):
        image_file = request.files["image"]
        image_bytes = image_file.read()
        img = Image.open(io.BytesIO(image_bytes))
        results = model(img, size=640)  # reduce size=320 for faster inference
        results2= model([img])      
        results2.render() # show_prob_thr=1.0

        # results2.show()

        now_time = datetime.datetime.now().strftime(DATETIME_FORMAT)
        img_savename = f"static/{now_time}.png"
        Image.fromarray(results2.ims[0]).save(img_savename)
        
        with open(img_savename, 'rb') as file:
            s3.upload_fileobj(file, S3_BUCKET, img_savename)
        image_url = f'https://{S3_BUCKET}.s3.amazonaws.com/{img_savename}'
        new = {'image_url': image_url}
        DF = results.pandas().xyxy[0]
        DF_dict = DF.to_dict()
# 딕셔너리와 new를 합쳐서 JSON 형태로 생성
        combined_data = {**new, **DF_dict}
# JSON 형태로 변환
        final_json = json.dumps(combined_data, ensure_ascii=False)
        return final_json

    #     return DF.to_json(orient='records')
    # return '잘못된 정보입니다.'
    
if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Flask api exposing yolov5 model")
    parser.add_argument("--port", default=5000, type=int, help="port number")
    parser.add_argument('--model', default='yolov5s', help='model to run, i.e. --model yolov5s')
    args = parser.parse_args()

    model = torch.hub.load('ultralytics/yolov5', args.model)
    app.run(host="0.0.0.0", port=args.port)  # debug=True causes Restarting with stat
