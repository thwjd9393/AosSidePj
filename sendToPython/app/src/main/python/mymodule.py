import cv2
import numpy as np


def add(a, b):
    return a + b


def add_and_sum(a, b):
    return (a + b, a - b)


def capture_image():
    """카메라로 사진을 찍고 이미지 프레임(NumPy 배열)을 반환합니다."""
    try:
        cap = cv2.VideoCapture(0)  # 0번 카메라 열기 (기본 카메라)
        if not cap.isOpened():
            raise IOError("Cannot open webcam")

        ret, frame = cap.read()  # 프레임 읽기
        cap.release()

        if not ret:
            raise IOError("Failed to capture image")

        return frame  # 촬영된 이미지 프레임(NumPy 배열) 반환

    except Exception as e:
        print(f"Error capturing image: {e}")
        return None

def encode_to_bytes(frame):
    """이미지 프레임을 JPEG 형식으로 인코딩하여 바이트 배열로 반환합니다."""
    try:
        success, img_encoded = cv2.imencode('.jpg', frame)
        if success:
            return img_encoded.tobytes()
        else:
            print("Error encoding frame to JPEG")
            return None
    except Exception as e:
        print(f"Error encoding frame: {e}")
        return None

def capture_and_get_bytes():
    """카메라로 사진을 찍고 바이트 배열 형태로 반환하는 통합 함수."""
    frame = capture_image()
    if frame is not None:
        return encode_to_bytes(frame)
    else:
        return None