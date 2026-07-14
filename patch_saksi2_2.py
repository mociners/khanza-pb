import os

def patch_saksi2_getdata():
    path = "src/surat/SuratPersetujuanUmum.java"
    with open(path, "r") as f:
        content = f.read()

    # In getData()
    content = content.replace(
        "EdukasiRS.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 36).toString());",
        "EdukasiRS.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 36).toString());\n                Saksi2.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 37).toString());"
    )

    # In ganti() setValueAt
    content = content.replace(
        "tbObat.setValueAt(EdukasiRS.getText(), tbObat.getSelectedRow(), 36);",
        "tbObat.setValueAt(EdukasiRS.getText(), tbObat.getSelectedRow(), 36);\n            tbObat.setValueAt(Saksi2.getText(), tbObat.getSelectedRow(), 37);"
    )

    with open(path, "w") as f:
        f.write(content)

if __name__ == "__main__":
    patch_saksi2_getdata()
    print("Patched getData and setValueAt")
