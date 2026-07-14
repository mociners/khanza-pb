import re

with open('temp_detail.xml', 'r') as f:
    content = f.read()

# find all bands
bands = re.findall(r'<band height="15">(.*?)</band>', content, re.DOTALL)
for i, b in enumerate(bands):
    fields = re.findall(r'\$F\{([^\}]+)\}', b)
    if not fields:
        # maybe it's the DETAIL LAINNYA header
        if 'DETAIL LAINNYA' in b:
            continue
    print(f"Band {i+1}: {fields}")

