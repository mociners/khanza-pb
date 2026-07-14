import os
jrxml_content = """<?xml version="1.0" encoding="UTF-8"?>
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd" name="null" language="groovy" pageWidth="595" pageHeight="842" columnWidth="555" leftMargin="20" rightMargin="20" topMargin="20" bottomMargin="20">
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
    <parameter name="finger" class="java.lang.String"/>
    <queryString>
        <![CDATA[select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_awal_medis_ranap_anak.tanggal,
penilaian_awal_medis_ranap_anak.kd_dokter,dokter.nm_dokter,
kamar_inap.kd_kamar, bangsal.nm_bangsal,
penilaian_awal_medis_ranap_anak.keluhan_utama,
penilaian_awal_medis_ranap_anak.keadaan_umum,penilaian_awal_medis_ranap_anak.gcs_e,penilaian_awal_medis_ranap_anak.gcs_v,penilaian_awal_medis_ranap_anak.gcs_m,penilaian_awal_medis_ranap_anak.kesadaran,
penilaian_awal_medis_ranap_anak.tensi,penilaian_awal_medis_ranap_anak.suhu,penilaian_awal_medis_ranap_anak.nadi,penilaian_awal_medis_ranap_anak.rr,penilaian_awal_medis_ranap_anak.bb,penilaian_awal_medis_ranap_anak.tb,penilaian_awal_medis_ranap_anak.lk,
penilaian_awal_medis_ranap_anak.kepala,penilaian_awal_medis_ranap_anak.leher,penilaian_awal_medis_ranap_anak.jantung,penilaian_awal_medis_ranap_anak.paru,penilaian_awal_medis_ranap_anak.abdomen,penilaian_awal_medis_ranap_anak.ekstremitas,
penilaian_awal_medis_ranap_anak.genitalia,penilaian_awal_medis_ranap_anak.status_neurologis,penilaian_awal_medis_ranap_anak.laboratorium,
penilaian_awal_medis_ranap_anak.diagnosa_banding,penilaian_awal_medis_ranap_anak.diagnosa_kerja,penilaian_awal_medis_ranap_anak.penatalaksanaan,
penilaian_awal_medis_ranap_anak.usul_pemeriksaan,penilaian_awal_medis_ranap_anak.prognosa
from reg_periksa 
inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
inner join penilaian_awal_medis_ranap_anak on reg_periksa.no_rawat=penilaian_awal_medis_ranap_anak.no_rawat 
inner join dokter on penilaian_awal_medis_ranap_anak.kd_dokter=dokter.kd_dokter 
inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat and kamar_inap.stts_pulang='-'
inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar
inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal]]>
    </queryString>
    <field name="no_rawat" class="java.lang.String"/>
    <field name="no_rkm_medis" class="java.lang.String"/>
    <field name="nm_pasien" class="java.lang.String"/>
    <field name="jk" class="java.lang.String"/>
    <field name="tgl_lahir" class="java.sql.Date"/>
    <field name="tanggal" class="java.sql.Timestamp"/>
    <field name="kd_dokter" class="java.lang.String"/>
    <field name="nm_dokter" class="java.lang.String"/>
    <field name="kd_kamar" class="java.lang.String"/>
    <field name="nm_bangsal" class="java.lang.String"/>
    <field name="keluhan_utama" class="java.lang.String"/>
    <field name="keadaan_umum" class="java.lang.String"/>
    <field name="gcs_e" class="java.lang.String"/>
    <field name="gcs_v" class="java.lang.String"/>
    <field name="gcs_m" class="java.lang.String"/>
    <field name="kesadaran" class="java.lang.String"/>
    <field name="tensi" class="java.lang.String"/>
    <field name="suhu" class="java.lang.String"/>
    <field name="nadi" class="java.lang.String"/>
    <field name="rr" class="java.lang.String"/>
    <field name="bb" class="java.lang.String"/>
    <field name="tb" class="java.lang.String"/>
    <field name="lk" class="java.lang.String"/>
    <field name="kepala" class="java.lang.String"/>
    <field name="leher" class="java.lang.String"/>
    <field name="jantung" class="java.lang.String"/>
    <field name="paru" class="java.lang.String"/>
    <field name="abdomen" class="java.lang.String"/>
    <field name="ekstremitas" class="java.lang.String"/>
    <field name="genitalia" class="java.lang.String"/>
    <field name="status_neurologis" class="java.lang.String"/>
    <field name="laboratorium" class="java.lang.String"/>
    <field name="diagnosa_banding" class="java.lang.String"/>
    <field name="diagnosa_kerja" class="java.lang.String"/>
    <field name="penatalaksanaan" class="java.lang.String"/>
    <field name="usul_pemeriksaan" class="java.lang.String"/>
    <field name="prognosa" class="java.lang.String"/>
    <title>
        <band height="790">
            <!-- Header section -->
            <rectangle>
                <reportElement x="0" y="0" width="555" height="90"/>
            </rectangle>
            <image scaleImage="FillFrame">
                <reportElement x="5" y="5" width="65" height="65"/>
                <imageExpression><![CDATA[$P{logo}]]></imageExpression>
            </image>
            <textField>
                <reportElement x="75" y="5" width="225" height="20"/>
                <textElement>
                    <font fontName="Tahoma" size="14" isBold="true"/>
                </textElement>
                <textFieldExpression><![CDATA[$P{namars}]]></textFieldExpression>
            </textField>
            <textField>
                <reportElement x="75" y="25" width="225" height="15"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <textFieldExpression><![CDATA[$P{alamatrs}+", "+$P{kotars}+", "+$P{propinsirs}]]></textFieldExpression>
            </textField>
            <textField>
                <reportElement x="75" y="40" width="225" height="15"/>
                <textElement>
                    <font fontName="Tahoma" size="9"/>
                </textElement>
                <textFieldExpression><![CDATA["E-mail: "+$P{emailrs}]]></textFieldExpression>
            </textField>
            
            <staticText>
                <reportElement x="310" y="5" width="70" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[No. RM]]></text>
            </staticText>
            <staticText>
                <reportElement x="380" y="5" width="10" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[:]]></text>
            </staticText>
            <textField>
                <reportElement x="390" y="5" width="160" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{no_rkm_medis}]]></textFieldExpression>
            </textField>
            
            <staticText>
                <reportElement x="310" y="20" width="70" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[Nama]]></text>
            </staticText>
            <staticText>
                <reportElement x="380" y="20" width="10" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[:]]></text>
            </staticText>
            <textField>
                <reportElement x="390" y="20" width="160" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{nm_pasien}]]></textFieldExpression>
            </textField>
            
            <staticText>
                <reportElement x="310" y="35" width="70" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[Tgl. Lahir]]></text>
            </staticText>
            <staticText>
                <reportElement x="380" y="35" width="10" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[:]]></text>
            </staticText>
            <textField pattern="dd/MM/yyyy">
                <reportElement x="390" y="35" width="160" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{tgl_lahir}]]></textFieldExpression>
            </textField>
            
            <staticText>
                <reportElement x="310" y="50" width="70" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[Jenis Kelamin]]></text>
            </staticText>
            <staticText>
                <reportElement x="380" y="50" width="10" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[:]]></text>
            </staticText>
            <textField>
                <reportElement x="390" y="50" width="160" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{jk}]]></textFieldExpression>
            </textField>

            <!-- Title -->
            <rectangle>
                <reportElement x="0" y="75" width="555" height="25" backcolor="#CCCCCC"/>
            </rectangle>
            <staticText>
                <reportElement x="0" y="75" width="555" height="25"/>
                <textElement textAlignment="Center" verticalAlignment="Middle">
                    <font fontName="Tahoma" size="12" isBold="true"/>
                </textElement>
                <text><![CDATA[ASSESMEN MEDIS AWAL RAWAT INAP ANAK]]></text>
            </staticText>

            <!-- Pengkajian Info -->
            <staticText>
                <reportElement x="5" y="105" width="100" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[Tanggal Pengkajian :]]></text>
            </staticText>
            <textField pattern="dd/MM/yyyy">
                <reportElement x="110" y="105" width="150" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{tanggal}]]></textFieldExpression>
            </textField>
            
            <staticText>
                <reportElement x="280" y="105" width="90" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[Jam Pengkajian :]]></text>
            </staticText>
            <textField pattern="HH:mm:ss">
                <reportElement x="370" y="105" width="150" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{tanggal}]]></textFieldExpression>
            </textField>
            
            <staticText>
                <reportElement x="5" y="125" width="100" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[DPJP :]]></text>
            </staticText>
            <textField>
                <reportElement x="110" y="125" width="150" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{nm_dokter}]]></textFieldExpression>
            </textField>

            <staticText>
                <reportElement x="280" y="125" width="90" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[Ruang/Kamar :]]></text>
            </staticText>
            <textField>
                <reportElement x="370" y="125" width="180" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{nm_bangsal} + " - " + $F{kd_kamar}]]></textFieldExpression>
            </textField>
            
            <line><reportElement x="0" y="145" width="555" height="1"/></line>
            
            <!-- A. ANAMNESA -->
            <staticText>
                <reportElement x="5" y="150" width="150" height="15"/>
                <textElement><font fontName="Tahoma" size="10" isBold="true"/></textElement>
                <text><![CDATA[A. ANAMNESA]]></text>
            </staticText>
            <staticText>
                <reportElement x="20" y="170" width="100" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[Keluhan Utama :]]></text>
            </staticText>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement x="125" y="170" width="420" height="50"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{keluhan_utama}]]></textFieldExpression>
            </textField>

            <!-- B. PEMERIKSAAN FISIK -->
            <staticText>
                <reportElement x="5" y="230" width="150" height="15"/>
                <textElement><font fontName="Tahoma" size="10" isBold="true"/></textElement>
                <text><![CDATA[B. PEMERIKSAAN FISIK]]></text>
            </staticText>
            
            <staticText><reportElement x="20" y="250" width="100" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Keadaan Umum]]></text></staticText>
            <staticText><reportElement x="120" y="250" width="10" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[:]]></text></staticText>
            <textField><reportElement x="130" y="250" width="415" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{keadaan_umum}]]></textFieldExpression></textField>
            
            <staticText><reportElement x="20" y="270" width="100" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Kesadaran]]></text></staticText>
            <staticText><reportElement x="120" y="270" width="10" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[:]]></text></staticText>
            <textField><reportElement x="130" y="270" width="415" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA["GCS: E " + $F{gcs_e} + " V " + $F{gcs_v} + " M " + $F{gcs_m} + " (" + $F{kesadaran} + ")"]]></textFieldExpression></textField>
            
            <staticText><reportElement x="20" y="290" width="100" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Tanda Vital]]></text></staticText>
            <staticText><reportElement x="120" y="290" width="10" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[:]]></text></staticText>
            <textField><reportElement x="130" y="290" width="415" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA["T: " + $F{tensi} + " mmHg   S: " + $F{suhu} + " C   N: " + $F{nadi} + " x/m   R: " + $F{rr} + " x/m"]]></textFieldExpression></textField>
            
            <staticText><reportElement x="20" y="310" width="100" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Antropometri]]></text></staticText>
            <staticText><reportElement x="120" y="310" width="10" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[:]]></text></staticText>
            <textField><reportElement x="130" y="310" width="415" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA["BB: " + $F{bb} + " Kg   TB: " + $F{tb} + " Cm   LK: " + $F{lk} + " Cm"]]></textFieldExpression></textField>

            <staticText><reportElement x="20" y="330" width="100" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Kepala]]></text></staticText>
            <staticText><reportElement x="120" y="330" width="10" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[:]]></text></staticText>
            <textField><reportElement x="130" y="330" width="415" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{kepala}]]></textFieldExpression></textField>
            
            <staticText><reportElement x="20" y="350" width="100" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Leher]]></text></staticText>
            <staticText><reportElement x="120" y="350" width="10" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[:]]></text></staticText>
            <textField><reportElement x="130" y="350" width="415" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{leher}]]></textFieldExpression></textField>

            <staticText><reportElement x="20" y="370" width="100" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Toraks]]></text></staticText>
            <staticText><reportElement x="120" y="370" width="10" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[:]]></text></staticText>
            
            <staticText><reportElement x="130" y="370" width="60" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Jantung :]]></text></staticText>
            <textField><reportElement x="200" y="370" width="345" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{jantung}]]></textFieldExpression></textField>
            
            <staticText><reportElement x="130" y="390" width="60" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Paru-paru :]]></text></staticText>
            <textField><reportElement x="200" y="390" width="345" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{paru}]]></textFieldExpression></textField>

            <staticText><reportElement x="20" y="410" width="100" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Abdomen]]></text></staticText>
            <staticText><reportElement x="120" y="410" width="10" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[:]]></text></staticText>
            <textField><reportElement x="130" y="410" width="415" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{abdomen}]]></textFieldExpression></textField>
            
            <staticText><reportElement x="20" y="430" width="100" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Ekstremitas]]></text></staticText>
            <staticText><reportElement x="120" y="430" width="10" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[:]]></text></staticText>
            <textField><reportElement x="130" y="430" width="415" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{ekstremitas}]]></textFieldExpression></textField>

            <staticText><reportElement x="20" y="450" width="100" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Genitalia]]></text></staticText>
            <staticText><reportElement x="120" y="450" width="10" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[:]]></text></staticText>
            <textField><reportElement x="130" y="450" width="415" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{genitalia}]]></textFieldExpression></textField>

            <staticText><reportElement x="20" y="470" width="100" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[Status Neurologis]]></text></staticText>
            <staticText><reportElement x="120" y="470" width="10" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><text><![CDATA[:]]></text></staticText>
            <textField><reportElement x="130" y="470" width="415" height="15"/><textElement><font fontName="Tahoma" size="9"/></textElement><textFieldExpression><![CDATA[$F{status_neurologis}]]></textFieldExpression></textField>

            <!-- C. LABORATORIUM -->
            <staticText>
                <reportElement x="5" y="495" width="150" height="15"/>
                <textElement><font fontName="Tahoma" size="10" isBold="true"/></textElement>
                <text><![CDATA[C. LABORATORIUM :]]></text>
            </staticText>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement x="20" y="515" width="525" height="30"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{laboratorium}]]></textFieldExpression>
            </textField>

            <!-- D. DIAGNOSA BANDING -->
            <staticText>
                <reportElement x="5" y="550" width="150" height="15"/>
                <textElement><font fontName="Tahoma" size="10" isBold="true"/></textElement>
                <text><![CDATA[D. DIAGNOSA BANDING :]]></text>
            </staticText>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement x="20" y="570" width="525" height="30"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{diagnosa_banding}]]></textFieldExpression>
            </textField>
            
            <staticText>
                <reportElement x="5" y="605" width="150" height="15"/>
                <textElement><font fontName="Tahoma" size="10" isBold="true"/></textElement>
                <text><![CDATA[E. DIAGNOSA KERJA :]]></text>
            </staticText>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement x="20" y="625" width="525" height="30"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{diagnosa_kerja}]]></textFieldExpression>
            </textField>

            <staticText>
                <reportElement x="5" y="660" width="150" height="15"/>
                <textElement><font fontName="Tahoma" size="10" isBold="true"/></textElement>
                <text><![CDATA[F. PENATALAKSANAAN :]]></text>
            </staticText>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement x="20" y="680" width="525" height="30"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{penatalaksanaan}]]></textFieldExpression>
            </textField>

            <staticText>
                <reportElement x="5" y="715" width="150" height="15"/>
                <textElement><font fontName="Tahoma" size="10" isBold="true"/></textElement>
                <text><![CDATA[G. USUL PEMERIKSAAN :]]></text>
            </staticText>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement x="20" y="735" width="525" height="20"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{usul_pemeriksaan}]]></textFieldExpression>
            </textField>

            <staticText>
                <reportElement x="5" y="760" width="150" height="15"/>
                <textElement><font fontName="Tahoma" size="10" isBold="true"/></textElement>
                <text><![CDATA[H. PROGNOSA :]]></text>
            </staticText>
            <textField isStretchWithOverflow="true" isBlankWhenNull="true">
                <reportElement x="20" y="775" width="525" height="15"/>
                <textElement><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA[$F{prognosa}]]></textFieldExpression>
            </textField>

        </band>
    </title>
    
    <summary>
        <band height="100">
            <staticText>
                <reportElement x="100" y="10" width="150" height="15"/>
                <textElement textAlignment="Center"><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[Yang Menerima]]></text>
            </staticText>
            <staticText>
                <reportElement x="100" y="70" width="150" height="15"/>
                <textElement textAlignment="Center"><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[(..........................................)]]></text>
            </staticText>
            <staticText>
                <reportElement x="100" y="85" width="150" height="15"/>
                <textElement textAlignment="Center"><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[Pasien/Keluarga]]></text>
            </staticText>

            <staticText>
                <reportElement x="350" y="10" width="150" height="15"/>
                <textElement textAlignment="Center"><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[Dokter DPJP]]></text>
            </staticText>
            <textField>
                <reportElement x="350" y="70" width="150" height="15"/>
                <textElement textAlignment="Center"><font fontName="Tahoma" size="9"/></textElement>
                <textFieldExpression><![CDATA["( "+$F{nm_dokter}+" )"]]></textFieldExpression>
            </textField>
            <staticText>
                <reportElement x="350" y="85" width="150" height="15"/>
                <textElement textAlignment="Center"><font fontName="Tahoma" size="9"/></textElement>
                <text><![CDATA[Nama dan Tanda Tangan]]></text>
            </staticText>
        </band>
    </summary>
</jasperReport>
"""
with open("report/rptCetakPenilaianAwalMedisRanapAnak.jrxml", "w") as f:
    f.write(jrxml_content)
