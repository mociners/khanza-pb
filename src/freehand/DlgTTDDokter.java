package freehand;

import fungsi.koneksiDB;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Dialog Tanda Tangan Digital Dokter
 * Fitur: Freehand Drawing, Save to Server
 * Nama file: TTD_<noRawat>.jpg agar mudah dicari via noRawat
 */
public class DlgTTDDokter extends JDialog {
    private javax.swing.JPanel PanelCanvas;
    private widget.Button BtnSimpan, BtnClear, BtnKeluar;
    private BufferedImage image;
    private Graphics2D g2d;
    private Point lastPoint;
    private String namaFileTersimpan = "";
    private String noRawat = "";

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

        PanelCanvas = new javax.swing.JPanel();
        PanelCanvas.setBackground(Color.WHITE);
        PanelCanvas.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        add(PanelCanvas, java.awt.BorderLayout.CENTER);

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
        image = new BufferedImage(400, 350, BufferedImage.TYPE_INT_RGB);
        g2d = image.createGraphics();

        clearCanvas();

        PanelCanvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                lastPoint = e.getPoint();
            }
        });

        PanelCanvas.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (lastPoint != null) {
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(4));
                    g2d.drawLine(lastPoint.x, lastPoint.y, e.getX(), e.getY());

                    Graphics g = PanelCanvas.getGraphics();
                    g.setColor(Color.BLACK);
                    ((Graphics2D) g).setStroke(new BasicStroke(4));
                    g.drawLine(lastPoint.x, lastPoint.y, e.getX(), e.getY());

                    lastPoint = e.getPoint();
                }
            }
        });
    }

    private void clearCanvas() {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 400, 350);
        PanelCanvas.repaint();
    }

    /**
     * Simpan tanda tangan ke server.
     * Mengikuti pola DlgMarkingImageAssMedisIGD:
     * 1. Simpan ke folder lokal tmpImageFreehand
     * 2. Upload ke server (folder imagefreehand root, tanpa subfolder)
     * 3. Return nama file untuk disimpan ke database
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
            ImageIO.write(image, "jpg", file);
            System.out.println("TTD saved locally: " + file.getAbsolutePath());

            // 2. Upload ke server (ke root imagefreehand, tanpa subfolder)
            // Menggunakan doc="" agar tersimpan langsung di folder imagefreehand
            boolean uploadSuccess = uploadImage(fileName);

            if (uploadSuccess) {
                // 3. Return nama file saja (tanpa path subfolder)
                namaFileTersimpan = fileName;
                System.out.println("TTD uploaded successfully: " + namaFileTersimpan);

                // Hapus file lokal
                deleteLocalFile(fileName);

                dispose();
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