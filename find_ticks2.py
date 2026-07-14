import sys
from PIL import Image

img = Image.open('src/picture/skala_nyeri.png').convert('RGB')
w, h = img.size

y = 350 # Just above the horizontal line
ticks = []
in_tick = False
start_x = 0
for x in range(w):
    r, g, b = img.getpixel((x, y))
    if r < 100 and g < 100 and b < 100:
        if not in_tick:
            start_x = x
            in_tick = True
    else:
        if in_tick:
            ticks.append((start_x + x) // 2)
            in_tick = False
print(f"Ticks X at Y={y}: {ticks}")

y = 360 # Just below
ticks2 = []
in_tick = False
start_x = 0
for x in range(w):
    r, g, b = img.getpixel((x, y))
    if r < 100 and g < 100 and b < 100:
        if not in_tick:
            start_x = x
            in_tick = True
    else:
        if in_tick:
            ticks2.append((start_x + x) // 2)
            in_tick = False
print(f"Ticks X at Y={y}: {ticks2}")
