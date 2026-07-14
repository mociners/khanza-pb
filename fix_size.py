import re

def fix_java():
    with open("src/surat/SuratPersetujuanUmum.java", "r") as f:
        java = f.read()

    # FormInput size
    java = re.sub(r'FormInput\.setPreferredSize\(new java\.awt\.Dimension\(100, \d+\)\);', 'FormInput.setPreferredSize(new java.awt.Dimension(100, 650));', java)
    # PanelInput size
    java = re.sub(r'PanelInput\.setPreferredSize\(new java\.awt\.Dimension\(192, \d+\)\);', 'PanelInput.setPreferredSize(new java.awt.Dimension(192, 650));', java)
    java = re.sub(r'PanelInput\.setPreferredSize\(new Dimension\(WIDTH, \d+\)\);', 'PanelInput.setPreferredSize(new Dimension(WIDTH, 650));', java)

    with open("src/surat/SuratPersetujuanUmum.java", "w") as f:
        f.write(java)

def fix_form():
    with open("src/surat/SuratPersetujuanUmum.form", "r") as f:
        form = f.read()

    # Replace preferred dimensions
    # FormInput
    form = re.sub(r'<Dimension value="\[100, \d+\]"/>', '<Dimension value="[100, 650]"/>', form)
    # PanelInput
    form = re.sub(r'<Dimension value="\[192, \d+\]"/>', '<Dimension value="[192, 650]"/>', form)

    with open("src/surat/SuratPersetujuanUmum.form", "w") as f:
        f.write(form)

if __name__ == "__main__":
    fix_java()
    fix_form()
    print("Fixed GUI sizes")
