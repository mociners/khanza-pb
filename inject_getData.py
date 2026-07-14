import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

with open('generated_getData.txt', 'r') as f:
    generated_code = f.read()

# We find the end of the getData() rs assignments.
# Let's insert it right after this.TMasalahSirkJelas3.setText(this.rs.getString("ket_masalah_sirk_3"));
# Or better, just before the closing block of if (!this.rs.next()) break block14; 
# Actually, the block is:
#                        this.Valid.SetTgl(this.TglAsesmen, this.rs.getString("tanggal"));
#                        ...
#                        this.TMasalahSirkJelas3.setText(this.rs.getString("ket_masalah_sirk_3"));
#                        [MORE STUFF?]
# Let's just find the exact block and append inside the try-catch of getData().

m = re.search(r'(private void getData\(\) \{.*?try \{.*?if \(!this\.rs\.next\(\)\) break block14;.*?)(                        \}[\s\n]*catch \(Exception e\) \{)', content, re.DOTALL)
if m:
    part1 = m.group(1)
    part2 = m.group(2)
    # We will append the generated code at the end of the block, right before the catch.
    # Wait, the try block ends with }
    # So we want to insert right before the } that precedes catch(Exception e)
    
    # Wait, actually m.group(2) starts with } catch
    new_content = content[:m.start()] + part1 + generated_code + '\n' + part2 + content[m.end():]
    with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
        f.write(new_content)
    print("Injected successfully!")
else:
    print("Could not find insertion point!")

