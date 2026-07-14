import re

def patch():
    with open("report/rptSuratPersetujuanUmum.jrxml", "r") as f:
        xml = f.read()

    # 1. Add fields
    fields = """	<field name="edukasi_pj" class="java.lang.String"/>
	<field name="edukasi_rs" class="java.lang.String"/>
"""
    xml = xml.replace('	<field name="alasan_naik_kelas" class="java.lang.String"/>\n', '	<field name="alasan_naik_kelas" class="java.lang.String"/>\n' + fields)

    # 2. Replace staticText with textField
    old_static = """			<staticText>
				<reportElement positionType="Float" x="10" y="661" width="570" height="14" uuid="9726ba0b-5154-4391-91dd-4d8501d701d1"/>
				<textElement textAlignment="Center">
					<font fontName="Tahoma" size="8"/>
				</textElement>
				<text><![CDATA[Edukasi Lanjutan : Pihak Keluarga.......................................Pihak Rumah Sakit.................................]]></text>
			</staticText>"""
    
    new_text_field = """			<textField isStretchWithOverflow="true">
				<reportElement positionType="Float" x="10" y="671" width="570" height="14" uuid="9726ba0b-5154-4391-91dd-4d8501d701d1"/>
				<textElement textAlignment="Center">
					<font fontName="Tahoma" size="8"/>
				</textElement>
				<textFieldExpression><![CDATA["Edukasi Lanjutan : Pihak Keluarga: " + ($F{edukasi_pj} == null || $F{edukasi_pj}.trim().equals("") ? "......................................." : $F{edukasi_pj}) + "     Pihak Rumah Sakit: " + ($F{edukasi_rs} == null || $F{edukasi_rs}.trim().equals("") ? "......................................." : $F{edukasi_rs})]]></textFieldExpression>
			</textField>"""
            
    xml = xml.replace(old_static, new_text_field)

    # 3. Increase band height
    xml = xml.replace('<band height="685">', '<band height="695">')

    with open("report/rptSuratPersetujuanUmum.jrxml", "w") as f:
        f.write(xml)

if __name__ == "__main__":
    patch()
    print("JRXML Edukasi Patched")
