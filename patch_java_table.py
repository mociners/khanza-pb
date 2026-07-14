import os

def patch_java():
    path = "src/surat/SuratPersetujuanUmum.java"
    with open(path, "r") as f:
        java = f.read()

    # 1. Update tabMode headers
    java = java.replace(
        "\"Asuransi\", \"No.Asuransi\", \"No.BPJS\", \"Hak Kelas\", \"Pilih Kelas\", \"Alasan Naik Kelas\", \"Edukasi PJ\", \"Edukasi RS\"",
        "\"Asuransi\", \"No.Asuransi\", \"No.BPJS\", \"Hak Kelas\", \"Pilih Kelas\", \"Alasan Naik Kelas\", \"Edukasi PJ\", \"Edukasi RS\", \"Saksi 2\""
    )

    # 2. Update tabMode column width loop
    java = java.replace(
        "for (i = 0; i < 37; i++) {",
        "for (i = 0; i < 38; i++) {"
    )
    java = java.replace(
        "else if (i == 36) column.setPreferredWidth(250);",
        "else if (i == 36) column.setPreferredWidth(250);\n            else if (i == 37) column.setPreferredWidth(250);"
    )

    # 3. Fix SQL query in tampil()
    java = java.replace(
        "surat_persetujuan_umum.alasan_naik_kelas, surat_persetujuan_umum.edukasi_pj, surat_persetujuan_umum.edukasi_rs, surat_persetujuan_umum.edukasi_pj, surat_persetujuan_umum.edukasi_rs",
        "surat_persetujuan_umum.alasan_naik_kelas, surat_persetujuan_umum.edukasi_pj, surat_persetujuan_umum.edukasi_rs, surat_persetujuan_umum.saksi_2"
    )

    # 4. Update addRow in tampil()
    java = java.replace(
        'rs.getString("hak_kelas"), rs.getString("pilihan_kamar"), rs.getString("alasan_naik_kelas")',
        'rs.getString("hak_kelas"), rs.getString("pilihan_kamar"), rs.getString("alasan_naik_kelas"), rs.getString("edukasi_pj"), rs.getString("edukasi_rs"), rs.getString("saksi_2")'
    )

    with open(path, "w") as f:
        f.write(java)

if __name__ == "__main__":
    patch_java()
    print("Patched Table Model and tampil()")
