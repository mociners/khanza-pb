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
    box_xml = ""
    if is_box:
        box_xml = '''<box><pen lineWidth="0.5"/><topPen lineWidth="0.5"/><leftPen lineWidth="0.5"/><bottomPen lineWidth="0.5"/><rightPen lineWidth="0.5"/></box>'''
    return f'''<textField isStretchWithOverflow="true" isBlankWhenNull="true"><reportElement x="{x}" y="{y}" width="{w}" height="{h}" uuid="{uuid.uuid4()}"/><textElement verticalAlignment="Middle"><font fontName="Tahoma" size="8"/></textElement><textFieldExpression><![CDATA[$F{{{field_name}}}]]></textFieldExpression></textField>'''

def create_image(x, y, w, h, path):
    return f'''<image scaleImage="FillFrame" onErrorType="Blank"><reportElement x="{x}" y="{y}" width="{w}" height="{h}" uuid="{uuid.uuid4()}"/><imageExpression><![CDATA["{path}"]]></imageExpression></image>'''

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'r') as f:
    content = f.read()

fields = re.findall(r'<field name="(.*?)"', content)
header_fields = ['no_rkm_medis', 'nm_pasien', 'jk', 'tgl_lahir', 'no_rawat', 'tanggal', 'informasi_dari', 'tgl_tiba', 'nip', 'kd_dokter', 'nm_dokter']
form_fields = [f for f in fields if f not in header_fields]

xml_bands = []

# Section Header Helper
def add_section(title):
    xml_bands.append(f'<band height="15">{create_static_text(0, 0, 574, 15, " " + title, True, True, True)}</band>')

# Band Pair Helper
def add_row(f1, f2):
    els = []
    if f1:
        els.append(create_static_text(0, 0, 110, 15, f1.replace("_", " ").title()))
        els.append(create_static_text(110, 0, 10, 15, ":"))
        els.append(create_text_field(120, 0, 160, 15, f1))
    if f2:
        els.append(create_static_text(290, 0, 110, 15, f2.replace("_", " ").title()))
        els.append(create_static_text(400, 0, 10, 15, ":"))
        els.append(create_text_field(410, 0, 164, 15, f2))
    xml_bands.append(f'<band height="15">{"".join(els)}</band>')

add_section("PEMERIKSAAN UMUM")
# We pair the form fields 2 by 2
i = 0
while i < len(form_fields):
    f1 = form_fields[i]
    f2 = form_fields[i+1] if i+1 < len(form_fields) else None
    
    # Inject Pain scale section before pain fields
    if f1 == 'nyeri_tidak_ada':
        add_section("ASSESMEN NYERI")
        # Inject Wong Baker Scale image!
        # image size ~ 200x50
        xml_bands.append(f'<band height="70">{create_image(150, 5, 250, 60, "./src/picture/skala_nyeri.png")}</band>')
        
    add_row(f1, f2)
    i += 2

# We also need to keep the Title Band unmodified since it has patient data, 
# But we must replace the detail section.
pattern = re.compile(r'<detail>.*?</detail>', re.DOTALL)
new_detail = '<detail>\n' + '\n'.join(xml_bands) + '\n</detail>'
content = pattern.sub(new_detail, content)

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'w') as f:
    f.write(content)
print("SUCCESS: Micro-bands layout with all fields and pain scale applied.")
