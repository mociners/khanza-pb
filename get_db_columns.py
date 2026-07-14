import re

columns = []
with open('/home/mociners/.gemini/antigravity/brain/81bca0a4-9afd-4623-b2aa-9611f7ea30b8/penilaian_awal_keperawatan_ranap_dewasa.sql', 'r') as f:
    sql1 = f.read()
for line in sql1.split('\n'):
    m = re.match(r'^\s+([a-zA-Z0-9_]+)\s+', line)
    if m:
        c = m.group(1)
        if c not in ['PRIMARY', 'KEY', 'CONSTRAINT']:
            columns.append(c)

with open('/home/mociners/.gemini/antigravity/brain/81bca0a4-9afd-4623-b2aa-9611f7ea30b8/tambah_kolom_nyeri.sql', 'r') as f:
    sql2 = f.read()
for line in sql2.split('\n'):
    m = re.search(r'ADD COLUMN `([a-zA-Z0-9_]+)`', line)
    if m:
        columns.append(m.group(1))

with open('/home/mociners/.gemini/antigravity/brain/81bca0a4-9afd-4623-b2aa-9611f7ea30b8/tambah_kolom_cpot.sql', 'r') as f:
    sql3 = f.read()
for line in sql3.split('\n'):
    m = re.search(r'ADD COLUMN `([a-zA-Z0-9_]+)`', line)
    if m:
        columns.append(m.group(1))

print(columns)
