import re

def patch():
    with open("report/rptSuratPersetujuanUmum.jrxml", "r") as f:
        xml = f.read()

    # 1. Update margins and columnWidth
    xml = re.sub(r'columnWidth="592"', 'columnWidth="552"', xml)
    xml = re.sub(r'leftMargin="10"', 'leftMargin="30"', xml)
    xml = re.sub(r'rightMargin="10"', 'rightMargin="30"', xml)

    # 2. Adjust widths and x coordinates
    xml = re.sub(r'width="592"', 'width="552"', xml)
    xml = re.sub(r'width="580"', 'width="540"', xml)
    xml = re.sub(r'width="565"', 'width="525"', xml)
    xml = re.sub(r'width="555"', 'width="515"', xml)
    xml = re.sub(r'width="550"', 'width="510"', xml) # for Y=195, 208
    xml = re.sub(r'width="544"', 'width="504"', xml)
    xml = re.sub(r'width="487"', 'width="447"', xml)
    
    # Left column specific widths
    xml = re.sub(r'x="105" y="(\d+)" width="290"', r'x="105" y="\1" width="250"', xml)
    
    # Right column shifts
    xml = re.sub(r'x="400"', 'x="360"', xml)
    xml = re.sub(r'x="455"', 'x="415"', xml)

    # 3. Y coordinates recalculation for detail bands
    detail_match = re.search(r'<detail>(.*?)</detail>', xml, flags=re.DOTALL)
    if detail_match:
        detail_xml = detail_match.group(1)
        
        def process_band(m):
            band = m.group(0)
            
            # Identify Bands 1 and 2
            if "1. PERSETUJUAN UNTUK PERAWATAN" in band or "4. BARANG-BARANG MILIK PASIEN" in band:
                # Set all reportElement heights to 12
                band = re.sub(r'(<reportElement[^>]*)height="\d+"', r'\1height="12"', band)
                
                # Find all unique Ys
                y_matches = re.finditer(r'<reportElement[^>]*\by="(\d+)"', band)
                original_ys = sorted(list(set(int(match.group(1)) for match in y_matches)))
                
                y_map = {}
                current_y = 0
                for orig_y in original_ys:
                    y_map[str(orig_y)] = str(current_y)
                    current_y += 16 # 12 height + 4 gap
                    
                # Replace Ys
                def replacer(match):
                    orig_y = match.group(1)
                    return match.group(0).replace(f'y="{orig_y}"', f'y="{y_map[orig_y]}"')
                    
                band = re.sub(r'<reportElement[^>]*\by="(\d+)"', replacer, band)
                
                # Optimize band height
                max_y = current_y - 16
                optimal_height = max_y + 12 + 5
                band = re.sub(r'(<band[^>]*\bheight=")\d+(")', rf'\g<1>{optimal_height}\g<2>', band, count=1)
                
            return band
            
        new_detail = re.sub(r'<band.*?</band>', process_band, detail_xml, flags=re.DOTALL)
        xml = xml.replace(detail_xml, new_detail)

    with open("report/rptSuratPersetujuanUmum.jrxml", "w") as f:
        f.write(xml)

if __name__ == "__main__":
    patch()
    print("JRXML Layout Fully Rewritten")
