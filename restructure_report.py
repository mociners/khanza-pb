import re

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'r') as f:
    xml = f.read()

# Split the xml into before <detail>, the <detail> content, and after </detail>
m = re.search(r'(<detail>)(.*?)(</detail>)', xml, re.DOTALL)
before_detail = xml[:m.start(2)]
detail_content = m.group(2)
after_detail = xml[m.end(2):]

# Extract all bands from detail_content
bands = re.findall(r'<band height="\d+">.*?</band>', detail_content, re.DOTALL)

# We want to remove the "DETAIL LAINNYA" header band and ALL bands after it.
# Let's find the index of the "DETAIL LAINNYA" header band.
detail_lainnya_idx = -1
for i, b in enumerate(bands):
    if 'DETAIL LAINNYA' in b:
        detail_lainnya_idx = i
        break

if detail_lainnya_idx == -1:
    print("DETAIL LAINNYA not found!")
    exit(1)

# The original bands WITHOUT detail lainnya
original_bands = bands[:detail_lainnya_idx]

# The extra bands (excluding the DETAIL LAINNYA header itself)
extra_bands = bands[detail_lainnya_idx+1:]

# Now we need to define the headers to know where to insert
headers = {
    'DATA PSIKOLOGIS SOSIAL EKONOMI, SPIRITUAL': 'ket_tinggal',
    'PENGKAJIAN NEUROSENSORIS': 'ket_ori_orang',
    'PERNAFASAN': 'benda_asing',
    'SIRKULASI': 'sirkulasi',
    'PERKEMIHAN': 'bak_lainnya',
    'SEKSUAL/REPRODUKSI': 'status_ob_p',
    'INTEGUMEN & MUSKULOSKELETAL': 'luka_dalam',
    'THT & MATA': 'telinga_lainnya',
    'PENCERNAAN': 'wasir',
    'ASSESMEN NYERI': 'nyeri_tidak_ada',
    'CPOT': 'cpot_ekspresi' # this header doesn't exist, we will create it!
}

# Find which extra band belongs to which section
# We'll just look at the first field of each extra band
section_for_extra_band = []
current_section = 'GENERAL'
for b in extra_bands:
    fields = re.findall(r'\$F\{([^\}]+)\}', b)
    if fields:
        first_field = fields[0]
        # check if this field triggers a section change
        for sec, trigger in headers.items():
            if first_field == trigger:
                current_section = sec
                break
    section_for_extra_band.append(current_section)

# Now, we reconstruct the original bands, inserting extra bands at the END of each section.
# The END of a section is right before the NEXT section's header band.
header_markers = {
    'DATA PSIKOLOGIS SOSIAL EKONOMI, SPIRITUAL': 'DATA PSIKOLOGIS SOSIAL EKONOMI, SPIRITUAL',
    'PENGKAJIAN NEUROSENSORIS': 'PENGKAJIAN NEUROSENSORIS',
    'PERNAFASAN': 'PERNAFASAN',
    'SIRKULASI': 'SIRKULASI',
    'PERKEMIHAN': 'PERKEMIHAN',
    'SEKSUAL/REPRODUKSI': 'SEKSUAL/REPRODUKSI',
    'INTEGUMEN & MUSKULOSKELETAL': 'INTEGUMEN & MUSKULOSKELETAL',
    'THT & MATA': 'THT & MATA',
    'PENCERNAAN': 'PENCERNAAN',
    'ASSESMEN NYERI': 'ASSESMEN NYERI'
}

# We need to find the band indices of these headers in `original_bands`
header_indices = {}
for i, b in enumerate(original_bands):
    for sec, marker in header_markers.items():
        if f'<![CDATA[  {marker}]]>' in b or f'<![CDATA[ {marker}]]>' in b:
            header_indices[sec] = i

# Order of sections in the report
section_order = ['GENERAL', 'DATA PSIKOLOGIS SOSIAL EKONOMI, SPIRITUAL', 'PENGKAJIAN NEUROSENSORIS', 'PERNAFASAN', 'SIRKULASI', 'PERKEMIHAN', 'SEKSUAL/REPRODUKSI', 'INTEGUMEN & MUSKULOSKELETAL', 'THT & MATA', 'PENCERNAAN', 'ASSESMEN NYERI', 'CPOT']

final_bands = []

for sec in section_order:
    # 1. Add original bands for this section
    # Wait, how to know which original bands belong to this section?
    # They are from this section's header up to the next section's header.
    start_idx = 0
    if sec == 'GENERAL':
        start_idx = 0
    elif sec == 'CPOT':
        # CPOT doesn't have original bands, we just create the header band
        header_band = """<band height="15">
<staticText><reportElement x="0" y="0" width="574" height="15" mode="Opaque" backcolor="#CCCCCC" uuid="c1234567-89ab-cdef-0123-456789abcdef"/><textElement verticalAlignment="Middle"><font fontName="Tahoma" size="9" isBold="true"/></textElement><text><![CDATA[  ASSESMEN CPOT]]></text></staticText>
</band>"""
        final_bands.append(header_band)
        start_idx = len(original_bands) # no original bands to add
    else:
        start_idx = header_indices.get(sec, len(original_bands))
    
    # end_idx is the next section's header index
    end_idx = len(original_bands)
    sec_index = section_order.index(sec)
    for next_sec in section_order[sec_index+1:]:
        if next_sec in header_indices:
            end_idx = header_indices[next_sec]
            break
            
    if sec != 'CPOT':
        final_bands.extend(original_bands[start_idx:end_idx])
        
    # 2. Add extra bands for this section
    for i, b in enumerate(extra_bands):
        if section_for_extra_band[i] == sec:
            final_bands.append(b)

# Replace detail_content
new_detail_content = '\n'.join(final_bands)

new_xml = before_detail + new_detail_content + '\n' + after_detail

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'w') as f:
    f.write(new_xml)

print("Restructured successfully!")

