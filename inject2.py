import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

with open('generated_getData.txt', 'r') as f:
    generated = f.read()

# find ket_masalah_sirk_3
pattern = r'(this\.TMasalahSirkJelas3\.setText\(this\.rs\.getString\("ket_masalah_sirk_3"\)\);)'
m = re.search(pattern, content)
if m:
    new_content = content[:m.end()] + '\n' + generated + content[m.end():]
    with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
        f.write(new_content)
    print("Injected successfully!")
else:
    print("Anchor not found!")
