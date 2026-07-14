import re

with open("report/rptSuratPersetujuanUmum.jrxml", "r") as f:
    code = f.read()

# Make the checkbox matching more robust (case-insensitive and partial match to handle enum truncations)
# UMUM
code = re.sub(r'\(\$F\{jenis_pembiayaan\}\.equals\("Umum"\) \? "\\u2611" : "\\u2610"\)', 
              r'($F{jenis_pembiayaan}!=null && $F{jenis_pembiayaan}.toUpperCase().contains("UMUM") ? "\\u2611" : "\\u2610")', code)
# ASURANSI SWASTA
code = re.sub(r'\(\$F\{jenis_pembiayaan\}\.equals\("Asuransi Swasta"\) \? "\\u2611" : "\\u2610"\)', 
              r'($F{jenis_pembiayaan}!=null && $F{jenis_pembiayaan}.toUpperCase().contains("ASURANSI") ? "\\u2611" : "\\u2610")', code)
# JASA RAHARJA
code = re.sub(r'\(\$F\{jenis_pembiayaan\}\.equals\("Jasa Raharja"\) \? "\\u2611" : "\\u2610"\)', 
              r'($F{jenis_pembiayaan}!=null && $F{jenis_pembiayaan}.toUpperCase().contains("JASA RAHARJA") ? "\\u2611" : "\\u2610")', code)
# BPJS KETENAGAKERJAAN
code = re.sub(r'\(\$F\{jenis_pembiayaan\}\.equals\("BPJS Ketenagakerjaan"\) \? "\\u2611" : "\\u2610"\)', 
              r'($F{jenis_pembiayaan}!=null && $F{jenis_pembiayaan}.toUpperCase().contains("KETENAGAKERJAAN") ? "\\u2611" : "\\u2610")', code)
# BPJS KESEHATAN
code = re.sub(r'\(\$F\{jenis_pembiayaan\}\.equals\("BPJS Kesehatan"\) \? "\\u2611" : "\\u2610"\)', 
              r'($F{jenis_pembiayaan}!=null && $F{jenis_pembiayaan}.toUpperCase().contains("BPJS KESEHATAN") ? "\\u2611" : "\\u2610")', code)


# Now fix the NO KARTU overlapping!
# JASA RAHARJA
code = code.replace('"No. JKN / Jasa Raharja " + ($F{no_jkn_jasa_raharja}==null ? "" : $F{no_jkn_jasa_raharja})', 
                    '"No. JKN / Jasa Raharja " + ($F{jenis_pembiayaan}!=null && $F{jenis_pembiayaan}.toUpperCase().contains("JASA RAHARJA") && $F{no_jkn_jasa_raharja}!=null && !$F{no_jkn_jasa_raharja}.equals("-") ? $F{no_jkn_jasa_raharja} : "")')

# BPJS KETENAGAKERJAAN & KESEHATAN share the same string literal in the file:
# "No. Kartu Peserta " + ($F{no_jkn_jasa_raharja}==null ? "" : $F{no_jkn_jasa_raharja})
# We have to replace them carefully based on line position or using a function.
# Let's just find them by index and replace the first one with Ketenagakerjaan logic and the second with Kesehatan logic.

target = '"No. Kartu Peserta " + ($F{no_jkn_jasa_raharja}==null ? "" : $F{no_jkn_jasa_raharja})'
parts = code.split(target)
if len(parts) == 3:
    ketenagakerjaan_logic = '"No. Kartu Peserta " + ($F{jenis_pembiayaan}!=null && $F{jenis_pembiayaan}.toUpperCase().contains("KETENAGAKERJAAN") && $F{no_jkn_jasa_raharja}!=null && !$F{no_jkn_jasa_raharja}.equals("-") ? $F{no_jkn_jasa_raharja} : "")'
    kesehatan_logic = '"No. Kartu Peserta " + ($F{jenis_pembiayaan}!=null && $F{jenis_pembiayaan}.toUpperCase().contains("BPJS KESEHATAN") && $F{no_jkn_jasa_raharja}!=null && !$F{no_jkn_jasa_raharja}.equals("-") ? $F{no_jkn_jasa_raharja} : "")'
    
    code = parts[0] + ketenagakerjaan_logic + parts[1] + kesehatan_logic + parts[2]
else:
    print("Warning: split did not find exactly 2 occurrences of the target string!")

with open("report/rptSuratPersetujuanUmum.jrxml", "w") as f:
    f.write(code)

print("JRXML Checkboxes and Fields Patched!")
