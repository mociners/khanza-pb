import re

truncated_path = "src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java"
recovered_path = "recovered_from_class.java"

with open(truncated_path, "r") as f:
    top_half = f.read()

with open(recovered_path, "r") as f:
    recovered_content = f.read()

# We need everything from recovered_from_class.java starting at `private void initComponents() {`
# to the end of the file.
match = re.search(r'    private void initComponents\(\) \{', recovered_content)
if match:
    bottom_half = recovered_content[match.start():]
    
    # Merge!
    full_content = top_half + "\n" + bottom_half
    
    with open(truncated_path, "w") as f:
        f.write(full_content)
    print("Merged successfully!")
else:
    print("Could not find initComponents in recovered file!")
