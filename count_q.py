with open("src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java", "r") as f:
    text = f.read()

import re
match = re.search(r'menyimpantf\("penilaian_awal_keperawatan_ranap_dewasa", "([^"]+)"', text)
if match:
    qs = match.group(1)
    print(f"Number of ?: {qs.count('?')}")
else:
    print("Not found")
