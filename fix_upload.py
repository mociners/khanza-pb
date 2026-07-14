import os
import re

def fix_surat_persetujuan_umum():
    path = "src/surat/SuratPersetujuanUmum.java"
    with open(path, "r") as f:
        content = f.read()
    
    # Fix broken sr  c
    content = content.replace("sr  c='", "src='")
    
    # Fix fallback serverUrl to use imagefreehand
    content = content.replace(
        "\"/pernyataanumum/pages/upload/\" + fotoWebcam",
        "\"/imagefreehand/pernyataanumum/\" + fotoWebcam"
    )
    content = content.replace(
        "\"/pernyataanumum/pages/upload/\" + fotoTtd",
        "\"/imagefreehand/pernyataanumum/\" + fotoTtd"
    )
    
    with open(path, "w") as f:
        f.write(content)

def fix_ttd_upload():
    path = "src/freehand/DlgTTDPersetujuanUmum.java"
    with open(path, "r") as f:
        content = f.read()
        
    content = content.replace(
        "\"/upload_persetujuan.php\"",
        "\"/imagefreehand/upload.php?doc=pernyataanumum\""
    )
    
    with open(path, "w") as f:
        f.write(content)

def fix_webcam_upload():
    path = "src/simrskhanza/DlgPersetujuanWebcam.java"
    with open(path, "r") as f:
        content = f.read()
        
    content = content.replace(
        "\"/upload_persetujuan.php\"",
        "\"/imagefreehand/upload.php?doc=pernyataanumum\""
    )
    
    with open(path, "w") as f:
        f.write(content)

if __name__ == "__main__":
    fix_surat_persetujuan_umum()
    fix_ttd_upload()
    fix_webcam_upload()
    print("Fixed upload paths!")
