import re

def patch():
    with open("report/rptSuratPersetujuanUmum.jrxml", "r") as f:
        xml = f.read()

    # 1. Add extra newlines for better paragraph spacing
    xml = xml.replace('profesional mereka.\\n"+', 'profesional mereka.\\n\\n"+')
    xml = xml.replace('obat-obatan.\\n"+', 'obat-obatan.\\n\\n"+')
    xml = xml.replace('bahwa :\\n"+', 'bahwa :\\n\\n"+')
    xml = xml.replace('setiap saat.\\n"+', 'setiap saat.\\n\\n"+')
    xml = xml.replace('prosedur/tindakan.\\n"+', 'prosedur/tindakan.\\n\\n"+')
    xml = xml.replace('kepada:\\n"+', 'kepada:\\n\\n"+')
    xml = xml.replace('menemui saya.\\n"+', 'menemui saya.\\n\\n"+')
    xml = xml.replace('di pengadilan.\\n', 'di pengadilan.\\n\\n')
    
    # Extra newlines for other tight spots
    xml = xml.replace('atas:\\n', 'atas:\\n\\n')
    xml = xml.replace('Rumah Sakit.\\n', 'Rumah Sakit.\\n\\n')
    xml = xml.replace('sebesar:\\n', 'sebesar:\\n\\n')

    # 2. Fix the band heights in the detail section
    def optimize_band(match):
        band_content = match.group(0)
        
        max_y_h = 0
        for elem in re.finditer(r'<reportElement[^>]*\by="(\d+)"[^>]*\bheight="(\d+)"', band_content):
            y = int(elem.group(1))
            h = int(elem.group(2))
            if y + h > max_y_h:
                max_y_h = y + h
                
        if max_y_h > 0:
            optimal_height = max_y_h + 5
            current_height_match = re.search(r'<band[^>]*\bheight="(\d+)"', band_content)
            if current_height_match:
                current_height = int(current_height_match.group(1))
                if current_height > optimal_height + 20: 
                    new_band = re.sub(r'(<band[^>]*\bheight=")\d+(")', rf'\g<1>{optimal_height}\g<2>', band_content, count=1)
                    return new_band
        return band_content

    xml = re.sub(r'<band.*?</band>', optimize_band, xml, flags=re.DOTALL)

    with open("report/rptSuratPersetujuanUmum.jrxml", "w") as f:
        f.write(xml)

if __name__ == "__main__":
    patch()
    print("JRXML Beautified")
