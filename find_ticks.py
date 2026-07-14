import sys
from PIL import Image

img = Image.open('src/picture/skala_nyeri.png').convert('RGB')
w, h = img.size

# Look for the horizontal black line of the NRS. 
# NRS is in the lower half. We search for a long horizontal line of dark pixels.
for y in range(h // 2, h):
    dark_count = sum(1 for x in range(w) if sum(img.getpixel((x, y))) < 100)
    if dark_count > 400: # Long horizontal line
        print(f"Found horizontal line at Y={y}")
        # Now find the vertical ticks passing through this line
        ticks = []
        in_tick = False
        start_x = 0
        for x in range(w):
            if sum(img.getpixel((x, y))) < 100:
                if not in_tick:
                    start_x = x
                    in_tick = True
            else:
                if in_tick:
                    ticks.append((start_x + x) // 2)
                    in_tick = False
        print(f"Ticks X coordinates: {ticks}")
        break
