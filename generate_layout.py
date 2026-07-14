import re

def create_static_text(x, y, w, h, text, is_bold=False, is_box=False, is_header=False):
    font_bold = '<font fontName="Tahoma" size="8" isBold="true"/>' if is_bold else '<font fontName="Tahoma" size="8"/>'
    if is_header:
        font_bold = '<font fontName="Tahoma" size="9" isBold="true"/>'
    
    box_xml = ""
    if is_box:
        box_xml = '''<box>
<pen lineWidth="0.5"/>
<topPen lineWidth="0.5"/>
<leftPen lineWidth="0.5"/>
<bottomPen lineWidth="0.5"/>
<rightPen lineWidth="0.5"/>
</box>'''
        
    mode = ' mode="Opaque" backcolor="#CCCCCC"' if is_header else ''
    
    return f'''
<staticText>
    <reportElement x="{x}" y="{y}" width="{w}" height="{h}"{mode} uuid="st_{x}_{y}"/>
    {box_xml}
    <textElement verticalAlignment="Middle">
        {font_bold}
    </textElement>
    <text><![CDATA[{text}]]></text>
</staticText>'''

def create_text_field(x, y, w, h, field_name, is_box=False):
    box_xml = ""
    if is_box:
        box_xml = '''<box>
<pen lineWidth="0.5"/>
<topPen lineWidth="0.5"/>
<leftPen lineWidth="0.5"/>
<bottomPen lineWidth="0.5"/>
<rightPen lineWidth="0.5"/>
</box>'''
    return f'''
<textField isStretchWithOverflow="true" isBlankWhenNull="true">
    <reportElement x="{x}" y="{y}" width="{w}" height="{h}" uuid="tf_{x}_{y}"/>
    {box_xml}
    <textElement verticalAlignment="Middle">
        <font fontName="Tahoma" size="8"/>
    </textElement>
    <textFieldExpression><![CDATA[$F{{{field_name}}}]]></textFieldExpression>
</textField>'''

def create_checkbox(x, y, w, h, label, field_name, condition, offset=15):
    # Condition e.g. "Ya"
    # We display a checked box [v] if condition matches, else [ ]
    return f'''
<textField isBlankWhenNull="true">
    <reportElement x="{x}" y="{y}" width="{offset}" height="{h}" uuid="chk_{x}_{y}"/>
    <textElement verticalAlignment="Middle">
        <font fontName="Tahoma" size="8"/>
    </textElement>
    <textFieldExpression><![CDATA[$F{{{field_name}}}.equals("{condition}") ? "[v]" : "[  ]"]]></textFieldExpression>
</textField>
<staticText>
    <reportElement x="{x+offset}" y="{y}" width="{w-offset}" height="{h}" uuid="lbl_{x}_{y}"/>
    <textElement verticalAlignment="Middle">
        <font fontName="Tahoma" size="8"/>
    </textElement>
    <text><![CDATA[{label}]]></text>
</staticText>'''

def create_line(x, y, w, h):
    return f'''
<line>
    <reportElement x="{x}" y="{y}" width="{w}" height="{h}" uuid="ln_{x}_{y}"/>
    <graphicElement>
        <pen lineWidth="0.5"/>
    </graphicElement>
</line>'''

xml_bands = []
current_band_y = 0
band_elements = []

def start_new_band():
    global current_band_y, band_elements
    if band_elements:
        xml_bands.append(f'<band height="{current_band_y + 10}">\n' + "".join(band_elements) + '\n</band>')
    current_band_y = 0
    band_elements = []

def add_element(el, y_offset=0):
    global current_band_y, band_elements
    band_elements.append(el)
    if y_offset > 0:
        current_band_y += y_offset

# --- BAND 1: HEADER & PEMERIKSAAN FISIK ---
add_element(create_static_text(0, current_band_y, 574, 15, "ASSESMEN KEPERAWATAN RAWAT INAP DEWASA", True, True, True), 15)
add_element(create_static_text(0, current_band_y, 574, 15, " (Diisi dalam waktu 24 jam pertama pasien masuk rawat inap)", False, True, False), 15)

y = current_band_y
add_element(create_static_text(0, y, 100, 15, "Tanggal Tiba:"))
add_element(create_text_field(100, y, 80, 15, "tgl_tiba"))
add_element(create_static_text(200, y, 100, 15, "Tanggal Assesmen:"))
add_element(create_text_field(300, y, 80, 15, "tanggal"))
add_element(create_static_text(400, y, 50, 15, "DPJP:"))
add_element(create_text_field(450, y, 120, 15, "nm_dokter"))
add_element('', 15)

y = current_band_y
add_element(create_static_text(0, y, 100, 15, "Informasi Dari:"))
add_element(create_text_field(100, y, 280, 15, "informasi_dari"))
add_element(create_static_text(400, y, 50, 15, "Perawat:"))
add_element(create_text_field(450, y, 120, 15, "nip"))
add_element('', 20)

add_element(create_static_text(0, current_band_y, 574, 15, " PEMERIKSAAN FISIK", True, True, True), 15)
y = current_band_y
add_element(create_static_text(0, y, 100, 15, "Diagnosa Masuk"))
add_element(create_static_text(100, y, 10, 15, ":"))
add_element(create_text_field(110, y, 460, 15, "diagnosa_masuk"))
add_element('', 15)

y = current_band_y
add_element(create_static_text(0, y, 100, 15, "Vital Sign"))
add_element(create_static_text(100, y, 10, 15, ":"))
add_element(create_static_text(110, y, 20, 15, "TD:"))
add_element(create_text_field(130, y, 40, 15, "td"))
add_element(create_static_text(170, y, 20, 15, "Suhu:"))
add_element(create_text_field(190, y, 40, 15, "suhu"))
add_element(create_static_text(230, y, 20, 15, "Nadi:"))
add_element(create_text_field(250, y, 40, 15, "nadi_utama"))
add_element(create_static_text(290, y, 20, 15, "RR:"))
add_element(create_text_field(310, y, 40, 15, "rr_utama"))
add_element('', 15)

y = current_band_y
add_element(create_static_text(0, y, 100, 15, "Riw. Peny. Keluarga"))
add_element(create_static_text(100, y, 10, 15, ":"))
add_element(create_text_field(110, y, 460, 15, "riwayat_keluarga"))
add_element('', 15)

y = current_band_y
add_element(create_static_text(0, y, 100, 15, "Riw. Pasien"))
add_element(create_static_text(100, y, 10, 15, ":"))
add_element(create_text_field(110, y, 460, 15, "riwayat_pasien"))
add_element('', 15)

y = current_band_y
add_element(create_static_text(0, y, 100, 15, "Alergi"))
add_element(create_static_text(100, y, 10, 15, ":"))
add_element(create_checkbox(110, y, 80, 15, "Tidak ada", "alergi", "Tidak"))
add_element(create_checkbox(200, y, 80, 15, "Ada, Jenis:", "alergi", "Ya"))
add_element(create_text_field(280, y, 290, 15, "jenis_alergi"))
add_element('', 20)

add_element(create_static_text(0, current_band_y, 574, 15, " DATA PSIKOLOGIS SOSIAL EKONOMI, SPIRITUAL", True, True, True), 15)
fields_psiko = [
    ("Psikologis", "kondisi_psikologis"),
    ("Status Pernikahan", "status_nikah"),
    ("Pendidikan Terakhir", "pendidikan"),
    ("Warga Negara", "warga_negara"),
    ("Agama", "agama"),
    ("Pekerjaan", "pekerjaan"),
    ("Aktivitas", "aktivitas"),
    ("Tinggal Bersama", "tinggal_bersama"),
    ("Tempat Tinggal", "tempat_tinggal")
]
for label, field in fields_psiko:
    y = current_band_y
    add_element(create_static_text(0, y, 120, 15, label))
    add_element(create_static_text(120, y, 10, 15, ":"))
    add_element(create_text_field(130, y, 440, 15, field))
    add_element('', 15)

add_element('', 10)
start_new_band()

# --- BAND 2: NEUROSENSORIS & PERNAFASAN & SIRKULASI ---
add_element(create_static_text(0, current_band_y, 574, 15, " PENGKAJIAN NEUROSENSORIS", True, True, True), 15)
fields_neuro = [
    ("Status Mental", "status_mental"),
    ("Orientasi Orang", "orientasi_orang"),
    ("Memori", "memori"),
    ("GCS (E, M, V)", "gcs_jml")
]
for label, field in fields_neuro:
    y = current_band_y
    add_element(create_static_text(0, y, 120, 15, label))
    add_element(create_static_text(120, y, 10, 15, ":"))
    add_element(create_text_field(130, y, 440, 15, field))
    add_element('', 15)

add_element('', 10)
add_element(create_static_text(0, current_band_y, 574, 15, " PERNAFASAN", True, True, True), 15)
fields_pernafasan = [
    ("Airway", "jalan_nafas"),
    ("Breathing", "pernafasan"),
    ("Bunyi Paru", "bunyi_nafas"),
    ("Kesulitan Bernafas", "kesulitan_nafas"),
    ("Oksigen", "oksigen_ltr"),
    ("Batuk", "batuk"),
    ("SpO2", "spo2")
]
for label, field in fields_pernafasan:
    y = current_band_y
    add_element(create_static_text(0, y, 120, 15, label))
    add_element(create_static_text(120, y, 10, 15, ":"))
    add_element(create_text_field(130, y, 440, 15, field))
    add_element('', 15)

add_element('', 10)
add_element(create_static_text(0, current_band_y, 574, 15, " SIRKULASI", True, True, True), 15)
fields_sirkulasi = [
    ("CRT", "crt"),
    ("Denyut Nadi", "denyut_nadi"),
    ("Irama Jantung", "irama"),
    ("Akral", "akral")
]
for label, field in fields_sirkulasi:
    y = current_band_y
    add_element(create_static_text(0, y, 120, 15, label))
    add_element(create_static_text(120, y, 10, 15, ":"))
    add_element(create_text_field(130, y, 440, 15, field))
    add_element('', 15)

add_element('', 10)
start_new_band()

# --- BAND 3: PERKEMIHAN, REPRODUKSI, INTEGUMEN ---
add_element(create_static_text(0, current_band_y, 574, 15, " PERKEMIHAN", True, True, True), 15)
fields_perkemihan = [
    ("BAK", "bak"),
    ("Kateter", "kateter"),
    ("Urin Jumlah", "urin_jumlah"),
    ("Urin Warna", "urin_warna")
]
for label, field in fields_perkemihan:
    y = current_band_y
    add_element(create_static_text(0, y, 120, 15, label))
    add_element(create_static_text(120, y, 10, 15, ":"))
    add_element(create_text_field(130, y, 440, 15, field))
    add_element('', 15)

add_element('', 10)
add_element(create_static_text(0, current_band_y, 574, 15, " SEKSUAL/REPRODUKSI", True, True, True), 15)
fields_reproduksi = [
    ("Status Obstetric", "status_ob_g"),
    ("Menstruasi", "menstruasi"),
    ("Kehamilan", "kehamilan"),
    ("Post Partum", "post_partum"),
    ("Papsmear", "papsmear")
]
for label, field in fields_reproduksi:
    y = current_band_y
    add_element(create_static_text(0, y, 120, 15, label))
    add_element(create_static_text(120, y, 10, 15, ":"))
    add_element(create_text_field(130, y, 440, 15, field))
    add_element('', 15)

add_element('', 10)
add_element(create_static_text(0, current_band_y, 574, 15, " INTEGUMEN & MUSKULOSKELETAL", True, True, True), 15)
fields_integumen = [
    ("Turgor", "turgor"),
    ("Rambut", "rambut"),
    ("Kuku", "kuku"),
    ("Luka", "luka"),
    ("Fraktur", "fraktur")
]
for label, field in fields_integumen:
    y = current_band_y
    add_element(create_static_text(0, y, 120, 15, label))
    add_element(create_static_text(120, y, 10, 15, ":"))
    add_element(create_text_field(130, y, 440, 15, field))
    add_element('', 15)

add_element('', 10)
start_new_band()

# --- BAND 4: THT, PENCERNAAN, NYERI ---
add_element(create_static_text(0, current_band_y, 574, 15, " THT & MATA", True, True, True), 15)
fields_tht = [
    ("Telinga", "telinga"),
    ("Hidung", "hidung"),
    ("Mata", "mata"),
    ("Gigi", "gigi")
]
for label, field in fields_tht:
    y = current_band_y
    add_element(create_static_text(0, y, 120, 15, label))
    add_element(create_static_text(120, y, 10, 15, ":"))
    add_element(create_text_field(130, y, 440, 15, field))
    add_element('', 15)

add_element('', 10)
add_element(create_static_text(0, current_band_y, 574, 15, " PENCERNAAN", True, True, True), 15)
fields_pencernaan = [
    ("Jenis Diit", "jenis_diit"),
    ("Abdomen", "abdomen"),
    ("BAB", "bab"),
    ("Konsistensi", "konsistensi")
]
for label, field in fields_pencernaan:
    y = current_band_y
    add_element(create_static_text(0, y, 120, 15, label))
    add_element(create_static_text(120, y, 10, 15, ":"))
    add_element(create_text_field(130, y, 440, 15, field))
    add_element('', 15)

add_element('', 10)
add_element(create_static_text(0, current_band_y, 574, 15, " ASSESMEN NYERI", True, True, True), 15)
fields_nyeri = [
    ("Nyeri Akut/Kronis", "nyeri_akut"),
    ("Provokes", "nyeri_provokes_diam"),
    ("Quality", "nyeri_quality_tajam"),
    ("Radiation", "nyeri_radiation"),
    ("Severity", "nyeri_severity_skor"),
    ("Time", "nyeri_time_setiap")
]
for label, field in fields_nyeri:
    y = current_band_y
    add_element(create_static_text(0, y, 120, 15, label))
    add_element(create_static_text(120, y, 10, 15, ":"))
    add_element(create_text_field(130, y, 440, 15, field))
    add_element('', 15)

add_element('', 10)
start_new_band()

# ----------------- INJECT INTO JRXML -----------------
with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'r') as f:
    content = f.read()

# Remove old <detail><band>...</band></detail>
import re
pattern = re.compile(r'<detail>.*?</detail>', re.DOTALL)

# Let's write the new detail section
new_detail = '<detail>\n' + '\n'.join(xml_bands) + '\n</detail>'

# Also fix the title band to add Patient Info Box
title_pattern = re.compile(r'<title>.*?</title>', re.DOTALL)
title_match = title_pattern.search(content)

if title_match:
    old_title = title_match.group(0)
    # We will append the Patient Box to the title
    patient_box = f'''
    <staticText>
        <reportElement x="350" y="0" width="60" height="15" uuid="pt_1"/>
        <textElement><font fontName="Tahoma" size="8" isBold="true"/></textElement>
        <text><![CDATA[No. RM]]></text>
    </staticText>
    <staticText>
        <reportElement x="410" y="0" width="10" height="15" uuid="pt_1_c"/>
        <text><![CDATA[:]]></text>
    </staticText>
    <textField isBlankWhenNull="true">
        <reportElement x="420" y="0" width="150" height="15" uuid="pt_tf_1"/>
        <textElement><font fontName="Tahoma" size="8"/></textElement>
        <textFieldExpression><![CDATA[$F{{no_rkm_medis}}]]></textFieldExpression>
    </textField>

    <staticText>
        <reportElement x="350" y="15" width="60" height="15" uuid="pt_2"/>
        <textElement><font fontName="Tahoma" size="8" isBold="true"/></textElement>
        <text><![CDATA[Nama Pasien]]></text>
    </staticText>
    <staticText>
        <reportElement x="410" y="15" width="10" height="15" uuid="pt_2_c"/>
        <text><![CDATA[:]]></text>
    </staticText>
    <textField isBlankWhenNull="true">
        <reportElement x="420" y="15" width="150" height="15" uuid="pt_tf_2"/>
        <textElement><font fontName="Tahoma" size="8"/></textElement>
        <textFieldExpression><![CDATA[$F{{nm_pasien}}]]></textFieldExpression>
    </textField>

    <staticText>
        <reportElement x="350" y="30" width="60" height="15" uuid="pt_3"/>
        <textElement><font fontName="Tahoma" size="8" isBold="true"/></textElement>
        <text><![CDATA[Tgl. Lahir]]></text>
    </staticText>
    <staticText>
        <reportElement x="410" y="30" width="10" height="15" uuid="pt_3_c"/>
        <text><![CDATA[:]]></text>
    </staticText>
    <textField isBlankWhenNull="true">
        <reportElement x="420" y="30" width="150" height="15" uuid="pt_tf_3"/>
        <textElement><font fontName="Tahoma" size="8"/></textElement>
        <textFieldExpression><![CDATA[$F{{tgl_lahir}}]]></textFieldExpression>
    </textField>

    <staticText>
        <reportElement x="350" y="45" width="60" height="15" uuid="pt_4"/>
        <textElement><font fontName="Tahoma" size="8" isBold="true"/></textElement>
        <text><![CDATA[J. Kelamin]]></text>
    </staticText>
    <staticText>
        <reportElement x="410" y="45" width="10" height="15" uuid="pt_4_c"/>
        <text><![CDATA[:]]></text>
    </staticText>
    <textField isBlankWhenNull="true">
        <reportElement x="420" y="45" width="150" height="15" uuid="pt_tf_4"/>
        <textElement><font fontName="Tahoma" size="8"/></textElement>
        <textFieldExpression><![CDATA[$F{{jk}}]]></textFieldExpression>
    </textField>
    '''
    # Find the <band ...> tag in title
    band_pattern = re.compile(r'(<band height="\d+">)')
    new_title = old_title.replace('</band>', patient_box + '</band>')
    content = content.replace(old_title, new_title)

content = pattern.sub(new_detail, content)

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'w') as f:
    f.write(content)
print("SUCCESS: New layout applied.")
EOF
