import re

file_path = "src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java"
with open(file_path, "r") as f:
    code = f.read()

# Extract all CekBox names
matches = re.findall(r'private CekBox (.*?);', code)
cekboxes = []
for m in matches:
    names = [n.strip() for n in m.split(',')]
    cekboxes.extend(names)

init_code = "\n".join([f"        {cb} = new widget.CekBox();" for cb in cekboxes])

# Inject at the end of constructor
constructor_end = code.find("setSize(800, 600);\n    }")
if constructor_end != -1:
    new_code = code[:constructor_end] + init_code + "\n        setSize(800, 600);\n    }"
    with open(file_path, "w") as f:
        f.write(new_code)
        print("Injected instantiations!")
