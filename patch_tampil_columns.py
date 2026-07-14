with open("src/rekammedis/RMTTVBalanceCairan.java", "r") as f:
    content = f.read()

# Replace masuk1 -> intake_makan etc in tampil
content = content.replace("rm_ttv_balance_cairan.masuk1", "rm_ttv_balance_cairan.intake_makan")
content = content.replace("rm_ttv_balance_cairan.masuk2", "rm_ttv_balance_cairan.intake_minum")
content = content.replace("rm_ttv_balance_cairan.masuk3", "rm_ttv_balance_cairan.intake_ngt")
content = content.replace("rm_ttv_balance_cairan.masuk4", "rm_ttv_balance_cairan.intake_transfusi")
content = content.replace("rm_ttv_balance_cairan.masuk5", "rm_ttv_balance_cairan.intake_infus")
content = content.replace("rm_ttv_balance_cairan.masuk6", "rm_ttv_balance_cairan.intake_sisa_infus")
content = content.replace("rm_ttv_balance_cairan.jumlahmasuk", "rm_ttv_balance_cairan.jumlah_input")

content = content.replace("rm_ttv_balance_cairan.keluar1", "rm_ttv_balance_cairan.output_urine")
content = content.replace("rm_ttv_balance_cairan.keluar2", "rm_ttv_balance_cairan.output_muntah")
content = content.replace("rm_ttv_balance_cairan.keluar3", "rm_ttv_balance_cairan.output_ngt")
content = content.replace("rm_ttv_balance_cairan.keluar4", "rm_ttv_balance_cairan.output_iwl")
content = content.replace("rm_ttv_balance_cairan.keluar5", "rm_ttv_balance_cairan.output_drain")
content = content.replace("rm_ttv_balance_cairan.jumlahkeluar", "rm_ttv_balance_cairan.jumlah_output")
content = content.replace("rm_ttv_balance_cairan.bc", "rm_ttv_balance_cairan.balance")

# Replace rs.getString("masuk1") etc in tampil
content = content.replace('rs.getString("masuk1")', 'rs.getString("intake_makan")')
content = content.replace('rs.getString("masuk2")', 'rs.getString("intake_minum")')
content = content.replace('rs.getString("masuk3")', 'rs.getString("intake_ngt")')
content = content.replace('rs.getString("masuk4")', 'rs.getString("intake_transfusi")')
content = content.replace('rs.getString("masuk5")', 'rs.getString("intake_infus")')
content = content.replace('rs.getString("masuk6")', 'rs.getString("intake_sisa_infus")')
content = content.replace('rs.getString("jumlahmasuk")', 'rs.getString("jumlah_input")')

content = content.replace('rs.getString("keluar1")', 'rs.getString("output_urine")')
content = content.replace('rs.getString("keluar2")', 'rs.getString("output_muntah")')
content = content.replace('rs.getString("keluar3")', 'rs.getString("output_ngt")')
content = content.replace('rs.getString("keluar4")', 'rs.getString("output_iwl")')
content = content.replace('rs.getString("keluar5")', 'rs.getString("output_drain")')
content = content.replace('rs.getString("jumlahkeluar")', 'rs.getString("jumlah_output")')
content = content.replace('rs.getString("bc")', 'rs.getString("balance")')

with open("src/rekammedis/RMTTVBalanceCairan.java", "w") as f:
    f.write(content)
