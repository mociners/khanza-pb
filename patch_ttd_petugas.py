import os

def patch_jrxml():
    path = "report/rptSuratPersetujuanUmum.jrxml"
    with open(path, "r") as f:
        jrxml = f.read()

    # Add parameter
    param_tag = '<parameter name="photo_saksi_2" class="java.lang.String"/>'
    new_param = '<parameter name="ttd_petugas" class="java.lang.String"/>'
    if new_param not in jrxml:
        jrxml = jrxml.replace(param_tag, param_tag + "\n\t" + new_param)

    # Add image block
    saksi2_img = """<image scaleImage="RetainShape" hAlign="Center" onErrorType="Blank">
				<reportElement positionType="Float" x="210" y="585" width="170" height="50" uuid="0cb2fd58-3673-4268-bc6e-b26b5948043e"/>
				<imageExpression><![CDATA[$P{photo_saksi_2}]]></imageExpression>
			</image>"""
    
    petugas_img = """
			<image scaleImage="RetainShape" hAlign="Center" onErrorType="Blank">
				<reportElement positionType="Float" x="10" y="585" width="170" height="50" uuid="e633dfab-9af6-4d74-95eb-079782531cd4"/>
				<imageExpression><![CDATA[$P{ttd_petugas}]]></imageExpression>
			</image>"""

    if "ttd_petugas" not in jrxml.split(saksi2_img)[-1]:
        jrxml = jrxml.replace(saksi2_img, saksi2_img + petugas_img)

    with open(path, "w") as f:
        f.write(jrxml)

def patch_java():
    path = "src/surat/SuratPersetujuanUmum.java"
    with open(path, "r") as f:
        java = f.read()

    # Find where to inject
    inject_point = 'Valid.MyReportqry("rptSuratPersetujuanUmum.jasper", "report", "::[ Surat Persetujuan Umum ]::",'
    
    logic = """
            String ttdPetugas = "";
            String nipLogin = akses.getkode();
            if (nipLogin != null) {
                nipLogin = nipLogin.toLowerCase();
            } else {
                nipLogin = "";
            }
            String namaLogin = Sequel.cariIsi("select nama from petugas where nip=?", akses.getkode());
            if (namaLogin != null) {
                namaLogin = namaLogin.toLowerCase();
            } else {
                namaLogin = "";
            }
            if (nipLogin.contains("wini") || namaLogin.contains("wini")) {
                ttdPetugas = "src/picture/ttdWini.png";
            } else if (nipLogin.contains("sandi") || namaLogin.contains("sandi")) {
                ttdPetugas = "src/picture/ttdSandi.png";
            } else if (nipLogin.contains("ridwan") || namaLogin.contains("ridwan")) {
                ttdPetugas = "src/picture/ttdRidwan.png";
            } else if (nipLogin.contains("inda") || namaLogin.contains("inda")) {
                ttdPetugas = "src/picture/ttdInda.png";
            } else if (nipLogin.contains("lusi") || namaLogin.contains("lusi")) {
                ttdPetugas = "src/picture/ttdLusi.png";
            }
            param.put("ttd_petugas", ttdPetugas);

            """
            
    if "param.put(\"ttd_petugas\", ttdPetugas);" not in java:
        java = java.replace(inject_point, logic + inject_point)

    with open(path, "w") as f:
        f.write(java)

if __name__ == "__main__":
    patch_jrxml()
    patch_java()
    print("Patched TTD Petugas")
