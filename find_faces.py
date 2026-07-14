import sys
from PIL import Image

img = Image.open('src/picture/skala_nyeri.png').convert('RGB')
w, h = img.size

# Vertical projection of non-white pixels
proj = [0] * w
for x in range(w):
    for y in range(0, 150): # Top 150 pixels should contain the faces
        r, g, b = img.getpixel((x, y))
        if r < 250 and g < 250 and b < 250: # Not pure white
            proj[x] += 1

# Find contiguous regions
centers = []
in_face = False
start_x = 0
for x in range(w):
    if proj[x] > 10:
        if not in_face:
            start_x = x
            in_face = True
    else:
        if in_face:
            centers.append((start_x + x) // 2)
            in_face = False

print(f"Face X centers: {centers}")
