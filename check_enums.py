import re

sql_file = "/home/mociners/.gemini/antigravity/brain/81bca0a4-9afd-4623-b2aa-9611f7ea30b8/penilaian_awal_keperawatan_ranap_dewasa.sql"
java_file = "src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java"

with open(sql_file, "r") as f:
    sql_text = f.read()

db_enums = {}
for match in re.finditer(r"^\s+([a-zA-Z_0-9]+)\s+enum\((.*?)\)", sql_text, re.MULTILINE):
    col = match.group(1)
    # Extract enum values: 'Val1','Val2'
    val_str = match.group(2)
    vals = [v.strip("'") for v in val_str.split("','")]
    # Handle the first and last single quotes if splitting missed them
    if vals:
        vals[0] = vals[0].lstrip("'")
        vals[-1] = vals[-1].rstrip("'")
    db_enums[col] = vals

print("Found", len(db_enums), "ENUM columns in DB.")

# Check common Java arrays:
with open(java_file, "r") as f:
    java_text = f.read()

# I will just grep all String[] arrays in the Java file
java_arrays = {}
for match in re.finditer(r"new String\[\]\{(.*?)\}", java_text):
    arr_str = match.group(1)
    # very naive split
    items = [x.strip().strip('"') for x in arr_str.split('", "')]
    if items:
        items[0] = items[0].lstrip('"')
        items[-1] = items[-1].rstrip('"')
    
    # Trying to guess which column this belongs to based on context
    # Usually right before it is the variable name
    idx = match.start()
    context = java_text[max(0, idx-50):idx]
    print(f"Java Array: {items} (Context: {context})")
