import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

# 1. Update emptTeks()
empt_teks_add = """        this.TNyeriRadiationLokasi.setText("");
        this.TNyeriSkor.setText("");
        this.TNyeriMenit.setText("");
        this.TCpotTotal.setText("");
        this.TCpotKategori.setText("");
        this.CmbNyeriRadiation.setSelectedIndex(0);
        this.CmbSeverityMetode.setSelectedIndex(0);
        this.CmbSeverityNyeri.setSelectedIndex(0);
        this.CmbTimeSetiap.setSelectedIndex(0);
        this.CmbTimeSelama.setSelectedIndex(0);
        this.CmbTimeSejak.setSelectedIndex(0);
        this.CmbCpotEkspresi.setSelectedIndex(0);
        this.CmbCpotGerakan.setSelectedIndex(0);
        this.CmbCpotKetegangan.setSelectedIndex(0);
        this.CmbCpotVentilator.setSelectedIndex(0);
        this.CmbCpotVokalisasi.setSelectedIndex(0);
        this.ChkNyeriTidakAda.setSelected(false);
        this.ChkNyeriAda.setSelected(false);
        this.ChkNyeriAkut.setSelected(false);
        this.ChkNyeriKronis.setSelected(false);
"""
old_empt_teks_end = r'(this\.TWarnaCerna\.setText\(""\);\n\s*)(this\.ChkAsesmen\.setSelected\(true\);)'
content = re.sub(old_empt_teks_end, r'\1' + empt_teks_add + r'        \2', content)

# 2. Add Dialog to simpan()
old_simpan_success = r'(if \(this\.Sequel\.menyimpantf\("penilaian_awal_keperawatan_ranap_dewasa".*?\s*\{\s*)(this\.tampil\(\);\s*)(this\.emptTeks\(\);\s*)(\})'
new_simpan_success = r'\1\2\3JOptionPane.showMessageDialog(null, "Data berhasil tersimpan");\n            \4'
content = re.sub(old_simpan_success, new_simpan_success, content, flags=re.DOTALL)

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
    f.write(content)

