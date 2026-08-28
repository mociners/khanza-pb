package rekammedis;

import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.validasi;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 * Dialog kustom untuk menambah template laporan operasi baru
 */
public final class DlgTemplateLaporanOperasiCasemix extends javax.swing.JDialog {

    private Connection koneksi = koneksiDB.condb();
    private validasi Valid = new validasi();

    // Components
    private widget.InternalFrame internalFrame1;
    private widget.ScrollPane scrollInput;
    private widget.PanelBiasa FormInput;
    private widget.panelisi panelBawah;

    private widget.Label jLabelNamaTemplate;
    private widget.TextBox TxtNamaTemplate;

    private widget.ComboBox JenisOperasi;
    private widget.ComboBox JenisAnastesi;
    private widget.TextBox DiagnosaPrabedah;
    private widget.TextBox DiagnosaPascabedah;
    private widget.TextBox Tindakan;
    private widget.TextBox LamaPembedahan;
    private widget.TextBox CaraPembiusan;
    private widget.TextBox PosisiPasien;
    private widget.TextArea LaporanOperasi;
    private widget.TextBox Komplikasi;
    private widget.TextBox JumlahPerdarahan;
    private widget.ComboBox KetJaringan;
    private widget.ComboBox Jaringan;
    private widget.TextBox AsalJaringan;
    private widget.ComboBox JenisPembedahan;
    private widget.RadioButton rdImplanYa;
    private widget.RadioButton rdImplanTidak;
    private javax.swing.ButtonGroup buttonGroupImplan;
    private widget.TextBox LokasiImplan;
    private widget.TextBox JenisImplan;
    private widget.TextBox NoRegImplan;
    private widget.ComboBox KlasifikasiOperasi;
    private widget.TextBox KonsultasiIntraOperatif;

    private widget.Button BtnSimpan;
    private widget.Button BtnBatal;

    private String statusSimpan = "Simpan";
    private String nomorTemplate = "";

    public DlgTemplateLaporanOperasiCasemix(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initForm();
        this.setLocation(10, 2);
        setSize(800, 600);
    }

    private void initForm() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(true);

        internalFrame1 = new widget.InternalFrame();
        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)),
                "::[ Tambah Template Laporan Operasi Casemix ]::",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Tahoma", 0, 11),
                new java.awt.Color(50, 50, 50)));
        internalFrame1.setLayout(new BorderLayout());

        scrollInput = new widget.ScrollPane();
        scrollInput.setOpaque(true);

        FormInput = new widget.PanelBiasa();
        FormInput.setLayout(null);
        FormInput.setPreferredSize(new Dimension(860, 680));

        // 1. Nama Template
        jLabelNamaTemplate = new widget.Label();
        jLabelNamaTemplate.setText("Nama Template :");
        jLabelNamaTemplate.setBounds(0, 10, 140, 23);
        FormInput.add(jLabelNamaTemplate);

        TxtNamaTemplate = new widget.TextBox();
        TxtNamaTemplate.setBounds(150, 10, 400, 23);
        TxtNamaTemplate.setDocument(new batasInput((byte) 100).getKata(TxtNamaTemplate));
        FormInput.add(TxtNamaTemplate);

        // Form properties mapped from RMLaporanOperasi
        int y = 40;
        widget.Label label2 = new widget.Label();
        label2.setText("Diagnosa Pre-Operatif :");
        label2.setBounds(0, y, 140, 23);
        FormInput.add(label2);
        DiagnosaPrabedah = new widget.TextBox();
        DiagnosaPrabedah.setBounds(150, y, 630, 23);
        FormInput.add(DiagnosaPrabedah);
        y += 30;

        widget.Label label3 = new widget.Label();
        label3.setText("Diagnosa Pasca-Operatif :");
        label3.setBounds(0, y, 140, 23);
        FormInput.add(label3);
        DiagnosaPascabedah = new widget.TextBox();
        DiagnosaPascabedah.setBounds(150, y, 630, 23);
        FormInput.add(DiagnosaPascabedah);
        y += 30;

        widget.Label label4 = new widget.Label();
        label4.setText("Tindakan/Operasi :");
        label4.setBounds(0, y, 140, 23);
        FormInput.add(label4);
        Tindakan = new widget.TextBox();
        Tindakan.setBounds(150, y, 630, 23);
        FormInput.add(Tindakan);
        y += 30;

        widget.Label label5 = new widget.Label();
        label5.setText("Jenis Operasi :");
        label5.setBounds(0, y, 140, 23);
        FormInput.add(label5);
        JenisOperasi = new widget.ComboBox();
        JenisOperasi
                .setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kecil", "Sedang", "Besar", "Khusus" }));
        JenisOperasi.setBounds(150, y, 130, 23);
        FormInput.add(JenisOperasi);

        widget.Label label6 = new widget.Label();
        label6.setText("Jenis Anestesi :");
        label6.setBounds(290, y, 100, 23);
        FormInput.add(label6);
        JenisAnastesi = new widget.ComboBox();
        JenisAnastesi.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Umum", "Spinal", "Epidural", "BSP*", "CSE*", "Lokal" }));
        JenisAnastesi.setBounds(400, y, 130, 23);
        FormInput.add(JenisAnastesi);

        widget.Label labelKlas = new widget.Label();
        labelKlas.setText("Klasifikasi:");
        labelKlas.setBounds(540, y, 70, 23);
        FormInput.add(labelKlas);
        KlasifikasiOperasi = new widget.ComboBox();
        KlasifikasiOperasi
                .setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Emergency/Cito", "Elektif", "ODS" }));
        KlasifikasiOperasi.setBounds(620, y, 160, 23);
        FormInput.add(KlasifikasiOperasi);
        y += 30;

        widget.Label label7 = new widget.Label();
        label7.setText("Jenis Pembiusan :");
        label7.setBounds(0, y, 140, 23);
        FormInput.add(label7);
        CaraPembiusan = new widget.TextBox();
        CaraPembiusan.setBounds(150, y, 630, 23);
        FormInput.add(CaraPembiusan);
        y += 30;

        widget.Label label8 = new widget.Label();
        label8.setText("Posisi Pasien :");
        label8.setBounds(0, y, 140, 23);
        FormInput.add(label8);
        PosisiPasien = new widget.TextBox();
        PosisiPasien.setBounds(150, y, 630, 23);
        FormInput.add(PosisiPasien);
        y += 30;

        widget.Label label9 = new widget.Label();
        label9.setText("Lama Pembedahan :");
        label9.setBounds(0, y, 140, 23);
        FormInput.add(label9);
        LamaPembedahan = new widget.TextBox();
        LamaPembedahan.setBounds(150, y, 630, 23);
        FormInput.add(LamaPembedahan);
        y += 30;

        widget.Label label10 = new widget.Label();
        label10.setText("Konsul Intra Operatif :");
        label10.setBounds(0, y, 140, 23);
        FormInput.add(label10);
        KonsultasiIntraOperatif = new widget.TextBox();
        KonsultasiIntraOperatif.setBounds(150, y, 630, 23);
        FormInput.add(KonsultasiIntraOperatif);
        y += 30;

        widget.Label label11 = new widget.Label();
        label11.setText("Uraian Pembedahan :");
        label11.setBounds(0, y, 140, 23);
        FormInput.add(label11);
        widget.ScrollPane scrollUraian = new widget.ScrollPane();
        scrollUraian.setBounds(150, y, 630, 150);
        LaporanOperasi = new widget.TextArea();
        scrollUraian.setViewportView(LaporanOperasi);
        FormInput.add(scrollUraian);
        y += 160;

        widget.Label label12 = new widget.Label();
        label12.setText("Komplikasi :");
        label12.setBounds(0, y, 140, 23);
        FormInput.add(label12);
        Komplikasi = new widget.TextBox();
        Komplikasi.setBounds(150, y, 630, 23);
        FormInput.add(Komplikasi);
        y += 30;

        widget.Label label13 = new widget.Label();
        label13.setText("Perdarahan (ml):");
        label13.setBounds(0, y, 140, 23);
        FormInput.add(label13);
        JumlahPerdarahan = new widget.TextBox();
        JumlahPerdarahan.setBounds(150, y, 100, 23);
        FormInput.add(JumlahPerdarahan);

        widget.Label labelBedah = new widget.Label();
        labelBedah.setText("Jenis Pembedahan :");
        labelBedah.setBounds(270, y, 130, 23);
        FormInput.add(labelBedah);
        JenisPembedahan = new widget.ComboBox();
        JenisPembedahan.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Bersih", "Bersih Tercemar", "Tercemar", "Kotor" }));
        JenisPembedahan.setBounds(410, y, 160, 23);
        FormInput.add(JenisPembedahan);
        y += 30;

        widget.Label labelKirim = new widget.Label();
        labelKirim.setText("Dikirim PA/Kultur :");
        labelKirim.setBounds(0, y, 140, 23);
        FormInput.add(labelKirim);
        KetJaringan = new widget.ComboBox();
        KetJaringan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KetJaringan.setBounds(150, y, 80, 23);
        FormInput.add(KetJaringan);
        Jaringan = new widget.ComboBox();
        Jaringan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "PA", "Kultur" }));
        Jaringan.setBounds(240, y, 90, 23); // I'm making it 90px width since "Tidak Ada" is longer than "Kultur" or "PA"
        FormInput.add(Jaringan);

        widget.Label labelAsal = new widget.Label();
        labelAsal.setText("Asal Jaringan :");
        labelAsal.setBounds(335, y, 90, 23);
        FormInput.add(labelAsal);
        AsalJaringan = new widget.TextBox();
        AsalJaringan.setBounds(430, y, 350, 23);
        FormInput.add(AsalJaringan);
        y += 30;

        widget.Label labelImplan = new widget.Label();
        labelImplan.setText("Pemasangan Implan :");
        labelImplan.setBounds(0, y, 140, 23);
        FormInput.add(labelImplan);
        buttonGroupImplan = new javax.swing.ButtonGroup();
        rdImplanYa = new widget.RadioButton();
        rdImplanYa.setText("Ya");
        rdImplanYa.setBounds(150, y, 50, 23);
        buttonGroupImplan.add(rdImplanYa);
        FormInput.add(rdImplanYa);
        rdImplanTidak = new widget.RadioButton();
        rdImplanTidak.setText("Tidak");
        rdImplanTidak.setBounds(200, y, 70, 23);
        rdImplanTidak.setSelected(true);
        buttonGroupImplan.add(rdImplanTidak);
        FormInput.add(rdImplanTidak);

        widget.Label labelLokImp = new widget.Label();
        labelLokImp.setText("Lokasi Implan :");
        labelLokImp.setBounds(280, y, 100, 23);
        FormInput.add(labelLokImp);
        LokasiImplan = new widget.TextBox();
        LokasiImplan.setBounds(390, y, 390, 23);
        FormInput.add(LokasiImplan);
        y += 30;

        widget.Label labelJenImp = new widget.Label();
        labelJenImp.setText("Jenis Implan :");
        labelJenImp.setBounds(0, y, 140, 23);
        FormInput.add(labelJenImp);
        JenisImplan = new widget.TextBox();
        JenisImplan.setBounds(150, y, 250, 23);
        FormInput.add(JenisImplan);

        widget.Label labelNoImp = new widget.Label();
        labelNoImp.setText("No.Registrasi Implan :");
        labelNoImp.setBounds(410, y, 130, 23);
        FormInput.add(labelNoImp);
        NoRegImplan = new widget.TextBox();
        NoRegImplan.setBounds(550, y, 230, 23);
        FormInput.add(NoRegImplan);

        scrollInput.setViewportView(FormInput);
        internalFrame1.add(scrollInput, BorderLayout.CENTER);

        // --- Bottom Panel (Buttons) ---
        panelBawah = new widget.panelisi();
        panelBawah.setPreferredSize(new Dimension(100, 50));
        panelBawah.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 9));

        BtnSimpan = new widget.Button();
        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png")));
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan Template");
        BtnSimpan.setToolTipText("Alt+S Simpan Template Baru");
        BtnSimpan.setPreferredSize(new Dimension(150, 30));
        BtnSimpan.addActionListener(evt -> simpan());
        panelBawah.add(BtnSimpan);

        BtnBatal = new widget.Button();
        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cancel.png")));
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Batal");
        BtnBatal.setToolTipText("Alt+B Tutup Jendela");
        BtnBatal.setPreferredSize(new Dimension(100, 30));
        BtnBatal.addActionListener(evt -> dispose());
        panelBawah.add(BtnBatal);

        internalFrame1.add(panelBawah, BorderLayout.PAGE_END);
        getContentPane().add(internalFrame1, BorderLayout.CENTER);

    }

    public void emptTeks() {
        TxtNamaTemplate.setText("");
        JenisOperasi.setSelectedIndex(0);
        JenisAnastesi.setSelectedIndex(0);
        DiagnosaPrabedah.setText("");
        DiagnosaPascabedah.setText("");
        Tindakan.setText("");
        LamaPembedahan.setText("");
        CaraPembiusan.setText("");
        PosisiPasien.setText("");
        LaporanOperasi.setText("");
        Komplikasi.setText("");
        JumlahPerdarahan.setText("");
        KetJaringan.setSelectedIndex(0);
        Jaringan.setSelectedIndex(0);
        AsalJaringan.setText("");
        JenisPembedahan.setSelectedIndex(0);
        rdImplanTidak.setSelected(true);
        LokasiImplan.setText("");
        JenisImplan.setText("");
        NoRegImplan.setText("");
        KlasifikasiOperasi.setSelectedIndex(0);
        KonsultasiIntraOperatif.setText("");
        TxtNamaTemplate.requestFocus();
        statusSimpan = "Simpan";
        nomorTemplate = "";
        BtnSimpan.setText("Simpan Template");
    }

    public void setTemplateData(String noTpl, String namaTpl, String jnsOp, String jnsAnes,
            String diagPrabedah, String diagPascabedah, String tindakan, String lamaPem,
            String caraPem, String posisi, String laporan, String kompl, String pdrhn,
            String ketJar, String jar, String asalJar, String jnsPem, String implan,
            String lokImp, String jenImp, String noRegImp, String klasifikasi, String konsul) {

        statusSimpan = "Ubah";
        nomorTemplate = noTpl;
        BtnSimpan.setText("Ubah Template");

        TxtNamaTemplate.setText(namaTpl);
        JenisOperasi.setSelectedItem(jnsOp);
        JenisAnastesi.setSelectedItem(jnsAnes);
        DiagnosaPrabedah.setText(diagPrabedah);
        DiagnosaPascabedah.setText(diagPascabedah);
        Tindakan.setText(tindakan);
        LamaPembedahan.setText(lamaPem);
        CaraPembiusan.setText(caraPem);
        PosisiPasien.setText(posisi);
        LaporanOperasi.setText(laporan);
        Komplikasi.setText(kompl);
        JumlahPerdarahan.setText(pdrhn);
        KetJaringan.setSelectedItem(ketJar);
        Jaringan.setSelectedItem(jar);
        AsalJaringan.setText(asalJar);
        JenisPembedahan.setSelectedItem(jnsPem);
        if (implan.equals("Ya")) {
            rdImplanYa.setSelected(true);
        } else {
            rdImplanTidak.setSelected(true);
        }
        LokasiImplan.setText(lokImp);
        JenisImplan.setText(jenImp);
        NoRegImplan.setText(noRegImp);
        KlasifikasiOperasi.setSelectedItem(klasifikasi);
        KonsultasiIntraOperatif.setText(konsul);
    }

    private void simpan() {
        if (TxtNamaTemplate.getText().trim().equals("")) {
            Valid.textKosong(TxtNamaTemplate, "Nama Template");
            return;
        }

        try {
            if (statusSimpan.equals("Simpan")) {
                String noTemplate = "TMC-00001";
                ResultSet rsMax = koneksi.prepareStatement(
                        "select ifnull(max(convert(right(no_template,5),signed)),0) from template_laporan_operasi_casemix")
                        .executeQuery();
                if (rsMax.next()) {
                    noTemplate = "TMC-" + String.format("%05d", rsMax.getInt(1) + 1);
                }
                rsMax.close();

                PreparedStatement ps = koneksi.prepareStatement(
                        "insert into template_laporan_operasi_casemix values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                ps.setString(1, noTemplate);
                ps.setString(2, TxtNamaTemplate.getText());
                ps.setString(3, JenisOperasi.getSelectedItem().toString());
                ps.setString(4, JenisAnastesi.getSelectedItem().toString());
                ps.setString(5, DiagnosaPrabedah.getText());
                ps.setString(6, DiagnosaPascabedah.getText());
                ps.setString(7, Tindakan.getText());
                ps.setString(8, LamaPembedahan.getText());
                ps.setString(9, CaraPembiusan.getText());
                ps.setString(10, PosisiPasien.getText());
                ps.setString(11, LaporanOperasi.getText());
                ps.setString(12, Komplikasi.getText());
                ps.setString(13, JumlahPerdarahan.getText());
                ps.setString(14, KetJaringan.getSelectedItem().toString());
                ps.setString(15, Jaringan.getSelectedItem().toString());
                ps.setString(16, AsalJaringan.getText());
                ps.setString(17, JenisPembedahan.getSelectedItem().toString());
                ps.setString(18, rdImplanYa.isSelected() ? "Ya" : "Tidak");
                ps.setString(19, LokasiImplan.getText());
                ps.setString(20, JenisImplan.getText());
                ps.setString(21, NoRegImplan.getText());
                ps.setString(22, KlasifikasiOperasi.getSelectedItem().toString());
                ps.setString(23, KonsultasiIntraOperatif.getText());

                ps.executeUpdate();
                ps.close();
                JOptionPane.showMessageDialog(null, "Template berhasil disimpan..");
            } else {
                PreparedStatement ps = koneksi.prepareStatement(
                        "update template_laporan_operasi_casemix set nama_template=?, jenisoperasi=?, jenisanestesi=?, "
                                + "diagnosaprabedah=?, diagnosapascabedah=?, tindakan=?, lamapembedahan=?, pembiusan=?, "
                                + "posisi=?, uraian=?, komplikasi=?, perdarahan=?, dikirim=?, dikirimket=?, asaljaringan=?, "
                                + "jenispembedahan=?, pemasanganimplan=?, lokasiimplan=?, jenisimplan=?, noregimplan=?, "
                                + "klasifikasioperasi=?, konsultasiintraoperatif=? where no_template=?");
                ps.setString(1, TxtNamaTemplate.getText());
                ps.setString(2, JenisOperasi.getSelectedItem().toString());
                ps.setString(3, JenisAnastesi.getSelectedItem().toString());
                ps.setString(4, DiagnosaPrabedah.getText());
                ps.setString(5, DiagnosaPascabedah.getText());
                ps.setString(6, Tindakan.getText());
                ps.setString(7, LamaPembedahan.getText());
                ps.setString(8, CaraPembiusan.getText());
                ps.setString(9, PosisiPasien.getText());
                ps.setString(10, LaporanOperasi.getText());
                ps.setString(11, Komplikasi.getText());
                ps.setString(12, JumlahPerdarahan.getText());
                ps.setString(13, KetJaringan.getSelectedItem().toString());
                ps.setString(14, Jaringan.getSelectedItem().toString());
                ps.setString(15, AsalJaringan.getText());
                ps.setString(16, JenisPembedahan.getSelectedItem().toString());
                ps.setString(17, rdImplanYa.isSelected() ? "Ya" : "Tidak");
                ps.setString(18, LokasiImplan.getText());
                ps.setString(19, JenisImplan.getText());
                ps.setString(20, NoRegImplan.getText());
                ps.setString(21, KlasifikasiOperasi.getSelectedItem().toString());
                ps.setString(22, KonsultasiIntraOperatif.getText());
                ps.setString(23, nomorTemplate);

                ps.executeUpdate();
                ps.close();
                JOptionPane.showMessageDialog(null, "Template berhasil diubah..");
            }
            dispose();

        } catch (Exception e) {
            System.out.println("Notif Simpan Template Laporan Operasi Casemix : " + e);
            JOptionPane.showMessageDialog(null, "Gagal menyimpan: " + e.getMessage());
        }
    }
}
