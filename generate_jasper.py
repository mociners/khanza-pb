import xml.etree.ElementTree as ET

# Define the JRXML content directly
jrxml_content = """<?xml version="1.0" encoding="UTF-8"?>
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd" name="null" language="groovy" pageWidth="595" pageHeight="842" columnWidth="557" leftMargin="19" rightMargin="19" topMargin="19" bottomMargin="19" uuid="34c13d5a-cd6b-451e-8001-9fd298d2274a">
    <property name="ireport.zoom" value="1.5"/>
    <property name="ireport.x" value="0"/>
    <property name="ireport.y" value="0"/>
    <parameter name="namars" class="java.lang.String"/>
    <parameter name="alamatrs" class="java.lang.String"/>
    <parameter name="kotars" class="java.lang.String"/>
    <parameter name="propinsirs" class="java.lang.String"/>
    <parameter name="kontakrs" class="java.lang.String"/>
    <parameter name="emailrs" class="java.lang.String"/>
    <parameter name="logo" class="java.io.InputStream"/>
    <queryString language="SQL">
        <![CDATA[select * from reg_periksa]]>
    </queryString>
    <field name="no_rkm_medis" class="java.lang.String"/>
    <field name="nm_pasien" class="java.lang.String"/>
    <field name="jk" class="java.lang.String"/>
    <field name="tgl_lahir" class="java.sql.Date"/>
    <field name="tanggal" class="java.sql.Timestamp"/>
    <field name="nm_dokter" class="java.lang.String"/>
    <field name="anamnesis" class="java.lang.String"/>
    <field name="keluhan_utama" class="java.lang.String"/>
    <field name="td" class="java.lang.String"/>
    <field name="nadi" class="java.lang.String"/>
    <field name="rr" class="java.lang.String"/>
    <field name="suhu" class="java.lang.String"/>
    <field name="tfu" class="java.lang.String"/>
    <field name="djj" class="java.lang.String"/>
    <field name="tbj" class="java.lang.String"/>
    <field name="ket_fisik" class="java.lang.String"/>
    <field name="lab" class="java.lang.String"/>
    <field name="ultra" class="java.lang.String"/>
    <field name="kardio" class="java.lang.String"/>
    <field name="diagnosis" class="java.lang.String"/>
    <field name="tata" class="java.lang.String"/>
    <field name="edukasi" class="java.lang.String"/>
    <field name="ketuban" class="java.lang.String"/>
    <field name="jenis_kelamin_bayi" class="java.lang.String"/>
    <field name="plasenta" class="java.lang.String"/>
    <background>
        <band splitType="Stretch"/>
    </background>
    <title>
        <band height="95" splitType="Stretch">
            <textField>
                <reportElement x="60" y="14" width="250" height="14" uuid="1c801e7e-8c88-466c-8dfc-ee0f33161c16"/>
                <textElement textAlignment="Left">
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <textFieldExpression><![CDATA[$P{alamatrs} + ", " + $P{kotars} + ", " + $P{propinsirs}]]></textFieldExpression>
            </textField>
            <textField>
                <reportElement x="60" y="35" width="250" height="14" uuid="629dfec7-46ba-4a57-b088-724bc22bcbc1"/>
                <textElement textAlignment="Left">
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <textFieldExpression><![CDATA["E-mail : " + $P{emailrs}]]></textFieldExpression>
            </textField>
            <textField>
                <reportElement x="60" y="25" width="250" height="14" uuid="169542da-ca7c-47fc-b1de-1a840e60802c"/>
                <textElement textAlignment="Left">
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <textFieldExpression><![CDATA[$P{kontakrs}]]></textFieldExpression>
            </textField>
            <textField>
                <reportElement x="60" y="0" width="250" height="17" uuid="2a1bb1c3-2b22-4a0b-8d6d-2c8c7d6fc31e"/>
                <textElement textAlignment="Left">
                    <font fontName="Tahoma" size="12" isBold="true"/>
                </textElement>
                <textFieldExpression><![CDATA[$P{namars}]]></textFieldExpression>
            </textField>
            <image scaleImage="FillFrame" onErrorType="Blank">
                <reportElement x="0" y="2" width="48" height="45" uuid="559d80d2-b0df-4f0e-b789-fbe937b27529"/>
                <imageExpression><![CDATA[$P{logo}]]></imageExpression>
            </image>
            <line>
                <reportElement x="0" y="48" width="557" height="1" uuid="62d2d9ad-6625-4b0d-83b6-4b2a8ed7cbff"/>
                <graphicElement>
                    <pen lineWidth="2.0" lineStyle="Double"/>
                </graphicElement>
            </line>
            
            <staticText>
                <reportElement x="320" y="2" width="60" height="12" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <text><![CDATA[No. RM]]></text>
            </staticText>
            <staticText>
                <reportElement x="380" y="2" width="10" height="12" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <text><![CDATA[:]]></text>
            </staticText>
            <textField>
                <reportElement x="390" y="2" width="160" height="12" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{no_rkm_medis}]]></textFieldExpression>
            </textField>
            
            <staticText>
                <reportElement x="320" y="14" width="60" height="12" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <text><![CDATA[Nama]]></text>
            </staticText>
            <staticText>
                <reportElement x="380" y="14" width="10" height="12" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <text><![CDATA[:]]></text>
            </staticText>
            <textField>
                <reportElement x="390" y="14" width="160" height="12" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{nm_pasien}]]></textFieldExpression>
            </textField>

            <staticText>
                <reportElement x="320" y="26" width="60" height="12" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <text><![CDATA[Tgl. Lahir]]></text>
            </staticText>
            <staticText>
                <reportElement x="380" y="26" width="10" height="12" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <text><![CDATA[:]]></text>
            </staticText>
            <textField pattern="dd/MM/yyyy">
                <reportElement x="390" y="26" width="160" height="12" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{tgl_lahir}]]></textFieldExpression>
            </textField>

            <staticText>
                <reportElement x="320" y="38" width="60" height="12" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <text><![CDATA[Jenis Kelamin]]></text>
            </staticText>
            <staticText>
                <reportElement x="380" y="38" width="10" height="12" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <text><![CDATA[:]]></text>
            </staticText>
            <textField>
                <reportElement x="390" y="38" width="160" height="12" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <textFieldExpression><![CDATA[$F{jk}]]></textFieldExpression>
            </textField>
            
            <rectangle>
                <reportElement x="0" y="55" width="557" height="30" backcolor="#333333" uuid="e05adcf8-e9f3-47cb-9257-2e118ea02e86"/>
            </rectangle>
            <staticText>
                <reportElement x="0" y="60" width="557" height="20" forecolor="#FFFFFF" uuid="d50bf69a-ec27-4a17-8e62-c1ec663220ef"/>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="12" isBold="true"/>
                </textElement>
                <text><![CDATA[ASSESMEN AWAL MEDIS INSTALASI RAWAT INAP KEBIDANAN DAN KANDUNGAN]]></text>
            </staticText>
        </band>
    </title>
    <detail>
        <band height="550" splitType="Stretch">
            <!-- ANAMNESIS -->
            <rectangle>
                <reportElement mode="Transparent" x="0" y="0" width="557" height="80" uuid="e05adcf8-e9f3-47cb-9257-2e118ea02e86"/>
            </rectangle>
            <staticText>
                <reportElement x="5" y="5" width="100" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement>
                    <font fontName="Tahoma" size="10" isBold="true"/>
                </textElement>
                <text><![CDATA[ANAMNESIS :]]></text>
            </staticText>
            <textField isStretchWithOverflow="true">
                <reportElement x="5" y="25" width="545" height="50" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <textFieldExpression><![CDATA["Anamnesis: " + $F{anamnesis} + "\nKeluhan Utama: " + $F{keluhan_utama}]]></textFieldExpression>
            </textField>

            <!-- PEMERIKSAAN FISIK -->
            <rectangle>
                <reportElement mode="Transparent" x="0" y="80" width="557" height="150" uuid="e05adcf8-e9f3-47cb-9257-2e118ea02e86"/>
            </rectangle>
            <staticText>
                <reportElement x="0" y="85" width="557" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement textAlignment="Center">
                    <font fontName="Tahoma" size="11" isBold="true"/>
                </textElement>
                <text><![CDATA[PEMERIKSAAN FISIK]]></text>
            </staticText>
            <line>
                <reportElement x="0" y="105" width="557" height="1" uuid="62d2d9ad-6625-4b0d-83b6-4b2a8ed7cbff"/>
            </line>
            
            <staticText>
                <reportElement x="5" y="110" width="75" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[Tekanan Darah :]]></text>
            </staticText>
            <textField><reportElement x="80" y="110" width="50" height="15" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{td}]]></textFieldExpression></textField>
            <staticText><reportElement x="130" y="110" width="30" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[mmHg]]></text></staticText>

            <staticText><reportElement x="170" y="110" width="60" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Pernafasan :]]></text></staticText>
            <textField><reportElement x="230" y="110" width="30" height="15" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{rr}]]></textFieldExpression></textField>
            <staticText><reportElement x="260" y="110" width="40" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[x/menit]]></text></staticText>

            <staticText><reportElement x="310" y="110" width="30" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Nadi :]]></text></staticText>
            <textField><reportElement x="340" y="110" width="30" height="15" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{nadi}]]></textFieldExpression></textField>
            <staticText><reportElement x="370" y="110" width="40" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[x/menit]]></text></staticText>

            <staticText><reportElement x="420" y="110" width="60" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Temperatur :]]></text></staticText>
            <textField><reportElement x="480" y="110" width="30" height="15" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{suhu}]]></textFieldExpression></textField>
            <staticText><reportElement x="510" y="110" width="20" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[°C]]></text></staticText>

            <!-- Status Obstetri -->
            <staticText><reportElement x="5" y="135" width="75" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Status Obstetri :]]></text></staticText>
            <staticText><reportElement x="85" y="135" width="40" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Letak :]]></text></staticText>
            <textField><reportElement x="125" y="135" width="160" height="15" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{tfu}]]></textFieldExpression></textField>
            
            <staticText><reportElement x="300" y="135" width="60" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Ketuban :]]></text></staticText>
            <textField><reportElement x="360" y="135" width="160" height="15" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{ketuban}]]></textFieldExpression></textField>

            <staticText><reportElement x="85" y="155" width="40" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[DJJ :]]></text></staticText>
            <textField><reportElement x="125" y="155" width="130" height="15" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{djj}]]></textFieldExpression></textField>
            <staticText><reportElement x="255" y="155" width="30" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[x/mnt]]></text></staticText>

            <staticText><reportElement x="300" y="155" width="60" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Jenis Kelamin :]]></text></staticText>
            <textField><reportElement x="360" y="155" width="160" height="15" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{jenis_kelamin_bayi}]]></textFieldExpression></textField>

            <staticText><reportElement x="85" y="175" width="40" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[TBBJ :]]></text></staticText>
            <textField><reportElement x="125" y="175" width="130" height="15" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{tbj}]]></textFieldExpression></textField>
            <staticText><reportElement x="255" y="175" width="30" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[gram]]></text></staticText>

            <staticText><reportElement x="300" y="175" width="60" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Plasenta :]]></text></staticText>
            <textField><reportElement x="360" y="175" width="160" height="15" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{plasenta}]]></textFieldExpression></textField>

            <staticText><reportElement x="5" y="195" width="95" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Status Ginekologi :]]></text></staticText>
            <textField isStretchWithOverflow="true"><reportElement x="100" y="195" width="450" height="30" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{ket_fisik}]]></textFieldExpression></textField>

            <!-- PEMERIKSAAN PENUNJANG -->
            <rectangle>
                <reportElement mode="Transparent" x="0" y="230" width="557" height="80" uuid="e05adcf8-e9f3-47cb-9257-2e118ea02e86"/>
            </rectangle>
            <staticText>
                <reportElement x="0" y="235" width="557" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement textAlignment="Center">
                    <font fontName="Tahoma" size="11" isBold="true"/>
                </textElement>
                <text><![CDATA[PEMERIKSAAN PENUNJANG]]></text>
            </staticText>
            <line>
                <reportElement x="0" y="255" width="557" height="1" uuid="62d2d9ad-6625-4b0d-83b6-4b2a8ed7cbff"/>
            </line>
            
            <staticText><reportElement x="5" y="260" width="90" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[a. Laboratorium :]]></text></staticText>
            <textField isStretchWithOverflow="true"><reportElement x="95" y="260" width="450" height="15" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{lab}]]></textFieldExpression></textField>

            <staticText><reportElement x="5" y="275" width="90" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[b. Radiologi    :]]></text></staticText>
            <textField isStretchWithOverflow="true"><reportElement x="95" y="275" width="450" height="15" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{ultra}]]></textFieldExpression></textField>

            <staticText><reportElement x="5" y="290" width="90" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[c. Lainnya      :]]></text></staticText>
            <textField isStretchWithOverflow="true"><reportElement x="95" y="290" width="450" height="15" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{kardio}]]></textFieldExpression></textField>

            <!-- DIAGNOSIS -->
            <rectangle>
                <reportElement mode="Transparent" x="0" y="310" width="557" height="60" uuid="e05adcf8-e9f3-47cb-9257-2e118ea02e86"/>
            </rectangle>
            <staticText>
                <reportElement x="0" y="315" width="557" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement textAlignment="Center">
                    <font fontName="Tahoma" size="11" isBold="true"/>
                </textElement>
                <text><![CDATA[DIAGNOSIS]]></text>
            </staticText>
            <line>
                <reportElement x="0" y="335" width="557" height="1" uuid="62d2d9ad-6625-4b0d-83b6-4b2a8ed7cbff"/>
            </line>
            <textField isStretchWithOverflow="true"><reportElement x="5" y="340" width="545" height="25" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{diagnosis}]]></textFieldExpression></textField>

            <!-- TERAPI -->
            <rectangle>
                <reportElement mode="Transparent" x="0" y="370" width="557" height="60" uuid="e05adcf8-e9f3-47cb-9257-2e118ea02e86"/>
            </rectangle>
            <staticText>
                <reportElement x="0" y="375" width="557" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement textAlignment="Center">
                    <font fontName="Tahoma" size="11" isBold="true"/>
                </textElement>
                <text><![CDATA[TERAPI]]></text>
            </staticText>
            <line>
                <reportElement x="0" y="395" width="557" height="1" uuid="62d2d9ad-6625-4b0d-83b6-4b2a8ed7cbff"/>
            </line>
            <textField isStretchWithOverflow="true"><reportElement x="5" y="400" width="545" height="25" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{tata}]]></textFieldExpression></textField>

            <!-- TINDAKAN -->
            <rectangle>
                <reportElement mode="Transparent" x="0" y="430" width="557" height="60" uuid="e05adcf8-e9f3-47cb-9257-2e118ea02e86"/>
            </rectangle>
            <staticText>
                <reportElement x="0" y="435" width="557" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement textAlignment="Center">
                    <font fontName="Tahoma" size="11" isBold="true"/>
                </textElement>
                <text><![CDATA[TINDAKAN]]></text>
            </staticText>
            <line>
                <reportElement x="0" y="455" width="557" height="1" uuid="62d2d9ad-6625-4b0d-83b6-4b2a8ed7cbff"/>
            </line>
            <textField isStretchWithOverflow="true"><reportElement x="5" y="460" width="545" height="25" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{edukasi}]]></textFieldExpression></textField>

            <!-- FOOTER -->
            <rectangle>
                <reportElement mode="Transparent" x="0" y="490" width="557" height="60" uuid="e05adcf8-e9f3-47cb-9257-2e118ea02e86"/>
            </rectangle>
            <staticText>
                <reportElement x="5" y="495" width="150" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement textAlignment="Center"><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[Yang Menerima Informasi]]></text>
            </staticText>
            <staticText>
                <reportElement x="5" y="530" width="150" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement textAlignment="Center"><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[( Pasien atau Keluarga )]]></text>
            </staticText>

            <textField pattern="dd/MM/yyyy HH:mm:ss">
                <reportElement x="400" y="495" width="150" height="15" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/>
                <textElement textAlignment="Center"><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{tanggal}]]></textFieldExpression>
            </textField>
            <staticText>
                <reportElement x="400" y="510" width="150" height="15" uuid="a710189d-7db0-4e3a-b9c1-5231c50e41f9"/>
                <textElement textAlignment="Center"><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[Dokter,]]></text>
            </staticText>
            <textField>
                <reportElement x="400" y="535" width="150" height="15" uuid="bdce137e-b7d1-419a-bac9-1d4400780447"/>
                <textElement textAlignment="Center"><font fontName="Tahoma" size="9" isUnderline="true"/></textElement>
                <textFieldExpression><![CDATA[$F{nm_dokter}]]></textFieldExpression>
            </textField>
        </band>
    </detail>
</jasperReport>"""

with open('report/rptCetakPenilaianAwalMedisRanapKandungan1.jrxml', 'w') as f:
    f.write(jrxml_content)

print("Generated JRXML successfully.")
