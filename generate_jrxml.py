fields = [
    ("tanggal", "Tgl", 45), ("jam", "Jam", 30), ("nadi", "N", 20), ("respirasi", "R", 20), 
    ("suhu", "S", 20), ("tensi", "TD", 35), ("bb", "BB", 20), ("tb", "TB", 20), 
    ("diet", "Diet", 40), ("kode_infus", "Infus", 40), ("interval_waktu", "Intv", 30),
    ("intake_makan", "Mkn", 25), ("intake_minum", "Mnm", 25), ("intake_ngt", "NGT.I", 30), 
    ("intake_transfusi", "Trnf", 25), ("intake_infus", "Infs", 25), ("intake_sisa_infus", "Sisa", 25), 
    ("jumlah_input", "Tot.I", 25), ("jumlah_input_24", "I/24", 25),
    ("output_urine", "Urn", 25), ("output_muntah", "Mnth", 25), ("output_ngt", "NGT.O", 30), 
    ("output_iwl", "IWL", 25), ("output_drain", "Drn", 25), ("jumlah_output", "Tot.O", 25), 
    ("jumlah_output_24", "O/24", 25), ("balance_24", "Bal/24", 35)
]

xml = """<?xml version="1.0" encoding="UTF-8"?>
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd" name="null" pageWidth="842" pageHeight="595" orientation="Landscape" columnWidth="802" leftMargin="20" rightMargin="20" topMargin="20" bottomMargin="20">
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
    <parameter name="grafik" class="java.io.InputStream"/>
    <queryString>
        <![CDATA[select rm_ttv_balance_cairan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,
              rm_ttv_balance_cairan.tanggal,rm_ttv_balance_cairan.jam,rm_ttv_balance_cairan.nadi,rm_ttv_balance_cairan.respirasi,
              rm_ttv_balance_cairan.suhu,rm_ttv_balance_cairan.tensi,rm_ttv_balance_cairan.bb,rm_ttv_balance_cairan.tb,
              rm_ttv_balance_cairan.diet,rm_ttv_balance_cairan.kode_infus,rm_ttv_balance_cairan.interval_waktu,
              rm_ttv_balance_cairan.intake_makan,rm_ttv_balance_cairan.intake_minum,rm_ttv_balance_cairan.intake_ngt,
              rm_ttv_balance_cairan.intake_transfusi,rm_ttv_balance_cairan.intake_infus,rm_ttv_balance_cairan.intake_sisa_infus,
              rm_ttv_balance_cairan.jumlah_input,rm_ttv_balance_cairan.jumlah_input_24,
              rm_ttv_balance_cairan.output_urine,rm_ttv_balance_cairan.output_muntah,rm_ttv_balance_cairan.output_ngt,
              rm_ttv_balance_cairan.output_iwl,rm_ttv_balance_cairan.output_drain,rm_ttv_balance_cairan.jumlah_output,
              rm_ttv_balance_cairan.jumlah_output_24,rm_ttv_balance_cairan.balance_24,rm_ttv_balance_cairan.nik,petugas.nama
              from rm_ttv_balance_cairan inner join reg_periksa on rm_ttv_balance_cairan.no_rawat=reg_periksa.no_rawat
              inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis
              inner join petugas on rm_ttv_balance_cairan.nik=petugas.nip]]>
    </queryString>
"""

for f, label, w in fields:
    clazz = "java.sql.Date" if f == "tanggal" else ("java.sql.Time" if f == "jam" else "java.lang.String")
    xml += f'    <field name="{f}" class="{clazz}"/>\n'
xml += '    <field name="no_rawat" class="java.lang.String"/>\n'
xml += '    <field name="no_rkm_medis" class="java.lang.String"/>\n'
xml += '    <field name="nm_pasien" class="java.lang.String"/>\n'
xml += '    <field name="tgl_lahir" class="java.sql.Date"/>\n'
xml += '    <field name="jk" class="java.lang.String"/>\n'

xml += """
    <background>
        <band splitType="Stretch"/>
    </background>
    <title>
        <band height="310" splitType="Stretch">
            <image scaleImage="FillFrame" onErrorType="Blank">
                <reportElement x="0" y="2" width="48" height="45"/>
                <imageExpression><![CDATA[$P{logo}]]></imageExpression>
            </image>
            <textField>
                <reportElement x="50" y="20" width="400" height="14"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <textFieldExpression><![CDATA[$P{alamatrs}+", "+$P{kotars}+", "+$P{propinsirs}]]></textFieldExpression>
            </textField>
            <textField>
                <reportElement x="50" y="35" width="400" height="14"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <textFieldExpression><![CDATA["E-mail : "+$P{emailrs}]]></textFieldExpression>
            </textField>
            <textField>
                <reportElement x="50" y="2" width="400" height="17"/>
                <textElement>
                    <font fontName="Tahoma" size="12"/>
                </textElement>
                <textFieldExpression><![CDATA[$P{namars}]]></textFieldExpression>
            </textField>
            <staticText>
                <reportElement x="0" y="60" width="802" height="15"/>
                <textElement textAlignment="Center">
                    <font fontName="Tahoma" size="11" isBold="true"/>
                </textElement>
                <text><![CDATA[OBSERVASI TANDA-TANDA VITAL & BALANCE CAIRAN]]></text>
            </staticText>
            
            <staticText><reportElement x="500" y="2" width="60" height="12"/><textElement><font fontName="Tahoma" size="8"/></textElement><text><![CDATA[No. RM]]></text></staticText>
            <staticText><reportElement x="560" y="2" width="5" height="12"/><textElement><font fontName="Tahoma" size="8"/></textElement><text><![CDATA[:]]></text></staticText>
            <textField><reportElement x="565" y="2" width="200" height="12"/><textElement><font fontName="Tahoma" size="8"/></textElement><textFieldExpression><![CDATA[$F{no_rkm_medis}]]></textFieldExpression></textField>
            
            <staticText><reportElement x="500" y="14" width="60" height="12"/><textElement><font fontName="Tahoma" size="8"/></textElement><text><![CDATA[Nama]]></text></staticText>
            <staticText><reportElement x="560" y="14" width="5" height="12"/><textElement><font fontName="Tahoma" size="8"/></textElement><text><![CDATA[:]]></text></staticText>
            <textField><reportElement x="565" y="14" width="200" height="12"/><textElement><font fontName="Tahoma" size="8"/></textElement><textFieldExpression><![CDATA[$F{nm_pasien}]]></textFieldExpression></textField>
            
            <staticText><reportElement x="500" y="26" width="60" height="12"/><textElement><font fontName="Tahoma" size="8"/></textElement><text><![CDATA[Tgl. Lahir]]></text></staticText>
            <staticText><reportElement x="560" y="26" width="5" height="12"/><textElement><font fontName="Tahoma" size="8"/></textElement><text><![CDATA[:]]></text></staticText>
            <textField pattern="dd/MM/yyyy"><reportElement x="565" y="26" width="200" height="12"/><textElement><font fontName="Tahoma" size="8"/></textElement><textFieldExpression><![CDATA[$F{tgl_lahir}]]></textFieldExpression></textField>
            
            <staticText><reportElement x="500" y="38" width="60" height="12"/><textElement><font fontName="Tahoma" size="8"/></textElement><text><![CDATA[Jenis Kelamin]]></text></staticText>
            <staticText><reportElement x="560" y="38" width="5" height="12"/><textElement><font fontName="Tahoma" size="8"/></textElement><text><![CDATA[:]]></text></staticText>
            <textField><reportElement x="565" y="38" width="200" height="12"/><textElement><font fontName="Tahoma" size="8"/></textElement><textFieldExpression><![CDATA[$F{jk}.equals("L") ? "Laki-laki" : "Perempuan"]]></textFieldExpression></textField>
            
            <image scaleImage="RetainShape" onErrorType="Blank">
                <reportElement x="0" y="80" width="802" height="220"/>
                <imageExpression><![CDATA[$P{grafik}]]></imageExpression>
            </image>
        </band>
    </title>
    <columnHeader>
        <band height="20" splitType="Stretch">
"""

x = 0
for f, label, w in fields:
    xml += f"""            <staticText>
                <reportElement x="{x}" y="0" width="{w}" height="15"/>
                <box><pen lineWidth="0.5"/><topPen lineWidth="0.5"/><leftPen lineWidth="0.5"/><bottomPen lineWidth="0.5"/><rightPen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="7" isBold="true"/>
                </textElement>
                <text><![CDATA[{label}]]></text>
            </staticText>\n"""
    x += w

xml += """        </band>
    </columnHeader>
    <detail>
        <band height="12" splitType="Stretch">\n"""

x = 0
for f, label, w in fields:
    if f == "tanggal":
        expr = f'$F{{{f}}}'
        pattern = ' pattern="dd/MM/yyyy"'
    elif f == "jam":
        expr = f'$F{{{f}}}'
        pattern = ' pattern="HH:mm"'
    else:
        expr = f'$F{{{f}}}'
        pattern = ''
    
    xml += f"""            <textField isStretchWithOverflow="true" isBlankWhenNull="true"{pattern}>
                <reportElement stretchType="RelativeToBandHeight" x="{x}" y="0" width="{w}" height="12"/>
                <box><pen lineWidth="0.5"/><topPen lineWidth="0.5"/><leftPen lineWidth="0.5"/><bottomPen lineWidth="0.5"/><rightPen lineWidth="0.5"/></box>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="7"/>
                </textElement>
                <textFieldExpression><![CDATA[{expr}]]></textFieldExpression>
            </textField>\n"""
    x += w

xml += """        </band>
    </detail>
</jasperReport>
"""

with open("report/rptObservasiTTVBalance.jrxml", "w") as f:
    f.write(xml)

