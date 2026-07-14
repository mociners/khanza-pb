import sys
from PIL import Image

img = Image.open('src/picture/skala_nyeri.png').convert('RGB')
w, h = img.size

# The Wong Baker scale is at the top. Let's find the bottom of the text under the faces.
max_y = 0
for y in range(0, h // 2):
    for x in range(w):
        r, g, b = img.getpixel((x, y))
        if r < 200 and g < 200 and b < 200: # Not white
            if y > max_y:
                max_y = y
print(f"Lowest non-white pixel for WB text is at Y={max_y}")

# The faces have red numbers under them. Red is roughly r>200, g<100, b<100. Let's find the X coordinates of these red numbers.
red_xs = []
in_red = False
start_x = 0
# Scan horizontally at Y=max_y - 40 (maybe around Y=200?)
# Better to find the red pixels by projecting vertically
red_x_projection = [0] * w
for x in range(w):
    for y in range(0, h // 2):
        r, g, b = img.getpixel((x, y))
        if r > 150 and g < 100 and b < 100:
            red_x_projection[x] += 1

xs = []
in_num = False
start_x = 0
for x in range(w):
    if red_x_projection[x] > 5:
        if not in_num:
            start_x = x
            in_num = True
    else:
        if in_num:
            xs.append((start_x + x) // 2)
            in_num = False
print(f"X coordinates of red numbers: {xs}")

