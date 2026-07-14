import re

with open('report/rptObservasiTTVBalance.jrxml', 'r') as f:
    jrxml = f.read()

# Add chart_image field
old_fields = '<field name="col12" class="java.lang.String"/>'
new_fields = '<field name="col12" class="java.lang.String"/>\n    <field name="chart_image" class="java.awt.Image"/>'
if "chart_image" not in jrxml:
    jrxml = jrxml.replace(old_fields, new_fields)

# Change <detail> to include two bands
old_detail = r"""    <detail>
        <band height="14" splitType="Stretch">
            <!-- Row Label -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="0" y="0" width="130" height="14"/>
                <box leftPadding="3"><pen lineWidth="0.5"/></box>
                <textElement verticalAlignment="Middle">
                    <font fontName="Tahoma" size="7" isBold="true"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{row_label}]]></textFieldExpression>
            </textField>
            <!-- Col 1 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="130" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col1}]]></textFieldExpression>
            </textField>
            <!-- Col 2 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="186" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col2}]]></textFieldExpression>
            </textField>
            <!-- Col 3 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="242" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col3}]]></textFieldExpression>
            </textField>
            <!-- Col 4 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="298" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col4}]]></textFieldExpression>
            </textField>
            <!-- Col 5 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="354" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col5}]]></textFieldExpression>
            </textField>
            <!-- Col 6 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="410" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col6}]]></textFieldExpression>
            </textField>
            <!-- Col 7 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="466" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col7}]]></textFieldExpression>
            </textField>
            <!-- Col 8 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="522" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col8}]]></textFieldExpression>
            </textField>
            <!-- Col 9 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="578" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col9}]]></textFieldExpression>
            </textField>
            <!-- Col 10 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="634" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col10}]]></textFieldExpression>
            </textField>
            <!-- Col 11 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="690" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col11}]]></textFieldExpression>
            </textField>
            <!-- Col 12 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="746" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col12}]]></textFieldExpression>
            </textField>
        </band>
    </detail>"""

new_detail = r"""    <detail>
        <band height="14" splitType="Stretch">
            <printWhenExpression><![CDATA[!$F{row_label}.equals("CHART")]]></printWhenExpression>
            <!-- Row Label -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="0" y="0" width="130" height="14"/>
                <box leftPadding="3"><pen lineWidth="0.5"/></box>
                <textElement verticalAlignment="Middle">
                    <font fontName="Tahoma" size="7" isBold="true"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{row_label}]]></textFieldExpression>
            </textField>
            <!-- Col 1 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="130" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col1}]]></textFieldExpression>
            </textField>
            <!-- Col 2 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="186" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col2}]]></textFieldExpression>
            </textField>
            <!-- Col 3 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="242" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col3}]]></textFieldExpression>
            </textField>
            <!-- Col 4 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="298" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col4}]]></textFieldExpression>
            </textField>
            <!-- Col 5 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="354" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col5}]]></textFieldExpression>
            </textField>
            <!-- Col 6 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="410" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col6}]]></textFieldExpression>
            </textField>
            <!-- Col 7 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="466" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col7}]]></textFieldExpression>
            </textField>
            <!-- Col 8 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="522" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col8}]]></textFieldExpression>
            </textField>
            <!-- Col 9 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="578" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col9}]]></textFieldExpression>
            </textField>
            <!-- Col 10 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="634" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col10}]]></textFieldExpression>
            </textField>
            <!-- Col 11 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="690" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col11}]]></textFieldExpression>
            </textField>
            <!-- Col 12 -->
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="746" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle" markup="styled">
                    <font fontName="Tahoma" size="6"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col12}]]></textFieldExpression>
            </textField>
        </band>
        
        <band height="120" splitType="Prevent">
            <printWhenExpression><![CDATA[$F{row_label}.equals("CHART")]]></printWhenExpression>
            
            <!-- Left Axis Column (Width 130) -->
            <rectangle>
                <reportElement x="0" y="0" width="130" height="120" backcolor="#F0F0F0"/>
                <graphicElement><pen lineWidth="0.5"/></graphicElement>
            </rectangle>
            
            <!-- Scale text positioned to align with horizontal grid lines -->
            <staticText>
                <reportElement x="5" y="0" width="120" height="14"/>
                <textElement verticalAlignment="Middle"><font fontName="Tahoma" size="7" isBold="true"/></textElement>
                <text><![CDATA[160           60           41]]></text>
            </staticText>
            <staticText>
                <reportElement x="5" y="15" width="120" height="14"/>
                <textElement verticalAlignment="Middle"><font fontName="Tahoma" size="7" isBold="true"/></textElement>
                <text><![CDATA[140           50           40]]></text>
            </staticText>
            <staticText>
                <reportElement x="5" y="33" width="120" height="14"/>
                <textElement verticalAlignment="Middle"><font fontName="Tahoma" size="7" isBold="true"/></textElement>
                <text><![CDATA[120           40           39]]></text>
            </staticText>
            <staticText>
                <reportElement x="5" y="53" width="120" height="14"/>
                <textElement verticalAlignment="Middle"><font fontName="Tahoma" size="7" isBold="true"/></textElement>
                <text><![CDATA[100           30           38]]></text>
            </staticText>
            <staticText>
                <reportElement x="5" y="73" width="120" height="14"/>
                <textElement verticalAlignment="Middle"><font fontName="Tahoma" size="7" isBold="true"/></textElement>
                <text><![CDATA[80             20           37]]></text>
            </staticText>
            <staticText>
                <reportElement x="5" y="93" width="120" height="14"/>
                <textElement verticalAlignment="Middle"><font fontName="Tahoma" size="7" isBold="true"/></textElement>
                <text><![CDATA[60             10           36]]></text>
            </staticText>
            <staticText>
                <reportElement x="5" y="106" width="120" height="14"/>
                <textElement verticalAlignment="Middle"><font fontName="Tahoma" size="7" isBold="true"/></textElement>
                <text><![CDATA[40              0           35]]></text>
            </staticText>
            
            <!-- Right Chart Area -->
            <rectangle>
                <reportElement x="130" y="0" width="672" height="120"/>
                <graphicElement><pen lineWidth="0.5"/></graphicElement>
            </rectangle>
            
            <!-- Vertical Lines for columns (130 to 802) -->
            <line><reportElement x="186" y="0" width="1" height="120"/><graphicElement><pen lineWidth="0.5"/></graphicElement></line>
            <line><reportElement x="242" y="0" width="1" height="120"/><graphicElement><pen lineWidth="0.5"/></graphicElement></line>
            <line><reportElement x="298" y="0" width="1" height="120"/><graphicElement><pen lineWidth="0.5"/></graphicElement></line>
            <line><reportElement x="354" y="0" width="1" height="120"/><graphicElement><pen lineWidth="0.5"/></graphicElement></line>
            <line><reportElement x="410" y="0" width="1" height="120"/><graphicElement><pen lineWidth="0.5"/></graphicElement></line>
            <line><reportElement x="466" y="0" width="1" height="120"/><graphicElement><pen lineWidth="0.5"/></graphicElement></line>
            <line><reportElement x="522" y="0" width="1" height="120"/><graphicElement><pen lineWidth="0.5"/></graphicElement></line>
            <line><reportElement x="578" y="0" width="1" height="120"/><graphicElement><pen lineWidth="0.5"/></graphicElement></line>
            <line><reportElement x="634" y="0" width="1" height="120"/><graphicElement><pen lineWidth="0.5"/></graphicElement></line>
            <line><reportElement x="690" y="0" width="1" height="120"/><graphicElement><pen lineWidth="0.5"/></graphicElement></line>
            <line><reportElement x="746" y="0" width="1" height="120"/><graphicElement><pen lineWidth="0.5"/></graphicElement></line>
            
            <!-- Image Overlay (Transparent JFreeChart) -->
            <image scaleImage="FillFrame" isUsingCache="true" onErrorType="Blank">
                <reportElement x="130" y="0" width="672" height="120"/>
                <imageExpression><![CDATA[$F{chart_image}]]></imageExpression>
            </image>
        </band>
    </detail>"""

if "<printWhenExpression>" not in jrxml:
    jrxml = jrxml.replace(old_detail, new_detail)

with open('report/rptObservasiTTVBalance.jrxml', 'w') as f:
    f.write(jrxml)

print("Patched JRXML with CHART detail band!")
