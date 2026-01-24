package keuangan;

import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JScrollPane;

public class DlgRekapBPJS extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;

    public DlgRekapBPJS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        // --- 1. SETUP KOLOM (Multi-Row Per Tindakan Dokter) ---
        Object[] row = {
                "NO.", "NAMA PASIEN", "NO. RM",
                "DIAGNOSA PRA-OPERASI", "TINDAKAN OPERASI", "STATUS PASIEN",
                "TGL. MASUK", "TGL. PULANG", "NO. KAMAR",

                // Detail Tindakan Dokter (per baris)
                "NAMA DOKTER", "SPESIALISASI", "JENIS TINDAKAN",
                "TGL TINDAKAN", "JAM TINDAKAN", "BIAYA TINDAKAN",

                // Tim Operasi - NAMA (tampil di baris pertama pasien saja)
                "NAMA OPERATOR 1", "NAMA OPERATOR 2", "NAMA OPERATOR 3",
                "NAMA ASISTEN OPERATOR 1", "NAMA ASISTEN OPERATOR 2", "NAMA ASISTEN OPERATOR 3",
                "NAMA ASISTEN INSTRUMEN",
                "NAMA OMLOOP 1", "NAMA OMLOOP 2", "NAMA OMLOOP 3", "NAMA OMLOOP 4", "NAMA OMLOOP 5",

                // -- JASA & Pelayanan (tampil di baris pertama) --
                "JASA DOKTER ANESTESI",
                "JASA OPERATOR", "JASA ASISTEN OPERATOR", "JASA ASISTEN INSTRUMEN",
                "JASA ON LOOP", "JASA OBSERVASI", "TOTAL TIM OK",
                "PELAYANAN TNO RAJAL / RANAP", "LAB PA 2",
                "TOTAL JASA MEDIS DOKTER", // Ganti Total Agregat dengan Total Jasa Dokter (Detail)

                // -- Detail Biaya (tampil di baris pertama) --
                "KAMAR/AKOMODASI", "RADIOLOGI", "LABORATORIUM",
                "SEWA OK", "AKOMODASI OK", "SARPRAS", "TAMBAHAN BIAYA",
                "POTONGAN",

                // -- Kategori Biaya (tampil di baris pertama) --
                "OBAT NON BEDAH", "OBAT BEDAH", "ALAT MEDIS", "PENUNJANG",

                // -- Summary Keuangan BPJS (tampil di baris pertama) --
                "PLAFOND", "SISA PLAFOND", "DIBAYAR PASIEN",
                "TOTAL BIAYA REKAP BPJS",

                // -- RINCIAN BILLING TAMBAHAN (warna kuning muda) --
                "REGISTRASI", "BIAYA HARIAN", "RETUR OBAT", "RESEP PULANG", "SERVICE",

                // -- TOTAL BILLING (warna hijau muda) --
                "TOTAL BILLING"
        };

        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbRekap.setModel(tabMode);
        tbRekap.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbRekap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Pengaturan Lebar Kolom
        for (i = 0; i < tbRekap.getColumnCount(); i++) {
            TableColumn column = tbRekap.getColumnModel().getColumn(i);
            if (i == 0)
                column.setPreferredWidth(35); // NO.
            else if (i == 1)
                column.setPreferredWidth(200); // NAMA PASIEN
            else if (i == 2)
                column.setPreferredWidth(80); // NO. RM

            // Kolom Nama Dokter DPJP (3-8) dan Umum (9-11)
            else if (i >= 3 && i <= 11)
                column.setPreferredWidth(150);

            else if (i == 12)
                column.setPreferredWidth(250); // DIAGNOSA
            else if (i == 13)
                column.setPreferredWidth(200); // TINDAKAN
            else if (i == 14)
                column.setPreferredWidth(100); // STATUS
            else if (i == 15 || i == 16)
                column.setPreferredWidth(120); // TGL MASUK/PULANG
            else if (i == 17)
                column.setPreferredWidth(80); // NO KAMAR

            // Kolom Financial (18-21): PLAFOND, dll
            else if (i >= 18 && i <= 21)
                column.setPreferredWidth(120);

            // Kolom JASA Dokter (22-30)
            else if (i >= 22 && i <= 30)
                column.setPreferredWidth(100);

            // JASA DOKTER ANESTESI (31)
            else if (i == 31)
                column.setPreferredWidth(120);

            // TIM OK Detail (32-37): Operator, Asisten, Omloop, Observasi, Total
            else if (i >= 32 && i <= 37)
                column.setPreferredWidth(100);

            // PELAYANAN, LAB PA 2, TOTAL JASA (38-40)
            else if (i >= 38 && i <= 40)
                column.setPreferredWidth(120);

            // Biaya Tidak Langsung (41-42)
            else if (i >= 41 && i <= 42)
                column.setPreferredWidth(120);

            // SISA PLAFOND, DIBAYAR PASIEN (43-44)
            else if (i >= 43 && i <= 44)
                column.setPreferredWidth(120);

            // Kategori Biaya: OBAT, ALAT, PENUNJANG (45-48)
            else if (i >= 45 && i <= 48)
                column.setPreferredWidth(100);

            // Rincian Billing Tambahan (51-55)
            else if (i >= 51 && i <= 55)
                column.setPreferredWidth(100);

            else
                column.setPreferredWidth(100);
        }

        // Custom renderer untuk warna kolom berbeda
        tbRekap.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);

                // Kolom 51-55 (REGISTRASI, HARIAN, RETUR OBAT, RESEP PULANG, SERVICE) = warna
                // kuning muda
                if (column >= 51 && column <= 55) {
                    if (!isSelected) {
                        c.setBackground(new java.awt.Color(255, 255, 200)); // Light yellow
                    }
                }
                // Kolom 56 (TOTAL BILLING) = warna hijau muda
                else if (column == 56) {
                    if (!isSelected) {
                        c.setBackground(new java.awt.Color(200, 255, 200)); // Light green
                    }
                } else {
                    // Kolom lainnya tetap putih (atau zebra striping)
                    if (!isSelected) {
                        if (row % 2 == 0) {
                            c.setBackground(new java.awt.Color(255, 255, 255)); // White
                        } else {
                            c.setBackground(new java.awt.Color(245, 245, 245)); // Light gray
                        }
                    }
                }

                return c;
            }
        });

        Tgl1.setDate(new java.util.Date());
        Tgl2.setDate(new java.util.Date());
    }

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            // Query Baru: Union Tindakan Dokter & Operasi
            ps = koneksi.prepareStatement(
                    "select " +
                            "    reg_periksa.no_rawat, pasien.nm_pasien, reg_periksa.no_rkm_medis, " +
                            "    reg_periksa.tgl_registrasi, concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) as tgl_masuk, "
                            +
                            "    reg_periksa.status_lanjut, kamar_inap.tgl_keluar, kamar.kd_kamar, " +
                            "    dokter.nm_dokter, spesialis.nm_sps, " +
                            "    jns_perawatan_inap.nm_perawatan as jenis_tindakan, " +
                            "    rawat_inap_dr.tgl_perawatan, rawat_inap_dr.jam_rawat, rawat_inap_dr.biaya_rawat, " +
                            "    'Ranap' as sumber_tindakan, 2 as urutan_sumber " +
                            "from rawat_inap_dr " +
                            "inner join reg_periksa on rawat_inap_dr.no_rawat = reg_periksa.no_rawat " +
                            "inner join pasien on reg_periksa.no_rkm_medis = pasien.no_rkm_medis " +
                            "inner join penjab on reg_periksa.kd_pj = penjab.kd_pj " +
                            "inner join dokter on rawat_inap_dr.kd_dokter = dokter.kd_dokter " +
                            "inner join spesialis on dokter.kd_sps = spesialis.kd_sps " +
                            "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw = jns_perawatan_inap.kd_jenis_prw "
                            +
                            "left join kamar_inap on reg_periksa.no_rawat = kamar_inap.no_rawat " +
                            "left join kamar on kamar_inap.kd_kamar = kamar.kd_kamar " +
                            "where reg_periksa.tgl_registrasi between ? and ? " +
                            "and penjab.png_jawab like '%BPJS%' " +
                            "and reg_periksa.status_lanjut = 'Ranap' " +

                            "UNION ALL " +

                            "select " +
                            "    reg_periksa.no_rawat, pasien.nm_pasien, reg_periksa.no_rkm_medis, " +
                            "    reg_periksa.tgl_registrasi, concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) as tgl_masuk, "
                            +
                            "    reg_periksa.status_lanjut, kamar_inap.tgl_keluar, kamar.kd_kamar, " +
                            "    dokter.nm_dokter, spesialis.nm_sps, " +
                            "    paket_operasi.nm_perawatan as jenis_tindakan, " +
                            "    operasi.tgl_operasi as tgl_perawatan, '00:00:00' as jam_rawat, " +
                            "    (operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biayasarpras+operasi.biayaalat+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum) as biaya_rawat, "
                            +
                            "    'Operasi' as sumber_tindakan, 1 as urutan_sumber " +
                            "from operasi " +
                            "inner join reg_periksa on operasi.no_rawat = reg_periksa.no_rawat " +
                            "inner join pasien on reg_periksa.no_rkm_medis = pasien.no_rkm_medis " +
                            "inner join penjab on reg_periksa.kd_pj = penjab.kd_pj " +
                            "inner join dokter on operasi.operator1 = dokter.kd_dokter " +
                            "inner join spesialis on dokter.kd_sps = spesialis.kd_sps " +
                            "inner join paket_operasi on operasi.kode_paket = paket_operasi.kode_paket " +
                            "left join kamar_inap on reg_periksa.no_rawat = kamar_inap.no_rawat " +
                            "left join kamar on kamar_inap.kd_kamar = kamar.kd_kamar " +
                            "where reg_periksa.tgl_registrasi between ? and ? " +
                            "and penjab.png_jawab like '%BPJS%' " +
                            "and reg_periksa.status_lanjut = 'Ranap' " +

                            // Urutkan: Pasien -> Sumber (Operasi dulu, baru Ranap) -> Kamar -> Tgl
                            "order by no_rawat, urutan_sumber, kd_kamar, tgl_perawatan, jam_rawat");

            ps.setString(1, Valid.SetTgl(Tgl1.getSelectedItem().toString()));
            ps.setString(2, Valid.SetTgl(Tgl2.getSelectedItem().toString()));
            ps.setString(3, Valid.SetTgl(Tgl1.getSelectedItem().toString()));
            ps.setString(4, Valid.SetTgl(Tgl2.getSelectedItem().toString()));
            rs = ps.executeQuery();

            int no = 0;
            String prevNoRawat = "";
            String prevTindakan = "";
            boolean isFirstRowOfPatient = true;

            // Cache untuk data pasien (agar tidak query ulang per row)
            String cachedDiagnosa = "";
            String cachedTindakan = "";
            String[] cachedNamaTimOK = null;
            double[] cachedBiaya = null;

            while (rs.next()) {
                String norawat = rs.getString("no_rawat");
                String currentTindakan = rs.getString("jenis_tindakan");

                // Deteksi jika pasien baru (ganti no_rawat)
                if (!norawat.equals(prevNoRawat)) {
                    isFirstRowOfPatient = true;
                    prevNoRawat = norawat;
                    prevTindakan = ""; // Reset tindakan sebelumnya
                    no++;

                    // Ambil data pasien (hanya sekali per pasien baru)
                    cachedDiagnosa = Sequel.cariIsi(
                            "select concat(diagnosa_pasien.kd_penyakit,' - ',penyakit.nm_penyakit) " +
                                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "
                                    +
                                    "where diagnosa_pasien.no_rawat='" + norawat
                                    + "' order by diagnosa_pasien.prioritas limit 1");

                    cachedTindakan = Sequel.cariIsi(
                            "select paket_operasi.nm_perawatan from operasi inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket where operasi.no_rawat='"
                                    + norawat + "' limit 1");

                    if (cachedTindakan.equals("")) {
                        cachedTindakan = "-";
                    }

                    cachedNamaTimOK = getNamaTimOperasi(norawat);
                    cachedBiaya = getRincianBiayaLain(norawat);
                } else {
                    isFirstRowOfPatient = false;
                }

                String tglPulang = rs.getString("tgl_keluar");
                if (tglPulang == null)
                    tglPulang = "-";

                // Prepare row data
                Object[] rowData;

                if (isFirstRowOfPatient) {
                    // Baris pertama pasien: tampilkan semua data termasuk tim operasi & biaya
                    double jasaDokterAnestesi = cachedBiaya[5];
                    double totalTimOK = cachedBiaya[11];
                    double pelayananTNO = cachedBiaya[12];
                    double labPA2 = cachedBiaya[13];

                    // Hitung total JASA dari semua tindakan dokter pasien ini
                    double totalJasaDokter = Sequel.cariIsiAngka(
                            "select sum(biaya_rawat) from rawat_inap_dr where no_rawat='" + norawat + "'");
                    double totalJasaPelayanan = totalJasaDokter + jasaDokterAnestesi + totalTimOK + pelayananTNO
                            + labPA2;

                    double kamarAkomodasi = cachedBiaya[14];
                    double radiologi = cachedBiaya[15];
                    double laboratorium = cachedBiaya[16];
                    double sewaOK = cachedBiaya[17];
                    double akomodasiOK = cachedBiaya[18];
                    double sarpras = cachedBiaya[19];
                    double tambahanBiaya = cachedBiaya[20];
                    double potongan = cachedBiaya[21];
                    double obatNonBedah = cachedBiaya[22];
                    double obatBedah = cachedBiaya[23];
                    double alatMedis = cachedBiaya[24];
                    double penunjang = cachedBiaya[25];

                    // Kategori tambahan (agar sama dengan billing)
                    double registrasi = cachedBiaya[26];
                    double harian = cachedBiaya[27];
                    double returObat = cachedBiaya[28];
                    double resepPulang = cachedBiaya[29];
                    double service = cachedBiaya[30];

                    double plafond = 0; // TODO
                    double dibayarPasien = 0; // TODO

                    // Total BPJS (Include semua komponen agar tidak beda dengan Billing)
                    double totalBPJS = totalJasaPelayanan + kamarAkomodasi + radiologi + laboratorium + sewaOK
                            + akomodasiOK + sarpras + tambahanBiaya - potongan + obatNonBedah + obatBedah
                            + alatMedis + penunjang + registrasi + harian - returObat + resepPulang + service;

                    double totalBilling = Sequel.cariIsiAngka(
                            "select sum(totalbiaya) from billing where no_rawat='" + norawat + "'");

                    double sisaPlafond = 0;

                    // Cek duplikasi nama tindakan
                    String displayTindakan = currentTindakan;
                    if (currentTindakan.equals(prevTindakan)) {
                        displayTindakan = "";
                    } else {
                        prevTindakan = currentTindakan;
                    }

                    rowData = new Object[] {
                            no,
                            rs.getString("nm_pasien"),
                            rs.getString("no_rkm_medis"),
                            cachedDiagnosa,
                            cachedTindakan,
                            rs.getString("status_lanjut"),
                            rs.getString("tgl_masuk"),
                            tglPulang,
                            rs.getString("kd_kamar"),

                            // Detail tindakan dokter (per baris)
                            rs.getString("nm_dokter"),
                            rs.getString("nm_sps"),
                            displayTindakan, // Gunakan displayTindakan (bisa kosong jika duplikat)
                            rs.getString("tgl_perawatan"),
                            rs.getString("jam_rawat"),
                            Valid.SetAngka(rs.getDouble("biaya_rawat")),

                            // Tim Operasi NAMA
                            cachedNamaTimOK[0], cachedNamaTimOK[1], cachedNamaTimOK[2],
                            cachedNamaTimOK[3], cachedNamaTimOK[4], cachedNamaTimOK[5],
                            cachedNamaTimOK[6],
                            cachedNamaTimOK[7], cachedNamaTimOK[8], cachedNamaTimOK[9], cachedNamaTimOK[10],
                            cachedNamaTimOK[11],

                            // JASA & Pelayanan
                            Valid.SetAngka(jasaDokterAnestesi),
                            Valid.SetAngka(cachedBiaya[6]), // JASA OPERATOR
                            Valid.SetAngka(cachedBiaya[7]), // JASA ASISTEN OPERATOR
                            Valid.SetAngka(cachedBiaya[8]), // JASA ASISTEN INSTRUMEN
                            Valid.SetAngka(cachedBiaya[9]), // JASA ON LOOP
                            Valid.SetAngka(cachedBiaya[10]), // JASA OBSERVASI
                            Valid.SetAngka(totalTimOK),
                            Valid.SetAngka(pelayananTNO),
                            Valid.SetAngka(labPA2),
                            Valid.SetAngka(totalJasaDokter), // Isi dengan Total Jasa Dokter (Rincian)

                            // Detail Biaya
                            Valid.SetAngka(kamarAkomodasi),
                            Valid.SetAngka(radiologi),
                            Valid.SetAngka(laboratorium),
                            Valid.SetAngka(sewaOK),
                            Valid.SetAngka(akomodasiOK),
                            Valid.SetAngka(sarpras),
                            Valid.SetAngka(tambahanBiaya),
                            Valid.SetAngka(potongan),

                            // Kategori Biaya
                            Valid.SetAngka(obatNonBedah),
                            Valid.SetAngka(obatBedah),
                            Valid.SetAngka(alatMedis),
                            Valid.SetAngka(penunjang),

                            // Summary Keuangan BPJS
                            Valid.SetAngka(plafond),
                            Valid.SetAngka(sisaPlafond),
                            Valid.SetAngka(dibayarPasien),
                            Valid.SetAngka(totalBPJS), // TOTAL BIAYA REKAP BPJS (tanpa billing extras)

                            // Rincian Billing Tambahan
                            Valid.SetAngka(registrasi),
                            Valid.SetAngka(harian),
                            Valid.SetAngka(returObat),
                            Valid.SetAngka(resepPulang),
                            Valid.SetAngka(service),

                            // TOTAL BILLING (lengkap)
                            Valid.SetAngka(totalBilling)
                    };
                } else {
                    // Cek duplikasi nama tindakan untuk baris selanjutnya
                    String displayTindakan = currentTindakan;
                    if (currentTindakan.equals(prevTindakan)) {
                        displayTindakan = "";
                    } else {
                        prevTindakan = currentTindakan;
                    }

                    // Baris kedua dst pasien: hanya tampilkan data pasien & tindakan dokter,
                    // sisanya kosong
                    rowData = new Object[] {
                            "", // NO kosong
                            rs.getString("nm_pasien"),
                            rs.getString("no_rkm_medis"),
                            cachedDiagnosa,
                            cachedTindakan,
                            rs.getString("status_lanjut"),
                            rs.getString("tgl_masuk"),
                            tglPulang,
                            rs.getString("kd_kamar"),

                            // Detail tindakan dokter (per baris)
                            rs.getString("nm_dokter"),
                            rs.getString("nm_sps"),
                            displayTindakan, // Gunakan displayTindakan
                            rs.getString("tgl_perawatan"),
                            rs.getString("jam_rawat"),
                            Valid.SetAngka(rs.getDouble("biaya_rawat")),

                            // Tim Operasi NAMA - kosong
                            "", "", "", "", "", "", "", "", "", "", "", "",

                            // JASA & Pelayanan - kosong
                            "", "", "", "", "", "", "", "", "", "",

                            // Detail Biaya - kosong
                            "", "", "", "", "", "", "", "",

                            // Kategori Biaya - kosong
                            "", "", "", "",

                            // Summary Keuangan - kosong
                            "", "", "", "",

                            // Rincian Billing Tambahan - kosong
                            "", "", "", "", "",

                            // TOTAL BILLING - kosong
                            ""
                    };
                }

                tabMode.addRow(rowData);
                if (isFirstRowOfPatient)
                    no++;
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    // --- LOGIKA BARU: AMBIL DOKTER DAN JASA/PEMBAYARAN MEREKA ---
    private Object[][] getDokterDanJasa(String norawat, String jenis) {
        int max = (jenis.equals("Spesialis")) ? 6 : 3;
        Object[][] hasil = new Object[max][2]; // Kolom 0: Nama, Kolom 1: Total Jasa
        // Init default
        for (int x = 0; x < max; x++) {
            hasil[x][0] = "-";
            hasil[x][1] = 0.0;
        }

        try {
            String sql = "";
            // Query: Group By Dokter, Sum SEMUA Biaya Rawat (JASA TOTAL)
            if (jenis.equals("Spesialis")) {
                sql = "select dokter.nm_dokter, sum(rawat_inap_dr.biaya_rawat) as tot_jasa " +
                        "from rawat_inap_dr " +
                        "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter " +
                        "inner join spesialis on dokter.kd_sps=spesialis.kd_sps " +
                        "where rawat_inap_dr.no_rawat='" + norawat + "' " +
                        "and spesialis.nm_sps not like '%UMUM%' " +
                        "group by dokter.kd_dokter limit " + max;
            } else {
                sql = "select dokter.nm_dokter, sum(rawat_inap_dr.biaya_rawat) as tot_jasa " +
                        "from rawat_inap_dr " +
                        "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter " +
                        "inner join spesialis on dokter.kd_sps=spesialis.kd_sps " +
                        "where rawat_inap_dr.no_rawat='" + norawat + "' " +
                        "and spesialis.nm_sps like '%UMUM%' " +
                        "group by dokter.kd_dokter limit " + max;
            }

            PreparedStatement ps2 = koneksi.prepareStatement(sql);
            ResultSet rs2 = ps2.executeQuery();
            int index = 0;
            while (rs2.next() && index < max) {
                hasil[index][0] = rs2.getString("nm_dokter");
                hasil[index][1] = rs2.getDouble("tot_jasa");
                index++;
            }
            rs2.close();
            ps2.close();

            // JIKA NAMA KOSONG (Karena mungkin dia DPJP tapi tidak ada inputan
            // Visite/Visit)
            // Kita cari nama dokternya saja dari data rawat inap (tanpa biaya visit)
            if (index == 0) {
                // Fallback: Ambil nama saja, biaya 0
                if (jenis.equals("Spesialis")) {
                    sql = "select distinct dokter.nm_dokter from rawat_inap_dr inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter inner join spesialis on dokter.kd_sps=spesialis.kd_sps where rawat_inap_dr.no_rawat='"
                            + norawat + "' and spesialis.nm_sps not like '%UMUM%' limit " + max;
                } else {
                    sql = "select distinct dokter.nm_dokter from rawat_inap_dr inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter inner join spesialis on dokter.kd_sps=spesialis.kd_sps where rawat_inap_dr.no_rawat='"
                            + norawat + "' and spesialis.nm_sps like '%UMUM%' limit " + max;
                }
                ps2 = koneksi.prepareStatement(sql);
                rs2 = ps2.executeQuery();
                while (rs2.next() && index < max) {
                    if (hasil[index][0].equals("-")) { // Hanya isi jika masih kosong
                        hasil[index][0] = rs2.getString("nm_dokter");
                        hasil[index][1] = 0.0;
                        index++;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error Get Dokter Jasa: " + e);
        }
        return hasil;
    }

    // --- AMBIL NAMA TIM OPERASI ---
    private String[] getNamaTimOperasi(String norawat) {
        // Array untuk menyimpan nama-nama tim: operator1-3, asisten1-3, instrumen,
        // omloop1-5 (12 total)
        String[] nama = new String[12];
        for (int i = 0; i < 12; i++)
            nama[i] = "-";

        try {
            PreparedStatement psOp = koneksi.prepareStatement(
                    "select " +
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.operator1) as operator1, " +
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.operator2) as operator2, " +
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.operator3) as operator3, " +
                            "(select nama from petugas where petugas.nip=operasi.asisten_operator1) as asisten_operator1, "
                            +
                            "(select nama from petugas where petugas.nip=operasi.asisten_operator2) as asisten_operator2, "
                            +
                            "(select nama from petugas where petugas.nip=operasi.asisten_operator3) as asisten_operator3, "
                            +
                            "(select nama from petugas where petugas.nip=operasi.instrumen) as instrumen, " +
                            "(select nama from petugas where petugas.nip=operasi.omloop) as omloop, " +
                            "(select nama from petugas where petugas.nip=operasi.omloop2) as omloop2, " +
                            "(select nama from petugas where petugas.nip=operasi.omloop3) as omloop3, " +
                            "(select nama from petugas where petugas.nip=operasi.omloop4) as omloop4, " +
                            "(select nama from petugas where petugas.nip=operasi.omloop5) as omloop5 " +
                            "from operasi where no_rawat=? limit 1");
            psOp.setString(1, norawat);
            ResultSet rsOp = psOp.executeQuery();

            if (rsOp.next()) {
                nama[0] = rsOp.getString("operator1") != null ? rsOp.getString("operator1") : "-";
                nama[1] = rsOp.getString("operator2") != null ? rsOp.getString("operator2") : "-";
                nama[2] = rsOp.getString("operator3") != null ? rsOp.getString("operator3") : "-";
                nama[3] = rsOp.getString("asisten_operator1") != null ? rsOp.getString("asisten_operator1") : "-";
                nama[4] = rsOp.getString("asisten_operator2") != null ? rsOp.getString("asisten_operator2") : "-";
                nama[5] = rsOp.getString("asisten_operator3") != null ? rsOp.getString("asisten_operator3") : "-";
                nama[6] = rsOp.getString("instrumen") != null ? rsOp.getString("instrumen") : "-";
                nama[7] = rsOp.getString("omloop") != null ? rsOp.getString("omloop") : "-";
                nama[8] = rsOp.getString("omloop2") != null ? rsOp.getString("omloop2") : "-";
                nama[9] = rsOp.getString("omloop3") != null ? rsOp.getString("omloop3") : "-";
                nama[10] = rsOp.getString("omloop4") != null ? rsOp.getString("omloop4") : "-";
                nama[11] = rsOp.getString("omloop5") != null ? rsOp.getString("omloop5") : "-";
            }
            rsOp.close();
            psOp.close();
        } catch (Exception e) {
            System.out.println("Error getNamaTimOperasi: " + e);
        }
        return nama;
    }

    // --- LOGIKA HITUNG BIAYA (DETAIL TERPISAH, BUKAN KATEGORI) ---
    private double[] getRincianBiayaLain(String norawat) {
        // Array index mapping:
        // [0-4] = Reserved
        // [5] = Jasa Dokter Anestesi
        // [6] = JASA OPERATOR
        // [7] = JASA ASISTEN OPERATOR
        // [8] = JASA ASISTEN INSTRUMEN
        // [9] = JASA ON LOOP
        // [10] = JASA OBSERVASI
        // [11] = TOTAL TIM OK
        // [12] = Pelayanan TNO RAJAL/RANAP
        // [13] = LAB PA 2
        // [14] = KAMAR/AKOMODASI
        // [15] = RADIOLOGI
        // [16] = LABORATORIUM
        // [17] = SEWA OK
        // [18] = AKOMODASI OK
        // [19] = SARPRAS
        // [20] = TAMBAHAN BIAYA
        // [21] = POTONGAN
        // [22] = OBAT NON BEDAH
        // [23] = OBAT BEDAH
        // [24] = ALAT MEDIS
        // [25] = PENUNJANG
        // [26] = REGISTRASI
        // [27] = BIAYA HARIAN
        // [28] = RETUR OBAT
        // [29] = RESEP PULANG
        // [30] = SERVICE
        double[] hasil = new double[31];

        try {
            // Jasa Dokter Anestesi
            hasil[5] = Sequel.cariIsiAngka(
                    "select sum(biayadokter_anestesi+biayaasisten_anestesi+biayaasisten_anestesi2) from operasi where no_rawat='"
                            + norawat + "'");

            // --- TIM OK - DETAIL PER PERAN ---
            hasil[6] = Sequel.cariIsiAngka(
                    "select sum(biayaoperator1+biayaoperator2+biayaoperator3) from operasi where no_rawat='" + norawat
                            + "'");
            hasil[7] = Sequel.cariIsiAngka(
                    "select sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3) from operasi where no_rawat='"
                            + norawat + "'");
            hasil[8] = Sequel.cariIsiAngka(
                    "select sum(biayainstrumen) from operasi where no_rawat='" + norawat + "'");
            hasil[9] = Sequel.cariIsiAngka(
                    "select sum(biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5) from operasi where no_rawat='"
                            + norawat + "'");
            hasil[10] = Sequel.cariIsiAngka(
                    "select sum(biayaperawaat_resusitas+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar) from operasi where no_rawat='"
                            + norawat + "'");
            hasil[11] = hasil[6] + hasil[7] + hasil[8] + hasil[9] + hasil[10]; // TOTAL TIM OK

            // Pelayanan TNO RAJAL/RANAP
            hasil[12] = Sequel
                    .cariIsiAngka("select sum(biaya_rawat) from rawat_inap_pr where no_rawat='" + norawat + "'");
            hasil[12] += Sequel
                    .cariIsiAngka("select sum(biaya_rawat) from rawat_inap_drpr where no_rawat='" + norawat + "'");

            // LAB PA 2
            hasil[13] = Sequel.cariIsiAngka("select sum(biaya) from periksa_lab where no_rawat='" + norawat + "'");

            // --- DETAIL BIAYA TERPISAH (bukan kategori) ---
            // KAMAR/AKOMODASI
            hasil[14] = Sequel
                    .cariIsiAngka("select sum(lama * trf_kamar) from kamar_inap where no_rawat='" + norawat + "'");

            // RADIOLOGI
            hasil[15] = Sequel
                    .cariIsiAngka("select sum(biaya) from periksa_radiologi where no_rawat='" + norawat + "'");

            // LABORATORIUM (duplikat dengan LAB PA 2 tapi kita pisahkan jika beda)
            hasil[16] = hasil[13]; // Sama dengan LAB PA 2, bisa disesuaikan jika berbeda

            // SEWA OK
            hasil[17] = Sequel.cariIsiAngka("select sum(biayasewaok) from operasi where no_rawat='" + norawat + "'");

            // AKOMODASI OK
            hasil[18] = Sequel.cariIsiAngka("select sum(akomodasi) from operasi where no_rawat='" + norawat + "'");

            // SARPRAS
            hasil[19] = Sequel
                    .cariIsiAngka("select sum(bagian_rs+biayasarpras) from operasi where no_rawat='" + norawat + "'");

            // TAMBAHAN BIAYA
            hasil[20] = Sequel
                    .cariIsiAngka("select sum(besar_biaya) from tambahan_biaya where no_rawat='" + norawat + "'");

            // POTONGAN
            hasil[21] = Sequel.cariIsiAngka(
                    "select sum(besar_pengurangan) from pengurangan_biaya where no_rawat='" + norawat + "'");

            // OBAT NON BEDAH (detail_pemberian_obat)
            hasil[22] = Sequel
                    .cariIsiAngka("select sum(total) from detail_pemberian_obat where no_rawat='" + norawat + "'");

            // OBAT BEDAH
            hasil[23] = 0; // TODO: Sesuaikan jika ada tabel terpisah

            // ALAT MEDIS (dari operasi)
            hasil[24] = Sequel.cariIsiAngka(
                    "select sum(biayaalat+biaya_dokter_pjanak+biaya_dokter_umum) from operasi where no_rawat='"
                            + norawat + "'");

            // PENUNJANG
            hasil[25] = 0; // TODO: Tambahkan jika ada sumber lain

            // === KATEGORI TAMBAHAN YANG TERLEWAT (agar sama dengan billing) ===
            // [26] = REGISTRASI
            // [27] = BIAYA HARIAN (biaya_harian dari kamar_inap)
            // [28] = RETUR OBAT
            // [29] = RESEP PULANG
            // [30] = SERVICE

            // REGISTRASI
            hasil[26] = Sequel.cariIsiAngka("select sum(biaya_reg) from reg_periksa where no_rawat='" + norawat + "'");

            // BIAYA HARIAN (dari detail_beri_diet, dll - jika ada)
            hasil[27] = 0; // TODO: Query dari tabel biaya harian jika ada

            // RETUR OBAT (dikurangi dari total) - dari detreturjual (Logic mirip Billing)
            hasil[28] = Sequel.cariIsiAngka(
                    "select sum(detreturjual.subtotal) from detreturjual " +
                            "inner join returjual on detreturjual.no_retur_jual=returjual.no_retur_jual " +
                            "where returjual.no_retur_jual like '%" + norawat + "%'");

            // RESEP PULANG
            hasil[29] = Sequel
                    .cariIsiAngka("select sum(jml_barang*harga) from resep_pulang where no_rawat='" + norawat + "'");

            // SERVICE (biaya tambahan service dari RS)
            hasil[30] = 0; // Dihitung di tampil() berdasarkan persentase dari kategori lain

        } catch (Exception e) {
            System.out.println("Error getRincianBiayaLain: " + e);
        }
        return hasil;
    }

    private void initComponents() {
        internalFrame1 = new javax.swing.JInternalFrame();
        internalFrame1.setName("internalFrame1");

        Scroll = new javax.swing.JScrollPane();
        tbRekap = new javax.swing.JTable();
        panelGlass5 = new widget.panelisi();
        label11 = new javax.swing.JLabel();
        Tgl1 = new widget.Tanggal();
        label12 = new javax.swing.JLabel();
        Tgl2 = new widget.Tanggal();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        BtnPrint = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)),
                ":: Rekap Tagihan BPJS (Format Excel) ::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11),
                new java.awt.Color(50, 50, 50)));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame1.setVisible(true);

        tbRekap.setAutoCreateRowSorter(true);
        Scroll.setViewportView(tbRekap);
        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal :");
        label11.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass5.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(Tgl1);

        label12.setText("s.d.");
        label12.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass5.add(label12);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(Tgl2);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setText("Tampilkan");
        BtnCari.setPreferredSize(new java.awt.Dimension(100, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampil();
            }
        });
        panelGlass5.add(BtnCari);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png")));
        BtnPrint.setMnemonic('6');
        BtnPrint.setToolTipText("Alt+6");
        BtnPrint.setText("Export Excel");
        BtnPrint.setPreferredSize(new java.awt.Dimension(120, 23));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (tabMode.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Maaf, data sudah habis. Tidak ada data yang bisa diexport...!!!!");
                    BtnCari.requestFocus();
                } else {
                    try {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Simpan File Excel (CSV)");
                        fileChooser.setSelectedFile(new File("RekapBPJS.csv"));
                        int userSelection = fileChooser.showSaveDialog(null);

                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File fileToSave = fileChooser.getSelectedFile();
                            String filePath = fileToSave.getAbsolutePath();
                            if (!filePath.endsWith(".csv")) {
                                filePath += ".csv";
                            }

                            FileWriter fw = new FileWriter(filePath);
                            BufferedWriter bw = new BufferedWriter(fw);

                            for (int i = 0; i < tbRekap.getColumnCount(); i++) {
                                bw.write(tbRekap.getColumnName(i));
                                if (i < tbRekap.getColumnCount() - 1)
                                    bw.write(",");
                            }
                            bw.newLine();

                            for (int i = 0; i < tbRekap.getRowCount(); i++) {
                                for (int j = 0; j < tbRekap.getColumnCount(); j++) {
                                    String val = tbRekap.getValueAt(i, j).toString();
                                    val = val.replace(",", " ");
                                    bw.write(val);
                                    if (j < tbRekap.getColumnCount() - 1)
                                        bw.write(",");
                                }
                                bw.newLine();
                            }

                            bw.close();
                            fw.close();
                            JOptionPane.showMessageDialog(null, "Data berhasil diexport ke: " + filePath);
                        }
                    } catch (Exception e) {
                        System.out.println("Error Export: " + e);
                    }
                }
            }
        });
        panelGlass5.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setText("Keluar");
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
            }
        });
        panelGlass5.add(BtnKeluar);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }

    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private javax.swing.JScrollPane Scroll;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private javax.swing.JInternalFrame internalFrame1;
    private javax.swing.JLabel label11;
    private javax.swing.JLabel label12;
    private widget.panelisi panelGlass5;
    private javax.swing.JTable tbRekap;
}