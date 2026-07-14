import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

broken_lines = [
    'this.TNyeriRadiationLokasi.setText("");',
    'this.TNyeriSkor.setText("");',
    'this.TNyeriMenit.setText("");',
    'this.CmbNyeriRadiation.setSelectedIndex(0);',
    'this.CmbSeverityNyeri.setSelectedIndex(0);',
    'this.CmbTimeSejak.setSelectedIndex(0);',
    'this.ChkNyeriTidakAda.setSelected(false);',
    'this.ChkNyeriAda.setSelected(false);',
    'this.ChkNyeriAkut.setSelected(false);',
    'this.ChkNyeriKronis.setSelected(false);'
]

for l in broken_lines:
    content = content.replace(l + '\n', '')

correct_lines = """        this.TRadiationLokasi.setText("");
        this.TSeveritySkor.setText("");
        this.TSeverityNyeri.setText("");
        this.TTimeSejak.setText("");
        this.CmbRadiation.setSelectedIndex(0);
        this.CmbTimeSetiap.setSelectedIndex(0);
        this.CmbTimeSelama.setSelectedIndex(0);
        this.CmbSeverityMetode.setSelectedIndex(0);
        this.CmbCpotEkspresi.setSelectedIndex(0);
        this.CmbCpotGerakan.setSelectedIndex(0);
        this.CmbCpotKetegangan.setSelectedIndex(0);
        this.CmbCpotVentilator.setSelectedIndex(0);
        this.CmbCpotVokalisasi.setSelectedIndex(0);
        this.TCpotTotal.setText("");
        this.TCpotKategori.setText("");
"""

content = content.replace('this.TCpotTotal.setText("");', correct_lines)

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
    f.write(content)

