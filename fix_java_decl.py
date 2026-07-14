import os

def fix_java_decl():
    path = "src/surat/SuratPersetujuanUmum.java"
    with open(path, "r") as f:
        java = f.read()

    # Declare variables if missing
    if "private widget.TextBox Saksi2;" not in java:
        java = java.replace(
            "private widget.TextBox EdukasiRS;",
            "private widget.TextBox EdukasiRS;\n    private widget.TextBox Saksi2;\n    private widget.Label jLabelSaksi2;\n    private widget.Button btnAmbilSaksi2;"
        )

    with open(path, "w") as f:
        f.write(java)

if __name__ == "__main__":
    fix_java_decl()
    print("Fixed Java Declarations")
