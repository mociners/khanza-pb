import re

with open("report/rptSuratPersetujuanUmum.jrxml", "r") as f:
    jrxml = f.read()

# 1. Add field definition
field_privasi = '<field name="privasi_khusus" class="java.lang.String"/>'
field_jenis = '<field name="privasi_khusus" class="java.lang.String"/>\n\t<field name="jenis_pembiayaan" class="java.lang.String"/>'
jrxml = jrxml.replace(field_privasi, field_jenis)

# 2. Update SQL query inside JRXML (it defines the same query as tampil)
# Actually, the JasperReport uses the query passed from Valid.MyReportqry in Java, but we should update it anyway.
sql_old = 'surat_persetujuan_umum.privasi_akses,surat_persetujuan_umum.privasi_khusus,\nsurat_persetujuan_umum.alasan_tolak_bpjs'
sql_new = 'surat_persetujuan_umum.privasi_akses,surat_persetujuan_umum.privasi_khusus,\nsurat_persetujuan_umum.jenis_pembiayaan,surat_persetujuan_umum.alasan_tolak_bpjs'
jrxml = jrxml.replace(sql_old, sql_new)
# Let's handle different whitespace/newlines if needed
sql_old2 = 'surat_persetujuan_umum.privasi_khusus,surat_persetujuan_umum.alasan_tolak_bpjs'
sql_new2 = 'surat_persetujuan_umum.privasi_khusus,surat_persetujuan_umum.jenis_pembiayaan,surat_persetujuan_umum.alasan_tolak_bpjs'
jrxml = jrxml.replace(sql_old2, sql_new2)

# 3. Update checkbox logic
# We must replace all the complex expressions with simple checks on jenis_pembiayaan.

# UMUM
# (($F{alasan_tolak_bpjs}!=null && $F{alasan_tolak_bpjs}.trim().length()>0) ? "\u2611" : "\u2610") + " UMUM"
# Wait, I previously changed the UMUM logic to this. Let's find it using regex and replace.
jrxml = re.sub(r'\(\$F\{alasan_tolak_bpjs\}!=null && \$F\{alasan_tolak_bpjs\}\.trim\(\)\.length\(\)>0 \? "\\u2611" : "\\u2610"\)', r'($F{jenis_pembiayaan}.equals("Umum") ? "\\u2611" : "\\u2610")', jrxml)

# Also for the UMUM child:
# ($F{alasan_tolak_bpjs}!=null && $F{alasan_tolak_bpjs}.trim().length()>0 ? "\u2611" : "\u2610") + " Saya telah mendapatkan penjelasan
jrxml = re.sub(r'\(\$F\{alasan_tolak_bpjs\}!=null && \$F\{alasan_tolak_bpjs\}\.trim\(\)\.length\(\)>0 \? "\\u2611" : "\\u2610"\)', r'($F{jenis_pembiayaan}.equals("Umum") ? "\\u2611" : "\\u2610")', jrxml)

# ASURANSI SWASTA
# (($F{png_jawab}!=null && $F{png_jawab}.toUpperCase().contains("ASURANSI")) || ($F{asuransi_swasta}!=null && $F{asuransi_swasta}.trim().length()>1 && !$F{asuransi_swasta}.trim().equals("-")) ? "\u2611" : "\u2610")
jrxml = re.sub(r'\(\(\$F\{png_jawab\}!=null && \$F\{png_jawab\}\.toUpperCase\(\)\.contains\("ASURANSI"\)\) \|\| \(\$F\{asuransi_swasta\}!=null && \$F\{asuransi_swasta\}\.trim\(\)\.length\(\)>1 && !\$F\{asuransi_swasta\}\.trim\(\)\.equals\("-"\)\) \? "\\u2611" : "\\u2610"\)', r'($F{jenis_pembiayaan}.equals("Asuransi Swasta") ? "\\u2611" : "\\u2610")', jrxml)

# JASA RAHARJA
# (($F{png_jawab}!=null && $F{png_jawab}.toUpperCase().contains("JASA RAHARJA")) ? "\u2611" : "\u2610")
jrxml = re.sub(r'\(\(\$F\{png_jawab\}!=null && \$F\{png_jawab\}\.toUpperCase\(\)\.contains\("JASA RAHARJA"\)\) \? "\\u2611" : "\\u2610"\)', r'($F{jenis_pembiayaan}.equals("Jasa Raharja") ? "\\u2611" : "\\u2610")', jrxml)

# BPJS Ketenagakerjaan
# (($F{png_jawab}!=null && $F{png_jawab}.toUpperCase().contains("KETENAGAKERJAAN")) ? "\u2611" : "\u2610")
jrxml = re.sub(r'\(\(\$F\{png_jawab\}!=null && \$F\{png_jawab\}\.toUpperCase\(\)\.contains\("KETENAGAKERJAAN"\)\) \? "\\u2611" : "\\u2610"\)', r'($F{jenis_pembiayaan}.equals("BPJS Ketenagakerjaan") ? "\\u2611" : "\\u2610")', jrxml)

# BPJS Kesehatan
# (($F{png_jawab}!=null && ($F{png_jawab}.toUpperCase().contains("BPJS KESEHATAN") || $F{png_jawab}.toUpperCase().equals("BPJS"))) || ($F{no_jkn_jasa_raharja}!=null && $F{no_jkn_jasa_raharja}.trim().length()>1 && !$F{no_jkn_jasa_raharja}.trim().equals("-")) ? "\u2611" : "\u2610")
jrxml = re.sub(r'\(\(\$F\{png_jawab\}!=null && \(\$F\{png_jawab\}\.toUpperCase\(\)\.contains\("BPJS KESEHATAN"\) \|\| \$F\{png_jawab\}\.toUpperCase\(\)\.equals\("BPJS"\)\)\) \|\| \(\$F\{no_jkn_jasa_raharja\}!=null && \$F\{no_jkn_jasa_raharja\}\.trim\(\)\.length\(\)>1 && !\$F\{no_jkn_jasa_raharja\}\.trim\(\)\.equals\("-"\)\) \? "\\u2611" : "\\u2610"\)', r'($F{jenis_pembiayaan}.equals("BPJS Kesehatan") ? "\\u2611" : "\\u2610")', jrxml)

with open("report/rptSuratPersetujuanUmum.jrxml", "w") as f:
    f.write(jrxml)

print("Patched JRXML successfully.")
