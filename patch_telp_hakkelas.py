import re
import sys

def patch_java():
    with open("src/surat/SuratPersetujuanUmum.java", "r") as f:
        code = f.read()

    code = code.replace('"Data", 30, new String[]', '"Data", 29, new String[]')
    
    old_menyimpantf_q = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
    new_menyimpantf_q = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
    code = code.replace(old_menyimpantf_q, new_menyimpantf_q)

    old_simpan_cols = "privasi_khusus, no_telp_pasien, informasi_biaya"
    new_simpan_cols = "privasi_khusus, informasi_biaya"
    code = code.replace(old_simpan_cols, new_simpan_cols)

    code = code.replace('alasan_naik_kelas=?", 28, new String[]', 'alasan_naik_kelas=?", 27, new String[]')
    
    old_edit_cols = "privasi_khusus=?,no_telp_pasien=?,informasi_biaya=?"
    new_edit_cols = "privasi_khusus=?,informasi_biaya=?"
    code = code.replace(old_edit_cols, new_edit_cols)

    code = code.replace('if (Sequel.menyimpantf("surat_persetujuan_umum"', 'Sequel.mengedit("pasien", "no_rkm_medis=\'" + TNoRM.getText() + "\'", "no_tlp=\'" + NoTelpPasien.getText() + "\'");\n            if (Sequel.menyimpantf("surat_persetujuan_umum"')
    code = code.replace('if (Sequel.mengedittf("surat_persetujuan_umum"', 'Sequel.mengedit("pasien", "no_rkm_medis=\'" + TNoRM.getText() + "\'", "no_tlp=\'" + NoTelpPasien.getText() + "\'");\n            if (Sequel.mengedittf("surat_persetujuan_umum"')

    old_tampil_cols = "surat_persetujuan_umum.privasi_khusus, surat_persetujuan_umum.no_telp_pasien, surat_persetujuan_umum.informasi_biaya"
    new_tampil_cols = "surat_persetujuan_umum.privasi_khusus, surat_persetujuan_umum.informasi_biaya"
    code = code.replace(old_tampil_cols, new_tampil_cols)
    code = code.replace("surat_persetujuan_umum.privasi_khusus,surat_persetujuan_umum.no_telp_pasien,surat_persetujuan_umum.informasi_biaya", "surat_persetujuan_umum.privasi_khusus,surat_persetujuan_umum.informasi_biaya")
    
    old_tabmode = '"Privasi Khusus", "No Telp Pasien", "Informasi Biaya"'
    new_tabmode = '"Privasi Khusus", "Informasi Biaya"'
    code = code.replace(old_tabmode, new_tabmode)

    old_addrow = 'rs.getString("privasi_khusus"), rs.getString("no_telp_pasien"), rs.getString("informasi_biaya")'
    new_addrow = 'rs.getString("privasi_khusus"), rs.getString("informasi_biaya")'
    code = code.replace(old_addrow, new_addrow)

    old_arr_vals = 'Privasi2.getSelectedItem().toString(), NoTelpPasien.getText(), InfoBiaya.getSelectedItem().toString()'
    new_arr_vals = 'Privasi2.getSelectedItem().toString(), InfoBiaya.getSelectedItem().toString()'
    code = code.replace(old_arr_vals, new_arr_vals)

    def replace_get_data_rev(match):
        prefix = match.group(1)
        index = int(match.group(2))
        suffix = match.group(3)
        if index > 22:
            return f"{prefix}{index - 1}{suffix}"
        return match.group(0)

    code = re.sub(r'(tbObat\.getValueAt\(tbObat\.getSelectedRow\(\),\s*)(\d+)(\))', replace_get_data_rev, code)
    code = code.replace('NoTelpPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());\n            ', '')

    def replace_set_value_rev(match):
        prefix = match.group(1)
        index = int(match.group(2))
        suffix = match.group(3)
        if index > 22:
            return f"{prefix}{index - 1}{suffix}"
        return match.group(0)

    code = re.sub(r'(tbObat\.setValueAt\(.*tbObat\.getSelectedRow\(\),\s*)(\d+)(\);)', replace_set_value_rev, code)
    code = code.replace('tbObat.setValueAt(NoTelpPasien.getText(), tbObat.getSelectedRow(), 22);\n            ', '')

    old_hakkelas_israwat = """                    NoKartuBPJS.setText("-");
                    NoJKNJasa.setText("-");
                    NoJKNKerja.setText("-");
                    HakKelas.setText("-");"""
    new_hakkelas_israwat = """                    NoKartuBPJS.setText("-");
                    NoJKNJasa.setText("-");
                    NoJKNKerja.setText("-");
                    HakKelas.setText(Sequel.cariIsi("select bridging_sep.klsrawat from bridging_sep where bridging_sep.no_rawat=?", TNoRw.getText()));
                    if (HakKelas.getText().trim().equals("")) {
                        HakKelas.setText("-");
                    }"""
    code = code.replace(old_hakkelas_israwat, new_hakkelas_israwat)

    with open("src/surat/SuratPersetujuanUmum.java", "w") as f:
        f.write(code)
    print("Java Patched!")


def patch_jrxml():
    with open("report/rptSuratPersetujuanUmum.jrxml", "r") as f:
        xml = f.read()

    old_q = "pasien.tmp_lahir,concat(pasien.alamat"
    new_q = "pasien.tmp_lahir,pasien.no_tlp,concat(pasien.alamat"
    xml = xml.replace(old_q, new_q)

    field_dec = '\t<field name="no_tlp" class="java.lang.String"/>\n'
    if field_dec not in xml:
        xml = xml.replace('\t<field name="tmp_lahir" class="java.lang.String"/>\n', 
                          '\t<field name="tmp_lahir" class="java.lang.String"/>\n' + field_dec)

    tgl_lahir_block = """				<textFieldExpression><![CDATA[": " + $F{tmp_lahir}+", "+new SimpleDateFormat("dd-MM-yyyy").format($F{tgl_lahir})]]></textFieldExpression>
			</textField>"""
    no_telp_xml = """
			<staticText>
				<reportElement x="400" y="22" width="50" height="11" uuid="e0e1e2e3-e4e5-e6e7-e8e9-eaebecedeeef"/>
				<textElement>
					<font fontName="Tahoma" size="8"/>
				</textElement>
				<text><![CDATA[No. Telp]]></text>
			</staticText>
			<textField>
				<reportElement x="455" y="22" width="137" height="11" uuid="f0f1f2f3-f4f5-f6f7-f8f9-fafbfcfdfeff"/>
				<textElement>
					<font fontName="Tahoma" size="8"/>
				</textElement>
				<textFieldExpression><![CDATA[": " + $F{no_tlp}]]></textFieldExpression>
			</textField>"""
    if no_telp_xml not in xml:
        xml = xml.replace(tgl_lahir_block, tgl_lahir_block + no_telp_xml)

    with open("report/rptSuratPersetujuanUmum.jrxml", "w") as f:
        f.write(xml)
    print("JRXML Query Patched!")

if __name__ == "__main__":
    patch_java()
    patch_jrxml()
