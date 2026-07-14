import re

with open('/home/mociners/.gemini/antigravity/brain/81bca0a4-9afd-4623-b2aa-9611f7ea30b8/penilaian_awal_keperawatan_ranap_dewasa.sql', 'r') as f:
    sql = f.read()

col_lines = re.findall(r'^\s+([a-zA-Z0-9_]+)\s+[a-zA-Z]', sql, re.MULTILINE)

# The SET string should be: col1=?, col2=?, ... (excluding no_rawat)
# Wait, let's verify if `col_lines` matches the 220 items.
print("Total columns:", len(col_lines))
set_str = "=?, ".join(col_lines[1:]) + "=?"
print("SET string:\n", set_str)
