import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

target = 'this.rs.getString("nip"), this.rs.getString("kd_dokter"), this.rs.getString("diagnosa_masuk")'
replacement = 'this.rs.getString("nip"), this.rs.getString("diagnosa_masuk")'

if target in content:
    content = content.replace(target, replacement)
    with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
        f.write(content)
    print("SUCCESS: Target replaced!")
else:
    print("FAILED: Target not found.")
