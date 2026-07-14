import re

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'r') as f:
    xml = f.read()

# 1. First, let's replace the coupled band with two separate bands.
# The band contains Nyeri Time Sejak and Cpot Ekspresi.
coupled_band_regex = r'<band height="15">(.*?>Nyeri Time Sejak<.*?>Cpot Ekspresi<.*?)</band>'

def split_band(match):
    content = match.group(1)
    # Split the content into nyeri part and cpot part
    # nyeri part is from start to before <staticText><reportElement x="290"
    m_cpot = re.search(r'<staticText><reportElement x="290".*?>Cpot Ekspresi<', content)
    nyeri_part = content[:m_cpot.start()]
    cpot_part = content[m_cpot.start():]
    
    # modify x coordinates in cpot_part: 290->0, 400->110, 410->120
    cpot_part = re.sub(r'x="290"', 'x="0"', cpot_part)
    cpot_part = re.sub(r'x="400"', 'x="110"', cpot_part)
    cpot_part = re.sub(r'x="410"', 'x="120"', cpot_part)
    
    return f'<band height="15">{nyeri_part}</band>\n<band height="15">{cpot_part}</band>'

xml = re.sub(coupled_band_regex, split_band, xml, flags=re.DOTALL)

# 2. Now we need to move all bands that contain CPOT fields from ASSESMEN NYERI to ASSESMEN CPOT.
m_detail = re.search(r'(<detail>)(.*?)(</detail>)', xml, re.DOTALL)
before_detail = xml[:m_detail.start(2)]
detail_content = m_detail.group(2)
after_detail = xml[m_detail.end(2):]

bands = re.findall(r'<band height="\d+">.*?</band>', detail_content, re.DOTALL)

nyeri_bands = []
cpot_bands = []
other_bands = []

cpot_fields = ['cpot_ekspresi', 'cpot_gerakan', 'cpot_ketegangan', 'cpot_ventilator', 'cpot_vokalisasi', 'cpot_total', 'cpot_kategori']

for b in bands:
    if any(f'$F{{{cf}}}' in b for cf in cpot_fields):
        # wait, what if it's the CPOT header? The CPOT header doesn't have $F{}
        cpot_bands.append(b)
    elif 'ASSESMEN CPOT' in b:
        # this is the header, let's keep it in other_bands, we will place cpot_bands after it
        other_bands.append(b)
    else:
        other_bands.append(b)

# Wait, we need to place cpot_bands right after the ASSESMEN CPOT header.
final_bands = []
for b in other_bands:
    final_bands.append(b)
    if 'ASSESMEN CPOT' in b:
        final_bands.extend(cpot_bands)

new_detail_content = '\n'.join(final_bands)
xml = before_detail + new_detail_content + '\n' + after_detail

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'w') as f:
    f.write(xml)

print("Fixed CPOT bands!")
