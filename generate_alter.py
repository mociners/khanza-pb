import re

sql_file = "/home/mociners/.gemini/antigravity/brain/81bca0a4-9afd-4623-b2aa-9611f7ea30b8/penilaian_awal_keperawatan_ranap_dewasa.sql"
java_file = "src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java"

with open(sql_file, "r") as f:
    sql_text = f.read()

db_enums = {}
for match in re.finditer(r"^\s+([a-zA-Z_0-9]+)\s+enum\((.*?)\)", sql_text, re.MULTILINE):
    col = match.group(1)
    val_str = match.group(2)
    vals = [v.strip("'") for v in val_str.split("','")]
    if vals:
        vals[0] = vals[0].lstrip("'")
        vals[-1] = vals[-1].rstrip("'")
    db_enums[col] = vals

with open(java_file, "r") as f:
    java_text = f.read()

combos = {}
for match in re.finditer(r"([A-Za-z0-9_]+)\.addItem\(\"(.*?)\"\);", java_text):
    cmb = match.group(1)
    val = match.group(2)
    if cmb not in combos:
        combos[cmb] = []
    combos[cmb].append(val)

for match in re.finditer(r"for \(String item : [a-zA-Z_0-9]+ = new String\[\]\{(.*?)\}\)", java_text):
    arr_str = match.group(1)
    items = [x.strip().strip('"') for x in arr_str.split('", "')]
    if items:
        items[0] = items[0].lstrip('"')
        items[-1] = items[-1].rstrip('"')
    
    idx = match.end()
    sub = java_text[idx:idx+100]
    cmb_match = re.search(r"this\.([a-zA-Z_0-9]+)\.addItem", sub)
    if cmb_match:
        combos[cmb_match.group(1)] = items

mapping = {}
for match in re.finditer(r"stringArray\[(\d+)\] = this\.([A-Za-z0-9_]+)\.getSelectedItem\(\)", java_text):
    idx = int(match.group(1))
    cmb = match.group(2)
    mapping[idx] = cmb

tampil_cols = []
for match in re.finditer(r"this\.rs\.getString\(\"(.*?)\"\)", java_text):
    tampil_cols.append(match.group(1))

# Just take the first block of tampil_cols
tampil_cols = tampil_cols[:220]

if len(tampil_cols) > 0:
    for i, col in enumerate(tampil_cols):
        if i in mapping:
            cmb = mapping[i]
            if col in db_enums and cmb in combos:
                db_vals = db_enums[col]
                java_vals = combos[cmb]
                if set(db_vals) != set(java_vals):
                    j_str = "','".join(java_vals)
                    print(f"ALTER TABLE penilaian_awal_keperawatan_ranap_dewasa MODIFY COLUMN {col} enum('{j_str}') DEFAULT '{java_vals[0]}';")
