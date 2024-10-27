import cv2
import os
import numpy as np

def extract_frames_from_gif(gif_path, output_folder, num_frames):
    # Create output folder if it doesn't exist
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)
    
    # Load the GIF
    gif = cv2.VideoCapture(gif_path)
    
    frame_count = int(gif.get(cv2.CAP_PROP_FRAME_COUNT))
    frames_to_extract = min(num_frames, frame_count)
    
    for i in range(frames_to_extract):
        ret, frame = gif.read()
        if not ret:
            break
        # Resize the frame to 178x138
        resized_frame = cv2.resize(frame, (178, 138))
        
        # Create a new image with a transparent background
        new_image = np.zeros((256, 512, 4), dtype=np.uint8)
        
        # Place the resized frame at the top-left corner of the new image
        new_image[0:138, 0:178, :3] = resized_frame
        new_image[0:138, 0:178, 3] = 255  # Set alpha channel to fully opaque
        
        frame_path = os.path.join(output_folder, f"{i}.png")
        cv2.imwrite(frame_path, new_image)
    
    gif.release()
    print(f"Extracted {frames_to_extract} frames to {output_folder}")

if __name__ == "__main__":
    gif_path = "cape.gif"
    output_folder = "output"
    num_frames = int(input("Enter the number of frames to extract: "))
    ##clean output folder
    for file in os.listdir(output_folder):
        os.remove(os.path.join(output_folder, file))
    extract_frames_from_gif(gif_path, output_folder, num_frames)