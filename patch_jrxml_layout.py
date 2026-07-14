import re

def patch():
    with open("report/rptSuratPersetujuanUmum.jrxml", "r") as f:
        xml = f.read()

    # 1. SQL Query trim
    old_q = "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat"
    new_q = "concat(trim(pasien.alamat),', ',trim(kelurahan.nm_kel),', ',trim(kecamatan.nm_kec),', ',trim(kabupaten.nm_kab),', ',trim(propinsi.nm_prop)) as alamat"
    xml = xml.replace(old_q, new_q)

    # 2. Fix Tgl Lahir spacing
    old_tgl = '$F{tmp_lahir}+", "+new SimpleDateFormat("dd-MM-yyyy").format($F{tgl_lahir})'
    new_tgl = '$F{tmp_lahir}.trim() + ", " + new SimpleDateFormat("dd-MM-yyyy").format($F{tgl_lahir})'
    xml = xml.replace(old_tgl, new_tgl)

    # 3. Add positionType="Float" to all elements in the first band that might need it
    # We will target specific Y values
    y_vals = ["44", "55", "68", "79", "90", "101", "112", "135"]
    for y in y_vals:
        xml = re.sub(rf'<reportElement x="(\d+)" y="{y}"', rf'<reportElement positionType="Float" x="\1" y="{y}"', xml)

    # 4. Add trim() to fields that often have trailing spaces
    xml = re.sub(r'\$F\{privasi_akses\}', r'$F{privasi_akses}.trim()', xml)
    xml = re.sub(r'\$F\{privasi_khusus\}', r'$F{privasi_khusus}.trim()', xml)
    xml = re.sub(r'\$F\{keluarga_1\}', r'$F{keluarga_1}.trim()', xml)
    xml = re.sub(r'\$F\{keluarga_2\}', r'$F{keluarga_2}.trim()', xml)
    
    # 5. Fix Alamat and Alamat PJ
    xml = re.sub(r'\$F\{alamat\}', r'$F{alamat}.trim()', xml)
    xml = re.sub(r'\$F\{alamat_pj\}', r'$F{alamat_pj}.trim()', xml)
    
    # Clean up double trims if any were accidentally created
    xml = xml.replace('.trim().trim()', '.trim()')

    # Also add a line break between bullet points in Persetujuan for better readability
    old_bullets = '"a. Saya mengetahui bahwa'
    new_bullets = '"a. Saya mengetahui bahwa'
    # Actually, the user complained about whitespace in between sentences, which implies TOO MUCH whitespace.
    # So I won't add extra line breaks. The double space from trailing spaces was the main issue.

    with open("report/rptSuratPersetujuanUmum.jrxml", "w") as f:
        f.write(xml)

if __name__ == "__main__":
    patch()
    print("JRXML Patched")
