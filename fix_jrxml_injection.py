import os
import re
import uuid

def fix_jrxml():
    path = "report/rptSuratPersetujuanUmum.jrxml"
    with open(path, "r") as f:
        jrxml = f.read()

    # The previous script mistakenly inserted the image after the FIRST </image> (logo).
    # Let's remove the wrongly inserted image first.
    wrong_img = re.search(r'<image scaleImage="RetainShape" onErrorType="Blank">\s*<reportElement positionType="Float" x="210" y="585" width="170" height="50" uuid="[^"]+"/>\s*<imageExpression><!\[CDATA\[\$P\{photo_saksi_2\}\]\]></imageExpression>\s*</image>', jrxml)
    if wrong_img:
        jrxml = jrxml.replace(wrong_img.group(0), '')

    # Insert it correctly after the PJ signature image which is:
    # <image scaleImage="RetainShape" onErrorType="Blank">
    #     <reportElement positionType="Float" x="380" y="585" width="180" height="50" uuid="..."/>
    #     <imageExpression><![CDATA[$P{photo}]]></imageExpression>
    # </image>
    
    # We will find $P{photo} image and insert our Saksi 2 image right after its </image>
    valid_uuid_img = str(uuid.uuid4())
    xml_image = f"""
                        <image scaleImage="RetainShape" onErrorType="Blank">
                                <reportElement positionType="Float" x="210" y="585" width="170" height="50" uuid="{valid_uuid_img}"/>
                                <imageExpression><![CDATA[$P{{photo_saksi_2}}]]></imageExpression>
                        </image>"""
                        
    pj_img_regex = r'(<image[^>]*>\s*<reportElement[^>]*\bwidth="180"[^>]*y="585"[^>]*/>\s*<imageExpression><!\[CDATA\[\$P\{photo\}\]\]></imageExpression>\s*</image>)'
    
    if '<imageExpression><![CDATA[$P{photo_saksi_2}]]></imageExpression>' not in jrxml:
        jrxml = re.sub(pj_img_regex, r'\1' + xml_image, jrxml)

    with open(path, "w") as f:
        f.write(jrxml)

if __name__ == "__main__":
    fix_jrxml()
    print("Fixed JRXML Injection Point")
