import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

with open('generated_missing.txt', 'r') as f:
    missing_code = f.read()

# Let's find the last line of my previous injection:
# this.RdoQualityDisayat.setSelected("Ya".equals(val_203));
# Actually, the last one was `val_180 = this.rs.getString("pencahar");` wait no, 
# The last one from `reinject_getdata.py` was:
# this.RdoQualityDisayat.setSelected("Ya".equals(val_203));

pattern = r'(this\.RdoQualityDisayat\.setSelected\("Ya"\.equals\(val_203\)\);)'
m = re.search(pattern, content)
if m:
    new_content = content[:m.end()] + '\n' + missing_code + content[m.end():]
    with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
        f.write(new_content)
    print("Injected successfully.")
else:
    print("Anchor not found!")
