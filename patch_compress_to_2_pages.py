import re

def patch():
    with open("report/rptSuratPersetujuanUmum.jrxml", "r") as f:
        xml = f.read()

    # 1. Update margins and columnWidth (expand by 20px)
    xml = re.sub(r'columnWidth="552"', 'columnWidth="572"', xml)
    xml = re.sub(r'leftMargin="30"', 'leftMargin="20"', xml)
    xml = re.sub(r'rightMargin="30"', 'rightMargin="20"', xml)

    # 2. Adjust widths (expand by 20px)
    xml = re.sub(r'width="552"', 'width="572"', xml)
    xml = re.sub(r'width="540"', 'width="560"', xml)
    xml = re.sub(r'width="525"', 'width="545"', xml)
    xml = re.sub(r'width="515"', 'width="535"', xml)
    xml = re.sub(r'width="510"', 'width="530"', xml) 
    xml = re.sub(r'width="504"', 'width="524"', xml)
    xml = re.sub(r'width="447"', 'width="467"', xml)
    
    # Left column specific widths
    xml = re.sub(r'x="105"([^>]*)width="250"', r'x="105"\1width="270"', xml)
    
    # Right column shifts (shift right by 20px)
    xml = re.sub(r'x="360"', 'x="380"', xml)
    xml = re.sub(r'x="415"', 'x="435"', xml)

    # 3. Y coordinates recalculation for detail bands (tighter gap)
    detail_match = re.search(r'<detail>(.*?)</detail>', xml, flags=re.DOTALL)
    if detail_match:
        detail_xml = detail_match.group(1)
        
        def process_band(m):
            band = m.group(0)
            
            # Identify Bands 1 and 2
            if "1. PERSETUJUAN UNTUK PERAWATAN" in band or "4. BARANG-BARANG MILIK PASIEN" in band:
                # Find all unique Ys
                y_matches = re.finditer(r'<reportElement[^>]*\by="(\d+)"', band)
                original_ys = sorted(list(set(int(match.group(1)) for match in y_matches)))
                
                y_map = {}
                current_y = 0
                for orig_y in original_ys:
                    y_map[str(orig_y)] = str(current_y)
                    current_y += 14 # 12 height + 2 gap (Tighter!)
                    
                # Replace Ys
                def replacer(match):
                    orig_y = match.group(1)
                    return match.group(0).replace(f'y="{orig_y}"', f'y="{y_map[orig_y]}"')
                    
                band = re.sub(r'<reportElement[^>]*\by="(\d+)"', replacer, band)
                
                # Optimize band height
                max_y = current_y - 14
                optimal_height = max_y + 12 + 5
                band = re.sub(r'(<band[^>]*\bheight=")\d+(")', rf'\g<1>{optimal_height}\g<2>', band, count=1)
                
            return band
            
        new_detail = re.sub(r'<band.*?</band>', process_band, detail_xml, flags=re.DOTALL)
        xml = xml.replace(detail_xml, new_detail)

    # 4. Signature compression
    # Reduce height from 64 to 50
    xml = xml.replace('height="64" uuid="9a058474', 'height="50" uuid="9a058474')
    
    # Push text UP by 14 pixels (from their +30 state to +16 state)
    xml = xml.replace('y="649"', 'y="635"')
    xml = xml.replace('y="661"', 'y="647"')
    xml = xml.replace('y="675"', 'y="661"')
    
    # Reduce band 3 height
    xml = xml.replace('<band height="699">', '<band height="685">')

    with open("report/rptSuratPersetujuanUmum.jrxml", "w") as f:
        f.write(xml)

if __name__ == "__main__":
    patch()
    print("JRXML Compressed to 2 pages")
