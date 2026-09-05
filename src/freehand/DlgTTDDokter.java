package freehand;

import fungsi.koneksiDB;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Dialog Tanda Tangan Digital Dokter
 * Fitur: Freehand Drawing dengan smooth stroke, optimasi pen tablet
 * Nama file: TTD_<noRawat>.jpg agar mudah dicari via noRawat
 */
public class DlgTTDDokter extends JDialog {
    private SignaturePanel signaturePanel;
    private widget.Button BtnSimpan, BtnClear, BtnKeluar;
    private String namaFileTersimpan = "";
    private String noRawat = "";

    // Konfigurasi stroke
    private static final float STROKE_WIDTH = 2.5f;
    private static final int MIN_POINT_DISTANCE = 2; // Filter noise pen tablet (px)
    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 350;

    public DlgTTDDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initCanvasLogic();
    }

    public void setNoRawat(String noRawat) {
        this.noRawat = noRawat;
    }

    public String getNamaFile() {
        return namaFileTersimpan;
    }

    private void initComponents() {
        setLayout(new java.awt.BorderLayout());
        setSize(400, 450);
        setLocationRelativeTo(null);
        setTitle("Tanda Tangan Digital Dokter");
        setResizable(false);

        signaturePanel = new SignaturePanel();
        add(signaturePanel, java.awt.BorderLayout.CENTER);

        javax.swing.JPanel PanelTombol = new javax.swing.JPanel();
        PanelTombol.setLayout(new java.awt.FlowLayout());

        BtnSimpan = new widget.Button();
        BtnSimpan.setText("Simpan TTD");
        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png")));
        BtnSimpan.setPreferredSize(new java.awt.Dimension(120, 30));

        BtnClear = new widget.Button();
        BtnClear.setText("Hapus/Ulang");
        BtnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png")));
        BtnClear.setPreferredSize(new java.awt.Dimension(120, 30));

        BtnKeluar = new widget.Button();
        BtnKeluar.setText("Tutup");
        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
        BtnKeluar.setPreferredSize(new java.awt.Dimension(90, 30));

        PanelTombol.add(BtnSimpan);
        PanelTombol.add(BtnClear);
        PanelTombol.add(BtnKeluar);
        add(PanelTombol, java.awt.BorderLayout.SOUTH);

        BtnClear.addActionListener(evt -> clearCanvas());
        BtnKeluar.addActionListener(evt -> dispose());
        BtnSimpan.addActionListener(evt -> simpanTTD());
    }

    private void initCanvasLogic() {
        signaturePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (javax.swing.SwingUtilities.isLeftMouseButton(e)) {
                    signaturePanel.startNewStroke(e.getPoint());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (javax.swing.SwingUtilities.isLeftMouseButton(e)) {
                    signaturePanel.finishStroke();
                }
            }
        });

        signaturePanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (javax.swing.SwingUtilities.isLeftMouseButton(e)) {
                    signaturePanel.addStrokePoint(e.getPoint());
                }
            }
        });
    }

    private void clearCanvas() {
        signaturePanel.clearAll();
    }

    /**
     * Render semua strokes ke BufferedImage untuk disimpan sebagai file.
     */
    private BufferedImage renderToImage() {
        BufferedImage img = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        // Background putih
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        // Render strokes dengan kualitas tinggi
        applyRenderingHints(g2d);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (List<Point> stroke : signaturePanel.getAllStrokes()) {
            drawSmoothStroke(g2d, stroke);
        }
        g2d.dispose();
        return img;
    }

    /**
     * Apply rendering hints untuk anti-aliasing dan kualitas tinggi.
     */
    private static void applyRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    }

    /**
     * Gambar stroke halus menggunakan QuadCurve2D dengan midpoint interpolation.
     * Teknik: untuk setiap 3 titik berurutan, gambar quadratic curve dari
     * midpoint(p0,p1) melalui p1 ke midpoint(p1,p2).
     */
    private static void drawSmoothStroke(Graphics2D g2d, List<Point> points) {
        if (points == null || points.size() < 2) {
            // Satu titik saja - gambar dot
            if (points != null && points.size() == 1) {
                Point p = points.get(0);
                g2d.fillOval(p.x - 1, p.y - 1, 3, 3);
            }
            return;
        }

        if (points.size() == 2) {
            // Dua titik - gambar garis lurus
            Point p0 = points.get(0);
            Point p1 = points.get(1);
            g2d.drawLine(p0.x, p0.y, p1.x, p1.y);
            return;
        }

        // Mulai dari titik pertama ke midpoint pertama
        Point p0 = points.get(0);
        Point p1 = points.get(1);
        g2d.drawLine(p0.x, p0.y, (p0.x + p1.x) / 2, (p0.y + p1.y) / 2);

        // Quadratic curves melalui midpoints
        QuadCurve2D.Float curve = new QuadCurve2D.Float();
        for (int i = 1; i < points.size() - 1; i++) {
            Point prev = points.get(i);
            Point next = points.get(i + 1);
            int midX1 = (points.get(i - 1).x + prev.x) / 2;
            int midY1 = (points.get(i - 1).y + prev.y) / 2;
            int midX2 = (prev.x + next.x) / 2;
            int midY2 = (prev.y + next.y) / 2;
            curve.setCurve(midX1, midY1, prev.x, prev.y, midX2, midY2);
            g2d.draw(curve);
        }

        // Dari midpoint terakhir ke titik terakhir
        Point pLast = points.get(points.size() - 1);
        Point pPrev = points.get(points.size() - 2);
        g2d.drawLine((pPrev.x + pLast.x) / 2, (pPrev.y + pLast.y) / 2, pLast.x, pLast.y);
    }

    // ============================================================
    // Inner class: SignaturePanel - custom JPanel untuk kanvas TTD
    // ============================================================
    private class SignaturePanel extends JPanel {
        private final List<List<Point>> allStrokes = new ArrayList<>();
        private List<Point> currentStroke = null;

        public SignaturePanel() {
            setBackground(Color.WHITE);
            setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            applyRenderingHints(g2d);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // Gambar semua stroke yang sudah selesai
            for (List<Point> stroke : allStrokes) {
                drawSmoothStroke(g2d, stroke);
            }

            // Gambar stroke yang sedang aktif
            if (currentStroke != null && currentStroke.size() > 0) {
                drawSmoothStroke(g2d, currentStroke);
            }
        }

        public void startNewStroke(Point p) {
            currentStroke = new ArrayList<>();
            currentStroke.add(p);
        }

        public void addStrokePoint(Point p) {
            if (currentStroke != null) {
                // Filter noise pen tablet: abaikan titik yang terlalu dekat
                Point last = currentStroke.get(currentStroke.size() - 1);
                double dist = Math.sqrt(Math.pow(p.x - last.x, 2) + Math.pow(p.y - last.y, 2));
                if (dist >= MIN_POINT_DISTANCE) {
                    currentStroke.add(p);
                    repaint();
                }
            }
        }

        public void finishStroke() {
            if (currentStroke != null && currentStroke.size() > 0) {
                allStrokes.add(currentStroke);
                currentStroke = null;
            }
        }

        public void clearAll() {
            allStrokes.clear();
            currentStroke = null;
            repaint();
        }

        public List<List<Point>> getAllStrokes() {
            return allStrokes;
        }
    }

    /**
     * Simpan tanda tangan ke server dan database.
     * Mengikuti pola DlgMarkingImageAssMedisIGD:
     * 1. Simpan ke folder lokal tmpImageFreehand
     * 2. Upload ke server (folder imagefreehand root, tanpa subfolder)
     * 3. Simpan ke database ttd_dokter_ralan
     * 4. Return nama file untuk disimpan ke database
     */
    private void simpanTTD() {
        try {
            String safeNoRawat = noRawat.replaceAll("/", "");
            if (safeNoRawat.equals(""))
                safeNoRawat = "Unknown";

            // Nama file sederhana berdasarkan noRawat saja
            String fileName = "TTD_" + safeNoRawat + ".jpg";

            // 1. Simpan ke folder lokal tmpImageFreehand dulu
            File localDir = new File("tmpImageFreehand");
            if (!localDir.exists()) {
                localDir.mkdirs();
            }
            File file = new File("tmpImageFreehand/" + fileName);
            BufferedImage renderedImage = renderToImage();
            ImageIO.write(renderedImage, "jpg", file);
            System.out.println("TTD saved locally: " + file.getAbsolutePath());

            // 2. Upload ke server (ke root imagefreehand, tanpa subfolder)
            // Menggunakan doc="." agar tersimpan langsung di folder imagefreehand
            boolean uploadSuccess = uploadImage(fileName);

            if (uploadSuccess) {
                // 3. Simpan ke database
                boolean dbSuccess = saveToDatabase(fileName);

                if (dbSuccess) {
                    // 4. Return nama file saja (tanpa path subfolder)
                    namaFileTersimpan = fileName;
                    System.out.println("TTD uploaded and saved to DB: " + namaFileTersimpan);

                    // Hapus file lokal
                    deleteLocalFile(fileName);

                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menyimpan tanda tangan ke database");
                    namaFileTersimpan = "";
                }
            } else {
                JOptionPane.showMessageDialog(this, "Gagal upload tanda tangan ke server");
                namaFileTersimpan = "";
            }

        } catch (Exception e) {
            System.out.println("Gagal simpan TTD: " + e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage());
            namaFileTersimpan = "";
        }
    }

    /**
     * Simpan data tanda tangan ke database ttd_dokter_ralan
     * Harus menggunakan tgl_perawatan dan jam_rawat yang ada di pemeriksaan_ralan
     * (FK constraint)
     */
    private boolean saveToDatabase(String fileName) {
        try {
            java.sql.Connection conn = koneksiDB.condb();

            boolean isRanap = false;
            
            // Ambil tgl_perawatan dan jam_rawat dari pemeriksaan_ralan (untuk FK
            // constraint)
            String getSql = "SELECT tgl_perawatan, jam_rawat, nip FROM pemeriksaan_ralan WHERE no_rawat=? ORDER BY tgl_perawatan DESC, jam_rawat DESC LIMIT 1";
            java.sql.PreparedStatement getPs = conn.prepareStatement(getSql);
            getPs.setString(1, noRawat);
            java.sql.ResultSet getRs = getPs.executeQuery();

            String tglPerawatan = "";
            String jamRawat = "";
            String nip = "";

            if (!getRs.next()) {
                getRs.close();
                getPs.close();
                
                // Coba cek di pemeriksaan_ranap
                getSql = "SELECT tgl_perawatan, jam_rawat, nip FROM pemeriksaan_ranap WHERE no_rawat=? ORDER BY tgl_perawatan DESC, jam_rawat DESC LIMIT 1";
                getPs = conn.prepareStatement(getSql);
                getPs.setString(1, noRawat);
                getRs = getPs.executeQuery();
                
                if (!getRs.next()) {
                    System.out.println("No pemeriksaan_ralan or pemeriksaan_ranap record found for no_rawat: " + noRawat);
                    getRs.close();
                    getPs.close();
                    
                    // Fallback to reg_periksa if no SOAP exists
                    getSql = "SELECT status_lanjut, kd_dokter FROM reg_periksa WHERE no_rawat=?";
                    getPs = conn.prepareStatement(getSql);
                    getPs.setString(1, noRawat);
                    getRs = getPs.executeQuery();
                    if (getRs.next()) {
                        isRanap = getRs.getString("status_lanjut").equals("Ranap");
                        nip = getRs.getString("kd_dokter");
                    }
                    getRs.close();
                    getPs.close();
                    
                    tglPerawatan = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
                    jamRawat = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
                    if (nip == null || nip.trim().isEmpty()) {
                        nip = fungsi.akses.getkode();
                    }
                    
                    // Insert dummy SOAP to satisfy foreign key constraint
                    String insertSoap = isRanap ? 
                        "INSERT INTO pemeriksaan_ranap (no_rawat, tgl_perawatan, jam_rawat, suhu_tubuh, tensi, nadi, respirasi, tinggi, berat, spo2, gcs, kesadaran, keluhan, pemeriksaan, alergi, penilaian, rtl, instruksi, evaluasi, nip) " +
                        " VALUES (?, ?, ?, '-', '-', '-', '-', '-', '-', '-', '-', 'Compos Mentis', '-', '-', '-', '-', '-', '-', '-', ?)" :
                        "INSERT INTO pemeriksaan_ralan (no_rawat, tgl_perawatan, jam_rawat, suhu_tubuh, tensi, nadi, respirasi, tinggi, berat, spo2, gcs, kesadaran, keluhan, pemeriksaan, alergi, lingkar_perut, rtl, penilaian, instruksi, evaluasi, nip) " +
                        " VALUES (?, ?, ?, '-', '-', '-', '-', '-', '-', '-', '-', 'Compos Mentis', '-', '-', '-', '-', '-', '-', '-', '-', ?)";
                    
                    java.sql.PreparedStatement insertSoapPs = conn.prepareStatement(insertSoap);
                    insertSoapPs.setString(1, noRawat);
                    insertSoapPs.setString(2, tglPerawatan);
                    insertSoapPs.setString(3, jamRawat);
                    insertSoapPs.setString(4, nip);
                    insertSoapPs.executeUpdate();
                    insertSoapPs.close();
                } else {
                    isRanap = true;
                    tglPerawatan = getRs.getString("tgl_perawatan");
                    jamRawat = getRs.getString("jam_rawat");
                    nip = getRs.getString("nip");
                    getRs.close();
                    getPs.close();
                }
            } else {
                tglPerawatan = getRs.getString("tgl_perawatan");
                jamRawat = getRs.getString("jam_rawat");
                nip = getRs.getString("nip");
                getRs.close();
                getPs.close();
            }

            String targetTable = isRanap ? "ttd_dokter_ranap" : "ttd_dokter_ralan";

            // Cek apakah sudah ada TTD untuk noRawat + tgl + jam ini
            String cekSql = "SELECT COUNT(*) FROM " + targetTable + " WHERE no_rawat=? AND tgl_perawatan=? AND jam_rawat=?";
            java.sql.PreparedStatement cekPs = conn.prepareStatement(cekSql);
            cekPs.setString(1, noRawat);
            cekPs.setString(2, tglPerawatan);
            cekPs.setString(3, jamRawat);
            java.sql.ResultSet cekRs = cekPs.executeQuery();
            cekRs.next();
            int count = cekRs.getInt(1);
            cekRs.close();
            cekPs.close();

            String sql;
            java.sql.PreparedStatement ps;

            if (count > 0) {
                // Update existing record
                sql = "UPDATE " + targetTable + " SET file_ttd=? WHERE no_rawat=? AND tgl_perawatan=? AND jam_rawat=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, fileName);
                ps.setString(2, noRawat);
                ps.setString(3, tglPerawatan);
                ps.setString(4, jamRawat);
            } else {
                // Insert new record dengan FK yang valid
                sql = "INSERT INTO " + targetTable + " (no_rawat, tgl_perawatan, jam_rawat, kd_dokter, file_ttd) VALUES (?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, noRawat);
                ps.setString(2, tglPerawatan);
                ps.setString(3, jamRawat);
                ps.setString(4, nip);
                ps.setString(5, fileName);
            }

            int result = ps.executeUpdate();
            ps.close();

            System.out.println("TTD saved to database: " + (result > 0 ? "Success" : "Failed"));
            return result > 0;

        } catch (Exception e) {
            System.out.println("Error saving TTD to database: " + e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Upload image ke server menggunakan upload.php yang sudah ada.
     * doc parameter dikosongkan agar file disimpan langsung di imagefreehand/
     */
    private boolean uploadImage(String fileName) {
        try {
            File file = new File("tmpImageFreehand/" + fileName);
            byte[] data = FileUtils.readFileToByteArray(file);

            // Upload ke imagefreehand root - doc dikosongkan
            // Note: jika doc kosong, PHP akan simpan ke "/" yang tidak valid
            // Jadi kita pakai "." sebagai current directory
            String url = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB()
                    + "/" + koneksiDB.HYBRIDWEB() + "/imagefreehand/upload.php?doc=.";
            System.out.println("Uploading TTD to: " + url);

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url);
            ByteArrayBody fileData = new ByteArrayBody(data, fileName);
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", fileData);
            postRequest.setEntity(reqEntity);

            org.apache.http.HttpResponse response = httpClient.execute(postRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Upload TTD response code: " + statusCode);

            return statusCode == 200;

        } catch (Exception e) {
            System.out.println("Upload TTD error: " + e);
            e.printStackTrace();
            return false;
        }
    }

    private void deleteLocalFile(String fileName) {
        try {
            File file = new File("tmpImageFreehand/" + fileName);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            System.out.println("Error deleting temp file: " + e);
        }
    }
}