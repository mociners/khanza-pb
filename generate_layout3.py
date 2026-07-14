import re
import uuid

def create_static_text(x, y, w, h, text, is_bold=False, is_box=False, is_header=False):
    font_bold = '<font fontName="Tahoma" size="8" isBold="true"/>' if is_bold else '<font fontName="Tahoma" size="8"/>'
    if is_header:
        font_bold = '<font fontName="Tahoma" size="9" isBold="true"/>'
    box_xml = ""
    if is_box:
        box_xml = '''<box><pen lineWidth="0.5"/><topPen lineWidth="0.5"/><leftPen lineWidth="0.5"/><bottomPen lineWidth="0.5"/><rightPen lineWidth="0.5"/></box>'''
    mode = ' mode="Opaque" backcolor="#CCCCCC"' if is_header else ''
    return f'''<staticText><reportElement x="{x}" y="{y}" width="{w}" height="{h}"{mode} uuid="{uuid.uuid4()}"/><textElement verticalAlignment="Middle">{font_bold}</textElement><text><![CDATA[{text}]]></text></staticText>'''

def create_text_field(x, y, w, h, field_name, is_box=False):
    placed_fields.add(field_name)
    box_xml = ""
    if is_box:
        box_xml = '''<box><pen lineWidth="0.5"/><topPen lineWidth="0.5"/><leftPen lineWidth="0.5"/><bottomPen lineWidth="0.5"/><rightPen lineWidth="0.5"/></box>'''
    return f'''<textField isStretchWithOverflow="true" isBlankWhenNull="true"><reportElement x="{x}" y="{y}" width="{w}" height="{h}" uuid="{uuid.uuid4()}"/><textElement verticalAlignment="Middle"><font fontName="Tahoma" size="8"/></textElement><textFieldExpression><![CDATA[$F{{{field_name}}}]]></textFieldExpression></textField>'''

def create_checkbox(x, y, w, h, label, field_name, condition, offset=15):
    placed_fields.add(field_name)
    return f'''<textField isBlankWhenNull="true"><reportElement x="{x}" y="{y}" width="{offset}" height="{h}" uuid="{uuid.uuid4()}"/><textElement verticalAlignment="Middle"><font fontName="Tahoma" size="8"/></textElement><textFieldExpression><![CDATA[$F{{{field_name}}}.equals("{condition}") ? "[v]" : "[  ]"]]></textFieldExpression></textField><staticText><reportElement x="{x+offset}" y="{y}" width="{w-offset}" height="{h}" uuid="{uuid.uuid4()}"/><textElement verticalAlignment="Middle"><font fontName="Tahoma" size="8"/></textElement><text><![CDATA[{label}]]></text></staticText>'''

def create_image(x, y, w, h, path):
    return f'''<image scaleImage="FillFrame" onErrorType="Blank"><reportElement x="{x}" y="{y}" width="{w}" height="{h}" uuid="{uuid.uuid4()}"/><imageExpression><![CDATA["{path}"]]></imageExpression></image>'''

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'r') as f:
    content = f.read()

all_fields = re.findall(r'<field name="(.*?)"', content)
placed_fields = set(['no_rkm_medis', 'nm_pasien', 'jk', 'tgl_lahir', 'no_rawat'])

xml_bands = []
current_row_elements = []

def finish_row(height=15):
    global current_row_elements
    if current_row_elements:
        xml_bands.append(f'<band height="{height}">\n' + "".join(current_row_elements) + '\n</band>')
    current_row_elements = []

def add_to_row(el):
    current_row_elements.append(el)

def add_section(title):
    finish_row()
    add_to_row(create_static_text(0, 0, 574, 15, " " + title, True, True, True))
    finish_row()
    
# --- PEMERIKSAAN FISIK ---
add_section("ASSESMEN KEPERAWATAN RAWAT INAP DEWASA")
add_to_row(create_static_text(0, 0, 574, 15, " (Diisi dalam waktu 24 jam pertama pasien masuk rawat inap)", False, True, False))
finish_row()

add_to_row(create_static_text(0, 0, 100, 15, "Tanggal Tiba:"))
add_to_row(create_text_field(100, 0, 80, 15, "tgl_tiba"))
add_to_row(create_static_text(200, 0, 100, 15, "Tanggal Assesmen:"))
add_to_row(create_text_field(300, 0, 80, 15, "tanggal"))
add_to_row(create_static_text(400, 0, 50, 15, "DPJP:"))
add_to_row(create_text_field(450, 0, 120, 15, "nm_dokter"))
finish_row()

add_to_row(create_static_text(0, 0, 100, 15, "Informasi Dari:"))
add_to_row(create_text_field(100, 0, 280, 15, "informasi_dari"))
add_to_row(create_static_text(400, 0, 50, 15, "Perawat:"))
add_to_row(create_text_field(450, 0, 120, 15, "nip"))
finish_row()

add_section("PEMERIKSAAN FISIK")
add_to_row(create_static_text(0, 0, 100, 15, "Diagnosa Masuk"))
add_to_row(create_static_text(100, 0, 10, 15, ":"))
add_to_row(create_text_field(110, 0, 460, 15, "diagnosa_masuk"))
finish_row()

add_to_row(create_static_text(0, 0, 100, 15, "Vital Sign"))
add_to_row(create_static_text(100, 0, 10, 15, ":"))
add_to_row(create_static_text(110, 0, 20, 15, "TD:"))
add_to_row(create_text_field(130, 0, 40, 15, "td"))
add_to_row(create_static_text(170, 0, 20, 15, "Suhu:"))
add_to_row(create_text_field(190, 0, 40, 15, "suhu"))
add_to_row(create_static_text(230, 0, 20, 15, "Nadi:"))
add_to_row(create_text_field(250, 0, 40, 15, "nadi_utama"))
add_to_row(create_static_text(290, 0, 20, 15, "RR:"))
add_to_row(create_text_field(310, 0, 40, 15, "rr_utama"))
finish_row()

add_to_row(create_static_text(0, 0, 100, 15, "Riw. Peny. Keluarga"))
add_to_row(create_static_text(100, 0, 10, 15, ":"))
add_to_row(create_text_field(110, 0, 460, 15, "riwayat_keluarga"))
finish_row()

add_to_row(create_static_text(0, 0, 100, 15, "Riw. Pasien"))
add_to_row(create_static_text(100, 0, 10, 15, ":"))
add_to_row(create_text_field(110, 0, 460, 15, "riwayat_pasien"))
finish_row()

add_to_row(create_static_text(0, 0, 100, 15, "Alergi"))
add_to_row(create_static_text(100, 0, 10, 15, ":"))
add_to_row(create_checkbox(110, 0, 80, 15, "Tidak ada", "alergi", "Tidak"))
add_to_row(create_checkbox(200, 0, 80, 15, "Ada, Jenis:", "alergi", "Ya"))
add_to_row(create_text_field(280, 0, 290, 15, "jenis_alergi"))
finish_row()

# Explicitly grouped sections from generate_layout.py
sections = [
    (" DATA PSIKOLOGIS SOSIAL EKONOMI, SPIRITUAL", [
        ("Psikologis", "kondisi_psikologis"),
        ("Status Pernikahan", "status_nikah"),
        ("Pendidikan Terakhir", "pendidikan"),
        ("Warga Negara", "warga_negara"),
        ("Agama", "agama"),
        ("Pekerjaan", "pekerjaan"),
        ("Aktivitas", "aktivitas"),
        ("Tinggal Bersama", "tinggal_bersama"),
        ("Tempat Tinggal", "tempat_tinggal")
    ]),
    (" PENGKAJIAN NEUROSENSORIS", [
        ("Status Mental", "status_mental"),
        ("Orientasi Orang", "orientasi_orang"),
        ("Memori", "memori"),
        ("GCS (E, M, V)", "gcs_jml")
    ]),
    (" PERNAFASAN", [
        ("Airway", "jalan_nafas"),
        ("Breathing", "pernafasan"),
        ("Bunyi Paru", "bunyi_nafas"),
        ("Kesulitan Bernafas", "kesulitan_nafas"),
        ("Oksigen", "oksigen_ltr"),
        ("Batuk", "batuk"),
        ("SpO2", "spo2")
    ]),
    (" SIRKULASI", [
        ("CRT", "crt"),
        ("Denyut Nadi", "denyut_nadi"),
        ("Irama Jantung", "irama"),
        ("Akral", "akral")
    ]),
    (" PERKEMIHAN", [
        ("BAK", "bak"),
        ("Kateter", "kateter"),
        ("Urin Jumlah", "urin_jumlah"),
        ("Urin Warna", "urin_warna")
    ]),
    (" SEKSUAL/REPRODUKSI", [
        ("Status Obstetric", "status_ob_g"),
        ("Menstruasi", "menstruasi"),
        ("Kehamilan", "kehamilan"),
        ("Post Partum", "post_partum"),
        ("Papsmear", "papsmear")
    ]),
    (" INTEGUMEN & MUSKULOSKELETAL", [
        ("Turgor", "turgor"),
        ("Rambut", "rambut"),
        ("Kuku", "kuku"),
        ("Luka", "luka"),
        ("Fraktur", "fraktur")
    ]),
    (" THT & MATA", [
        ("Telinga", "telinga"),
        ("Hidung", "hidung"),
        ("Mata", "mata"),
        ("Gigi", "gigi")
    ]),
    (" PENCERNAAN", [
        ("Jenis Diit", "jenis_diit"),
        ("Abdomen", "abdomen"),
        ("BAB", "bab"),
        ("Konsistensi", "konsistensi")
    ])
]

for sec_title, sec_fields in sections:
    add_section(sec_title)
    for label, field in sec_fields:
        add_to_row(create_static_text(0, 0, 120, 15, label))
        add_to_row(create_static_text(120, 0, 10, 15, ":"))
        add_to_row(create_text_field(130, 0, 440, 15, field))
        finish_row()

add_section(" ASSESMEN NYERI")
# INJECT IMAGE!
add_to_row(create_image(150, 5, 250, 60, "./src/picture/skala_nyeri.png"))
finish_row(70)

fields_nyeri = [
    ("Nyeri Akut/Kronis", "nyeri_akut"),
    ("Provokes", "nyeri_provokes_diam"),
    ("Quality", "nyeri_quality_tajam"),
    ("Radiation", "nyeri_radiation"),
    ("Severity", "nyeri_severity_skor"),
    ("Time", "nyeri_time_setiap")
]
for label, field in fields_nyeri:
    add_to_row(create_static_text(0, 0, 120, 15, label))
    add_to_row(create_static_text(120, 0, 10, 15, ":"))
    add_to_row(create_text_field(130, 0, 440, 15, field))
    finish_row()

# NOW, dynamically add ALL REMAINING unplaced fields
unplaced = [f for f in all_fields if f not in placed_fields]
if unplaced:
    add_section(" DETAIL LAINNYA")
    for i in range(0, len(unplaced), 2):
        f1 = unplaced[i]
        f2 = unplaced[i+1] if i+1 < len(unplaced) else None
        
        placed_fields.add(f1)
        add_to_row(create_static_text(0, 0, 110, 15, f1.replace("_", " ").title()))
        add_to_row(create_static_text(110, 0, 10, 15, ":"))
        add_to_row(create_text_field(120, 0, 160, 15, f1))
        
        if f2:
            placed_fields.add(f2)
            add_to_row(create_static_text(290, 0, 110, 15, f2.replace("_", " ").title()))
            add_to_row(create_static_text(400, 0, 10, 15, ":"))
            add_to_row(create_text_field(410, 0, 164, 15, f2))
        finish_row()


# ----------------- INJECT INTO JRXML -----------------
import re
pattern = re.compile(r'<detail>.*?</detail>', re.DOTALL)
new_detail = '<detail>\n' + '\n'.join(xml_bands) + '\n</detail>'
content = pattern.sub(new_detail, content)

# Remove the title modification part since it's already there from previous runs!
# BUT WAIT, did my previous generate_layout2.py wipe it out? Yes, it overwrote the old file. Wait, no it just read the file and replaced the title, so the patient info is ALREADY there.

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'w') as f:
    f.write(content)
print("SUCCESS: Hybrid micro-bands layout with all fields and pain scale applied.")
