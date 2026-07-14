with open("recovered_from_class.java", "r") as f:
    recovered_lines = f.readlines()

imports = [line for line in recovered_lines if line.startswith("import ")]

with open("src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java", "r") as f:
    original = f.read()

# remove my manual import
original = original.replace("import java.awt.Component;\n", "")

# Find package line
pkg_idx = original.find("package rekammedis;")
if pkg_idx != -1:
    end_pkg_idx = pkg_idx + len("package rekammedis;")
    new_code = original[:end_pkg_idx] + "\n" + "".join(imports) + original[end_pkg_idx:]
    with open("src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java", "w") as f:
        f.write(new_code)
    print("Fixed imports!")
