with open("report/rptCetakPenilaianAwalMedisRanapKandungan1.jrxml", "r") as f:
    text = f.read()

text = text.replace('"Anamnesis: " + $F{anamnesis} + "\nKeluhan Utama: " + $F{keluhan_utama}', '"Anamnesis: " + $F{anamnesis} + "\\nKeluhan Utama: " + $F{keluhan_utama}')

with open("report/rptCetakPenilaianAwalMedisRanapKandungan1.jrxml", "w") as f:
    f.write(text)
