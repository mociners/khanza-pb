import os
import uuid

def patch_jrxml_explicit():
    path = "report/rptSuratPersetujuanUmum.jrxml"
    with open(path, "r") as f:
        jrxml = f.read()

    # 1. Insert Saksi 2 signature image
    pj_img_str = """			<image scaleImage="RetainShape" onErrorType="Blank">
				<reportElement positionType="Float" x="380" y="585" width="180" height="50" uuid="9a058474-0f2c-4977-bc6d-62f4beafaf14"/>
				<imageExpression><![CDATA[$P{photo}]]></imageExpression>
			</image>"""
    
    valid_uuid_img = str(uuid.uuid4())
    saksi2_img_str = f"""			<image scaleImage="RetainShape" onErrorType="Blank">
				<reportElement positionType="Float" x="210" y="585" width="170" height="50" uuid="{valid_uuid_img}"/>
				<imageExpression><![CDATA[$P{{photo_saksi_2}}]]></imageExpression>
			</image>"""
            
    if saksi2_img_str not in jrxml:
        jrxml = jrxml.replace(pj_img_str, pj_img_str + "\n" + saksi2_img_str)

    # 2. Replace Saksi 2 name text field
    old_text_field = """			<textField>
				<reportElement positionType="Float" x="210" y="635" width="170" height="12" uuid="8b9667e2-6c01-453c-8a5c-12c3a0dad332"/>
				<textElement textAlignment="Center">
					<font fontName="Tahoma" size="9"/>
				</textElement>
				<textFieldExpression><![CDATA["( .......................................... )"]]></textFieldExpression>
			</textField>"""
            
    new_text_field = """			<textField>
				<reportElement positionType="Float" x="210" y="635" width="170" height="12" uuid="8b9667e2-6c01-453c-8a5c-12c3a0dad332"/>
				<textElement textAlignment="Center">
					<font fontName="Tahoma" size="9"/>
				</textElement>
				<textFieldExpression><![CDATA["( "+ ($F{saksi_2} == null || $F{saksi_2}.trim().equals("") ? "......................................" : $F{saksi_2}) +" )"]]></textFieldExpression>
			</textField>"""
            
    jrxml = jrxml.replace(old_text_field, new_text_field)
    
    # 3. Check if Pembuat Pernyataan TTD is stretched
    # Let's fix Pembuat Pernyataan TTD bounds if it is stretched.
    # Currently x="380" y="585" width="180" height="50". 
    # Maybe make height="70" to prevent squishing?
    # Actually, scaleImage="RetainShape" is already set.
    # We will leave Pembuat Pernyataan size alone for now as we don't know the exact issue without seeing it.
    
    with open(path, "w") as f:
        f.write(jrxml)

if __name__ == "__main__":
    patch_jrxml_explicit()
    print("Patched JRXML Expliclty")
