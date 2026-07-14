import os
import re

def patch_jrxml():
    path = "report/rptSuratPersetujuanUmum.jrxml"
    with open(path, "r") as f:
        jrxml = f.read()

    # Add parameter photo_saksi_2
    if 'name="photo_saksi_2"' not in jrxml:
        param = '<parameter name="photo_saksi_2" class="java.lang.String"/>'
        jrxml = jrxml.replace('<parameter name="photo" class="java.lang.String"/>', 
                              '<parameter name="photo" class="java.lang.String"/>\n\t' + param)

    # Add field saksi_2
    if 'name="saksi_2"' not in jrxml:
        field = '\t<field name="saksi_2" class="java.lang.String"/>'
        jrxml = jrxml.replace('<field name="edukasi_rs" class="java.lang.String"/>', 
                              '<field name="edukasi_rs" class="java.lang.String"/>\n' + field)

    # Add signature image for Saksi 2
    if '<imageExpression><![CDATA[$P{photo_saksi_2}]]></imageExpression>' not in jrxml:
        xml_image = """
                        <image scaleImage="RetainShape" onErrorType="Blank">
                                <reportElement positionType="Float" x="210" y="585" width="170" height="50" uuid="saksi2-img-1234"/>
                                <imageExpression><![CDATA[$P{photo_saksi_2}]]></imageExpression>
                        </image>"""
        jrxml = jrxml.replace('</image>', '</image>' + xml_image, 1)

    # Add text field for Saksi 2 name
    # Currently it is: ( .......................................... )
    xml_text = """
                        <textField>
                                <reportElement positionType="Float" x="210" y="635" width="170" height="12" uuid="saksi2-text-1234"/>
                                <textElement textAlignment="Center">
                                        <font fontName="Tahoma" size="9"/>
                                </textElement>
                                <textFieldExpression><![CDATA["( "+ ($F{saksi_2} == null || $F{saksi_2}.trim().equals("") ? "......................................" : $F{saksi_2}) +" )"]]></textFieldExpression>
                        </textField>"""
    
    jrxml = re.sub(r'<textField>\s*<reportElement positionType="Float" x="210" y="635" width="170" height="12"[^>]+/>\s*<textElement textAlignment="Center">\s*<font fontName="Tahoma" size="9"/>\s*</textElement>\s*<textFieldExpression><!\[CDATA\["\(\s*\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\.\s*\)"\]\]></textFieldExpression>\s*</textField>', xml_text, jrxml)

    with open(path, "w") as f:
        f.write(jrxml)

if __name__ == "__main__":
    patch_jrxml()
    print("Patched JRXML")
