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

public class DlgTTDSaksi2 extends JDialog {
    private SignaturePanel signaturePanel;
    private widget.Button BtnSimpan, BtnClear, BtnKeluar;
    private String namaFileTersimpan = "";
    private String noSurat = "";
    
    private static final float STROKE_WIDTH = 2.5f;
    private static final int MIN_POINT_DISTANCE = 2;
    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 350;

    public DlgTTDSaksi2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initCanvasLogic();
    }

    public void setNoSurat(String noSurat) {
        this.noSurat = noSurat;
    }

    public String getNamaFile() {
        return namaFileTersimpan;
    }

    private void initComponents() {
        setLayout(new java.awt.BorderLayout());
        setSize(400, 450);
        setLocationRelativeTo(null);
        setTitle("Tanda Tangan Saksi 2 (Persetujuan Umum)");
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

    private BufferedImage renderToImage() {
        BufferedImage img = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        applyRenderingHints(g2d);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (List<Point> stroke : signaturePanel.getAllStrokes()) {
            drawSmoothStroke(g2d, stroke);
        }
        g2d.dispose();
        return img;
    }

    private static void applyRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    }

    private static void drawSmoothStroke(Graphics2D g2d, List<Point> points) {
        if (points == null || points.size() < 2) {
            if (points != null && points.size() == 1) {
                Point p = points.get(0);
                g2d.fillOval(p.x - 1, p.y - 1, 3, 3);
            }
            return;
        }

        if (points.size() == 2) {
            Point p0 = points.get(0);
            Point p1 = points.get(1);
            g2d.drawLine(p0.x, p0.y, p1.x, p1.y);
            return;
        }

        Point p0 = points.get(0);
        Point p1 = points.get(1);
        g2d.drawLine(p0.x, p0.y, (p0.x + p1.x) / 2, (p0.y + p1.y) / 2);

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

        Point pLast = points.get(points.size() - 1);
        Point pPrev = points.get(points.size() - 2);
        g2d.drawLine((pPrev.x + pLast.x) / 2, (pPrev.y + pLast.y) / 2, pLast.x, pLast.y);
    }

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

            for (List<Point> stroke : allStrokes) {
                drawSmoothStroke(g2d, stroke);
            }

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

    private void simpanTTD() {
        try {
            String safeNoSurat = noSurat.replaceAll("/", "");
            if (safeNoSurat.equals(""))
                safeNoSurat = "Unknown";

            String fileName = "TTDSAKSI2PU_" + safeNoSurat + ".jpeg";

            String localFolderPath = "pernyataanumum" + File.separator + "pages" + File.separator + "upload";
            File localDir = new File(localFolderPath);
            if (!localDir.exists()) {
                localDir.mkdirs();
            }
            File file = new File(localFolderPath + File.separator + fileName);
            BufferedImage renderedImage = renderToImage();
            ImageIO.write(renderedImage, "jpeg", file);
            System.out.println("TTD saved locally: " + file.getAbsolutePath());

            // --- LANGKAH 2: UPLOAD KE SERVER (Sekarang menggunakan metode v3) ---
            boolean uploadSuccess = uploadKeServer(file, noSurat);
            
            if (!uploadSuccess) {
                JOptionPane.showMessageDialog(this, 
                    "Peringatan: Gagal meng-upload file ke server.\n" +
                    "File hanya tersimpan di komputer lokal.", 
                    "Peringatan Upload", JOptionPane.WARNING_MESSAGE);
            }

            boolean dbSuccess = saveToDatabase(fileName);
            if (dbSuccess) {
                namaFileTersimpan = fileName;
                System.out.println("TTD saved: " + namaFileTersimpan);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan tanda tangan ke database");
                namaFileTersimpan = "";
            }

        } catch (Exception e) {
            System.out.println("Gagal simpan TTD: " + e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage());
            namaFileTersimpan = "";
        }
    }

        private boolean uploadKeServer(File file, String noSurat) {
        String urlUpload = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + 
                           koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + 
                           "/imagefreehand/upload.php?doc=pernyataanumum";
        
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        org.apache.commons.httpclient.methods.PostMethod post = new org.apache.commons.httpclient.methods.PostMethod(urlUpload);
        
        try {
            org.apache.commons.httpclient.methods.multipart.Part[] parts = {
                new org.apache.commons.httpclient.methods.multipart.StringPart("noSurat", noSurat),
                new org.apache.commons.httpclient.methods.multipart.FilePart("file", file)
            };
    
            post.setRequestEntity(new org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity(parts, post.getParams()));
    
            int statusCode = client.executeMethod(post);
            
            return statusCode == org.apache.commons.httpclient.HttpStatus.SC_OK; 

        } catch (Exception e) {
            System.out.println("Gagal upload ke server: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            post.releaseConnection(); 
        }
    }

    private boolean saveToDatabase(String fileName) {
        try {
            java.sql.Connection conn = koneksiDB.condb();
            String sql = "UPDATE surat_persetujuan_umum SET photo_saksi_2=? WHERE no_surat=?";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fileName);
            ps.setString(2, noSurat);
            
            int result = ps.executeUpdate();
            ps.close();

            System.out.println("TTD saved to database surat_persetujuan_umum: " + (result > 0 ? "Success" : "Failed"));
            return result > 0;
        } catch (Exception e) {
            System.out.println("Error saving TTD to database: " + e);
            e.printStackTrace();
            return false;
        }
    }
}
