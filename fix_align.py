import os

def fix_alignment():
    path = "report/rptSuratPersetujuanUmum.jrxml"
    with open(path, "r") as f:
        jrxml = f.read()

    # Add hAlign="Center" to all signature images
    jrxml = jrxml.replace(
        '<image scaleImage="RetainShape" onErrorType="Blank">',
        '<image scaleImage="RetainShape" hAlign="Center" onErrorType="Blank">'
    )

    with open(path, "w") as f:
        f.write(jrxml)

if __name__ == "__main__":
    fix_alignment()
    print("Fixed signature alignment")
