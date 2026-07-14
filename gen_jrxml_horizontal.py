import xml.etree.ElementTree as ET

jrxml = """<?xml version="1.0" encoding="UTF-8"?>
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd" name="null" language="groovy" pageWidth="842" pageHeight="595" orientation="Landscape" columnWidth="802" leftMargin="20" rightMargin="20" topMargin="20" bottomMargin="20">
    <property name="ireport.zoom" value="1.0"/>
    <property name="ireport.x" value="0"/>
    <property name="ireport.y" value="0"/>
    <parameter name="namars" class="java.lang.String"/>
    <parameter name="alamatrs" class="java.lang.String"/>
    <parameter name="kotars" class="java.lang.String"/>
    <parameter name="propinsirs" class="java.lang.String"/>
    <parameter name="kontakrs" class="java.lang.String"/>
    <parameter name="emailrs" class="java.lang.String"/>
    <parameter name="logo" class="java.io.InputStream"/>
    <parameter name="grafik" class="java.awt.Image"/>
    <parameter name="diagnosa" class="java.lang.String"/>
    <parameter name="ruang" class="java.lang.String"/>
    <parameter name="norm" class="java.lang.String"/>
    <parameter name="nama" class="java.lang.String"/>
    <parameter name="tgl_lahir" class="java.lang.String"/>
    <parameter name="jk" class="java.lang.String"/>
    <field name="page_number" class="java.lang.Integer"/>
    <field name="kelompok" class="java.lang.String"/>
    <field name="row_label" class="java.lang.String"/>
    <field name="col1" class="java.lang.String"/>
    <field name="col2" class="java.lang.String"/>
    <field name="col3" class="java.lang.String"/>
    <field name="col4" class="java.lang.String"/>
    <field name="col5" class="java.lang.String"/>
    <field name="col6" class="java.lang.String"/>
    <field name="col7" class="java.lang.String"/>
    <field name="col8" class="java.lang.String"/>
    <field name="col9" class="java.lang.String"/>
    <field name="col10" class="java.lang.String"/>
    <field name="col11" class="java.lang.String"/>
    <field name="col12" class="java.lang.String"/>
    
    <group name="PageGroup" isStartNewPage="true">
        <groupExpression><![CDATA[$F{page_number}]]></groupExpression>
        <groupHeader>
            <band height="106">
                <textField>
                    <reportElement x="0" y="30" width="460" height="14"/>
                    <textElement textAlignment="Center">
                        <font fontName="Tahoma" size="9"/>
                    </textElement>
                    <textFieldExpression><![CDATA[$P{alamatrs}+", "+$P{kotars}+", "+$P{propinsirs}]]></textFieldExpression>
                </textField>
                <textField>
                    <reportElement x="0" y="45" width="460" height="14"/>
                    <textElement textAlignment="Center">
                        <font fontName="Tahoma" size="9"/>
                    </textElement>
                    <textFieldExpression><![CDATA["E-mail : "+$P{emailrs}]]></textFieldExpression>
                </textField>
                <textField>
                    <reportElement x="0" y="0" width="460" height="20"/>
                    <textElement textAlignment="Center">
                        <font fontName="Tahoma" size="13" isBold="true"/>
                    </textElement>
                    <textFieldExpression><![CDATA[$P{namars}]]></textFieldExpression>
                </textField>
                <image scaleImage="FillFrame" onErrorType="Blank">
                    <reportElement x="0" y="2" width="48" height="45"/>
                    <imageExpression><![CDATA[$P{logo}]]></imageExpression>
                </image>
                <rectangle>
                    <reportElement x="470" y="0" width="332" height="70"/>
                    <graphicElement>
                        <pen lineWidth="1.0"/>
                    </graphicElement>
                </rectangle>
                <staticText>
                    <reportElement x="475" y="5" width="50" height="15"/>
                    <textElement><font fontName="Tahoma" size="9"/></textElement>
                    <text><![CDATA[No. RM]]></text>
                </staticText>
                <staticText>
                    <reportElement x="525" y="5" width="5" height="15"/>
                    <textElement><font fontName="Tahoma" size="9"/></textElement>
                    <text><![CDATA[:]]></text>
                </staticText>
                <textField isBlankWhenNull="true">
                    <reportElement x="530" y="5" width="260" height="15"/>
                    <textElement><font fontName="Tahoma" size="9"/></textElement>
                    <textFieldExpression><![CDATA[$P{norm}]]></textFieldExpression>
                </textField>
                
                <staticText>
                    <reportElement x="475" y="20" width="50" height="15"/>
                    <textElement><font fontName="Tahoma" size="9"/></textElement>
                    <text><![CDATA[Nama]]></text>
                </staticText>
                <staticText>
                    <reportElement x="525" y="20" width="5" height="15"/>
                    <textElement><font fontName="Tahoma" size="9"/></textElement>
                    <text><![CDATA[:]]></text>
                </staticText>
                <textField isBlankWhenNull="true">
                    <reportElement x="530" y="20" width="260" height="15"/>
                    <textElement><font fontName="Tahoma" size="9"/></textElement>
                    <textFieldExpression><![CDATA[$P{nama}]]></textFieldExpression>
                </textField>
                
                <staticText>
                    <reportElement x="475" y="35" width="50" height="15"/>
                    <textElement><font fontName="Tahoma" size="9"/></textElement>
                    <text><![CDATA[Tgl. Lahir]]></text>
                </staticText>
                <staticText>
                    <reportElement x="525" y="35" width="5" height="15"/>
                    <textElement><font fontName="Tahoma" size="9"/></textElement>
                    <text><![CDATA[:]]></text>
                </staticText>
                <textField isBlankWhenNull="true">
                    <reportElement x="530" y="35" width="260" height="15"/>
                    <textElement><font fontName="Tahoma" size="9"/></textElement>
                    <textFieldExpression><![CDATA[$P{tgl_lahir}]]></textFieldExpression>
                </textField>
                
                <staticText>
                    <reportElement x="475" y="50" width="60" height="15"/>
                    <textElement><font fontName="Tahoma" size="9"/></textElement>
                    <text><![CDATA[Jenis Kelamin :]]></text>
                </staticText>
                <textField isBlankWhenNull="true">
                    <reportElement x="535" y="50" width="255" height="15"/>
                    <textElement><font fontName="Tahoma" size="9"/></textElement>
                    <textFieldExpression><![CDATA[$P{jk}]]></textFieldExpression>
                </textField>

                <rectangle>
                    <reportElement x="0" y="70" width="802" height="16" backcolor="#000000"/>
                    <graphicElement>
                        <pen lineWidth="1.0"/>
                    </graphicElement>
                </rectangle>
                <staticText>
                    <reportElement x="0" y="70" width="802" height="16" forecolor="#FFFFFF"/>
                    <textElement textAlignment="Center" verticalAlignment="Middle">
                        <font fontName="Tahoma" size="10" isBold="true"/>
                    </textElement>
                    <text><![CDATA[OBSERVASI TANDA-TANDA VITAL & BALANCE CAIRAN]]></text>
                </staticText>

                <rectangle>
                    <reportElement x="0" y="86" width="300" height="20"/>
                </rectangle>
                <staticText>
                    <reportElement x="5" y="86" width="50" height="20"/>
                    <textElement verticalAlignment="Middle"><font fontName="Tahoma" size="9"/></textElement>
                    <text><![CDATA[Diagnosa :]]></text>
                </staticText>
                <textField isBlankWhenNull="true">
                    <reportElement x="55" y="86" width="240" height="20"/>
                    <textElement verticalAlignment="Middle"><font fontName="Tahoma" size="9"/></textElement>
                    <textFieldExpression><![CDATA[$P{diagnosa}]]></textFieldExpression>
                </textField>
                
                <rectangle>
                    <reportElement x="300" y="86" width="200" height="20"/>
                </rectangle>
                <staticText>
                    <reportElement x="305" y="86" width="50" height="20"/>
                    <textElement verticalAlignment="Middle"><font fontName="Tahoma" size="9"/></textElement>
                    <text><![CDATA[Bagian :]]></text>
                </staticText>
                
                <rectangle>
                    <reportElement x="500" y="86" width="302" height="20"/>
                </rectangle>
                <staticText>
                    <reportElement x="505" y="86" width="70" height="20"/>
                    <textElement verticalAlignment="Middle"><font fontName="Tahoma" size="9"/></textElement>
                    <text><![CDATA[Ruang / Kelas :]]></text>
                </staticText>
                <textField isBlankWhenNull="true">
                    <reportElement x="575" y="86" width="220" height="20"/>
                    <textElement verticalAlignment="Middle"><font fontName="Tahoma" size="9"/></textElement>
                    <textFieldExpression><![CDATA[$P{ruang}]]></textFieldExpression>
                </textField>
            </band>
        </groupHeader>
    </group>

    <group name="KelompokGroup">
        <groupExpression><![CDATA[$F{kelompok}]]></groupExpression>
        <groupFooter>
            <band height="200">
                <printWhenExpression><![CDATA[$F{kelompok}.equals("TOP")]]></printWhenExpression>
                <image scaleImage="FillFrame" onErrorType="Blank">
                    <reportElement x="120" y="0" width="682" height="200"/>
                    <imageExpression><![CDATA[$P{grafik}]]></imageExpression>
                </image>
            </band>
        </groupFooter>
    </group>

    <detail>
        <band height="14" splitType="Stretch">
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="0" y="0" width="120" height="14"/>
                <box leftPadding="3"><pen lineWidth="0.5"/></box>
                <textElement verticalAlignment="Middle">
                    <font fontName="Tahoma" size="8" isBold="true"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{row_label}]]></textFieldExpression>
            </textField>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="120" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="8"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col1}]]></textFieldExpression>
            </textField>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="176" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="8"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col2}]]></textFieldExpression>
            </textField>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="232" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="8"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col3}]]></textFieldExpression>
            </textField>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="288" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="8"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col4}]]></textFieldExpression>
            </textField>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="344" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="8"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col5}]]></textFieldExpression>
            </textField>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="400" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="8"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col6}]]></textFieldExpression>
            </textField>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="456" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="8"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col7}]]></textFieldExpression>
            </textField>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="512" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="8"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col8}]]></textFieldExpression>
            </textField>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="568" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="8"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col9}]]></textFieldExpression>
            </textField>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="624" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="8"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col10}]]></textFieldExpression>
            </textField>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="680" y="0" width="56" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="8"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col11}]]></textFieldExpression>
            </textField>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement stretchType="RelativeToBandHeight" x="736" y="0" width="66" height="14"/>
                <box><pen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="8"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{col12}]]></textFieldExpression>
            </textField>
        </band>
    </detail>
</jasperReport>
"""

with open("report/rptObservasiTTVBalance.jrxml", "w") as f:
    f.write(jrxml)

print("Created new rptObservasiTTVBalance.jrxml")
