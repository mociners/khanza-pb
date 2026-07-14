import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

target_query = r'this\.ps = this\.koneksi\.prepareStatement\("SELECT \* FROM penilaian_awal_keperawatan_ranap_dewasa"\);'

replacement_query = """            this.ps = this.koneksi.prepareStatement("select reg_periksa.no_rkm_medis, pasien.nm_pasien, " +
                "if(pasien.jk='L','Laki-Laki','Perempuan') as jk, pasien.tgl_lahir, reg_periksa.kd_dokter, dokter.nm_dokter, " +
                "penilaian_awal_keperawatan_ranap_dewasa.* " +
                "from penilaian_awal_keperawatan_ranap_dewasa inner join reg_periksa on penilaian_awal_keperawatan_ranap_dewasa.no_rawat=reg_periksa.no_rawat " +
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter " +
                "where penilaian_awal_keperawatan_ranap_dewasa.tanggal between ? and ? and (penilaian_awal_keperawatan_ranap_dewasa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or dokter.nm_dokter like ?) order by penilaian_awal_keperawatan_ranap_dewasa.tanggal");
            this.ps.setString(1, this.Valid.SetTgl(this.DTPCari1.getSelectedItem() + "") + " 00:00:00");
            this.ps.setString(2, this.Valid.SetTgl(this.DTPCari2.getSelectedItem() + "") + " 23:59:59");
            this.ps.setString(3, "%" + this.TCari.getText() + "%");
            this.ps.setString(4, "%" + this.TCari.getText() + "%");
            this.ps.setString(5, "%" + this.TCari.getText() + "%");
            this.ps.setString(6, "%" + this.TCari.getText() + "%");"""

new_content = re.sub(target_query, replacement_query, content)

if new_content != content:
    with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
        f.write(new_content)
    print("SUCCESS: Target query replaced!")
else:
    print("FAILED: Target query not found.")
