import base64

def encode_image_from_path(image_path):
    """주어진 경로의 이미지 파일을 읽어 Base64로 인코딩하여 반환합니다."""
    try:
        with open(image_path, "rb") as image_file:
            encoded_string = base64.b64encode(image_file.read()).decode('utf-8')
        return encoded_string
    except FileNotFoundError:
        print(f"[Python] Error: Image file not found at {image_path}")
        return None
    except Exception as e:
        print(f"[Python] Error encoding image: {e}")
        return None

# 테스트 (안드로이드에서는 이 부분이 실행되지 않습니다)
if __name__ == "__main__":
    image_path = "C:\\Users\\sojung\\Downloads\\logo.png"
    encoded_image = encode_image_from_path(image_path)
    if encoded_image:
        print(f"[Python] Base64 Encoded Image (first 50 chars): {encoded_image[:50]}...")