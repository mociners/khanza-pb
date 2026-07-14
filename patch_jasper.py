import os
import re

def patch_print():
    path = "src/surat/SuratPersetujuanUmum.java"
    with open(path, "r") as f:
        content = f.read()

    # 1. Update photo parameter HTTP fallback
    content = content.replace(
        "\"http://\" + koneksiDB.HOSTHYBRIDWEB() + \":\" + koneksiDB.PORTWEB() + \"/\" + koneksiDB.HYBRIDWEB() + \"/pernyataanumum/pages/upload/\" + lokasifile",
        "\"http://\" + koneksiDB.HOSTHYBRIDWEB() + \":\" + koneksiDB.PORTWEB() + \"/\" + koneksiDB.HYBRIDWEB() + \"/imagefreehand/pernyataanumum/\" + lokasifile"
    )

    # 2. Add photo_saksi_2 parameter
    param_saksi = """
            String lokasisaksi2 = Sequel.cariIsi("select photo_saksi_2 from surat_persetujuan_umum where no_surat=?", tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            if (lokasisaksi2 == null || lokasisaksi2.equals("") || lokasisaksi2.equals("null") || lokasisaksi2.equals("-")) {
                param.put("photo_saksi_2", ""); 
            } else {
                java.io.File fotoSaksi = new java.io.File("pernyataanumum/pages/upload/" + lokasisaksi2);
                if (fotoSaksi.exists()) {
                    param.put("photo_saksi_2", fotoSaksi.getAbsolutePath());
                } else {
                    param.put("photo_saksi_2", "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/pernyataanumum/" + lokasisaksi2);
                }
            }
"""
    content = content.replace(
        "Valid.MyReportqry(\"rptSuratPersetujuanUmum.jasper\", \"report\", \"::[ Surat Persetujuan Umum ]::\",",
        param_saksi + "\n            Valid.MyReportqry(\"rptSuratPersetujuanUmum.jasper\", \"report\", \"::[ Surat Persetujuan Umum ]::\","
    )

    # 3. Update SQL in MyReportqry
    content = content.replace(
        "surat_persetujuan_umum.edukasi_pj, surat_persetujuan_umum.edukasi_rs, \"",
        "surat_persetujuan_umum.edukasi_pj, surat_persetujuan_umum.edukasi_rs, surat_persetujuan_umum.saksi_2, \""
    )

    with open(path, "w") as f:
        f.write(content)

if __name__ == "__main__":
    patch_print()
    print("Patched Jasper parameters")
