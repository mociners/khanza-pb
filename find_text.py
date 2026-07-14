import sys
from PIL import Image

img = Image.open('src/picture/skala_nyeri.png').convert('RGB')
w, h = img.size

# Find the lowest non-white pixel in the image around X=65
max_y = 0
for y in range(360, h):
    for x in range(50, 80):
        r, g, b = img.getpixel((x, y))
        if r < 200 and g < 200 and b < 200: # Not white
            if y > max_y:
                max_y = y
print(f"Lowest non-white pixel near X=65 is at Y={max_y}")

# Find lowest non-white pixel for the entire image
max_y_all = 0
for y in range(360, h):
    for x in range(w):
        r, g, b = img.getpixel((x, y))
        if r < 200 and g < 200 and b < 200: # Not white
            if y > max_y_all:
                max_y_all = y
print(f"Lowest non-white pixel overall is at Y={max_y_all}")
