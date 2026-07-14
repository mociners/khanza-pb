import re

with open("report/rptSuratPersetujuanUmum.jrxml", "r") as f:
    code = f.read()

# 1. Add field declaration for informasi_biaya
field_dec = '\t<field name="informasi_biaya" class="java.lang.String"/>\n'
if field_dec not in code:
    code = code.replace('\t<field name="jenis_pembiayaan"', field_dec + '\t<field name="jenis_pembiayaan"')

# 2. Patch Rawat Jalan
code = code.replace('"\\u2610 Saat mendaftar di Instalasi Rawat Jalan, wajib melengkapi persyaratan jaminan pembayaran "+$P{namars}', 
                    '($F{informasi_biaya}!=null && $F{informasi_biaya}.toUpperCase().contains("RAWAT JALAN") ? "[ v ]" : "[   ]") + " Saat mendaftar di Instalasi Rawat Jalan, wajib melengkapi persyaratan jaminan pembayaran "+$P{namars}')

# 3. Patch Rawat Inap
code = code.replace('"\\u2610 Bersedia melengkapi persyaratan jaminan pembayaran dalam waktu 1 x 24 jam terhitung sejak hari, tanggal dan jam masuk di "+$P{namars}+"."', 
                    '($F{informasi_biaya}!=null && $F{informasi_biaya}.toUpperCase().contains("RAWAT INAP") ? "[ v ]" : "[   ]") + " Bersedia melengkapi persyaratan jaminan pembayaran dalam waktu 1 x 24 jam terhitung sejak hari, tanggal dan jam masuk di "+$P{namars}+"."')

# 4. Patch IGD
code = code.replace('"\\u2610 Bagi pasien BPJS yang masuk melalui IGD, apabila hasil skrining awal tidak memenuhi kriteria Gawat Darurat, bersedia untuk membayar biaya perawatan dan terdaftar sebagai pasien umum."', 
                    '($F{informasi_biaya}!=null && $F{informasi_biaya}.toUpperCase().contains("GAWAT DARURAT") ? "[ v ]" : "[   ]") + " Bagi pasien BPJS yang masuk melalui IGD, apabila hasil skrining awal tidak memenuhi kriteria Gawat Darurat, bersedia untuk membayar biaya perawatan dan terdaftar sebagai pasien umum."')


with open("report/rptSuratPersetujuanUmum.jrxml", "w") as f:
    f.write(code)

print("Patch applied for JRXML Informasi Biaya!")
