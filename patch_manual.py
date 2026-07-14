import re

with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# Remove setEditable(false) for these calculation fields
content = content.replace("        TInput24.setEditable(false);\n", "")
content = content.replace("        TOutput24.setEditable(false);\n", "")
content = content.replace("        TBalance24.setEditable(false);\n", "")
content = content.replace("        JumlahKeluar.setEditable(false);\n", "")

# We will also empty hitung24Jam() to stop it from overriding manual inputs
# Currently it looks like:
#     private void hitung24Jam() {
#         if (TNoRw.getText().trim().equals("") || Tanggal.getDate() == null) {
# ...
#             TBalance24.setText(balance24 + "");
#         } catch (Exception e) {
#             System.out.println("Notif : "+e);
#         }
#     }

pattern = r"(    private void hitung24Jam\(\) \{).*?(    \})"
replacement = r"\1\n        // Kalkulasi otomatis dinonaktifkan karena permintaan input manual\n\2"
content = re.sub(pattern, replacement, content, flags=re.DOTALL)

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)
