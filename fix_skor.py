import re

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'r') as f:
    content = f.read()

# find: this.TSeveritySkor.setText(val_208);
# replace with:
# this.TSeveritySkor.setText(val_208);
# if (val_208 != null) {
#     switch(val_208) {
#         case "0": this.RdoSkor0.setSelected(true); break;
#         case "1": this.RdoSkor1.setSelected(true); break;
#         case "2": this.RdoSkor2.setSelected(true); break;
#         case "3": this.RdoSkor3.setSelected(true); break;
#         case "4": this.RdoSkor4.setSelected(true); break;
#         case "5": this.RdoSkor5.setSelected(true); break;
#         case "6": this.RdoSkor6.setSelected(true); break;
#         case "7": this.RdoSkor7.setSelected(true); break;
#         case "8": this.RdoSkor8.setSelected(true); break;
#         case "9": this.RdoSkor9.setSelected(true); break;
#         case "10": this.RdoSkor10.setSelected(true); break;
#     }
# }

pattern = r'(this\.TSeveritySkor\.setText\(val_208\);)'
replacement = """\\1
                        if (val_208 != null) {
                            switch(val_208) {
                                case "0": this.RdoSkor0.setSelected(true); break;
                                case "1": this.RdoSkor1.setSelected(true); break;
                                case "2": this.RdoSkor2.setSelected(true); break;
                                case "3": this.RdoSkor3.setSelected(true); break;
                                case "4": this.RdoSkor4.setSelected(true); break;
                                case "5": this.RdoSkor5.setSelected(true); break;
                                case "6": this.RdoSkor6.setSelected(true); break;
                                case "7": this.RdoSkor7.setSelected(true); break;
                                case "8": this.RdoSkor8.setSelected(true); break;
                                case "9": this.RdoSkor9.setSelected(true); break;
                                case "10": this.RdoSkor10.setSelected(true); break;
                            }
                        }"""

new_content = re.sub(pattern, replacement, content)

with open('src/rekammedis/RMPenilaianAwalKeperawatanRanapDewasa.java', 'w') as f:
    f.write(new_content)

print("Injected Radio buttons for skor!")
