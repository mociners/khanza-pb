import os

def generate_saksi2():
    path_in = "src/freehand/DlgTTDPersetujuanUmum.java"
    path_out = "src/freehand/DlgTTDSaksi2.java"
    
    with open(path_in, "r") as f:
        content = f.read()
        
    # Replace class name
    content = content.replace("DlgTTDPersetujuanUmum", "DlgTTDSaksi2")
    
    # Replace window title
    content = content.replace("Tanda Tangan Pembuat Pernyataan", "Tanda Tangan Saksi 2 (Persetujuan Umum)")
    
    # Replace file name prefix
    content = content.replace("\"TTDPU_\"", "\"TTDSAKSI2PU_\"")
    
    # Replace SQL column
    content = content.replace("SET photo=? WHERE", "SET photo_saksi_2=? WHERE")
    
    with open(path_out, "w") as f:
        f.write(content)

if __name__ == "__main__":
    generate_saksi2()
    print("Created DlgTTDSaksi2.java")
