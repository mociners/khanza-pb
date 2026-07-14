import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

target = r'this\.tabMode\.addRow\(new String\[\]\{this\.rs\.getString\("no_rawat"\), this\.rs\.getString\("tanggal"\), this\.rs\.getString\("informasi_dari"\), this\.rs\.getString\("tgl_tiba"\), this\.rs\.getString\("nip"\), this\.rs\.getString\("diagnosa_masuk"\)'
replacement = r'this.tabMode.addRow(new String[]{this.rs.getString("no_rawat"), this.rs.getString("no_rkm_medis"), this.rs.getString("nm_pasien"), this.rs.getString("tgl_lahir"), this.rs.getString("jk"), this.rs.getString("kd_dokter"), this.rs.getString("nm_dokter"), this.rs.getString("tanggal"), this.rs.getString("informasi_dari"), this.rs.getString("tgl_tiba"), this.rs.getString("nip"), this.rs.getString("diagnosa_masuk")'

new_content = re.sub(target, replacement, content)
if new_content != content:
    with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
        f.write(new_content)
    print("SUCCESS: Target replaced!")
else:
    print("FAILED: Target not found.")
    
    # Let's try matching with flexible whitespace
    target2 = r'this\.tabMode\.addRow\(\s*new String\[\]\s*\{\s*this\.rs\.getString\("no_rawat"\)\s*,\s*this\.rs\.getString\("tanggal"\)\s*,\s*this\.rs\.getString\("informasi_dari"\)\s*,\s*this\.rs\.getString\("tgl_tiba"\)\s*,\s*this\.rs\.getString\("nip"\)\s*,\s*this\.rs\.getString\("diagnosa_masuk"\)'
    new_content2 = re.sub(target2, replacement, content)
    if new_content2 != content:
        with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
            f.write(new_content2)
        print("SUCCESS 2: Target replaced with flexible whitespace!")
    else:
        print("FAILED 2: Target not found even with flexible whitespace.")

