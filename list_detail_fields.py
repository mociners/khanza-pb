import re

with open('temp_detail.xml', 'r') as f:
    content = f.read()

# Extract all fields wrapped in $F{...}
fields = re.findall(r'\$F\{([^\}]+)\}', content)
print("Fields in DETAIL LAINNYA:")
for i, f in enumerate(fields):
    print(f"{i+1}. {f}")
