import re
with open("src/rekammedis/RMPenilaianAwalMedisRanapKandungan1.java", "r") as f:
    text = f.read()

text = text.replace("KetubanKeyPressed", "HisKeyPressed")
text = text.replace("JenisKelaminBayiKeyPressed", "KontraksiKeyPressed")
text = text.replace("PlasentaKeyPressed", "KepalaKeyPressed")

text = text.replace("Ketuban", "His")
text = text.replace("JenisKelaminBayi", "Kontraksi")
text = text.replace("Plasenta", "Kepala")

# The columns are ketuban, jenis_kelamin_bayi, plasenta. Wait, if I replace the variables, I might also replace the sql columns.
# Let's fix the SQL columns manually next.

with open("src/rekammedis/RMPenilaianAwalMedisRanapKandungan1.java", "w") as f:
    f.write(text)
