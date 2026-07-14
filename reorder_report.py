import re

db_columns = [
    'no_rawat', 'tanggal', 'informasi_dari', 'tgl_tiba', 'nip', 'kd_dokter', 'diagnosa_masuk', 'suhu', 'td', 'nadi_utama', 'rr_utama', 'riwayat_keluarga', 'riwayat_pasien', 'deskripsi_penyakit', 'riwayat_sekarang', 'alat_bantu_igd', 'alat_bantu_persetujuan', 'alat_bantu_perintah', 'alergi', 'jenis_alergi', 'status_nikah', 'pendidikan', 'agama', 'warga_negara', 'pekerjaan', 'aktivitas', 'tinggal_bersama', 'ket_tinggal', 'tempat_tinggal', 'ket_tempat_tinggal', 'curiga', 'curiga_ya', 'curiga_tidak', 'budaya', 'budaya_ya', 'budaya_tidak', 'anak', 'jumlah_anak', 'bimbingan_ibadah', 'masalah_psiko_1', 'masalah_psiko_2', 'masalah_psiko_3', 'masalah_psiko_4', 'kondisi_psikologis', 'ket_psiko', 'status_mental', 'orientasi_orang', 'ket_ori_orang', 'orientasi_tempat', 'ket_ori_tempat', 'orientasi_waktu', 'ket_ori_waktu', 'orientasi_situasi', 'ket_ori_situasi', 'memori', 'pupil_kanan_uk', 'pupil_kanan_reflex', 'pupil_kiri_uk', 'pupil_kiri_reflex', 'gcs_e', 'gcs_m', 'gcs_v', 'gcs_jml', 'tanda_meningeal_kaku', 'tanda_meningeal_brudzinski', 'tanda_meningeal_kernig', 'tanda_meningeal_lain', 'masalah_neuro_1', 'masalah_neuro_2', 'jalan_nafas', 'benda_asing', 'airway_ukuran', 'pernafasan', 'bunyi_nafas', 'kesulitan_nafas', 'alat_nafas', 'oksigen', 'oksigen_ltr', 'oksigen_jenis', 'frekuensi_nafas', 'batuk', 'spo2', 'masalah_nafas_1', 'masalah_nafas_2', 'masalah_nafas_3', 'masalah_nafas_4', 'sirkulasi', 'crt', 'denyut_nadi', 'nadi', 'nadi_jelas', 'irama', 'pacemaker', 'pacemaker_jelas', 'akral', 'masalah_sirk_1', 'ket_masalah_sirk_1', 'masalah_sirk_2', 'ket_masalah_sirk_2', 'masalah_sirk_3', 'ket_masalah_sirk_3', 'bak', 'bak_lainnya', 'kateter', 'kateter_jelas', 'urin_jumlah', 'urin_warna', 'prostat', 'nyeri_pinggang', 'kelainan_kemih', 'kelainan_sebut', 'masalah_kemih_1', 'ket_masalah_kemih_1', 'status_ob_g', 'status_ob_p', 'status_ob_a', 'menstruasi', 'kehamilan', 'kehamilan_hpht', 'kehamilan_hpl', 'post_partum', 'lochea', 'lochea_jumlah', 'payudara', 'pengeluaran_asi', 'kontraksi', 'papsmear', 'papsmear_tgl', 'mammo', 'mammo_tgl', 'sadari', 'skrining_kanker', 'skrining_tgl', 'masalah_rep_1', 'masalah_rep_2', 'masalah_rep_3', 'masalah_rep_4', 'masalah_rep_lain', 'gejala_awal', 'kekerasan_fisik', 'turgor', 'rambut', 'kuku', 'luka', 'luka_dalam', 'perdarahan_integ', 'fraktur', 'lokasi', 'lokasi_lain', 'masalah_integ_1', 'ket_masalah_integ_1', 'masalah_integ_2', 'ket_masalah_integ_2', 'masalah_integ_3', 'ket_masalah_integ_3', 'masalah_integ_4', 'ket_masalah_integ_4', 'telinga', 'telinga_lainnya', 'hidung', 'tenggorokan', 'gigi', 'sakit_gigi', 'gigi_palsu', 'mata', 'mata_lainnya', 'masalah_tht', 'wasir', 'perdarahan_rectal', 'jenis_diit', 'feeding_tube', 'pembatasan_cairan', 'abdomen', 'bunyi_usus', 'bunyi_usus_freq', 'bab', 'bab_sejak', 'bab_freq', 'konsistensi', 'warna_cerna', 'pencahar', 'masalah_pencernaan', 'nyeri_tidak_ada', 'nyeri_ada', 'nyeri_akut', 'nyeri_kronis', 'nyeri_viseral', 'nyeri_somatis', 'nyeri_provokes_diam', 'nyeri_provokes_mobilisasi', 'nyeri_provokes_ditekan', 'nyeri_provokes_tiduran', 'nyeri_provokes_berdiri', 'nyeri_provokes_berjalan', 'nyeri_provokes_lainnya', 'nyeri_quality_tajam', 'nyeri_quality_tumpul', 'nyeri_quality_ditusuk', 'nyeri_quality_ditarik', 'nyeri_quality_dipukul', 'nyeri_quality_berdenyut', 'nyeri_quality_dibakar', 'nyeri_quality_ditikam', 'nyeri_quality_disayat', 'nyeri_quality_lainnya', 'nyeri_radiation', 'nyeri_radiation_lokasi', 'nyeri_severity_metode', 'nyeri_severity_skor', 'nyeri_severity_nyeri', 'nyeri_time_setiap', 'nyeri_time_selama', 'nyeri_time_sejak', 'cpot_ekspresi', 'cpot_gerakan', 'cpot_ketegangan', 'cpot_ventilator', 'cpot_vokalisasi', 'cpot_total', 'cpot_kategori'
]

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'r') as f:
    xml = f.read()

m = re.search(r'(<detail>)(.*?)(</detail>)', xml, re.DOTALL)
before_detail = xml[:m.start(2)]
detail_content = m.group(2)
after_detail = xml[m.end(2):]

# Find all bands including their headers
bands = re.findall(r'<band height="\d+">.*?</band>', detail_content, re.DOTALL)

# Let's group them by section again
header_markers = {
    'DATA PSIKOLOGIS SOSIAL EKONOMI, SPIRITUAL': 'DATA PSIKOLOGIS SOSIAL EKONOMI, SPIRITUAL',
    'PENGKAJIAN NEUROSENSORIS': 'PENGKAJIAN NEUROSENSORIS',
    'PERNAFASAN': 'PERNAFASAN',
    'SIRKULASI': 'SIRKULASI',
    'PERKEMIHAN': 'PERKEMIHAN',
    'SEKSUAL/REPRODUKSI': 'SEKSUAL/REPRODUKSI',
    'INTEGUMEN & MUSKULOSKELETAL': 'INTEGUMEN & MUSKULOSKELETAL',
    'THT & MATA': 'THT & MATA',
    'PENCERNAAN': 'PENCERNAAN',
    'ASSESMEN NYERI': 'ASSESMEN NYERI',
    'ASSESMEN CPOT': 'ASSESMEN CPOT'
}

sections = []
current_section = {'header': None, 'bands': []}

for b in bands:
    is_header = False
    for sec, marker in header_markers.items():
        if f'<![CDATA[  {marker}]]>' in b or f'<![CDATA[ {marker}]]>' in b:
            # We encountered a new header
            sections.append(current_section)
            current_section = {'header': b, 'bands': []}
            is_header = True
            break
    if not is_header:
        current_section['bands'].append(b)
        
sections.append(current_section)

# Now sort the bands in each section!
def get_band_index(band):
    fields = re.findall(r'\$F\{([^\}]+)\}', band)
    if not fields:
        return -1
    
    first_field = fields[0]
    if first_field in db_columns:
        return db_columns.index(first_field)
    return 9999

for sec in sections:
    if sec['bands']:
        sec['bands'].sort(key=get_band_index)

# Reconstruct
final_bands = []
for sec in sections:
    if sec['header']:
        final_bands.append(sec['header'])
    final_bands.extend(sec['bands'])

new_detail_content = '\n'.join(final_bands)

new_xml = before_detail + new_detail_content + '\n' + after_detail

with open('report/rptCetakPenilaianAwalKeperawatanRanapDewasa.jrxml', 'w') as f:
    f.write(new_xml)

print("Reordered successfully!")
