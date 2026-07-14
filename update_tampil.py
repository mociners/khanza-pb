import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

# 1. Add BtnCariActionPerformed and BtnAllActionPerformed
btn_actions = """
    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {
        tampil();
    }

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {
        TCari.setText("");
        tampil();
    }
"""
content = re.sub(r'(private void tampil\(\) {)', btn_actions + r'\1', content)

# 2. Update tampil() to include joins
# Find the old select statement
tampil_old = r'this\.ps = this\.koneksi\.prepareStatement\("SELECT \* FROM penilaian_awal_keperawatan_ranap_dewasa"\);'
tampil_new = r"""this.ps = this.koneksi.prepareStatement("select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,reg_periksa.kd_dokter,dokter.nm_dokter," +
                "penilaian_awal_keperawatan_ranap_dewasa.tanggal,penilaian_awal_keperawatan_ranap_dewasa.informasi_dari,penilaian_awal_keperawatan_ranap_dewasa.tgl_tiba," +
                "penilaian_awal_keperawatan_ranap_dewasa.nip,penilaian_awal_keperawatan_ranap_dewasa.diagnosa_masuk,penilaian_awal_keperawatan_ranap_dewasa.suhu," +
                "penilaian_awal_keperawatan_ranap_dewasa.td,penilaian_awal_keperawatan_ranap_dewasa.nadi_utama,penilaian_awal_keperawatan_ranap_dewasa.rr_utama," +
                "penilaian_awal_keperawatan_ranap_dewasa.riwayat_keluarga,penilaian_awal_keperawatan_ranap_dewasa.riwayat_pasien,penilaian_awal_keperawatan_ranap_dewasa.deskripsi_penyakit," +
                "penilaian_awal_keperawatan_ranap_dewasa.riwayat_sekarang,penilaian_awal_keperawatan_ranap_dewasa.alat_bantu_igd,penilaian_awal_keperawatan_ranap_dewasa.alat_bantu_persetujuan," +
                "penilaian_awal_keperawatan_ranap_dewasa.alat_bantu_perintah,penilaian_awal_keperawatan_ranap_dewasa.alergi,penilaian_awal_keperawatan_ranap_dewasa.jenis_alergi," +
                "penilaian_awal_keperawatan_ranap_dewasa.status_nikah,penilaian_awal_keperawatan_ranap_dewasa.pendidikan,penilaian_awal_keperawatan_ranap_dewasa.agama," +
                "penilaian_awal_keperawatan_ranap_dewasa.warga_negara,penilaian_awal_keperawatan_ranap_dewasa.pekerjaan,penilaian_awal_keperawatan_ranap_dewasa.aktivitas," +
                "penilaian_awal_keperawatan_ranap_dewasa.tinggal_bersama,penilaian_awal_keperawatan_ranap_dewasa.ket_tinggal,penilaian_awal_keperawatan_ranap_dewasa.tempat_tinggal," +
                "penilaian_awal_keperawatan_ranap_dewasa.ket_tempat_tinggal,penilaian_awal_keperawatan_ranap_dewasa.curiga,penilaian_awal_keperawatan_ranap_dewasa.curiga_ya," +
                "penilaian_awal_keperawatan_ranap_dewasa.curiga_tidak,penilaian_awal_keperawatan_ranap_dewasa.budaya,penilaian_awal_keperawatan_ranap_dewasa.budaya_ya," +
                "penilaian_awal_keperawatan_ranap_dewasa.budaya_tidak,penilaian_awal_keperawatan_ranap_dewasa.anak,penilaian_awal_keperawatan_ranap_dewasa.jumlah_anak," +
                "penilaian_awal_keperawatan_ranap_dewasa.bimbingan_ibadah,penilaian_awal_keperawatan_ranap_dewasa.masalah_psiko_1,penilaian_awal_keperawatan_ranap_dewasa.masalah_psiko_2," +
                "penilaian_awal_keperawatan_ranap_dewasa.masalah_psiko_3,penilaian_awal_keperawatan_ranap_dewasa.masalah_psiko_4,penilaian_awal_keperawatan_ranap_dewasa.kondisi_psikologis," +
                "penilaian_awal_keperawatan_ranap_dewasa.ket_psiko,penilaian_awal_keperawatan_ranap_dewasa.status_mental,penilaian_awal_keperawatan_ranap_dewasa.orientasi_orang," +
                "penilaian_awal_keperawatan_ranap_dewasa.ket_ori_orang,penilaian_awal_keperawatan_ranap_dewasa.orientasi_tempat,penilaian_awal_keperawatan_ranap_dewasa.ket_ori_tempat," +
                "penilaian_awal_keperawatan_ranap_dewasa.orientasi_waktu,penilaian_awal_keperawatan_ranap_dewasa.ket_ori_waktu,penilaian_awal_keperawatan_ranap_dewasa.orientasi_situasi," +
                "penilaian_awal_keperawatan_ranap_dewasa.ket_ori_situasi,penilaian_awal_keperawatan_ranap_dewasa.memori,penilaian_awal_keperawatan_ranap_dewasa.pupil_kanan_uk," +
                "penilaian_awal_keperawatan_ranap_dewasa.pupil_kanan_reflex,penilaian_awal_keperawatan_ranap_dewasa.pupil_kiri_uk,penilaian_awal_keperawatan_ranap_dewasa.pupil_kiri_reflex," +
                "penilaian_awal_keperawatan_ranap_dewasa.gcs_e,penilaian_awal_keperawatan_ranap_dewasa.gcs_m,penilaian_awal_keperawatan_ranap_dewasa.gcs_v,penilaian_awal_keperawatan_ranap_dewasa.gcs_jml," +
                "penilaian_awal_keperawatan_ranap_dewasa.tanda_meningeal_kaku,penilaian_awal_keperawatan_ranap_dewasa.tanda_meningeal_brudzinski,penilaian_awal_keperawatan_ranap_dewasa.tanda_meningeal_kernig," +
                "penilaian_awal_keperawatan_ranap_dewasa.tanda_meningeal_lain,penilaian_awal_keperawatan_ranap_dewasa.masalah_neuro_1,penilaian_awal_keperawatan_ranap_dewasa.masalah_neuro_2," +
                "penilaian_awal_keperawatan_ranap_dewasa.jalan_nafas,penilaian_awal_keperawatan_ranap_dewasa.benda_asing,penilaian_awal_keperawatan_ranap_dewasa.airway_ukuran," +
                "penilaian_awal_keperawatan_ranap_dewasa.pernafasan,penilaian_awal_keperawatan_ranap_dewasa.bunyi_nafas,penilaian_awal_keperawatan_ranap_dewasa.kesulitan_nafas," +
                "penilaian_awal_keperawatan_ranap_dewasa.alat_nafas,penilaian_awal_keperawatan_ranap_dewasa.oksigen,penilaian_awal_keperawatan_ranap_dewasa.oksigen_ltr," +
                "penilaian_awal_keperawatan_ranap_dewasa.oksigen_jenis,penilaian_awal_keperawatan_ranap_dewasa.frekuensi_nafas,penilaian_awal_keperawatan_ranap_dewasa.batuk," +
                "penilaian_awal_keperawatan_ranap_dewasa.spo2,penilaian_awal_keperawatan_ranap_dewasa.masalah_nafas_1,penilaian_awal_keperawatan_ranap_dewasa.masalah_nafas_2," +
                "penilaian_awal_keperawatan_ranap_dewasa.masalah_nafas_3,penilaian_awal_keperawatan_ranap_dewasa.masalah_nafas_4,penilaian_awal_keperawatan_ranap_dewasa.sirkulasi," +
                "penilaian_awal_keperawatan_ranap_dewasa.crt,penilaian_awal_keperawatan_ranap_dewasa.denyut_nadi,penilaian_awal_keperawatan_ranap_dewasa.nadi,penilaian_awal_keperawatan_ranap_dewasa.nadi_jelas," +
                "penilaian_awal_keperawatan_ranap_dewasa.irama,penilaian_awal_keperawatan_ranap_dewasa.pacemaker,penilaian_awal_keperawatan_ranap_dewasa.pacemaker_jelas," +
                "penilaian_awal_keperawatan_ranap_dewasa.akral,penilaian_awal_keperawatan_ranap_dewasa.masalah_sirk_1,penilaian_awal_keperawatan_ranap_dewasa.ket_masalah_sirk_1," +
                "penilaian_awal_keperawatan_ranap_dewasa.masalah_sirk_2,penilaian_awal_keperawatan_ranap_dewasa.ket_masalah_sirk_2,penilaian_awal_keperawatan_ranap_dewasa.masalah_sirk_3," +
                "penilaian_awal_keperawatan_ranap_dewasa.ket_masalah_sirk_3,penilaian_awal_keperawatan_ranap_dewasa.bak,penilaian_awal_keperawatan_ranap_dewasa.bak_lainnya," +
                "penilaian_awal_keperawatan_ranap_dewasa.kateter,penilaian_awal_keperawatan_ranap_dewasa.kateter_jelas,penilaian_awal_keperawatan_ranap_dewasa.urin_jumlah," +
                "penilaian_awal_keperawatan_ranap_dewasa.urin_warna,penilaian_awal_keperawatan_ranap_dewasa.prostat,penilaian_awal_keperawatan_ranap_dewasa.nyeri_pinggang," +
                "penilaian_awal_keperawatan_ranap_dewasa.kelainan_kemih,penilaian_awal_keperawatan_ranap_dewasa.kelainan_sebut,penilaian_awal_keperawatan_ranap_dewasa.masalah_kemih_1," +
                "penilaian_awal_keperawatan_ranap_dewasa.ket_masalah_kemih_1,penilaian_awal_keperawatan_ranap_dewasa.status_ob_g,penilaian_awal_keperawatan_ranap_dewasa.status_ob_p," +
                "penilaian_awal_keperawatan_ranap_dewasa.status_ob_a,penilaian_awal_keperawatan_ranap_dewasa.menstruasi,penilaian_awal_keperawatan_ranap_dewasa.kehamilan," +
                "penilaian_awal_keperawatan_ranap_dewasa.kehamilan_hpht,penilaian_awal_keperawatan_ranap_dewasa.kehamilan_hpl,penilaian_awal_keperawatan_ranap_dewasa.post_partum," +
                "penilaian_awal_keperawatan_ranap_dewasa.lochea,penilaian_awal_keperawatan_ranap_dewasa.lochea_jumlah,penilaian_awal_keperawatan_ranap_dewasa.payudara," +
                "penilaian_awal_keperawatan_ranap_dewasa.pengeluaran_asi,penilaian_awal_keperawatan_ranap_dewasa.kontraksi,penilaian_awal_keperawatan_ranap_dewasa.papsmear," +
                "penilaian_awal_keperawatan_ranap_dewasa.papsmear_tgl,penilaian_awal_keperawatan_ranap_dewasa.mammo,penilaian_awal_keperawatan_ranap_dewasa.mammo_tgl," +
                "penilaian_awal_keperawatan_ranap_dewasa.sadari,penilaian_awal_keperawatan_ranap_dewasa.skrining_kanker,penilaian_awal_keperawatan_ranap_dewasa.skrining_tgl," +
                "penilaian_awal_keperawatan_ranap_dewasa.masalah_rep_1,penilaian_awal_keperawatan_ranap_dewasa.masalah_rep_2,penilaian_awal_keperawatan_ranap_dewasa.masalah_rep_3," +
                "penilaian_awal_keperawatan_ranap_dewasa.masalah_rep_4,penilaian_awal_keperawatan_ranap_dewasa.masalah_rep_lain,penilaian_awal_keperawatan_ranap_dewasa.gejala_awal," +
                "penilaian_awal_keperawatan_ranap_dewasa.kekerasan_fisik,penilaian_awal_keperawatan_ranap_dewasa.turgor,penilaian_awal_keperawatan_ranap_dewasa.rambut," +
                "penilaian_awal_keperawatan_ranap_dewasa.kuku,penilaian_awal_keperawatan_ranap_dewasa.luka,penilaian_awal_keperawatan_ranap_dewasa.luka_dalam,penilaian_awal_keperawatan_ranap_dewasa.perdarahan_integ," +
                "penilaian_awal_keperawatan_ranap_dewasa.fraktur,penilaian_awal_keperawatan_ranap_dewasa.lokasi,penilaian_awal_keperawatan_ranap_dewasa.lokasi_lain," +
                "penilaian_awal_keperawatan_ranap_dewasa.masalah_integ_1,penilaian_awal_keperawatan_ranap_dewasa.ket_masalah_integ_1,penilaian_awal_keperawatan_ranap_dewasa.masalah_integ_2," +
                "penilaian_awal_keperawatan_ranap_dewasa.ket_masalah_integ_2,penilaian_awal_keperawatan_ranap_dewasa.masalah_integ_3,penilaian_awal_keperawatan_ranap_dewasa.ket_masalah_integ_3," +
                "penilaian_awal_keperawatan_ranap_dewasa.masalah_integ_4,penilaian_awal_keperawatan_ranap_dewasa.ket_masalah_integ_4,penilaian_awal_keperawatan_ranap_dewasa.telinga," +
                "penilaian_awal_keperawatan_ranap_dewasa.telinga_lainnya,penilaian_awal_keperawatan_ranap_dewasa.hidung,penilaian_awal_keperawatan_ranap_dewasa.tenggorokan," +
                "penilaian_awal_keperawatan_ranap_dewasa.gigi,penilaian_awal_keperawatan_ranap_dewasa.sakit_gigi,penilaian_awal_keperawatan_ranap_dewasa.gigi_palsu,penilaian_awal_keperawatan_ranap_dewasa.mata," +
                "penilaian_awal_keperawatan_ranap_dewasa.mata_lainnya,penilaian_awal_keperawatan_ranap_dewasa.masalah_tht,penilaian_awal_keperawatan_ranap_dewasa.wasir," +
                "penilaian_awal_keperawatan_ranap_dewasa.perdarahan_rectal,penilaian_awal_keperawatan_ranap_dewasa.jenis_diit,penilaian_awal_keperawatan_ranap_dewasa.feeding_tube," +
                "penilaian_awal_keperawatan_ranap_dewasa.pembatasan_cairan,penilaian_awal_keperawatan_ranap_dewasa.abdomen,penilaian_awal_keperawatan_ranap_dewasa.bunyi_usus," +
                "penilaian_awal_keperawatan_ranap_dewasa.bunyi_usus_freq,penilaian_awal_keperawatan_ranap_dewasa.bab,penilaian_awal_keperawatan_ranap_dewasa.bab_sejak,penilaian_awal_keperawatan_ranap_dewasa.bab_freq," +
                "penilaian_awal_keperawatan_ranap_dewasa.konsistensi,penilaian_awal_keperawatan_ranap_dewasa.warna_cerna,penilaian_awal_keperawatan_ranap_dewasa.pencahar," +
                "penilaian_awal_keperawatan_ranap_dewasa.masalah_pencernaan,penilaian_awal_keperawatan_ranap_dewasa.nyeri_tidak_ada,penilaian_awal_keperawatan_ranap_dewasa.nyeri_ada," +
                "penilaian_awal_keperawatan_ranap_dewasa.nyeri_akut,penilaian_awal_keperawatan_ranap_dewasa.nyeri_kronis,penilaian_awal_keperawatan_ranap_dewasa.nyeri_viseral," +
                "penilaian_awal_keperawatan_ranap_dewasa.nyeri_somatis,penilaian_awal_keperawatan_ranap_dewasa.nyeri_provokes_diam,penilaian_awal_keperawatan_ranap_dewasa.nyeri_provokes_mobilisasi," +
                "penilaian_awal_keperawatan_ranap_dewasa.nyeri_provokes_ditekan,penilaian_awal_keperawatan_ranap_dewasa.nyeri_provokes_tiduran,penilaian_awal_keperawatan_ranap_dewasa.nyeri_provokes_berdiri," +
                "penilaian_awal_keperawatan_ranap_dewasa.nyeri_provokes_berjalan,penilaian_awal_keperawatan_ranap_dewasa.nyeri_provokes_lainnya,penilaian_awal_keperawatan_ranap_dewasa.nyeri_quality_tajam," +
                "penilaian_awal_keperawatan_ranap_dewasa.nyeri_quality_tumpul,penilaian_awal_keperawatan_ranap_dewasa.nyeri_quality_ditusuk,penilaian_awal_keperawatan_ranap_dewasa.nyeri_quality_ditarik," +
                "penilaian_awal_keperawatan_ranap_dewasa.nyeri_quality_dipukul,penilaian_awal_keperawatan_ranap_dewasa.nyeri_quality_berdenyut,penilaian_awal_keperawatan_ranap_dewasa.nyeri_quality_dibakar," +
                "penilaian_awal_keperawatan_ranap_dewasa.nyeri_quality_ditikam,penilaian_awal_keperawatan_ranap_dewasa.nyeri_quality_disayat,penilaian_awal_keperawatan_ranap_dewasa.nyeri_quality_lainnya," +
                "penilaian_awal_keperawatan_ranap_dewasa.nyeri_radiation,penilaian_awal_keperawatan_ranap_dewasa.nyeri_radiation_lokasi,penilaian_awal_keperawatan_ranap_dewasa.nyeri_severity_metode," +
                "penilaian_awal_keperawatan_ranap_dewasa.nyeri_severity_skor,penilaian_awal_keperawatan_ranap_dewasa.nyeri_severity_nyeri,penilaian_awal_keperawatan_ranap_dewasa.nyeri_time_setiap," +
                "penilaian_awal_keperawatan_ranap_dewasa.nyeri_time_selama,penilaian_awal_keperawatan_ranap_dewasa.nyeri_time_sejak,penilaian_awal_keperawatan_ranap_dewasa.cpot_ekspresi," +
                "penilaian_awal_keperawatan_ranap_dewasa.cpot_gerakan,penilaian_awal_keperawatan_ranap_dewasa.cpot_ketegangan,penilaian_awal_keperawatan_ranap_dewasa.cpot_ventilator," +
                "penilaian_awal_keperawatan_ranap_dewasa.cpot_vokalisasi,penilaian_awal_keperawatan_ranap_dewasa.cpot_total,penilaian_awal_keperawatan_ranap_dewasa.cpot_kategori " +
                "from penilaian_awal_keperawatan_ranap_dewasa inner join reg_periksa on penilaian_awal_keperawatan_ranap_dewasa.no_rawat=reg_periksa.no_rawat " +
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter " +
                "where penilaian_awal_keperawatan_ranap_dewasa.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or dokter.nm_dokter like ?) order by penilaian_awal_keperawatan_ranap_dewasa.tanggal");
"""
content = content.replace(tampil_old, tampil_new)

# 3. Add parameters to executeQuery
old_while = r'this\.rs = this\.ps\.executeQuery\(\);'
new_while = r"""this.ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                this.ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                this.ps.setString(3, "%" + TCari.getText().trim() + "%");
                this.ps.setString(4, "%" + TCari.getText().trim() + "%");
                this.ps.setString(5, "%" + TCari.getText().trim() + "%");
                this.ps.setString(6, "%" + TCari.getText().trim() + "%");
                this.rs = this.ps.executeQuery();"""
content = content.replace(old_while, new_while)

# 4. Update the addRow in tampil()
old_add_row = r'this\.tabMode\.addRow\(new String\[\]\{this\.rs\.getString\("no_rawat"\), this\.rs\.getString\("tanggal"\), this\.rs\.getString\("informasi_dari"\)'
new_add_row = r'this.tabMode.addRow(new String[]{this.rs.getString("no_rawat"), this.rs.getString("no_rkm_medis"), this.rs.getString("nm_pasien"), this.rs.getString("tgl_lahir"), this.rs.getString("jk"), this.rs.getString("kd_dokter"), this.rs.getString("nm_dokter"), this.rs.getString("tanggal"), this.rs.getString("informasi_dari")'
content = content.replace(old_add_row, new_add_row)

# 5. Add LCount update
lcount_update = r'LCount.setText("" + tabMode.getRowCount());\n'
content = re.sub(r'(\s*\} catch \(Exception e\) \{\n\s*System\.out\.println\("Notifikasi : " \+ e\);\n\s*\}\n\s*finally \{)', '\n                ' + lcount_update + r'\1', content)

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
    f.write(content)

