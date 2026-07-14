import re

with open('report/rptObservasiTTVBalance.jrxml', 'r') as f:
    jrxml = f.read()

# Change the legend text
old_legend = r'<text><![CDATA[Keterangan:   &#x25A0; = Nadi     &#x25CF; = Respirasi     &#x25B2; = Suhu]]></text>'
new_legend = r'<text><![CDATA[Keterangan:   <font color="red">Nadi</font>     Respirasi     <font color="blue">Suhu</font>]]></text>'

# We must set markup="html" for the legend text field
old_legend_field = r'<textElement textAlignment="Center" verticalAlignment="Middle">'
new_legend_field = r'<textElement textAlignment="Center" verticalAlignment="Middle" markup="html">'

jrxml = jrxml.replace(old_legend, new_legend)
# wait, legend is a staticText! staticText does not support markup="html" in older JasperReports. We need to change it to textField.

old_static_text = """                <staticText>
                    <reportElement x="135" y="74" width="662" height="14"/>
                    <textElement textAlignment="Center" verticalAlignment="Middle">
                        <font fontName="Tahoma" size="7" isBold="true"/>
                    </textElement>
                    <text><![CDATA[Keterangan:   &#x25A0; = Nadi     &#x25CF; = Respirasi     &#x25B2; = Suhu]]></text>
                </staticText>"""

new_text_field = """                <textField>
                    <reportElement x="135" y="74" width="662" height="14"/>
                    <textElement textAlignment="Center" verticalAlignment="Middle" markup="html">
                        <font fontName="Tahoma" size="7" isBold="true"/>
                    </textElement>
                    <textFieldExpression><![CDATA["Keterangan:   <font color='red'>Nadi</font>     Respirasi     <font color='blue'>Suhu</font>"]]></textFieldExpression>
                </textField>"""

jrxml = jrxml.replace(old_static_text, new_text_field)

# Set markup="html" for col1 to col12 textElements
for i in range(1, 13):
    old_col_xml = f"""            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="{130 + (i-1)*56}" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{{col{i}}}]]></textFieldExpression>
            </textField>"""
    
    new_col_xml = f"""            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="{130 + (i-1)*56}" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="html">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{{col{i}}}]]></textFieldExpression>
            </textField>"""
    jrxml = jrxml.replace(old_col_xml, new_col_xml)


with open('report/rptObservasiTTVBalance.jrxml', 'w') as f:
    f.write(jrxml)

print("JRXML patched successfully with HTML markup")
