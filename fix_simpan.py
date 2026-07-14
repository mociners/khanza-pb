import re

file_path = "/home/mociners/Documents/rsthbfinal/src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java"

with open(file_path, 'r') as f:
    java_code = f.read()

# Fix simpan() ?,?,... string and 182 -> 220
question_marks = ",".join(["?"] * 220)
# Find the exact Sequel.menyimpantf call
simpan_pattern = re.compile(r'Sequel\.menyimpantf\("penilaian_awal_keperawatan_ranap_dewasa",\s*"(\?.*?)"\s*,\s*"Data"\s*,\s*\d+\s*,\s*new String\[\]\s*\{', re.DOTALL)

def simpan_repl(m):
    return 'Sequel.menyimpantf("penilaian_awal_keperawatan_ranap_dewasa", "' + question_marks + '", "Data", 220, new String[] {'

java_code = simpan_pattern.sub(simpan_repl, java_code)


# Fix ganti() SQL string and 182 -> 220
# First, extract the current ganti SQL string to append to it
# The current string ends with `masalah_pencernaan=?`
ganti_pattern = re.compile(r'Sequel\.mengedittf\("penilaian_awal_keperawatan_ranap_dewasa",\s*"no_rawat=\?",\s*"(.*?)",\s*\d+\s*,\s*new String\[\]\s*\{', re.DOTALL)

def ganti_repl(m):
    original_cols = m.group(1)
    # the original_cols already has the 31 columns I added earlier, or maybe not!
    # Wait, in the earlier check, it showed it ONLY had `masalah_pencernaan=?` then `?, ?, ?,...` which was wrong!
    # Let me just rewrite the entire list of fields for ganti().
    # It's better to just replace `masalah_pencernaan=\?(.*?)"` with the correct column names!
    pass

