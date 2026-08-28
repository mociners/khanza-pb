import re

file_path = "/home/itrsupb/Documents/khanza-pb/src/simrskhanza/DlgRawatInap.java"

with open(file_path, "r") as f:
    content = f.read()

def replace_method(content, method_name, new_body):
    pattern = r"(private void " + method_name + r"\(java\.awt\.event\.KeyEvent evt\) \{//GEN-FIRST:event_" + method_name + r").*?(    \}//GEN-LAST:event_" + method_name + r")"
    
    # Check if the pattern is found
    match = re.search(pattern, content, re.DOTALL)
    if not match:
        # Some methods might not have the GEN tags properly if they were manually written, like TTensi2KeyPressed
        if method_name in ["TTensi2KeyPressed", "TTensi1_2KeyPressed"]:
            pattern_manual = r"(private void " + method_name + r"\(java\.awt\.event\.KeyEvent evt\) \{).*?(\n    \})"
            match_manual = re.search(pattern_manual, content, re.DOTALL)
            if match_manual:
                replacement = match_manual.group(1) + "\n" + new_body + match_manual.group(2)
                return re.sub(pattern_manual, replacement, content, flags=re.DOTALL)
        return content

    replacement = match.group(1) + "\n" + new_body + match.group(2)
    return re.sub(pattern, replacement, content, flags=re.DOTALL)


# Panel 12
content = replace_method(content, "TAlergiKeyPressed", "        Valid.pindah(evt, TAlergi, TKeluhan);\n        Valid.pindah2(evt, TAlergi, TKeluhan);\n")
content = replace_method(content, "TKeluhanKeyPressed", "        Valid.pindah2(evt, TKeluhan, TPemeriksaan);\n")
content = replace_method(content, "TPemeriksaanKeyPressed", "        Valid.pindah2(evt, TPemeriksaan, TPenilaian);\n")
content = replace_method(content, "TPenilaianKeyPressed", "        Valid.pindah2(evt, TPenilaian, TindakLanjut);\n")
content = replace_method(content, "TindakLanjutKeyPressed", "        Valid.pindah2(evt, TindakLanjut, TTensi);\n")
content = replace_method(content, "TTensiKeyPressed", "        Valid.pindah(evt, TTensi, TTensi2);\n        Valid.pindah2(evt, TTensi, TTensi2);\n")
content = replace_method(content, "TTensi2KeyPressed", "        Valid.pindah(evt, TTensi2, TNadi);\n        Valid.pindah2(evt, TTensi2, TNadi);\n")
content = replace_method(content, "TNadiKeyPressed", "        Valid.pindah(evt, TNadi, TRespirasi);\n        Valid.pindah2(evt, TNadi, TRespirasi);\n")
content = replace_method(content, "TRespirasiKeyPressed", "        Valid.pindah(evt, TRespirasi, TSuhu);\n        Valid.pindah2(evt, TRespirasi, TSuhu);\n")
content = replace_method(content, "TSuhuKeyPressed", "        Valid.pindah(evt, TSuhu, SpO2);\n        Valid.pindah2(evt, TSuhu, SpO2);\n")
content = replace_method(content, "SpO2KeyPressed", "        Valid.pindah(evt, SpO2, TBerat);\n        Valid.pindah2(evt, SpO2, TBerat);\n")
content = replace_method(content, "TBeratKeyPressed", "        Valid.pindah(evt, TBerat, TTinggi);\n        Valid.pindah2(evt, TBerat, TTinggi);\n")
content = replace_method(content, "TTinggiKeyPressed", "        Valid.pindah(evt, TTinggi, TGCS);\n        Valid.pindah2(evt, TTinggi, TGCS);\n")
content = replace_method(content, "TGCSKeyPressed", "        Valid.pindah(evt, TGCS, cmbKesadaran);\n        Valid.pindah2(evt, TGCS, cmbKesadaran);\n")
content = replace_method(content, "cmbKesadaranKeyPressed", "        Valid.pindah(evt, cmbKesadaran, TInstruksi);\n        Valid.pindah2(evt, cmbKesadaran, TInstruksi);\n")
content = replace_method(content, "TInstruksiKeyPressed", "        Valid.pindah2(evt, TInstruksi, TEvaluasi);\n")
content = replace_method(content, "TEvaluasiKeyPressed", "        Valid.pindah2(evt, TEvaluasi, BtnSimpan);\n")

# KdPeg needs custom replacement for its else block
kd_peg_body = """        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            TPegawai.setText(pegawai.tampil3(KdPeg.getText()));
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekPegawaiActionPerformed(null);
        } else {
            Valid.pindah(evt, KdPeg, TAlergi);
            Valid.pindah2(evt, KdPeg, TAlergi);
        }
"""
content = replace_method(content, "KdPegKeyPressed", kd_peg_body)


# Panel 18
content = replace_method(content, "TAlergi1KeyPressed", "        Valid.pindah(evt, TAlergi1, Asesmen);\n        Valid.pindah2(evt, TAlergi1, Asesmen);\n")
content = replace_method(content, "AsesmenKeyPressed", "        Valid.pindah2(evt, Asesmen, Diagnosis);\n")
content = replace_method(content, "DiagnosisKeyPressed", "        Valid.pindah2(evt, Diagnosis, Intervensi);\n")
content = replace_method(content, "IntervensiKeyPressed", "        Valid.pindah2(evt, Intervensi, Monitoring);\n")
content = replace_method(content, "MonitoringKeyPressed", "        Valid.pindah2(evt, Monitoring, TTensi1);\n")
content = replace_method(content, "TTensi1KeyPressed", "        Valid.pindah(evt, TTensi1, TTensi1_2);\n        Valid.pindah2(evt, TTensi1, TTensi1_2);\n")
content = replace_method(content, "TTensi1_2KeyPressed", "        Valid.pindah(evt, TTensi1_2, TNadi1);\n        Valid.pindah2(evt, TTensi1_2, TNadi1);\n")
content = replace_method(content, "TNadi1KeyPressed", "        Valid.pindah(evt, TNadi1, TRespirasi1);\n        Valid.pindah2(evt, TNadi1, TRespirasi1);\n")
content = replace_method(content, "TRespirasi1KeyPressed", "        Valid.pindah(evt, TRespirasi1, TSuhu1);\n        Valid.pindah2(evt, TRespirasi1, TSuhu1);\n")
content = replace_method(content, "TSuhu1KeyPressed", "        Valid.pindah(evt, TSuhu1, SpO3);\n        Valid.pindah2(evt, TSuhu1, SpO3);\n")
content = replace_method(content, "SpO3KeyPressed", "        Valid.pindah(evt, SpO3, TBerat1);\n        Valid.pindah2(evt, SpO3, TBerat1);\n")
content = replace_method(content, "TBerat1KeyPressed", "        Valid.pindah(evt, TBerat1, TTinggi1);\n        Valid.pindah2(evt, TBerat1, TTinggi1);\n")
content = replace_method(content, "TTinggi1KeyPressed", "        Valid.pindah(evt, TTinggi1, TGCS1);\n        Valid.pindah2(evt, TTinggi1, TGCS1);\n")
content = replace_method(content, "TGCS1KeyPressed", "        Valid.pindah(evt, TGCS1, cmbKesadaran1);\n        Valid.pindah2(evt, TGCS1, cmbKesadaran1);\n")
content = replace_method(content, "cmbKesadaran1KeyPressed", "        Valid.pindah(evt, cmbKesadaran1, Instruksi);\n        Valid.pindah2(evt, cmbKesadaran1, Instruksi);\n")
content = replace_method(content, "InstruksiKeyPressed", "        Valid.pindah2(evt, Instruksi, Evaluasi);\n")
content = replace_method(content, "EvaluasiKeyPressed", "        Valid.pindah2(evt, Evaluasi, BtnSimpan);\n")

# KdPetugas needs custom replacement for its else block
kd_petugas_body = """        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            NmPetugas.setText(pegawai.tampil3(KdPetugas.getText()));
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekPegawai3ActionPerformed(null);
        } else {
            Valid.pindah(evt, KdPetugas, TAlergi1);
            Valid.pindah2(evt, KdPetugas, TAlergi1);
        }
"""
content = replace_method(content, "KdPetugasKeyPressed", kd_petugas_body)


with open(file_path, "w") as f:
    f.write(content)

print("Done replacing key bindings!")
