import re

def patch():
    with open("report/rptSuratPersetujuanUmum.jrxml", "r") as f:
        xml = f.read()

    # 1. Change scaleImage
    xml = xml.replace('scaleImage="FillFrame"', 'scaleImage="RetainShape"')

    # 2. Change height of the signature image
    xml = xml.replace('y="585" width="180" height="34"', 'y="585" width="180" height="64"')

    # 3. Push down text elements below it by 30 pixels
    # Y=619 -> 649 (Names)
    xml = xml.replace('y="619"', 'y="649"')
    # Y=631 -> 661 (Tanda tangan & nama jelas)
    xml = xml.replace('y="631"', 'y="661"')
    # Y=645 -> 675 (Edukasi lanjutan)
    xml = xml.replace('y="645"', 'y="675"')

    # 4. Increase band height
    xml = xml.replace('<band height="669">', '<band height="699">')

    with open("report/rptSuratPersetujuanUmum.jrxml", "w") as f:
        f.write(xml)

if __name__ == "__main__":
    patch()
    print("Signature Box Fixed")
