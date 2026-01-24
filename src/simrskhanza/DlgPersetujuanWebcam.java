package simrskhanza; // GANTI JIKA NAMA PAKET ANDA BERBEDA

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import fungsi.koneksiDB;
import fungsi.sekuel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog; 
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

// === IMPORT BARU (VERSI 3) ===
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.StringPart;

// === HAPUS SEMUA IMPORT org.apache.http.* ===

public class DlgPersetujuanWebcam extends JDialog {

    private Webcam webcam;
    private WebcamPanel panelWebcam;
    private JLabel lblPreview;
    private BufferedImage capturedImage = null;
    private sekuel Sequel;
    private Connection koneksi;
    private PreparedStatement ps;
    private ResultSet rs;

    private String noSurat;
    private boolean isSaved = false;

    // Variabel UI
    private JLabel lblNamaPasien = new JLabel();
    private JLabel lblNoRM = new JLabel();
    private JLabel lblJkPasien = new JLabel();
    private JLabel lblTglLahirPasien = new JLabel();
    private JLabel lblAlamatPasien = new JLabel();
    private JLabel lblNamaPJ = new JLabel();
    private JLabel lblNoKTPPJ = new JLabel();
    private JLabel lblJkPJ = new JLabel();
    private JLabel lblTelpPJ = new JLabel();
    private JLabel lblAlamatPJ = new JLabel();
    private JLabel lblHubungan = new JLabel();
    private JLabel lblTanggal = new JLabel();
    private JSplitPane splitPane;
    private JPanel panelData;

    /**
     * Creates new form DlgPersetujuanWebcam
     */
    public DlgPersetujuanWebcam(java.awt.Frame parent, boolean modal, sekuel sequel, Connection conn, String noSurat, String noRawat) {
        super(parent, modal);
        this.Sequel = sequel;
        this.koneksi = conn;
        this.noSurat = noSurat;

        setTitle("Persetujuan Umum & Ambil Foto (No: " + noSurat + ")");
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        fetchData(noRawat, noSurat);
        initComponentsInternal();

        try {
            webcam = Webcam.getDefault();
            webcam.setViewSize(WebcamResolution.VGA.getSize());
            panelWebcam = new WebcamPanel(webcam);
            panelWebcam.setMirrored(true);
            splitPane.setLeftComponent(panelWebcam);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tidak ada webcam yang ditemukan!", "Error Webcam", JOptionPane.ERROR_MESSAGE);
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (webcam != null) {
                    webcam.close();
                }
                dispose();
            }
        });

        pack();
    }

    private void fetchData(String noRawat, String noSurat) {
        // 1. Ambil Data Pasien
        try {
            ps = koneksi.prepareStatement(
                "select reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, if(pasien.jk='L','LAKI-LAKI','PEREMPUAN') as jk, " +
                "DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as tgl_lahir, " +
                "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat " +
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel " +
                "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec " +
                "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab " +
                "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, noRawat);
                rs = ps.executeQuery();
                if (rs.next()) {
                    lblNamaPasien.setText(": " + rs.getString("nm_pasien"));
                    lblNoRM.setText(": " + rs.getString("no_rkm_medis"));
                    lblJkPasien.setText(": " + rs.getString("jk"));
                    lblTglLahirPasien.setText(": " + rs.getString("tgl_lahir"));
                    lblAlamatPasien.setText(": " + rs.getString("alamat"));
                }
            } finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notif Pasien: " + e);
        }

        // 2. Ambil Data PJ
        try {
            ps = koneksi.prepareStatement(
                "select DATE_FORMAT(tanggal,'%d-%m-%Y') as tanggal, nama_pj, no_ktppj, " +
                "if(jkpj='L','LAKI-LAKI','PEREMPUAN') as jkpj, no_telp, bertindak_atas " +
                "from surat_persetujuan_umum where no_surat=?");
            try {
                ps.setString(1, noSurat);
                rs = ps.executeQuery();
                if (rs.next()) {
                    lblTanggal.setText("Tanggal: " + rs.getString("tanggal"));
                    lblNamaPJ.setText(": " + rs.getString("nama_pj"));
                    lblNoKTPPJ.setText(": " + rs.getString("no_ktppj"));
                    lblJkPJ.setText(": " + rs.getString("jkpj"));
                    lblTelpPJ.setText(": " + rs.getString("no_telp"));
                    lblHubungan.setText(": " + rs.getString("bertindak_atas"));
                    lblAlamatPJ.setText(": -"); 
                }
            } finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notif PJ: " + e);
        }
    }
    
    private void initComponentsInternal() {
        panelData = new JPanel(new BorderLayout(5, 5));
        panelData.setBorder(BorderFactory.createTitledBorder("Data Persetujuan"));

        JPanel panelInfoPJ = new JPanel(new GridLayout(0, 4, 2, 2));
        panelInfoPJ.add(new JLabel("Nama PJ"));
        panelInfoPJ.add(lblNamaPJ);
        panelInfoPJ.add(new JLabel("No. KTP PJ"));
        panelInfoPJ.add(lblNoKTPPJ);
        panelInfoPJ.add(new JLabel("Jenis Kelamin PJ"));
        panelInfoPJ.add(lblJkPJ);
        panelInfoPJ.add(new JLabel("No. Telp PJ"));
        panelInfoPJ.add(lblTelpPJ);
        panelInfoPJ.add(new JLabel("Alamat PJ"));
        panelInfoPJ.add(lblAlamatPJ);
        panelInfoPJ.add(new JLabel("Hubungan Dgn Pasien"));
        panelInfoPJ.add(lblHubungan);

        JPanel panelInfoPasien = new JPanel(new GridLayout(0, 4, 2, 2));
        panelInfoPasien.setBorder(BorderFactory.createTitledBorder("Data Pasien"));
        panelInfoPasien.add(new JLabel("Nama Pasien"));
        panelInfoPasien.add(lblNamaPasien);
        panelInfoPasien.add(new JLabel("No. R.M."));
        panelInfoPasien.add(lblNoRM);
        panelInfoPasien.add(new JLabel("Jenis Kelamin"));
        panelInfoPasien.add(lblJkPasien);
        panelInfoPasien.add(new JLabel("Tgl. Lahir"));
        panelInfoPasien.add(lblTglLahirPasien);
        panelInfoPasien.add(new JLabel("Alamat Pasien"));
        panelInfoPasien.add(lblAlamatPasien);

        lblTanggal.setHorizontalAlignment(SwingConstants.CENTER);
        panelData.add(lblTanggal, BorderLayout.NORTH);
        panelData.add(panelInfoPJ, BorderLayout.CENTER);
        panelData.add(panelInfoPasien, BorderLayout.SOUTH);

        String naskahHtml = "<html><body style='font-family:Tahoma; font-size:10pt;'>"
            + "<h4 align='center'>PERSETUJUAN UMUM</h4>"
            + "<p>Saya yang membuat pernyataan di bawah ini, menyatakan bahwa:</p>"
            + "<p><i>(Data Penanggung Jawab dan Pasien seperti tertera di atas)</i></p>"
            + "<p>Menyatakan bahwa benar pasien tidak memiliki jaminan <b>ASURANSI/BPJS/TC/PT</b>, "
            + "oleh karena itu saya bersedia bertanggung jawab dengan kewajiban administrasi rumah sakit sebagai "
            + "<b>PASIEN UMUM (CASH)</b> dari awal sampai selesai perawatan.</p>"
            + "<p>Saya sudah diedukasi oleh pihak ADMINISTRASI dan sudah mengerti, memahami, serta "
            + "menyetujui bahwa pasien dirawat dengan pembayaran <b>UMUM/CASH</b> atas permintaan sendiri "
            + "dan tanpa paksaan dari pihak manapun dan tidak akan menuntut/menggugat pernyataaan ini "
            + "dikemudian hari untuk alasan apapun.</p>"
            + "<p>Demikian surat ini saya buat dengan sebenar-benarnya agar dapat dipergunakan untuk tujuan "
            + "diatas. Atas perhatiannya saya ucapkan terima kasih.</p>"
            + "</html>";
        
        JEditorPane editorNaskah = new JEditorPane("text/html", naskahHtml);
        editorNaskah.setEditable(false);
        JScrollPane scrollNaskah = new JScrollPane(editorNaskah);
        scrollNaskah.setPreferredSize(new Dimension(WIDTH, 200));

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5);
        splitPane.setPreferredSize(new Dimension(640, 320));

        lblPreview = new JLabel();
        lblPreview.setHorizontalAlignment(SwingConstants.CENTER);
        lblPreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblPreview.setText("Hasil Foto");
        splitPane.setRightComponent(lblPreview);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JButton btnAmbil = new JButton("Ambil Foto Pernyataan");
        JButton btnSimpan = new JButton("Simpan Persetujuan");
        
        btnAmbil.addActionListener(e -> ambilFoto());
        btnSimpan.addActionListener(e -> simpanGambar());

        panelTombol.add(btnAmbil);
        panelTombol.add(btnSimpan);

        getContentPane().setLayout(new BorderLayout(5, 5));
        getContentPane().add(panelData, BorderLayout.NORTH);
        getContentPane().add(scrollNaskah, BorderLayout.CENTER);
        
        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.add(splitPane, BorderLayout.CENTER);
        panelBawah.add(panelTombol, BorderLayout.SOUTH);

        getContentPane().add(panelBawah, BorderLayout.SOUTH);
    }
    
    private void ambilFoto() {
        if (webcam == null || !webcam.isOpen()) {
            JOptionPane.showMessageDialog(this, "Webcam belum siap atau tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        capturedImage = webcam.getImage();
        Image scaledImage = capturedImage.getScaledInstance(lblPreview.getWidth(), lblPreview.getHeight(), Image.SCALE_SMOOTH);
        lblPreview.setIcon(new ImageIcon(scaledImage));
        lblPreview.setText("");
    }

    // === METODE UPLOAD KE SERVER (VERSI 3) ===
    private boolean uploadKeServer(File file, String noSurat) {
        String urlUpload = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + 
                           koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + 
                           "/upload_persetujuan.php";
        
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(urlUpload);
        
        try {
            // Buat bagian-bagian dari form multipart
            Part[] parts = {
                new StringPart("noSurat", noSurat),
                new FilePart("file", file) // "file" harus cocok dengan $_FILES["file"] di PHP
            };
    
            // Atur entity request menggunakan parts
            post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
    
            // Jalankan metode
            int statusCode = client.executeMethod(post);
            
            // Baca respons (untuk debugging)
            String responseString = post.getResponseBodyAsString();
            System.out.println("Respons Upload Server (v3): " + responseString);

            // Cek jika sukses (status code 200)
            return statusCode == HttpStatus.SC_OK; // SC_OK adalah 200

        } catch (Exception e) {
            System.out.println("Gagal upload ke server (v3): " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            // v3 HARUS me-release koneksi secara manual
            post.releaseConnection(); 
        }
    }

    private void simpanGambar() {
        if (capturedImage == null) {
            JOptionPane.showMessageDialog(this, "Silakan ambil foto terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tentukan path lokal (sesuai permintaan Anda)
        String localFolderPath = "pernyataanumum" + File.separator + "pages" + File.separator + "upload";
        String fileName = this.noSurat + ".jpeg";
        
        File folder = new File(localFolderPath);
        if (!folder.exists()) {
            folder.mkdirs(); // Buat folder jika belum ada
        }
        
        File file = new File(folder.getAbsolutePath() + File.separator + fileName);

        // Hapus file lokal lama
        if (file.exists()) {
            file.delete();
        }

        try {
            // --- LANGKAH 1: SIMPAN LOKAL ---
            ImageIO.write(capturedImage, "jpeg", file);
            
            // --- LANGKAH 2: UPLOAD KE SERVER (Sekarang menggunakan metode v3) ---
            boolean uploadSukses = uploadKeServer(file, this.noSurat);
            
            if (!uploadSukses) {
                JOptionPane.showMessageDialog(this, 
                    "Peringatan: Gagal meng-upload file ke server.\n" +
                    "File hanya tersimpan di komputer lokal.", 
                    "Peringatan Upload", JOptionPane.WARNING_MESSAGE);
            }

            // --- LANGKAH 3: SIMPAN KE DATABASE ---
            Sequel.queryu("delete from surat_persetujuan_umum_pembuat_pernyataan where no_surat='" + this.noSurat + "'");

            if (Sequel.menyimpantf("surat_persetujuan_umum_pembuat_pernyataan", "?,?", "Photo", 2, new String[]{
                this.noSurat, fileName // Simpan nama filenya saja
            }) == true) {
                isSaved = true;
                JOptionPane.showMessageDialog(this, "Foto persetujuan berhasil disimpan (Lokal & Server)!");
                
                if (webcam != null) {
                    webcam.close();
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data foto ke database!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan file gambar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public boolean isSaved() {
        return isSaved;
    }
}