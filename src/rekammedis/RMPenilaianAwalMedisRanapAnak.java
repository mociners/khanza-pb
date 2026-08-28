/*
 * Kontribusi dari Abdul Wahid, RSUD Cipayung Jakarta Timur
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalMedisRanapAnak extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;
    private String finger="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalMedisRanapAnak(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
       
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Agama","Bahasa","Cacat Fisik","Kode Dokter","Nama Dokter","Tanggal","Anamnesis","Hubungan","Keluhan Utama","Riwayat Penyakit Sekarang","Riwayat Penyakit Dahulu",
            "Riwayat Penyakit Keluarga","Riwayat Penggunakan Obat","Riwayat Alergi","Keadaan Umum","GCS","Kesadaran","TD(mmHg)","Nadi(x/menit)","RR(x/menit)","Suhu","SpO2","BB(Kg)","TB(cm)","Kepala",
            "Mata","Gigi & Mulut","THT","Thoraks","Abdomen","Genital & Anus","Ekstremitas","Kulit","Ket.Pemeriksaan Fisik","Ket.Status Lokalis",
            "Alat Bantu","Ket. Alat Bantu","Prothesa","Ket. Prothesa","ADL","Stts Psikologi","Ket. Psikologi","Hubungan Keluarga","Tinggal Dengan",
            "Ket. Tinggal","Ekonomi","Budaya","Ket. Budaya","Edukasi","Ket. Edukasi","Skor","Skala Humpty Dumpty (ANAK)",
            "Ket. Lapor","Skrining Gizi 1","Nilai 1","Skrining Gizi 2","Nilai 2","Skrining Gizi 3","Nilai 3","Skrining Gizi 4","Nilai 4","Total Skor Gizi",
            "Skala Wajah","N.S. Wajah","Skala Kaki",
            "N.S. Kaki","Skala Aktifitas","N.S. Aktifitas","Skala Menangis","N.S. Menangis","Skala Bersuara","N.S. Bersuara","Skala Nyeri","Tingkat Nyeri",
            "Lokasi","Durasi","Nyeri Hilang","Ket. Hilang Nyeri","Penunjang","Diagnosis/Asesmen","Tatalaksana",
            "Keterbatasn Mobilitas","Perawatan atau Pengobatan Lanjutan","Bantuan Untuk Melakukan Aktifitas Sehari-hari","Terdapat Gangguan Psikologis/Retardasi Mental","Perawatan Diri (Mandi,BAB,BAK)","Perawatan Pemberian Obat","Pemantuan Diet","Perawatan Luka","Latihan Fisik Lanjutan",
            "Edukasi Keluarga utuk perawatan Mandiri dirumah","Bantuan Medis/Perawatan Dirumah (HomeCare)","Bantuan untuk Melakukan Aktifitas fisik","Setelah Dilakukan Intervensi Selama",
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 97; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(55);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(300);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(120);
            }else if(i==16){
                column.setPreferredWidth(90);
            }else if(i==17){
                column.setPreferredWidth(50);
            }else if(i==18){
                column.setPreferredWidth(80);
            }else if(i==19){
                column.setPreferredWidth(60);
            }else if(i==20){
                column.setPreferredWidth(75);
            }else if(i==21){
                column.setPreferredWidth(67);
            }else if(i==22){
                column.setPreferredWidth(90);
            }else if(i==23){
                column.setPreferredWidth(90);
            }else if(i==24){
                column.setPreferredWidth(90);
            }else if(i==25){
                column.setPreferredWidth(90);
            }else if(i==26){
                column.setPreferredWidth(80);
            }else if(i==27){
                column.setPreferredWidth(80);
            }else if(i==28){
                column.setPreferredWidth(80);
            }else if(i==29){
                column.setPreferredWidth(80);
            }else if(i==30){
                column.setPreferredWidth(80);
            }else if(i==31){
                column.setPreferredWidth(80);
            }else if(i==32){
                column.setPreferredWidth(80);
            }else if(i==33){
                column.setPreferredWidth(80);
            }else if(i==34){
                column.setPreferredWidth(80);
            }else if(i==35){
                column.setPreferredWidth(300);
            }else if(i==36){
                column.setPreferredWidth(200);
            }else if(i==37){
                column.setPreferredWidth(50);
            }else if(i==38){
                column.setPreferredWidth(90);
            }else if(i==39){
                column.setPreferredWidth(30);
            }else if(i==40){
                column.setPreferredWidth(90);
            }else if(i==42){
                column.setPreferredWidth(90);
            }else if(i==43){
                column.setPreferredWidth(90);
            }else if(i==44){
                column.setPreferredWidth(90);
            }else if(i==45){
                column.setPreferredWidth(90);
            }else if(i==46){
                column.setPreferredWidth(90);
            }else if(i==47){
                column.setPreferredWidth(90);
            }else if(i==48){
                column.setPreferredWidth(90);
            }else if(i==49){
                column.setPreferredWidth(90);
            }else if(i==50){
                column.setPreferredWidth(90);
            }else if(i==51){
                column.setPreferredWidth(90);
            }else if(i==52){
                column.setPreferredWidth(90);
            }else if(i==53){
                column.setPreferredWidth(90);
            }else if(i==54){
                column.setPreferredWidth(90);
            }else if(i==55){
                column.setPreferredWidth(90);
            }else if(i==56){
                column.setPreferredWidth(90);
            }else if(i==57){
                column.setPreferredWidth(90);
            }else if(i==58){
                column.setPreferredWidth(90);
            }else if(i==59){
                column.setPreferredWidth(90);
            }else if(i==60){
                column.setPreferredWidth(90);
            }else if(i==61){
                column.setPreferredWidth(90);
            }else if(i==62){
                column.setPreferredWidth(90);
            }else if(i==63){
                column.setPreferredWidth(90);
            }else if(i==64){
                column.setPreferredWidth(90);
            }else if(i==65){
                column.setPreferredWidth(90);
            }else if(i==66){
                column.setPreferredWidth(90);
            }else if(i==67){
                column.setPreferredWidth(90);
            }else if(i==68){
                column.setPreferredWidth(90);
            }else if(i==69){
                column.setPreferredWidth(90);
            }else if(i==70){
                column.setPreferredWidth(90);
            }else if(i==71){
                column.setPreferredWidth(90);
            }else if(i==72){
                column.setPreferredWidth(90);
            }else if(i==73){
                column.setPreferredWidth(90);
            }else if(i==74){
                column.setPreferredWidth(90);
            }else if(i==75){
                column.setPreferredWidth(90);
            }else if(i==76){
                column.setPreferredWidth(50);
            }else if(i==77){
                column.setPreferredWidth(300);
            }else if(i==78){
                column.setPreferredWidth(300);
            }else if(i==79){
                column.setPreferredWidth(300);
            }else if(i==80){
                column.setPreferredWidth(50);
            }else if(i==81){
                column.setPreferredWidth(50);
            }else if(i==82){
                column.setPreferredWidth(50);
            }else if(i==83){
                column.setPreferredWidth(50);
            }else if(i==84){
                column.setPreferredWidth(50);
            }else if(i==85){
                column.setPreferredWidth(50);
            }else if(i==86){
                column.setPreferredWidth(300);
            }else if(i==87){
                column.setPreferredWidth(300);
            }else if(i==88){
                column.setPreferredWidth(300);
            }else if(i==89){
                column.setPreferredWidth(50);
            }else if(i==90){
                column.setPreferredWidth(50);
            }else if(i==91){
                column.setPreferredWidth(50);
            }else if(i==92){
                column.setPreferredWidth(50);
            }else if(i==93){
                column.setPreferredWidth(50);
            }else if(i==94){
                column.setPreferredWidth(50);
            }else if(i==95){
                column.setPreferredWidth(50);
            }else if(i==96){
                column.setPreferredWidth(50);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((int)30).getKata(Hubungan));
        KeluhanUtama.setDocument(new batasInput((int)2000).getKata(KeluhanUtama));
        RPS.setDocument(new batasInput((int)2000).getKata(RPS));
        RPK.setDocument(new batasInput((int)2000).getKata(RPK));
        RPD.setDocument(new batasInput((int)1000).getKata(RPD));
        RPO.setDocument(new batasInput((int)1000).getKata(RPO));
        Alergi.setDocument(new batasInput((int)50).getKata(Alergi));
        GCS.setDocument(new batasInput((byte)10).getKata(GCS));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        SPO.setDocument(new batasInput((byte)5).getKata(SPO));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        TB.setDocument(new batasInput((byte)5).getKata(TB));
        KetFisik.setDocument(new batasInput((int)5000).getKata(KetFisik));
        KetLokalis.setDocument(new batasInput((int)3000).getKata(KetLokalis));
        Penunjang.setDocument(new batasInput((int)3000).getKata(Penunjang));
        Diagnosis.setDocument(new batasInput((int)500).getKata(Diagnosis));
        Tatalaksana.setDocument(new batasInput((int)5000).getKata(Tatalaksana));
        //Konsul.setDocument(new batasInput((int)1000).getKata(Konsul));
        KetBantu.setDocument(new batasInput((int)50).getKata(KetBantu));
        KetProthesa.setDocument(new batasInput((int)50).getKata(KetProthesa));
        KetBudaya.setDocument(new batasInput((int)50).getKata(KetBudaya));
        KetPsiko.setDocument(new batasInput((int)70).getKata(KetPsiko));
        KetTinggal.setDocument(new batasInput((int)40).getKata(KetTinggal));
        KetEdukasi.setDocument(new batasInput((int)50).getKata(KetEdukasi));
       // KetLapor.setDocument(new batasInput((int)15).getKata(KetLapor));
        //KetProvokes.setDocument(new batasInput((int)40).getKata(KetProvokes));
        //KetQuality.setDocument(new batasInput((int)50).getKata(KetQuality));
        Lokasi.setDocument(new batasInput((int)50).getKata(Lokasi));
        Durasi.setDocument(new batasInput((int)25).getKata(Durasi));
        KetNyeri.setDocument(new batasInput((int)40).getKata(KetNyeri));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KdDokter.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        addWindowListener(new WindowAdapter() {
        @Override
        public void windowOpened(WindowEvent e) {
        isRawat();
    }
          });
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadHTML = new widget.editorpane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianMedis = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel9 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel13 = new widget.Label();
        TB = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        RR = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel37 = new widget.Label();
        Alergi = new widget.TextBox();
        Anamnesis = new widget.ComboBox();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RPD = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        RPK = new widget.TextArea();
        jLabel32 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RPO = new widget.TextArea();
        scrollPane5 = new widget.ScrollPane();
        KetFisik = new widget.TextArea();
        jLabel28 = new widget.Label();
        GCS = new widget.TextBox();
        jLabel94 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        jLabel33 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel39 = new widget.Label();
        Keadaan = new widget.ComboBox();
        jLabel40 = new widget.Label();
        Kesadaran = new widget.ComboBox();
        jLabel41 = new widget.Label();
        jLabel29 = new widget.Label();
        SPO = new widget.TextBox();
        jLabel35 = new widget.Label();
        Kepala = new widget.ComboBox();
        jLabel44 = new widget.Label();
        Gigi = new widget.ComboBox();
        jLabel45 = new widget.Label();
        THT = new widget.ComboBox();
        jLabel46 = new widget.Label();
        Thoraks = new widget.ComboBox();
        jLabel49 = new widget.Label();
        Abdomen = new widget.ComboBox();
        jLabel50 = new widget.Label();
        Genital = new widget.ComboBox();
        jLabel51 = new widget.Label();
        Ekstremitas = new widget.ComboBox();
        jLabel52 = new widget.Label();
        Kulit = new widget.ComboBox();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        PanelWall = new usu.widget.glass.PanelGlass();
        scrollPane8 = new widget.ScrollPane();
        KetLokalis = new widget.TextArea();
        jLabel79 = new widget.Label();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel100 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        Penunjang = new widget.TextArea();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        Diagnosis = new widget.TextArea();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel102 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        Tatalaksana = new widget.TextArea();
        jLabel103 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel104 = new widget.Label();
        jLabel42 = new widget.Label();
        Mata = new widget.ComboBox();
        jLabel96 = new widget.Label();
        jLabel62 = new widget.Label();
        StatusPsiko = new widget.ComboBox();
        KetPsiko = new widget.TextBox();
        jLabel76 = new widget.Label();
        Bahasa = new widget.TextBox();
        Ekonomi = new widget.ComboBox();
        jLabel61 = new widget.Label();
        KetTinggal = new widget.TextBox();
        TinggalDengan = new widget.ComboBox();
        jLabel60 = new widget.Label();
        HubunganKeluarga = new widget.ComboBox();
        jLabel54 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel95 = new widget.Label();
        jLabel77 = new widget.Label();
        Agama = new widget.TextBox();
        jLabel58 = new widget.Label();
        Edukasi = new widget.ComboBox();
        KetEdukasi = new widget.TextBox();
        KetBudaya = new widget.TextBox();
        StatusBudaya = new widget.ComboBox();
        jLabel63 = new widget.Label();
        jLabel75 = new widget.Label();
        jLabel74 = new widget.Label();
        jLabel43 = new widget.Label();
        jLabel69 = new widget.Label();
        jLabel92 = new widget.Label();
        jLabel53 = new widget.Label();
        PanelWall1 = new usu.widget.glass.PanelGlass();
        Nyeri = new widget.ComboBox();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        Lokasi = new widget.TextBox();
        Durasi = new widget.TextBox();
        jLabel87 = new widget.Label();
        jLabel89 = new widget.Label();
        NyeriHilang = new widget.ComboBox();
        KetNyeri = new widget.TextBox();
        jSeparator18 = new javax.swing.JSeparator();
        jSeparator20 = new javax.swing.JSeparator();
        jSeparator21 = new javax.swing.JSeparator();
        jLabel56 = new widget.Label();
        jLabel55 = new widget.Label();
        jLabel57 = new widget.Label();
        CacatFisik = new widget.TextBox();
        AlatBantu = new widget.ComboBox();
        KetBantu = new widget.TextBox();
        jLabel64 = new widget.Label();
        jLabel78 = new widget.Label();
        Prothesa = new widget.ComboBox();
        KetProthesa = new widget.TextBox();
        ADL = new widget.ComboBox();
        jLabel47 = new widget.Label();
        jLabel48 = new widget.Label();
        jLabel84 = new widget.Label();
        jLabel86 = new widget.Label();
        jLabel93 = new widget.Label();
        jLabel98 = new widget.Label();
        jLabel164 = new widget.Label();
        jLabel165 = new widget.Label();
        jLabel166 = new widget.Label();
        jLabel167 = new widget.Label();
        SkalaAktifitas = new widget.ComboBox();
        SkalaKaki = new widget.ComboBox();
        SkalaWajah = new widget.ComboBox();
        NilaiWajah = new widget.TextBox();
        NilaiKaki = new widget.TextBox();
        NilaiAktifitas = new widget.TextBox();
        jLabel168 = new widget.Label();
        jLabel169 = new widget.Label();
        SkalaBersuara = new widget.ComboBox();
        SkalaMenangis = new widget.ComboBox();
        NilaiMenangis = new widget.TextBox();
        NilaiBersuara = new widget.TextBox();
        SkalaNyeri = new widget.TextBox();
        jLabel170 = new widget.Label();
        SG1 = new widget.ComboBox();
        NilaiGizi1 = new widget.TextBox();
        NilaiGizi2 = new widget.TextBox();
        SG2 = new widget.ComboBox();
        SG3 = new widget.ComboBox();
        NilaiGizi3 = new widget.TextBox();
        NilaiGizi4 = new widget.TextBox();
        SG4 = new widget.ComboBox();
        jLabel162 = new widget.Label();
        TotalNilaiGizi = new widget.TextBox();
        TingkatResiko = new widget.Label();
        resiko = new widget.ComboBox();
        jLabel146 = new widget.Label();
        jLabel147 = new widget.Label();
        jLabel163 = new widget.Label();
        jLabel172 = new widget.Label();
        jLabel173 = new widget.Label();
        jLabel174 = new widget.Label();
        jLabel175 = new widget.Label();
        jLabel178 = new widget.Label();
        jLabel179 = new widget.Label();
        jLabel180 = new widget.Label();
        jLabel181 = new widget.Label();
        jLabel182 = new widget.Label();
        jLabel183 = new widget.Label();
        jLabel184 = new widget.Label();
        jLabel185 = new widget.Label();
        mobilitas = new widget.ComboBox();
        lanjutan = new widget.ComboBox();
        bantuan = new widget.ComboBox();
        psikologis = new widget.ComboBox();
        perdi = new widget.ComboBox();
        perpe = new widget.ComboBox();
        pd = new widget.ComboBox();
        pl = new widget.ComboBox();
        lf = new widget.ComboBox();
        eke = new widget.ComboBox();
        hc = new widget.ComboBox();
        jLabel186 = new widget.Label();
        bu = new widget.ComboBox();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMedis.setText("Laporan Penilaian Medis");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Medis Rawat Inap Bayi/Anak ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnAll);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 2608));
        FormInput.setLayout(null);

        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 40, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(166, 40, 180, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(348, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        jLabel9.setText("Riwayat Penggunaan Obat :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 190, 180, 23);

        Jk.setEditable(false);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(760, 260, 30, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(794, 260, 45, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(842, 260, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(672, 260, 45, 23);

        jLabel15.setText("TB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(638, 260, 30, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(370, 290, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(322, 290, 45, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(278, 290, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(606, 290, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(650, 290, 45, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 290, 127, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(131, 290, 76, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(698, 290, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(210, 290, 50, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText(" cm");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(720, 260, 30, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(527, 290, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(479, 290, 45, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(435, 290, 40, 23);

        jLabel37.setText("Riwayat Alergi :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(440, 190, 150, 23);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(594, 190, 260, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(644, 40, 128, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(KeluhanUtama);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(129, 90, 310, 43);

        jLabel30.setText("Riwayat Penyakit Sekarang :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(440, 90, 150, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RPD.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPD.setColumns(20);
        RPD.setRows(5);
        RPD.setName("RPD"); // NOI18N
        RPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPDKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RPD);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(594, 140, 260, 43);

        jLabel31.setText("Riwayat Penyakit Dahulu :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(440, 140, 150, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        RPK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPK.setColumns(20);
        RPK.setRows(5);
        RPK.setName("RPK"); // NOI18N
        RPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPKKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(RPK);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(184, 140, 255, 42);

        jLabel32.setText("Riwayat Penyakit Keluarga :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 140, 180, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        RPO.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPO.setColumns(20);
        RPO.setRows(5);
        RPO.setName("RPO"); // NOI18N
        RPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(RPO);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(184, 190, 255, 42);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        KetFisik.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetFisik.setColumns(20);
        KetFisik.setRows(12);
        KetFisik.setName("KetFisik"); // NOI18N
        KetFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetFisikKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(KetFisik);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(514, 320, 340, 143);

        jLabel28.setText("GCS(E,V,M) :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(489, 260, 70, 23);

        GCS.setFocusTraversalPolicyProvider(true);
        GCS.setName("GCS"); // NOI18N
        GCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCSKeyPressed(evt);
            }
        });
        FormInput.add(GCS);
        GCS.setBounds(563, 260, 60, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("II. PEMERIKSAAN FISIK");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 240, 180, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel38.setText("Anamnesis :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(570, 40, 70, 23);

        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(774, 40, 80, 23);

        jLabel33.setText("Keluhan Utama :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 90, 125, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        RPS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPS.setColumns(20);
        RPS.setRows(5);
        RPS.setName("RPS"); // NOI18N
        RPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPSKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(RPS);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(594, 90, 260, 43);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 240, 880, 1);

        jLabel39.setText("Kesadaran :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(265, 260, 70, 23);

        Keadaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sehat", "Sakit Ringan", "Sakit Sedang", "Sakit Berat" }));
        Keadaan.setName("Keadaan"); // NOI18N
        Keadaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanKeyPressed(evt);
            }
        });
        FormInput.add(Keadaan);
        Keadaan.setBounds(131, 260, 118, 23);

        jLabel40.setText("Kepala :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 320, 127, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Apatis", "Somnolen", "Sopor", "Koma" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(339, 260, 130, 23);

        jLabel41.setText("Keadaan Umum :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(0, 260, 127, 23);

        jLabel29.setText("SpO2 :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(750, 290, 40, 23);

        SPO.setFocusTraversalPolicyProvider(true);
        SPO.setName("SPO"); // NOI18N
        SPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SPOKeyPressed(evt);
            }
        });
        FormInput.add(SPO);
        SPO.setBounds(794, 290, 45, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("%");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(842, 290, 30, 23);

        Kepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala.setName("Kepala"); // NOI18N
        Kepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaKeyPressed(evt);
            }
        });
        FormInput.add(Kepala);
        Kepala.setBounds(131, 320, 128, 23);

        jLabel44.setText("Gigi & Mulut :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(0, 380, 127, 23);

        Gigi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Gigi.setName("Gigi"); // NOI18N
        Gigi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GigiKeyPressed(evt);
            }
        });
        FormInput.add(Gigi);
        Gigi.setBounds(131, 380, 128, 23);

        jLabel45.setText("THT :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 410, 127, 23);

        THT.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        THT.setName("THT"); // NOI18N
        THT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THTKeyPressed(evt);
            }
        });
        FormInput.add(THT);
        THT.setBounds(131, 410, 128, 23);

        jLabel46.setText("Thoraks :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(0, 440, 127, 23);

        Thoraks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Thoraks.setName("Thoraks"); // NOI18N
        Thoraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ThoraksKeyPressed(evt);
            }
        });
        FormInput.add(Thoraks);
        Thoraks.setBounds(131, 440, 128, 23);

        jLabel49.setText("Abdomen :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(267, 320, 95, 23);

        Abdomen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Abdomen.setName("Abdomen"); // NOI18N
        Abdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbdomenKeyPressed(evt);
            }
        });
        FormInput.add(Abdomen);
        Abdomen.setBounds(366, 320, 128, 23);

        jLabel50.setText("Genital & Anus :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(267, 350, 95, 23);

        Genital.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Genital.setName("Genital"); // NOI18N
        Genital.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GenitalKeyPressed(evt);
            }
        });
        FormInput.add(Genital);
        Genital.setBounds(366, 350, 128, 23);

        jLabel51.setText("Ekstremitas :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(267, 380, 95, 23);

        Ekstremitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Ekstremitas.setName("Ekstremitas"); // NOI18N
        Ekstremitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkstremitasKeyPressed(evt);
            }
        });
        FormInput.add(Ekstremitas);
        Ekstremitas.setBounds(366, 380, 128, 23);

        jLabel52.setText("Kulit :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(267, 410, 95, 23);

        Kulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kulit.setName("Kulit"); // NOI18N
        Kulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KulitKeyPressed(evt);
            }
        });
        FormInput.add(Kulit);
        Kulit.setBounds(366, 410, 128, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 470, 880, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT KESEHATAN");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 180, 23);

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/semua.png"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput.add(PanelWall);
        PanelWall.setBounds(45, 490, 809, 300);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        KetLokalis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetLokalis.setColumns(20);
        KetLokalis.setRows(5);
        KetLokalis.setName("KetLokalis"); // NOI18N
        KetLokalis.setPreferredSize(new java.awt.Dimension(182, 92));
        KetLokalis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetLokalisKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(KetLokalis);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(44, 810, 810, 83);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Keterangan :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(44, 790, 100, 23);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 900, 880, 1);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("III. STATUS LOKALIS");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(10, 470, 180, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        Penunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Penunjang.setColumns(20);
        Penunjang.setRows(5);
        Penunjang.setName("Penunjang"); // NOI18N
        Penunjang.setPreferredSize(new java.awt.Dimension(102, 52));
        Penunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenunjangKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(Penunjang);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(30, 1830, 810, 60);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 980, 880, 4);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("VIII. PEMERIKSAAN PENUNJANG");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 1810, 190, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        Diagnosis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosis.setColumns(20);
        Diagnosis.setRows(3);
        Diagnosis.setName("Diagnosis"); // NOI18N
        Diagnosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(Diagnosis);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(30, 1910, 810, 43);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 1260, 880, 4);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("IX. DIAGNOSIS/ASESMEN");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(10, 1890, 190, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        Tatalaksana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tatalaksana.setColumns(20);
        Tatalaksana.setRows(15);
        Tatalaksana.setName("Tatalaksana"); // NOI18N
        Tatalaksana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TatalaksanaKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Tatalaksana);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(30, 1990, 810, 153);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("X. TATALAKSANA");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(10, 1970, 190, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(380, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2026 16:48:03" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(436, 40, 130, 23);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 1155, 880, 4);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("XI. DISCHARGE PLANNING");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(0, 2160, 190, 23);

        jLabel42.setText("Mata :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 350, 127, 23);

        Mata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Mata.setName("Mata"); // NOI18N
        Mata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MataKeyPressed(evt);
            }
        });
        FormInput.add(Mata);
        Mata.setBounds(131, 350, 128, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("IV. RIWAYAT PSIKO-SOSIAL, SPIRITUAL DAN EDUKASI");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(10, 990, 380, 23);

        jLabel62.setText("Status Psikologis :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(0, 1010, 130, 23);

        StatusPsiko.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tenang", "Takut", "Cemas", "Depresi", "Lain-lain" }));
        StatusPsiko.setName("StatusPsiko"); // NOI18N
        StatusPsiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusPsikoKeyPressed(evt);
            }
        });
        FormInput.add(StatusPsiko);
        StatusPsiko.setBounds(130, 1010, 110, 23);

        KetPsiko.setFocusTraversalPolicyProvider(true);
        KetPsiko.setName("KetPsiko"); // NOI18N
        KetPsiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetPsikoKeyPressed(evt);
            }
        });
        FormInput.add(KetPsiko);
        KetPsiko.setBounds(250, 1010, 230, 23);

        jLabel76.setText("Bahasa yang digunakan sehari-hari :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(450, 1010, 230, 23);

        Bahasa.setEditable(false);
        Bahasa.setFocusTraversalPolicyProvider(true);
        Bahasa.setName("Bahasa"); // NOI18N
        FormInput.add(Bahasa);
        Bahasa.setBounds(680, 1010, 170, 23);

        Ekonomi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Cukup", "Kurang" }));
        Ekonomi.setName("Ekonomi"); // NOI18N
        Ekonomi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkonomiKeyPressed(evt);
            }
        });
        FormInput.add(Ekonomi);
        Ekonomi.setBounds(770, 1060, 84, 23);

        jLabel61.setText("c. Ekonomi :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(690, 1060, 71, 23);

        KetTinggal.setFocusTraversalPolicyProvider(true);
        KetTinggal.setName("KetTinggal"); // NOI18N
        KetTinggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetTinggalKeyPressed(evt);
            }
        });
        FormInput.add(KetTinggal);
        KetTinggal.setBounds(610, 1060, 85, 23);

        TinggalDengan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sendiri", "Orang Tua", "Suami / Istri", "Lainnya" }));
        TinggalDengan.setName("TinggalDengan"); // NOI18N
        TinggalDengan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggalDenganKeyPressed(evt);
            }
        });
        FormInput.add(TinggalDengan);
        TinggalDengan.setBounds(500, 1060, 110, 23);

        jLabel60.setText("b. Tinggal dengan :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(390, 1060, 100, 23);

        HubunganKeluarga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Tidak Baik" }));
        HubunganKeluarga.setName("HubunganKeluarga"); // NOI18N
        HubunganKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(HubunganKeluarga);
        HubunganKeluarga.setBounds(290, 1060, 100, 23);

        jLabel54.setText("a. Hubungan pasien dengan anggota keluarga :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(30, 1060, 250, 23);

        jLabel59.setText("Status Sosial dan ekonomi :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 1040, 176, 23);

        jLabel95.setText("Kepercayaan / Budaya / Nilai-nilai khusus yang perlu diperhatikan :");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(0, 1090, 366, 23);

        jLabel77.setText("Agama :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(10, 1120, 82, 23);

        Agama.setEditable(false);
        Agama.setFocusTraversalPolicyProvider(true);
        Agama.setName("Agama"); // NOI18N
        Agama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgamaActionPerformed(evt);
            }
        });
        FormInput.add(Agama);
        Agama.setBounds(100, 1120, 110, 23);

        jLabel58.setText("Edukasi diberikan kepada :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(230, 1120, 140, 23);

        Edukasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pasien", "Keluarga" }));
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        FormInput.add(Edukasi);
        Edukasi.setBounds(380, 1120, 110, 23);

        KetEdukasi.setFocusTraversalPolicyProvider(true);
        KetEdukasi.setName("KetEdukasi"); // NOI18N
        KetEdukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetEdukasiKeyPressed(evt);
            }
        });
        FormInput.add(KetEdukasi);
        KetEdukasi.setBounds(490, 1120, 370, 23);

        KetBudaya.setFocusTraversalPolicyProvider(true);
        KetBudaya.setName("KetBudaya"); // NOI18N
        KetBudaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetBudayaKeyPressed(evt);
            }
        });
        FormInput.add(KetBudaya);
        KetBudaya.setBounds(480, 1090, 370, 23);

        StatusBudaya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        StatusBudaya.setName("StatusBudaya"); // NOI18N
        StatusBudaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusBudayaKeyPressed(evt);
            }
        });
        FormInput.add(StatusBudaya);
        StatusBudaya.setBounds(370, 1090, 110, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("V. PENILAIAN RESIKO JATUH");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(10, 1170, 380, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("1. Apakah ada penurunan berat badan yang tidak diinginkan selama 6 bulan terakhir ?");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(40, 1300, 460, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("VI. SKRINING GIZI");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(10, 1280, 380, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("bila ada atau untuk bayi < 1 tahun ; berat badan tidak naik selama 3 bulan terakhir)");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(50, 1350, 410, 23);

        jLabel69.setText("Nilai :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(730, 1300, 75, 23);

        jLabel92.setText("Nilai :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(730, 1330, 75, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("VII. PENILAIAN TINGKAT NYERI");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 1510, 380, 23);

        PanelWall1.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall1.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/nyeri.png"))); // NOI18N
        PanelWall1.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall1.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall1.setRound(false);
        PanelWall1.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall1.setLayout(null);
        FormInput.add(PanelWall1);
        PanelWall1.setBounds(30, 1650, 320, 130);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Nyeri", "Nyeri Akut", "Nyeri Kronis" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(360, 1650, 130, 23);

        jLabel82.setText("Wilayah :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(360, 1680, 55, 23);

        jLabel83.setText("Lokasi :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(380, 1700, 60, 23);

        Lokasi.setFocusTraversalPolicyProvider(true);
        Lokasi.setName("Lokasi"); // NOI18N
        Lokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiKeyPressed(evt);
            }
        });
        FormInput.add(Lokasi);
        Lokasi.setBounds(450, 1700, 220, 23);

        Durasi.setFocusTraversalPolicyProvider(true);
        Durasi.setName("Durasi"); // NOI18N
        Durasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DurasiKeyPressed(evt);
            }
        });
        FormInput.add(Durasi);
        Durasi.setBounds(770, 1700, 90, 23);

        jLabel87.setText("Waktu / Durasi :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(680, 1700, 90, 23);

        jLabel89.setText("Nyeri hilang bila :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(350, 1740, 130, 23);

        NyeriHilang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Istirahat", "Medengar Musik", "Minum Obat" }));
        NyeriHilang.setName("NyeriHilang"); // NOI18N
        NyeriHilang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriHilangKeyPressed(evt);
            }
        });
        FormInput.add(NyeriHilang);
        NyeriHilang.setBounds(490, 1740, 130, 23);

        KetNyeri.setFocusTraversalPolicyProvider(true);
        KetNyeri.setName("KetNyeri"); // NOI18N
        KetNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNyeriKeyPressed(evt);
            }
        });
        FormInput.add(KetNyeri);
        KetNyeri.setBounds(630, 1740, 150, 23);

        jSeparator18.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator18.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator18.setName("jSeparator18"); // NOI18N
        FormInput.add(jSeparator18);
        jSeparator18.setBounds(0, 2150, 880, 4);

        jSeparator20.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator20.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator20.setName("jSeparator20"); // NOI18N
        FormInput.add(jSeparator20);
        jSeparator20.setBounds(0, 1800, 880, 4);

        jSeparator21.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator21.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator21.setName("jSeparator21"); // NOI18N
        FormInput.add(jSeparator21);
        jSeparator21.setBounds(0, 1500, 880, 4);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("IV. FUNGSIONAL");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 900, 230, 23);

        jLabel55.setText("Alat Bantu :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(0, 920, 120, 23);

        jLabel57.setText("Cacat Fisik :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(0, 950, 120, 23);

        CacatFisik.setEditable(false);
        CacatFisik.setFocusTraversalPolicyProvider(true);
        CacatFisik.setName("CacatFisik"); // NOI18N
        CacatFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CacatFisikKeyPressed(evt);
            }
        });
        FormInput.add(CacatFisik);
        CacatFisik.setBounds(120, 950, 314, 23);

        AlatBantu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        AlatBantu.setName("AlatBantu"); // NOI18N
        AlatBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatBantuKeyPressed(evt);
            }
        });
        FormInput.add(AlatBantu);
        AlatBantu.setBounds(120, 920, 90, 23);

        KetBantu.setFocusTraversalPolicyProvider(true);
        KetBantu.setName("KetBantu"); // NOI18N
        KetBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetBantuKeyPressed(evt);
            }
        });
        FormInput.add(KetBantu);
        KetBantu.setBounds(220, 920, 220, 23);

        jLabel64.setText("Aktivitas Kehidupan Sehari-hari ( ADL ) :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(440, 950, 280, 23);

        jLabel78.setText("Prothesa :");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(470, 920, 60, 23);

        Prothesa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Prothesa.setName("Prothesa"); // NOI18N
        Prothesa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProthesaKeyPressed(evt);
            }
        });
        FormInput.add(Prothesa);
        Prothesa.setBounds(540, 920, 90, 23);

        KetProthesa.setFocusTraversalPolicyProvider(true);
        KetProthesa.setName("KetProthesa"); // NOI18N
        KetProthesa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetProthesaKeyPressed(evt);
            }
        });
        FormInput.add(KetProthesa);
        KetProthesa.setBounds(630, 920, 220, 23);

        ADL.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Dibantu" }));
        ADL.setName("ADL"); // NOI18N
        ADL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ADLKeyPressed(evt);
            }
        });
        FormInput.add(ADL);
        ADL.setBounds(720, 950, 130, 23);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("Asupan makanan berkurang selama 1 minggu terakhir");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(50, 1400, 600, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("2. Apakah terdapat penurunan berat badan selama satu bulan terakhir? (berdasarkan penilaian objektif data berat badan");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(40, 1330, 600, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("4.  Apakah terdapat penyakit atau keadaan yang menyebabkan pasien beresiko mengalami malnutrisi?");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(40, 1430, 600, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("3. Apakah terdapat salah satu dari kondisi tersebut? Diare > 5 kali/hari dan/muntah > 3 kali/hari dalam seminggu terakhir;");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(40, 1380, 600, 23);

        jLabel93.setText("Nilai :");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(730, 1380, 75, 23);

        jLabel98.setText("Nilai :");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(730, 1430, 75, 23);

        jLabel164.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel164.setText("Skala FLACCS :");
        jLabel164.setName("jLabel164"); // NOI18N
        FormInput.add(jLabel164);
        jLabel164.setBounds(50, 1530, 210, 23);

        jLabel165.setText("Wajah :");
        jLabel165.setName("jLabel165"); // NOI18N
        FormInput.add(jLabel165);
        jLabel165.setBounds(50, 1550, 60, 23);

        jLabel166.setText("Kaki :");
        jLabel166.setName("jLabel166"); // NOI18N
        FormInput.add(jLabel166);
        jLabel166.setBounds(50, 1580, 60, 23);

        jLabel167.setText("Aktifitas :");
        jLabel167.setName("jLabel167"); // NOI18N
        FormInput.add(jLabel167);
        jLabel167.setBounds(50, 1610, 60, 23);

        SkalaAktifitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidur posisi normal, mudah bergerak", "Gerakan menggeliat/berguling, kaku", "Melengkungkan punggung/kaku menghentak" }));
        SkalaAktifitas.setName("SkalaAktifitas"); // NOI18N
        SkalaAktifitas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaAktifitasItemStateChanged(evt);
            }
        });
        SkalaAktifitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaAktifitasKeyPressed(evt);
            }
        });
        FormInput.add(SkalaAktifitas);
        SkalaAktifitas.setBounds(120, 1610, 310, 23);

        SkalaKaki.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Gerakan normal/relaksasi", "Tidak tenang/tegang", "Kaki dibuat menendang/menarik" }));
        SkalaKaki.setName("SkalaKaki"); // NOI18N
        SkalaKaki.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaKakiItemStateChanged(evt);
            }
        });
        SkalaKaki.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaKakiKeyPressed(evt);
            }
        });
        FormInput.add(SkalaKaki);
        SkalaKaki.setBounds(120, 1580, 310, 23);

        SkalaWajah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tersenyum/tidak ada ekspresi khusus", "Terkadang meringis/menarik diri", "Sering menggetarkan dagu dan mengatupkan rahang" }));
        SkalaWajah.setName("SkalaWajah"); // NOI18N
        SkalaWajah.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaWajahItemStateChanged(evt);
            }
        });
        SkalaWajah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaWajahKeyPressed(evt);
            }
        });
        FormInput.add(SkalaWajah);
        SkalaWajah.setBounds(120, 1550, 310, 23);

        NilaiWajah.setEditable(false);
        NilaiWajah.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiWajah.setText("0");
        NilaiWajah.setFocusTraversalPolicyProvider(true);
        NilaiWajah.setName("NilaiWajah"); // NOI18N
        NilaiWajah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiWajahKeyPressed(evt);
            }
        });
        FormInput.add(NilaiWajah);
        NilaiWajah.setBounds(440, 1550, 40, 23);

        NilaiKaki.setEditable(false);
        NilaiKaki.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiKaki.setText("0");
        NilaiKaki.setFocusTraversalPolicyProvider(true);
        NilaiKaki.setName("NilaiKaki"); // NOI18N
        NilaiKaki.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiKakiKeyPressed(evt);
            }
        });
        FormInput.add(NilaiKaki);
        NilaiKaki.setBounds(440, 1580, 40, 23);

        NilaiAktifitas.setEditable(false);
        NilaiAktifitas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiAktifitas.setText("0");
        NilaiAktifitas.setFocusTraversalPolicyProvider(true);
        NilaiAktifitas.setName("NilaiAktifitas"); // NOI18N
        NilaiAktifitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiAktifitasKeyPressed(evt);
            }
        });
        FormInput.add(NilaiAktifitas);
        NilaiAktifitas.setBounds(440, 1610, 40, 23);

        jLabel168.setText("Menangis :");
        jLabel168.setName("jLabel168"); // NOI18N
        FormInput.add(jLabel168);
        jLabel168.setBounds(490, 1550, 60, 23);

        jLabel169.setText("Bersuara :");
        jLabel169.setName("jLabel169"); // NOI18N
        FormInput.add(jLabel169);
        jLabel169.setBounds(490, 1580, 60, 23);

        SkalaBersuara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bersuara normal/tenang", "Tenang bila dipeluk, digendong/diajak bicara", "Sulit untuk menenangkan" }));
        SkalaBersuara.setName("SkalaBersuara"); // NOI18N
        SkalaBersuara.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaBersuaraItemStateChanged(evt);
            }
        });
        SkalaBersuara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaBersuaraKeyPressed(evt);
            }
        });
        FormInput.add(SkalaBersuara);
        SkalaBersuara.setBounds(550, 1580, 266, 23);

        SkalaMenangis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak menangis (mudah bergerak)", "Mengerang/merengek", "Menangis terus menerus, terisak, menjerit" }));
        SkalaMenangis.setName("SkalaMenangis"); // NOI18N
        SkalaMenangis.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaMenangisItemStateChanged(evt);
            }
        });
        SkalaMenangis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaMenangisKeyPressed(evt);
            }
        });
        FormInput.add(SkalaMenangis);
        SkalaMenangis.setBounds(550, 1550, 266, 23);

        NilaiMenangis.setEditable(false);
        NilaiMenangis.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiMenangis.setText("0");
        NilaiMenangis.setFocusTraversalPolicyProvider(true);
        NilaiMenangis.setName("NilaiMenangis"); // NOI18N
        NilaiMenangis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiMenangisKeyPressed(evt);
            }
        });
        FormInput.add(NilaiMenangis);
        NilaiMenangis.setBounds(820, 1550, 40, 23);

        NilaiBersuara.setEditable(false);
        NilaiBersuara.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiBersuara.setText("0");
        NilaiBersuara.setFocusTraversalPolicyProvider(true);
        NilaiBersuara.setName("NilaiBersuara"); // NOI18N
        NilaiBersuara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiBersuaraKeyPressed(evt);
            }
        });
        FormInput.add(NilaiBersuara);
        NilaiBersuara.setBounds(820, 1580, 40, 23);

        SkalaNyeri.setEditable(false);
        SkalaNyeri.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        SkalaNyeri.setText("0");
        SkalaNyeri.setFocusTraversalPolicyProvider(true);
        SkalaNyeri.setName("SkalaNyeri"); // NOI18N
        SkalaNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNyeriKeyPressed(evt);
            }
        });
        FormInput.add(SkalaNyeri);
        SkalaNyeri.setBounds(780, 1610, 80, 23);

        jLabel170.setText("Skala nyeri :");
        jLabel170.setName("jLabel170"); // NOI18N
        FormInput.add(jLabel170);
        jLabel170.setBounds(690, 1610, 90, 23);

        SG1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SG1.setName("SG1"); // NOI18N
        SG1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG1ItemStateChanged(evt);
            }
        });
        SG1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG1KeyPressed(evt);
            }
        });
        FormInput.add(SG1);
        SG1.setBounds(680, 1300, 80, 23);

        NilaiGizi1.setEditable(false);
        NilaiGizi1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiGizi1.setText("0");
        NilaiGizi1.setFocusTraversalPolicyProvider(true);
        NilaiGizi1.setName("NilaiGizi1"); // NOI18N
        NilaiGizi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiGizi1KeyPressed(evt);
            }
        });
        FormInput.add(NilaiGizi1);
        NilaiGizi1.setBounds(810, 1300, 60, 23);

        NilaiGizi2.setEditable(false);
        NilaiGizi2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiGizi2.setText("0");
        NilaiGizi2.setFocusTraversalPolicyProvider(true);
        NilaiGizi2.setName("NilaiGizi2"); // NOI18N
        NilaiGizi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiGizi2KeyPressed(evt);
            }
        });
        FormInput.add(NilaiGizi2);
        NilaiGizi2.setBounds(810, 1330, 60, 23);

        SG2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SG2.setName("SG2"); // NOI18N
        SG2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG2ItemStateChanged(evt);
            }
        });
        SG2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG2KeyPressed(evt);
            }
        });
        FormInput.add(SG2);
        SG2.setBounds(680, 1330, 80, 23);

        SG3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SG3.setName("SG3"); // NOI18N
        SG3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG3ItemStateChanged(evt);
            }
        });
        SG3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG3KeyPressed(evt);
            }
        });
        FormInput.add(SG3);
        SG3.setBounds(680, 1380, 80, 23);

        NilaiGizi3.setEditable(false);
        NilaiGizi3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiGizi3.setText("0");
        NilaiGizi3.setFocusTraversalPolicyProvider(true);
        NilaiGizi3.setName("NilaiGizi3"); // NOI18N
        NilaiGizi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiGizi3KeyPressed(evt);
            }
        });
        FormInput.add(NilaiGizi3);
        NilaiGizi3.setBounds(810, 1380, 60, 23);

        NilaiGizi4.setEditable(false);
        NilaiGizi4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiGizi4.setText("0");
        NilaiGizi4.setFocusTraversalPolicyProvider(true);
        NilaiGizi4.setName("NilaiGizi4"); // NOI18N
        NilaiGizi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiGizi4KeyPressed(evt);
            }
        });
        FormInput.add(NilaiGizi4);
        NilaiGizi4.setBounds(810, 1430, 60, 23);

        SG4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SG4.setName("SG4"); // NOI18N
        SG4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG4ItemStateChanged(evt);
            }
        });
        SG4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG4KeyPressed(evt);
            }
        });
        FormInput.add(SG4);
        SG4.setBounds(680, 1430, 80, 23);

        jLabel162.setText("Total Skor :");
        jLabel162.setName("jLabel162"); // NOI18N
        FormInput.add(jLabel162);
        jLabel162.setBounds(690, 1470, 90, 23);

        TotalNilaiGizi.setEditable(false);
        TotalNilaiGizi.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        TotalNilaiGizi.setText("0");
        TotalNilaiGizi.setFocusTraversalPolicyProvider(true);
        TotalNilaiGizi.setName("TotalNilaiGizi"); // NOI18N
        TotalNilaiGizi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalNilaiGiziKeyPressed(evt);
            }
        });
        FormInput.add(TotalNilaiGizi);
        TotalNilaiGizi.setBounds(790, 1470, 80, 23);

        TingkatResiko.setText("Intervensi : Resiko Rendah");
        TingkatResiko.setName("TingkatResiko"); // NOI18N
        FormInput.add(TingkatResiko);
        TingkatResiko.setBounds(200, 1230, 140, 23);

        resiko.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "7 - 11", ">= 12" }));
        resiko.setName("resiko"); // NOI18N
        resiko.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                resikoItemStateChanged(evt);
            }
        });
        resiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                resikoKeyPressed(evt);
            }
        });
        FormInput.add(resiko);
        resiko.setBounds(110, 1230, 80, 23);

        jLabel146.setText("Skala Humpty Dumpty (ANAK)");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput.add(jLabel146);
        jLabel146.setBounds(20, 1200, 150, 23);

        jLabel147.setText("Skor : ");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(20, 1230, 72, 23);

        jLabel163.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel163.setText("a. Kriteria Discharge Planning :");
        jLabel163.setName("jLabel163"); // NOI18N
        FormInput.add(jLabel163);
        jLabel163.setBounds(20, 2180, 380, 23);

        jLabel172.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel172.setText("1. Keterbatasan Mobilitas");
        jLabel172.setName("jLabel172"); // NOI18N
        FormInput.add(jLabel172);
        jLabel172.setBounds(40, 2210, 140, 23);

        jLabel173.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel173.setText("2. Perawatan atau Pengobatan Lanjutan");
        jLabel173.setName("jLabel173"); // NOI18N
        FormInput.add(jLabel173);
        jLabel173.setBounds(40, 2240, 300, 23);

        jLabel174.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel174.setText("3. Bantuan untuk Melakukan Aktifitas sehari-hari");
        jLabel174.setName("jLabel174"); // NOI18N
        FormInput.add(jLabel174);
        jLabel174.setBounds(40, 2270, 320, 23);

        jLabel175.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel175.setText("4. Terdapat Gangguan Psikologis/Retardasi Mental ");
        jLabel175.setName("jLabel175"); // NOI18N
        FormInput.add(jLabel175);
        jLabel175.setBounds(40, 2300, 300, 23);

        jLabel178.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel178.setText("b. Bila Salah Satu jawaban YA dari Kriteria di atas, maka akan diajukan dengan perencanaan sbb :");
        jLabel178.setName("jLabel178"); // NOI18N
        FormInput.add(jLabel178);
        jLabel178.setBounds(20, 2330, 480, 23);

        jLabel179.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel179.setText("Perawatan Diri (Mandi,BAB,BAK) :");
        jLabel179.setName("jLabel179"); // NOI18N
        FormInput.add(jLabel179);
        jLabel179.setBounds(40, 2360, 280, 23);

        jLabel180.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel180.setText("Perawatan Pemberian Obat :");
        jLabel180.setName("jLabel180"); // NOI18N
        FormInput.add(jLabel180);
        jLabel180.setBounds(40, 2390, 260, 23);

        jLabel181.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel181.setText("Pemantauan Diet :");
        jLabel181.setName("jLabel181"); // NOI18N
        FormInput.add(jLabel181);
        jLabel181.setBounds(40, 2420, 260, 23);

        jLabel182.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel182.setText("Perawatan Luka :");
        jLabel182.setName("jLabel182"); // NOI18N
        FormInput.add(jLabel182);
        jLabel182.setBounds(40, 2450, 160, 23);

        jLabel183.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel183.setText("Latihan Fisik Lanjutan :");
        jLabel183.setName("jLabel183"); // NOI18N
        FormInput.add(jLabel183);
        jLabel183.setBounds(40, 2480, 280, 23);

        jLabel184.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel184.setText("Edukasi Keluarga untuk Perawatan Mandiri diRumah :");
        jLabel184.setName("jLabel184"); // NOI18N
        FormInput.add(jLabel184);
        jLabel184.setBounds(40, 2510, 280, 23);

        jLabel185.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel185.setText("Bantuan Medis/Perawatan diRumah (HomeCare) :");
        jLabel185.setName("jLabel185"); // NOI18N
        FormInput.add(jLabel185);
        jLabel185.setBounds(40, 2540, 280, 23);

        mobilitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        mobilitas.setName("mobilitas"); // NOI18N
        mobilitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mobilitasKeyPressed(evt);
            }
        });
        FormInput.add(mobilitas);
        mobilitas.setBounds(340, 2210, 80, 20);

        lanjutan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        lanjutan.setName("lanjutan"); // NOI18N
        lanjutan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lanjutanKeyPressed(evt);
            }
        });
        FormInput.add(lanjutan);
        lanjutan.setBounds(340, 2240, 80, 20);

        bantuan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        bantuan.setName("bantuan"); // NOI18N
        bantuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bantuanKeyPressed(evt);
            }
        });
        FormInput.add(bantuan);
        bantuan.setBounds(340, 2270, 80, 20);

        psikologis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        psikologis.setName("psikologis"); // NOI18N
        psikologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                psikologisKeyPressed(evt);
            }
        });
        FormInput.add(psikologis);
        psikologis.setBounds(340, 2300, 80, 20);

        perdi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        perdi.setName("perdi"); // NOI18N
        perdi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                perdiKeyPressed(evt);
            }
        });
        FormInput.add(perdi);
        perdi.setBounds(340, 2360, 80, 20);

        perpe.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        perpe.setName("perpe"); // NOI18N
        perpe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                perpeKeyPressed(evt);
            }
        });
        FormInput.add(perpe);
        perpe.setBounds(340, 2390, 80, 20);

        pd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Jenis Diet", "Jumlah Kalori", "Modifikasi Diet" }));
        pd.setName("pd"); // NOI18N
        pd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pdKeyPressed(evt);
            }
        });
        FormInput.add(pd);
        pd.setBounds(340, 2420, 160, 20);

        pl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Luka Bersih", "Luka Bakar", "Luka Tekan (Dekubitus)" }));
        pl.setName("pl"); // NOI18N
        pl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                plKeyPressed(evt);
            }
        });
        FormInput.add(pl);
        pl.setBounds(340, 2450, 160, 20);

        lf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "ROM" }));
        lf.setName("lf"); // NOI18N
        lf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lfKeyPressed(evt);
            }
        });
        FormInput.add(lf);
        lf.setBounds(340, 2480, 160, 20);

        eke.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        eke.setName("eke"); // NOI18N
        eke.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ekeKeyPressed(evt);
            }
        });
        FormInput.add(eke);
        eke.setBounds(340, 2510, 80, 20);

        hc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        hc.setName("hc"); // NOI18N
        hc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hcKeyPressed(evt);
            }
        });
        FormInput.add(hc);
        hc.setBounds(340, 2540, 80, 20);

        jLabel186.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel186.setText("Bantuan untuk Melakukan Aktifitas Fisik :");
        jLabel186.setName("jLabel186"); // NOI18N
        FormInput.add(jLabel186);
        jLabel186.setBounds(40, 2570, 280, 23);

        bu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Kursi Roda", "Kruk" }));
        bu.setName("bu"); // NOI18N
        bu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buKeyPressed(evt);
            }
        });
        FormInput.add(bu);
        bu.setBounds(340, 2570, 160, 20);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penilaian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Penilaian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Riwayat Penyakit Keluarga");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pengunaan obat");
        }else{
            if(Sequel.menyimpantf("penilaian_medis_ranap_anak","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",89,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    KeluhanUtama.getText(),RPS.getText(),RPD.getText(),RPK.getText(),RPO.getText(),Alergi.getText(),Keadaan.getSelectedItem().toString(),GCS.getText(),Kesadaran.getSelectedItem().toString(),TD.getText(),
                    Nadi.getText(),RR.getText(),Suhu.getText(),SPO.getText(),BB.getText(),TB.getText(),Kepala.getSelectedItem().toString(),Mata.getSelectedItem().toString(),Gigi.getSelectedItem().toString(),THT.getSelectedItem().toString(),
                    Thoraks.getSelectedItem().toString(),Abdomen.getSelectedItem().toString(),Genital.getSelectedItem().toString(),Ekstremitas.getSelectedItem().toString(),Kulit.getSelectedItem().toString(),KetFisik.getText(),
                    KetLokalis.getText(),
                    Penunjang.getText(),Diagnosis.getText(),Tatalaksana.getText(),
                    
                    AlatBantu.getSelectedItem().toString(),KetBantu.getText(),Prothesa.getSelectedItem().toString(),KetProthesa.getText(),ADL.getSelectedItem().toString(),StatusPsiko.getSelectedItem().toString(),KetPsiko.getText(),HubunganKeluarga.getSelectedItem().toString(),TinggalDengan.getSelectedItem().toString(),KetTinggal.getText(),Ekonomi.getSelectedItem().toString(),
                    StatusBudaya.getSelectedItem().toString(),KetBudaya.getText(),Edukasi.getSelectedItem().toString(),KetEdukasi.getText(),resiko.getSelectedItem().toString(),TingkatResiko.getText(),SG1.getSelectedItem().toString(),NilaiGizi1.getText(),SG2.getSelectedItem().toString(),NilaiGizi2.getText(),SG3.getSelectedItem().toString(),NilaiGizi3.getText(),SG4.getSelectedItem().toString(),NilaiGizi4.getText(),TotalNilaiGizi.getText(),
                    SkalaWajah.getSelectedItem().toString(),NilaiWajah.getText(),SkalaKaki.getSelectedItem().toString(),NilaiKaki.getText(),SkalaAktifitas.getSelectedItem().toString(),NilaiAktifitas.getText(),SkalaMenangis.getSelectedItem().toString(),
                    NilaiMenangis.getText(),SkalaBersuara.getSelectedItem().toString(),NilaiBersuara.getText(),SkalaNyeri.getText(),
                    Nyeri.getSelectedItem().toString(),Lokasi.getText(),Durasi.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),
                    mobilitas.getSelectedItem().toString(),lanjutan.getSelectedItem().toString(),bantuan.getSelectedItem().toString(),
                    psikologis.getSelectedItem().toString(),perdi.getSelectedItem().toString(),perpe.getSelectedItem().toString(),pd.getSelectedItem().toString(),pl.getSelectedItem().toString(),
                    lf.getSelectedItem().toString(),eke.getSelectedItem().toString(),hc.getSelectedItem().toString(),bu.getSelectedItem().toString()
                })==true){
                    emptTeks();
                    JOptionPane.showMessageDialog(rootPane, "Data berhasil disimpan!");
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    public void windowOpened(WindowEvent e) {
       isRawat();
    }

    
    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,KetFisik,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }              
            
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Riwayat Penyakit Keluarga");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pengunaan obat");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString())){
                        ganti();
                    } else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
                    }  
                } 
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
    
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            try{
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='105px'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='70px'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='65px'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='55px'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Nama Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='115px'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Anamnesis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Hubungan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Keluhan Utama</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Riwayat Penyakit Sekarang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Riwayat Penyakit Dahulu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Riwayat Penyakit Keluarga</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Riwayat Penggunakan Obat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='120px'><b>Riwayat Alergi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>Keadaan Umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='50px'><b>GCS</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Kesadaran</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'><b>TD(mmHg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='75px'><b>Nadi(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='67px'><b>RR(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>Suhu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>SpO2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>BB(Kg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>TB(cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Kepala</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Mata</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Gigi & Mulut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>THT</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Thoraks</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Abdomen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Genital & Anus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Ekstremitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Kulit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Ket.Pemeriksaan Fisik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='200px'><b>Ket.Status Lokalis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='170px'><b>Laboratorium</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='170px'><b>Radiologi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='170px'><b>Penunjang Lainnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Diagnosis/Asesmen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Tatalaksana</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Konsul/Rujuk</b></td>"+
                    "</tr>"
                );
                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append(
                        "<tr class='isi'>"+
                           "<td valign='top'>"+tbObat.getValueAt(i,0).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,4).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,5).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,7).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,8).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,9).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,10).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,11).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,13).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,14).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,15).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,16).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,17).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,18).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,20).toString()+"</td>"+ 
                            "<td valign='top'>"+tbObat.getValueAt(i,21).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,22).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,23).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,24).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,25).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,26).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,27).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,28).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,29).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,30).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,31).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,32).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,33).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,34).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,35).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,36).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,37).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,38).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,39).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,40).toString()+"</td>"+ 
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='4600px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );

                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                    ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                    ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                    ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                    ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                    ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
                );
                bg.close();

                File f = new File("DataPenilaianAwalMedisRanap.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='4600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL MEDIS RAWAT JALAN BAYI/ANAK<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        
    }//GEN-LAST:event_KdDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,TB,TD);
    }//GEN-LAST:event_BBKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,GCS,BB);
    }//GEN-LAST:event_TBKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,SPO);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,BB,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,RPO,Keadaan);
    }//GEN-LAST:event_AlergiKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt,Hubungan,RPS);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPDKeyPressed
        Valid.pindah2(evt,RPK,RPO);
    }//GEN-LAST:event_RPDKeyPressed

    private void RPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPKKeyPressed
        Valid.pindah2(evt,RPS,RPD);
    }//GEN-LAST:event_RPKKeyPressed

    private void RPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOKeyPressed
        Valid.pindah2(evt,RPD,Alergi);
    }//GEN-LAST:event_RPOKeyPressed

    private void KetFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetFisikKeyPressed
        Valid.pindah2(evt,Kulit,KetLokalis);
    }//GEN-LAST:event_KetFisikKeyPressed

    private void GCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCSKeyPressed
        Valid.pindah(evt,Kesadaran,TB);
    }//GEN-LAST:event_GCSKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt,KeluhanUtama,RPK);
    }//GEN-LAST:event_RPSKeyPressed

    private void KeadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanKeyPressed
        Valid.pindah(evt,Alergi,Kesadaran);
    }//GEN-LAST:event_KeadaanKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        Valid.pindah(evt,Keadaan,GCS);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void SPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPOKeyPressed
        Valid.pindah(evt,Suhu,Kepala);
    }//GEN-LAST:event_SPOKeyPressed

    private void KepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaKeyPressed
        Valid.pindah(evt,SPO,Mata);
    }//GEN-LAST:event_KepalaKeyPressed

    private void GigiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GigiKeyPressed
        Valid.pindah(evt,Mata,THT);
    }//GEN-LAST:event_GigiKeyPressed

    private void THTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_THTKeyPressed
        Valid.pindah(evt,Gigi,Thoraks);
    }//GEN-LAST:event_THTKeyPressed

    private void ThoraksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ThoraksKeyPressed
        Valid.pindah(evt,THT,Thoraks);
    }//GEN-LAST:event_ThoraksKeyPressed

    private void AbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbdomenKeyPressed
        Valid.pindah(evt,Thoraks,Genital);
    }//GEN-LAST:event_AbdomenKeyPressed

    private void GenitalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GenitalKeyPressed
        Valid.pindah(evt,Abdomen,Ekstremitas);
    }//GEN-LAST:event_GenitalKeyPressed

    private void EkstremitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkstremitasKeyPressed
        Valid.pindah(evt,Genital,Kulit);
    }//GEN-LAST:event_EkstremitasKeyPressed

    private void KulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KulitKeyPressed
        Valid.pindah(evt,Ekstremitas,KetFisik);
    }//GEN-LAST:event_KulitKeyPressed

    private void KetLokalisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetLokalisKeyPressed
        Valid.pindah2(evt,KetFisik,Penunjang);
    }//GEN-LAST:event_KetLokalisKeyPressed

    private void PenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangKeyPressed
        Valid.pindah2(evt,KetLokalis,Diagnosis);
    }//GEN-LAST:event_PenunjangKeyPressed

    private void DiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKeyPressed
        Valid.pindah2(evt,Penunjang,Tatalaksana);
    }//GEN-LAST:event_DiagnosisKeyPressed

    private void TatalaksanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TatalaksanaKeyPressed
        Valid.pindah2(evt,Diagnosis,mobilitas);
    }//GEN-LAST:event_TatalaksanaKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,bu,Anamnesis);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        Valid.pindah(evt,Anamnesis,KeluhanUtama);
    }//GEN-LAST:event_HubunganKeyPressed

    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMedisActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());          
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            param.put("nyeri",Sequel.cariGambar("select gambar.nyeri from gambar")); 
            try {
                param.put("lokalis",getClass().getResource("/picture/semua.png").openStream());
            } catch (Exception e) {
            } 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            
            Valid.MyReportqry("rptCetakPenilaianAwalMedisRanapBayi.jasper","report","::[ Laporan Penilaian Awal Medis Ranap Bayi/Anak ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ranap_anak.tanggal,"+
                "penilaian_medis_ranap_anak.kd_dokter,penilaian_medis_ranap_anak.anamnesis,penilaian_medis_ranap_anak.hubungan,penilaian_medis_ranap_anak.keluhan_utama,penilaian_medis_ranap_anak.rps,penilaian_medis_ranap_anak.rpk,penilaian_medis_ranap_anak.rpd,penilaian_medis_ranap_anak.rpo,penilaian_medis_ranap_anak.alergi,"+
                "penilaian_medis_ranap_anak.keadaan,penilaian_medis_ranap_anak.gcs,penilaian_medis_ranap_anak.kesadaran,penilaian_medis_ranap_anak.td,penilaian_medis_ranap_anak.nadi,penilaian_medis_ranap_anak.rr,penilaian_medis_ranap_anak.suhu,penilaian_medis_ranap_anak.spo,penilaian_medis_ranap_anak.bb,penilaian_medis_ranap_anak.tb,"+
                "penilaian_medis_ranap_anak.kepala,penilaian_medis_ranap_anak.mata,penilaian_medis_ranap_anak.gigi,penilaian_medis_ranap_anak.tht,penilaian_medis_ranap_anak.thoraks,penilaian_medis_ranap_anak.abdomen,penilaian_medis_ranap_anak.ekstremitas,penilaian_medis_ranap_anak.genital,penilaian_medis_ranap_anak.kulit,"+
                "penilaian_medis_ranap_anak.ket_fisik,penilaian_medis_ranap_anak.ket_lokalis,penilaian_medis_ranap_anak.penunjang,penilaian_medis_ranap_anak.diagnosis,penilaian_medis_ranap_anak.tata,penilaian_medis_ranap_anak.alat_bantu,penilaian_medis_ranap_anak.ket_bantu,penilaian_medis_ranap_anak.prothesa," +
                "penilaian_medis_ranap_anak.ket_pro,penilaian_medis_ranap_anak.adl,penilaian_medis_ranap_anak.status_psiko,penilaian_medis_ranap_anak.ket_psiko,penilaian_medis_ranap_anak.hub_keluarga,penilaian_medis_ranap_anak.tinggal_dengan," +
                "penilaian_medis_ranap_anak.ket_tinggal,penilaian_medis_ranap_anak.ekonomi,penilaian_medis_ranap_anak.budaya,penilaian_medis_ranap_anak.ket_budaya,penilaian_medis_ranap_anak.edukasi,penilaian_medis_ranap_anak.ket_edukasi," +
                "penilaian_medis_ranap_anak.skor,penilaian_medis_ranap_anak.skala,penilaian_medis_ranap_anak.sg1,penilaian_medis_ranap_anak.nilai1,penilaian_medis_ranap_anak.sg2,penilaian_medis_ranap_anak.nilai2,penilaian_medis_ranap_anak.sg3,penilaian_medis_ranap_anak.nilai3,penilaian_medis_ranap_anak.sg4,penilaian_medis_ranap_anak.nilai4," +
                "penilaian_medis_ranap_anak.total_hasil,penilaian_medis_ranap_anak.wajah,penilaian_medis_ranap_anak.nilaiwajah,penilaian_medis_ranap_anak.kaki,"+
                "penilaian_medis_ranap_anak.nilaikaki,penilaian_medis_ranap_anak.aktifitas,penilaian_medis_ranap_anak.nilaiaktifitas,penilaian_medis_ranap_anak.menangis,penilaian_medis_ranap_anak.nilaimenangis,"+
                "penilaian_medis_ranap_anak.bersuara,penilaian_medis_ranap_anak.nilaibersuara,penilaian_medis_ranap_anak.hasilnyeri,penilaian_medis_ranap_anak.nyeri,penilaian_medis_ranap_anak.lokasi," +
                "penilaian_medis_ranap_anak.durasi,penilaian_medis_ranap_anak.nyeri_hilang,penilaian_medis_ranap_anak.ket_nyeri,penilaian_medis_ranap_anak.mobilitas,penilaian_medis_ranap_anak.lanjutan,penilaian_medis_ranap_anak.bantuan,penilaian_medis_ranap_anak.psikologis," +
                "penilaian_medis_ranap_anak.perdi,penilaian_medis_ranap_anak.perpe,penilaian_medis_ranap_anak.pd,penilaian_medis_ranap_anak.pl,penilaian_medis_ranap_anak.lf,penilaian_medis_ranap_anak.eke,penilaian_medis_ranap_anak.hc,penilaian_medis_ranap_anak.bu, "+
                "dokter.nm_dokter "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_medis_ranap_anak on reg_periksa.no_rawat=penilaian_medis_ranap_anak.no_rawat "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                 "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "+
                "inner join dokter on penilaian_medis_ranap_anak.kd_dokter=dokter.kd_dokter where penilaian_medis_ranap_anak.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
            
            Valid.MyReportqry("rptCetakPenilaianAwalMedisRanapBayi2.jasper","report","::[ Laporan Penilaian Awal Medis Ranap Bayi/Anak ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ranap_anak.tanggal,"+
                "penilaian_medis_ranap_anak.kd_dokter,penilaian_medis_ranap_anak.anamnesis,penilaian_medis_ranap_anak.hubungan,penilaian_medis_ranap_anak.keluhan_utama,penilaian_medis_ranap_anak.rps,penilaian_medis_ranap_anak.rpk,penilaian_medis_ranap_anak.rpd,penilaian_medis_ranap_anak.rpo,penilaian_medis_ranap_anak.alergi,"+
                "penilaian_medis_ranap_anak.keadaan,penilaian_medis_ranap_anak.gcs,penilaian_medis_ranap_anak.kesadaran,penilaian_medis_ranap_anak.td,penilaian_medis_ranap_anak.nadi,penilaian_medis_ranap_anak.rr,penilaian_medis_ranap_anak.suhu,penilaian_medis_ranap_anak.spo,penilaian_medis_ranap_anak.bb,penilaian_medis_ranap_anak.tb,"+
                "penilaian_medis_ranap_anak.kepala,penilaian_medis_ranap_anak.mata,penilaian_medis_ranap_anak.gigi,penilaian_medis_ranap_anak.tht,penilaian_medis_ranap_anak.thoraks,penilaian_medis_ranap_anak.abdomen,penilaian_medis_ranap_anak.ekstremitas,penilaian_medis_ranap_anak.genital,penilaian_medis_ranap_anak.kulit,"+
                "penilaian_medis_ranap_anak.ket_fisik,penilaian_medis_ranap_anak.ket_lokalis,penilaian_medis_ranap_anak.penunjang,penilaian_medis_ranap_anak.diagnosis,penilaian_medis_ranap_anak.tata,penilaian_medis_ranap_anak.alat_bantu,penilaian_medis_ranap_anak.ket_bantu,penilaian_medis_ranap_anak.prothesa," +
                "penilaian_medis_ranap_anak.ket_pro,penilaian_medis_ranap_anak.adl,penilaian_medis_ranap_anak.status_psiko,penilaian_medis_ranap_anak.ket_psiko,penilaian_medis_ranap_anak.hub_keluarga,penilaian_medis_ranap_anak.tinggal_dengan," +
                "penilaian_medis_ranap_anak.ket_tinggal,penilaian_medis_ranap_anak.ekonomi,penilaian_medis_ranap_anak.budaya,penilaian_medis_ranap_anak.ket_budaya,penilaian_medis_ranap_anak.edukasi,penilaian_medis_ranap_anak.ket_edukasi," +
                "penilaian_medis_ranap_anak.skor,penilaian_medis_ranap_anak.skala,penilaian_medis_ranap_anak.sg1,penilaian_medis_ranap_anak.nilai1,penilaian_medis_ranap_anak.sg2,penilaian_medis_ranap_anak.nilai2,penilaian_medis_ranap_anak.sg3,penilaian_medis_ranap_anak.nilai3,penilaian_medis_ranap_anak.sg4,penilaian_medis_ranap_anak.nilai4," +
                "penilaian_medis_ranap_anak.total_hasil,penilaian_medis_ranap_anak.wajah,penilaian_medis_ranap_anak.nilaiwajah,penilaian_medis_ranap_anak.kaki,"+
                "penilaian_medis_ranap_anak.nilaikaki,penilaian_medis_ranap_anak.aktifitas,penilaian_medis_ranap_anak.nilaiaktifitas,penilaian_medis_ranap_anak.menangis,penilaian_medis_ranap_anak.nilaimenangis,"+
                "penilaian_medis_ranap_anak.bersuara,penilaian_medis_ranap_anak.nilaibersuara,penilaian_medis_ranap_anak.hasilnyeri,penilaian_medis_ranap_anak.nyeri,penilaian_medis_ranap_anak.lokasi," +
                "penilaian_medis_ranap_anak.durasi,penilaian_medis_ranap_anak.nyeri_hilang,penilaian_medis_ranap_anak.ket_nyeri,penilaian_medis_ranap_anak.mobilitas,penilaian_medis_ranap_anak.lanjutan,penilaian_medis_ranap_anak.bantuan,penilaian_medis_ranap_anak.psikologis," +
                "penilaian_medis_ranap_anak.perdi,penilaian_medis_ranap_anak.perpe,penilaian_medis_ranap_anak.pd,penilaian_medis_ranap_anak.pl,penilaian_medis_ranap_anak.lf,penilaian_medis_ranap_anak.eke,penilaian_medis_ranap_anak.hc,penilaian_medis_ranap_anak.bu, "+
                "dokter.nm_dokter "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_medis_ranap_anak on reg_periksa.no_rawat=penilaian_medis_ranap_anak.no_rawat "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                 "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "+
                "inner join dokter on penilaian_medis_ranap_anak.kd_dokter=dokter.kd_dokter where penilaian_medis_ranap_anak.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void MataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MataKeyPressed
        Valid.pindah(evt,Kepala,Gigi);
    }//GEN-LAST:event_MataKeyPressed

    private void StatusPsikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusPsikoKeyPressed
        Valid.pindah(evt,ADL,KetPsiko);
    }//GEN-LAST:event_StatusPsikoKeyPressed

    private void KetPsikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetPsikoKeyPressed
        Valid.pindah(evt,StatusPsiko,HubunganKeluarga);
    }//GEN-LAST:event_KetPsikoKeyPressed

    private void EkonomiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkonomiKeyPressed
        Valid.pindah(evt,KetTinggal,StatusBudaya);
    }//GEN-LAST:event_EkonomiKeyPressed

    private void KetTinggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetTinggalKeyPressed
        Valid.pindah(evt,TinggalDengan,Ekonomi);
    }//GEN-LAST:event_KetTinggalKeyPressed

    private void TinggalDenganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinggalDenganKeyPressed
        Valid.pindah(evt,HubunganKeluarga,KetTinggal);
    }//GEN-LAST:event_TinggalDenganKeyPressed

    private void HubunganKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeluargaKeyPressed
        Valid.pindah(evt,KetPsiko,TinggalDengan);
    }//GEN-LAST:event_HubunganKeluargaKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        Valid.pindah(evt,KetBudaya,KetEdukasi);
    }//GEN-LAST:event_EdukasiKeyPressed

    private void KetEdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetEdukasiKeyPressed
        Valid.pindah(evt,Edukasi,resiko);
    }//GEN-LAST:event_KetEdukasiKeyPressed

    private void KetBudayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetBudayaKeyPressed
        Valid.pindah(evt,StatusBudaya,Edukasi);
    }//GEN-LAST:event_KetBudayaKeyPressed

    private void StatusBudayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusBudayaKeyPressed
        Valid.pindah(evt,Ekonomi,KetBudaya);
    }//GEN-LAST:event_StatusBudayaKeyPressed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        Valid.pindah(evt,NilaiGizi2,SG3);
    }//GEN-LAST:event_NyeriKeyPressed

    private void LokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiKeyPressed
        Valid.pindah(evt,Nyeri,Durasi);
    }//GEN-LAST:event_LokasiKeyPressed

    private void DurasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DurasiKeyPressed
        Valid.pindah(evt,Lokasi,NyeriHilang);
    }//GEN-LAST:event_DurasiKeyPressed

    private void NyeriHilangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriHilangKeyPressed
        Valid.pindah(evt,Durasi,KetNyeri);
    }//GEN-LAST:event_NyeriHilangKeyPressed

    private void KetNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetNyeriKeyPressed
        Valid.pindah(evt,NyeriHilang,Penunjang);
    }//GEN-LAST:event_KetNyeriKeyPressed

    private void AgamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AgamaActionPerformed

    private void CacatFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CacatFisikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CacatFisikKeyPressed

    private void AlatBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatBantuKeyPressed
        Valid.pindah(evt,Alergi,KetBantu);
    }//GEN-LAST:event_AlatBantuKeyPressed

    private void KetBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetBantuKeyPressed
        Valid.pindah(evt,AlatBantu,Prothesa);
    }//GEN-LAST:event_KetBantuKeyPressed

    private void ProthesaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProthesaKeyPressed
        Valid.pindah(evt,KetBantu,KetProthesa);
    }//GEN-LAST:event_ProthesaKeyPressed

    private void KetProthesaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetProthesaKeyPressed
        Valid.pindah(evt,Prothesa,ADL);
    }//GEN-LAST:event_KetProthesaKeyPressed

    private void ADLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ADLKeyPressed
        Valid.pindah(evt,KetProthesa,StatusPsiko);
    }//GEN-LAST:event_ADLKeyPressed

    private void SkalaAktifitasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaAktifitasItemStateChanged
        NilaiAktifitas.setText(SkalaAktifitas.getSelectedIndex()+"");
        SkalaNyeri.setText(""+(Integer.parseInt(NilaiWajah.getText())+Integer.parseInt(NilaiKaki.getText())+Integer.parseInt(NilaiAktifitas.getText())+Integer.parseInt(NilaiMenangis.getText())+Integer.parseInt(NilaiBersuara.getText())));
    }//GEN-LAST:event_SkalaAktifitasItemStateChanged

    private void SkalaAktifitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaAktifitasKeyPressed
        Valid.pindah(evt,SkalaKaki,SkalaMenangis);
    }//GEN-LAST:event_SkalaAktifitasKeyPressed

    private void SkalaKakiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaKakiItemStateChanged
        NilaiKaki.setText(SkalaKaki.getSelectedIndex()+"");
        SkalaNyeri.setText(""+(Integer.parseInt(NilaiWajah.getText())+Integer.parseInt(NilaiKaki.getText())+Integer.parseInt(NilaiAktifitas.getText())+Integer.parseInt(NilaiMenangis.getText())+Integer.parseInt(NilaiBersuara.getText())));
    }//GEN-LAST:event_SkalaKakiItemStateChanged

    private void SkalaKakiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaKakiKeyPressed
        Valid.pindah(evt,SkalaWajah,SkalaAktifitas);
    }//GEN-LAST:event_SkalaKakiKeyPressed

    private void SkalaWajahItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaWajahItemStateChanged
        NilaiWajah.setText(SkalaWajah.getSelectedIndex()+"");
        SkalaNyeri.setText(""+(Integer.parseInt(NilaiWajah.getText())+Integer.parseInt(NilaiKaki.getText())+Integer.parseInt(NilaiAktifitas.getText())+Integer.parseInt(NilaiMenangis.getText())+Integer.parseInt(NilaiBersuara.getText())));
    }//GEN-LAST:event_SkalaWajahItemStateChanged

    private void SkalaWajahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaWajahKeyPressed
        Valid.pindah(evt,SG4,SkalaKaki);
    }//GEN-LAST:event_SkalaWajahKeyPressed

    private void NilaiWajahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiWajahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiWajahKeyPressed

    private void NilaiKakiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiKakiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiKakiKeyPressed

    private void NilaiAktifitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiAktifitasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiAktifitasKeyPressed

    private void SkalaBersuaraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaBersuaraItemStateChanged
        NilaiBersuara.setText(SkalaBersuara.getSelectedIndex()+"");
        SkalaNyeri.setText(""+(Integer.parseInt(NilaiWajah.getText())+Integer.parseInt(NilaiKaki.getText())+Integer.parseInt(NilaiAktifitas.getText())+Integer.parseInt(NilaiMenangis.getText())+Integer.parseInt(NilaiBersuara.getText())));
    }//GEN-LAST:event_SkalaBersuaraItemStateChanged

    private void SkalaBersuaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaBersuaraKeyPressed
        Valid.pindah(evt,SkalaMenangis,Nyeri);
    }//GEN-LAST:event_SkalaBersuaraKeyPressed

    private void SkalaMenangisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaMenangisItemStateChanged
        NilaiMenangis.setText(SkalaMenangis.getSelectedIndex()+"");
        SkalaNyeri.setText(""+(Integer.parseInt(NilaiWajah.getText())+Integer.parseInt(NilaiKaki.getText())+Integer.parseInt(NilaiAktifitas.getText())+Integer.parseInt(NilaiMenangis.getText())+Integer.parseInt(NilaiBersuara.getText())));
    }//GEN-LAST:event_SkalaMenangisItemStateChanged

    private void SkalaMenangisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaMenangisKeyPressed
        Valid.pindah(evt,SkalaAktifitas,SkalaBersuara);
    }//GEN-LAST:event_SkalaMenangisKeyPressed

    private void NilaiMenangisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiMenangisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiMenangisKeyPressed

    private void NilaiBersuaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiBersuaraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiBersuaraKeyPressed

    private void SkalaNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNyeriKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNyeriKeyPressed

    private void SG1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG1ItemStateChanged
        NilaiGizi1.setText(SG1.getSelectedIndex()+"");
        TotalNilaiGizi.setText(""+(Integer.parseInt(NilaiGizi1.getText())+Integer.parseInt(NilaiGizi2.getText())+Integer.parseInt(NilaiGizi3.getText())+Integer.parseInt(NilaiGizi4.getText())));
    }//GEN-LAST:event_SG1ItemStateChanged

    private void SG1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG1KeyPressed
        Valid.pindah(evt,resiko,SG2);
    }//GEN-LAST:event_SG1KeyPressed

    private void NilaiGizi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiGizi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiGizi1KeyPressed

    private void NilaiGizi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiGizi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiGizi2KeyPressed

    private void SG2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG2ItemStateChanged
        NilaiGizi2.setText(SG2.getSelectedIndex()+"");
        TotalNilaiGizi.setText(""+(Integer.parseInt(NilaiGizi1.getText())+Integer.parseInt(NilaiGizi2.getText())+Integer.parseInt(NilaiGizi3.getText())+Integer.parseInt(NilaiGizi4.getText())));
    }//GEN-LAST:event_SG2ItemStateChanged

    private void SG2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG2KeyPressed
        Valid.pindah(evt,SG1,SG3);
    }//GEN-LAST:event_SG2KeyPressed

    private void SG3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG3ItemStateChanged
        NilaiGizi3.setText(SG3.getSelectedIndex()+"");
        TotalNilaiGizi.setText(""+(Integer.parseInt(NilaiGizi1.getText())+Integer.parseInt(NilaiGizi2.getText())+Integer.parseInt(NilaiGizi3.getText())+Integer.parseInt(NilaiGizi4.getText())));
    }//GEN-LAST:event_SG3ItemStateChanged

    private void SG3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG3KeyPressed
        Valid.pindah(evt,SG2,SG4);
    }//GEN-LAST:event_SG3KeyPressed

    private void NilaiGizi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiGizi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiGizi3KeyPressed

    private void NilaiGizi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiGizi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiGizi4KeyPressed

    private void SG4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG4ItemStateChanged
        NilaiGizi4.setText(SG4.getSelectedIndex()+"");
        TotalNilaiGizi.setText(""+(Integer.parseInt(NilaiGizi1.getText())+Integer.parseInt(NilaiGizi2.getText())+Integer.parseInt(NilaiGizi3.getText())+Integer.parseInt(NilaiGizi4.getText())));
    }//GEN-LAST:event_SG4ItemStateChanged

    private void SG4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG4KeyPressed
        Valid.pindah(evt,SG3,SkalaWajah);
    }//GEN-LAST:event_SG4KeyPressed

    private void TotalNilaiGiziKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalNilaiGiziKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalNilaiGiziKeyPressed

    private void resikoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_resikoItemStateChanged
        if(resiko.getSelectedIndex()==0){
            TingkatResiko.setText("Intervensi : Risiko Rendah");
        }else{
            TingkatResiko.setText("Intervensi : Risiko Tinggi");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_resikoItemStateChanged

    private void resikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resikoKeyPressed
        Valid.pindah(evt,KetEdukasi,SG1);
    }//GEN-LAST:event_resikoKeyPressed

    private void mobilitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mobilitasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_mobilitasKeyPressed

    private void lanjutanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lanjutanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lanjutanKeyPressed

    private void bantuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bantuanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_bantuanKeyPressed

    private void psikologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_psikologisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_psikologisKeyPressed

    private void perdiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_perdiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_perdiKeyPressed

    private void perpeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_perpeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_perpeKeyPressed

    private void pdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pdKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pdKeyPressed

    private void plKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_plKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_plKeyPressed

    private void lfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lfKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lfKeyPressed

    private void ekeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ekeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ekeKeyPressed

    private void hcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hcKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_hcKeyPressed

    private void buKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_buKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalMedisRanapAnak dialog = new RMPenilaianAwalMedisRanapAnak(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.ComboBox ADL;
    private widget.ComboBox Abdomen;
    private widget.TextBox Agama;
    private widget.ComboBox AlatBantu;
    private widget.TextBox Alergi;
    private widget.ComboBox Anamnesis;
    private widget.TextBox BB;
    private widget.TextBox Bahasa;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextBox CacatFisik;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea Diagnosis;
    private widget.TextBox Durasi;
    private widget.ComboBox Edukasi;
    private widget.ComboBox Ekonomi;
    private widget.ComboBox Ekstremitas;
    private widget.PanelBiasa FormInput;
    private widget.TextBox GCS;
    private widget.ComboBox Genital;
    private widget.ComboBox Gigi;
    private widget.TextBox Hubungan;
    private widget.ComboBox HubunganKeluarga;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.ComboBox Keadaan;
    private widget.TextArea KeluhanUtama;
    private widget.ComboBox Kepala;
    private widget.ComboBox Kesadaran;
    private widget.TextBox KetBantu;
    private widget.TextBox KetBudaya;
    private widget.TextBox KetEdukasi;
    private widget.TextArea KetFisik;
    private widget.TextArea KetLokalis;
    private widget.TextBox KetNyeri;
    private widget.TextBox KetProthesa;
    private widget.TextBox KetPsiko;
    private widget.TextBox KetTinggal;
    private widget.ComboBox Kulit;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextBox Lokasi;
    private widget.ComboBox Mata;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox Nadi;
    private widget.TextBox NilaiAktifitas;
    private widget.TextBox NilaiBersuara;
    private widget.TextBox NilaiGizi1;
    private widget.TextBox NilaiGizi2;
    private widget.TextBox NilaiGizi3;
    private widget.TextBox NilaiGizi4;
    private widget.TextBox NilaiKaki;
    private widget.TextBox NilaiMenangis;
    private widget.TextBox NilaiWajah;
    private widget.TextBox NmDokter;
    private widget.ComboBox Nyeri;
    private widget.ComboBox NyeriHilang;
    private usu.widget.glass.PanelGlass PanelWall;
    private usu.widget.glass.PanelGlass PanelWall1;
    private widget.TextArea Penunjang;
    private widget.ComboBox Prothesa;
    private widget.TextArea RPD;
    private widget.TextArea RPK;
    private widget.TextArea RPO;
    private widget.TextArea RPS;
    private widget.TextBox RR;
    private widget.ComboBox SG1;
    private widget.ComboBox SG2;
    private widget.ComboBox SG3;
    private widget.ComboBox SG4;
    private widget.TextBox SPO;
    private widget.ScrollPane Scroll;
    private widget.ComboBox SkalaAktifitas;
    private widget.ComboBox SkalaBersuara;
    private widget.ComboBox SkalaKaki;
    private widget.ComboBox SkalaMenangis;
    private widget.TextBox SkalaNyeri;
    private widget.ComboBox SkalaWajah;
    private widget.ComboBox StatusBudaya;
    private widget.ComboBox StatusPsiko;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.ComboBox THT;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextArea Tatalaksana;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox Thoraks;
    private widget.ComboBox TinggalDengan;
    private widget.Label TingkatResiko;
    private widget.TextBox TotalNilaiGizi;
    private widget.ComboBox bantuan;
    private widget.ComboBox bu;
    private widget.ComboBox eke;
    private widget.ComboBox hc;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel146;
    private widget.Label jLabel147;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel162;
    private widget.Label jLabel163;
    private widget.Label jLabel164;
    private widget.Label jLabel165;
    private widget.Label jLabel166;
    private widget.Label jLabel167;
    private widget.Label jLabel168;
    private widget.Label jLabel169;
    private widget.Label jLabel17;
    private widget.Label jLabel170;
    private widget.Label jLabel172;
    private widget.Label jLabel173;
    private widget.Label jLabel174;
    private widget.Label jLabel175;
    private widget.Label jLabel178;
    private widget.Label jLabel179;
    private widget.Label jLabel18;
    private widget.Label jLabel180;
    private widget.Label jLabel181;
    private widget.Label jLabel182;
    private widget.Label jLabel183;
    private widget.Label jLabel184;
    private widget.Label jLabel185;
    private widget.Label jLabel186;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel35;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel74;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel78;
    private widget.Label jLabel79;
    private widget.Label jLabel8;
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel86;
    private widget.Label jLabel87;
    private widget.Label jLabel89;
    private widget.Label jLabel9;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel98;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private widget.Label label11;
    private widget.Label label14;
    private widget.ComboBox lanjutan;
    private widget.ComboBox lf;
    private widget.ComboBox mobilitas;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ComboBox pd;
    private widget.ComboBox perdi;
    private widget.ComboBox perpe;
    private widget.ComboBox pl;
    private widget.ComboBox psikologis;
    private widget.ComboBox resiko;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_medis_ranap_anak.tanggal,"+
                        "penilaian_medis_ranap_anak.kd_dokter,penilaian_medis_ranap_anak.anamnesis,penilaian_medis_ranap_anak.hubungan,penilaian_medis_ranap_anak.keluhan_utama,penilaian_medis_ranap_anak.rps,penilaian_medis_ranap_anak.rpk,penilaian_medis_ranap_anak.rpd,penilaian_medis_ranap_anak.rpo,penilaian_medis_ranap_anak.alergi,"+
                        "penilaian_medis_ranap_anak.keadaan,penilaian_medis_ranap_anak.gcs,penilaian_medis_ranap_anak.kesadaran,penilaian_medis_ranap_anak.td,penilaian_medis_ranap_anak.nadi,penilaian_medis_ranap_anak.rr,penilaian_medis_ranap_anak.suhu,penilaian_medis_ranap_anak.spo,penilaian_medis_ranap_anak.bb,penilaian_medis_ranap_anak.tb,"+
                        "penilaian_medis_ranap_anak.kepala,penilaian_medis_ranap_anak.mata,penilaian_medis_ranap_anak.gigi,penilaian_medis_ranap_anak.tht,penilaian_medis_ranap_anak.thoraks,penilaian_medis_ranap_anak.abdomen,penilaian_medis_ranap_anak.ekstremitas,penilaian_medis_ranap_anak.genital,penilaian_medis_ranap_anak.kulit,"+
                        "penilaian_medis_ranap_anak.ket_fisik,penilaian_medis_ranap_anak.ket_lokalis,penilaian_medis_ranap_anak.penunjang,penilaian_medis_ranap_anak.diagnosis,penilaian_medis_ranap_anak.tata,"+
                                
                        "penilaian_medis_ranap_anak.alat_bantu,penilaian_medis_ranap_anak.ket_bantu,penilaian_medis_ranap_anak.prothesa," +
                        "penilaian_medis_ranap_anak.ket_pro,penilaian_medis_ranap_anak.adl,penilaian_medis_ranap_anak.status_psiko,penilaian_medis_ranap_anak.ket_psiko,penilaian_medis_ranap_anak.hub_keluarga,penilaian_medis_ranap_anak.tinggal_dengan," +
                        "penilaian_medis_ranap_anak.ket_tinggal,penilaian_medis_ranap_anak.ekonomi,penilaian_medis_ranap_anak.budaya,penilaian_medis_ranap_anak.ket_budaya,penilaian_medis_ranap_anak.edukasi,penilaian_medis_ranap_anak.ket_edukasi," +
                        "penilaian_medis_ranap_anak.skor,penilaian_medis_ranap_anak.skala,penilaian_medis_ranap_anak.sg1,penilaian_medis_ranap_anak.nilai1,penilaian_medis_ranap_anak.sg2,penilaian_medis_ranap_anak.nilai2,penilaian_medis_ranap_anak.sg3,penilaian_medis_ranap_anak.nilai3,penilaian_medis_ranap_anak.sg4,penilaian_medis_ranap_anak.nilai4," +
                        "penilaian_medis_ranap_anak.total_hasil,penilaian_medis_ranap_anak.wajah,penilaian_medis_ranap_anak.nilaiwajah,penilaian_medis_ranap_anak.kaki,"+
                            "penilaian_medis_ranap_anak.nilaikaki,penilaian_medis_ranap_anak.aktifitas,penilaian_medis_ranap_anak.nilaiaktifitas,penilaian_medis_ranap_anak.menangis,penilaian_medis_ranap_anak.nilaimenangis,"+
                            "penilaian_medis_ranap_anak.bersuara,penilaian_medis_ranap_anak.nilaibersuara,penilaian_medis_ranap_anak.hasilnyeri,penilaian_medis_ranap_anak.nyeri,penilaian_medis_ranap_anak.lokasi," +
                        "penilaian_medis_ranap_anak.durasi,penilaian_medis_ranap_anak.nyeri_hilang,penilaian_medis_ranap_anak.ket_nyeri,penilaian_medis_ranap_anak.mobilitas,penilaian_medis_ranap_anak.lanjutan,penilaian_medis_ranap_anak.bantuan,penilaian_medis_ranap_anak.psikologis," +
                        "penilaian_medis_ranap_anak.perdi,penilaian_medis_ranap_anak.perpe,penilaian_medis_ranap_anak.pd,penilaian_medis_ranap_anak.pl,penilaian_medis_ranap_anak.lf,penilaian_medis_ranap_anak.eke,penilaian_medis_ranap_anak.hc,penilaian_medis_ranap_anak.bu, "+

                        "dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ranap_anak on reg_periksa.no_rawat=penilaian_medis_ranap_anak.no_rawat "+
                                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "+
                        "inner join dokter on penilaian_medis_ranap_anak.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ranap_anak.tanggal between ? and ? order by penilaian_medis_ranap_anak.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_medis_ranap_anak.tanggal,"+
                        "penilaian_medis_ranap_anak.kd_dokter,penilaian_medis_ranap_anak.anamnesis,penilaian_medis_ranap_anak.hubungan,penilaian_medis_ranap_anak.keluhan_utama,penilaian_medis_ranap_anak.rps,penilaian_medis_ranap_anak.rpk,penilaian_medis_ranap_anak.rpd,penilaian_medis_ranap_anak.rpo,penilaian_medis_ranap_anak.alergi,"+
                        "penilaian_medis_ranap_anak.keadaan,penilaian_medis_ranap_anak.gcs,penilaian_medis_ranap_anak.kesadaran,penilaian_medis_ranap_anak.td,penilaian_medis_ranap_anak.nadi,penilaian_medis_ranap_anak.rr,penilaian_medis_ranap_anak.suhu,penilaian_medis_ranap_anak.spo,penilaian_medis_ranap_anak.bb,penilaian_medis_ranap_anak.tb,"+
                        "penilaian_medis_ranap_anak.kepala,penilaian_medis_ranap_anak.mata,penilaian_medis_ranap_anak.gigi,penilaian_medis_ranap_anak.tht,penilaian_medis_ranap_anak.thoraks,penilaian_medis_ranap_anak.abdomen,penilaian_medis_ranap_anak.ekstremitas,penilaian_medis_ranap_anak.genital,penilaian_medis_ranap_anak.kulit,"+
                        "penilaian_medis_ranap_anak.ket_fisik,penilaian_medis_ranap_anak.ket_lokalis,penilaian_medis_ranap_anak.penunjang,penilaian_medis_ranap_anak.diagnosis,penilaian_medis_ranap_anak.tata,"+
                        
                           "penilaian_medis_ranap_anak.alat_bantu,penilaian_medis_ranap_anak.ket_bantu,penilaian_medis_ranap_anak.prothesa," +
                            "penilaian_medis_ranap_anak.ket_pro,penilaian_medis_ranap_anak.adl,penilaian_medis_ranap_anak.status_psiko,penilaian_medis_ranap_anak.ket_psiko,penilaian_medis_ranap_anak.hub_keluarga,penilaian_medis_ranap_anak.tinggal_dengan," +
                            "penilaian_medis_ranap_anak.ket_tinggal,penilaian_medis_ranap_anak.ekonomi,penilaian_medis_ranap_anak.budaya,penilaian_medis_ranap_anak.ket_budaya,penilaian_medis_ranap_anak.edukasi,penilaian_medis_ranap_anak.ket_edukasi, " +
                            "penilaian_medis_ranap_anak.skor,penilaian_medis_ranap_anak.skala,penilaian_medis_ranap_anak.sg1,penilaian_medis_ranap_anak.nilai1,penilaian_medis_ranap_anak.sg2,penilaian_medis_ranap_anak.nilai2,penilaian_medis_ranap_anak.sg3,penilaian_medis_ranap_anak.nilai3,penilaian_medis_ranap_anak.sg4,penilaian_medis_ranap_anak.nilai4," +
                            "penilaian_medis_ranap_anak.total_hasil,penilaian_medis_ranap_anak.wajah,penilaian_medis_ranap_anak.nilaiwajah,penilaian_medis_ranap_anak.kaki,"+
                            "penilaian_medis_ranap_anak.nilaikaki,penilaian_medis_ranap_anak.aktifitas,penilaian_medis_ranap_anak.nilaiaktifitas,penilaian_medis_ranap_anak.menangis,penilaian_medis_ranap_anak.nilaimenangis,"+
                            "penilaian_medis_ranap_anak.bersuara,penilaian_medis_ranap_anak.nilaibersuara,penilaian_medis_ranap_anak.hasilnyeri,penilaian_medis_ranap_anak.nyeri,penilaian_medis_ranap_anak.lokasi," +
                            "penilaian_medis_ranap_anak.durasi,penilaian_medis_ranap_anak.nyeri_hilang,penilaian_medis_ranap_anak.ket_nyeri,penilaian_medis_ranap_anak.mobilitas,penilaian_medis_ranap_anak.lanjutan,penilaian_medis_ranap_anak.bantuan,penilaian_medis_ranap_anak.psikologis," +
                            "penilaian_medis_ranap_anak.perdi,penilaian_medis_ranap_anak.perpe,penilaian_medis_ranap_anak.pd,penilaian_medis_ranap_anak.pl,penilaian_medis_ranap_anak.lf,penilaian_medis_ranap_anak.eke,penilaian_medis_ranap_anak.hc,penilaian_medis_ranap_anak.bu, "+

                                "dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ranap_anak on reg_periksa.no_rawat=penilaian_medis_ranap_anak.no_rawat "+
                                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "+
                        "inner join dokter on penilaian_medis_ranap_anak.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ranap_anak.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_ranap_anak.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ranap_anak.tanggal");
            }
                
            try {
                if(TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("agama"),rs.getString("nama_bahasa"),rs.getString("nama_cacat"),
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("keluhan_utama"),rs.getString("rps"),rs.getString("rpd"),rs.getString("rpk"),rs.getString("rpo"),rs.getString("alergi"),
                        rs.getString("keadaan"),rs.getString("gcs"),rs.getString("kesadaran"),rs.getString("td"),rs.getString("nadi"),rs.getString("rr"),rs.getString("suhu"),rs.getString("spo"),rs.getString("bb"),
                        rs.getString("tb"),rs.getString("kepala"),rs.getString("mata"),rs.getString("gigi"),rs.getString("tht"),rs.getString("thoraks"),rs.getString("abdomen"),rs.getString("genital"),
                        rs.getString("ekstremitas"),rs.getString("kulit"),rs.getString("ket_fisik"),rs.getString("ket_lokalis"),
                        rs.getString("alat_bantu"),rs.getString("ket_bantu"),rs.getString("prothesa"),rs.getString("ket_pro"),rs.getString("adl"),rs.getString("status_psiko"),
                        rs.getString("ket_psiko"),rs.getString("hub_keluarga"),rs.getString("tinggal_dengan"),rs.getString("ket_tinggal"),rs.getString("ekonomi"),rs.getString("budaya"),
                        rs.getString("ket_budaya"),rs.getString("edukasi"),rs.getString("ket_edukasi"),rs.getString("skor"),rs.getString("skala"),
                        rs.getString("sg1"),rs.getString("nilai1"),rs.getString("sg2"),rs.getString("nilai2"),
                        rs.getString("sg3"),rs.getString("nilai3"),rs.getString("sg4"),rs.getString("nilai4"),rs.getString("total_hasil"),
                        rs.getString("wajah"),rs.getString("nilaiwajah"),rs.getString("kaki"),rs.getString("nilaikaki"),rs.getString("aktifitas"),rs.getString("nilaiaktifitas"),rs.getString("menangis"),
                        rs.getString("nilaimenangis"),rs.getString("bersuara"),rs.getString("nilaibersuara"),rs.getString("hasilnyeri"),
                        rs.getString("nyeri"),rs.getString("lokasi"),rs.getString("durasi"),rs.getString("nyeri_hilang"),rs.getString("ket_nyeri"),
                        rs.getString("penunjang"),rs.getString("diagnosis"),rs.getString("tata"),
                         rs.getString("mobilitas"),rs.getString("lanjutan"),rs.getString("bantuan"),rs.getString("psikologis"),rs.getString("perdi"),rs.getString("perpe"),rs.getString("pd"),rs.getString("pl"),rs.getString("lf"),rs.getString("eke"),rs.getString("hc"),rs.getString("bu")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        Anamnesis.setSelectedIndex(0);
        Hubungan.setText("");
        KeluhanUtama.setText("");
        RPS.setText("");
        RPK.setText("");
        RPD.setText("");
        RPO.setText("");
        Alergi.setText("");
        Keadaan.setSelectedIndex(0);
        GCS.setText("");
        Kesadaran.setSelectedIndex(0);
        TD.setText("");
        Nadi.setText("");
        RR.setText("");
        Suhu.setText("");
        BB.setText("");
        TB.setText("");
        Kepala.setSelectedIndex(0);
        Mata.setSelectedIndex(0);
        Gigi.setSelectedIndex(0);
        THT.setSelectedIndex(0);
        Thoraks.setSelectedIndex(0);
        Abdomen.setSelectedIndex(0);
        Genital.setSelectedIndex(0);
        Ekstremitas.setSelectedIndex(0);
        Kulit.setSelectedIndex(0);
        KetFisik.setText("");
        KetLokalis.setText("");
        Penunjang.setText("");
        Penunjang.setText("");
        
        Diagnosis.setText("");
        Tatalaksana.setText("");
        AlatBantu.setSelectedIndex(0);
        KetBantu.setText("");
        Prothesa.setSelectedIndex(0);
        KetProthesa.setText("");
        ADL.setSelectedIndex(0);
        StatusPsiko.setSelectedIndex(0);
        KetPsiko.setText("");
        HubunganKeluarga.setSelectedIndex(0);
        TinggalDengan.setSelectedIndex(0);
        KetTinggal.setText("");
        Ekonomi.setSelectedIndex(0);
        StatusBudaya.setSelectedIndex(0);
        KetBudaya.setText("");
        Edukasi.setSelectedIndex(0);
        KetEdukasi.setText("");
        
        resiko.setSelectedIndex(0);
        TingkatResiko.setText("");
        SG1.setSelectedIndex(0);
        NilaiGizi1.setText("0");
        SG2.setSelectedIndex(0);
        NilaiGizi2.setText("0");
        SG3.setSelectedIndex(0);
        NilaiGizi3.setText("0");
        SG4.setSelectedIndex(0);
        NilaiGizi4.setText("0");
        TotalNilaiGizi.setText("0");
        Nyeri.setSelectedIndex(0);
        SkalaWajah.setSelectedIndex(0);
        NilaiWajah.setText("0");
        SkalaKaki.setSelectedIndex(0);
        NilaiKaki.setText("0");
        SkalaAktifitas.setSelectedIndex(0);
        NilaiAktifitas.setText("0");
        SkalaMenangis.setSelectedIndex(0);
        NilaiMenangis.setText("0");
        SkalaBersuara.setSelectedIndex(0);
        NilaiBersuara.setText("0");
        SkalaNyeri.setText("0");
        Lokasi.setText("");
        Durasi.setText("");
        NyeriHilang.setSelectedIndex(0);
        KetNyeri.setText("");
        mobilitas.setSelectedIndex(0);
        lanjutan.setSelectedIndex(0);
        bantuan.setSelectedIndex(0);
        psikologis.setSelectedIndex(0);
        perdi.setSelectedIndex(0);
        perpe.setSelectedIndex(0);
        pd.setSelectedIndex(0);
        pl.setSelectedIndex(0);
        lf.setSelectedIndex(0);
        eke.setSelectedIndex(0);
        hc.setSelectedIndex(0);
        bu.setSelectedIndex(0);
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        Anamnesis.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            //Agama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            //Bahasa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            //CacatFisik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Anamnesis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            RPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            RPD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            RPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            RPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Keadaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            GCS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Kesadaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            SPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Kepala.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Mata.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Gigi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            THT.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Thoraks.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Abdomen.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Genital.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Ekstremitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Kulit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            KetFisik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            KetLokalis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Penunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),86).toString());
            Diagnosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),87).toString());
            Tatalaksana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),88).toString());
            
            AlatBantu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            KetBantu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Prothesa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            KetProthesa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            ADL.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            StatusPsiko.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            KetPsiko.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            HubunganKeluarga.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            TinggalDengan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            KetTinggal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Ekonomi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            StatusBudaya.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            KetBudaya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            Edukasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            KetEdukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            
            resiko.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            TingkatResiko.setText(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            SG1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
           // NilaiGizi1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
           NilaiGizi1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            SG2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            NilaiGizi2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            SG3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            NilaiGizi3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            SG4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            NilaiGizi4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            TotalNilaiGizi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            
            SkalaWajah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            NilaiWajah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            SkalaKaki.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            NilaiKaki.setText(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            SkalaAktifitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            NilaiAktifitas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            SkalaMenangis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            NilaiMenangis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            SkalaBersuara.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            NilaiBersuara.setText(tbObat.getValueAt(tbObat.getSelectedRow(),75).toString());
            SkalaNyeri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString());
            Nyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            Lokasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
            Durasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),79).toString());
            NyeriHilang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),80).toString());
            KetNyeri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),81).toString());            
            Penunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),82).toString());  
            Diagnosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),83).toString());  
            Tatalaksana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),84).toString());       
             mobilitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),85).toString());
            lanjutan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),86).toString());
            bantuan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),87).toString());
            psikologis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),88).toString());
            perdi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),89).toString());
            perpe.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),90).toString());
            pd.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),91).toString());
            pl.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),92).toString());
            lf.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),93).toString());
            eke.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),94).toString());
            hc.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),95).toString());
            bu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),96).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    Agama.setText(rs.getString("agama"));
                    Bahasa.setText(rs.getString("nama_bahasa"));
                    CacatFisik.setText(rs.getString("nama_cacat"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
        if(Sequel.cariInteger("select count(no_rawat) from penilaian_awal_keperawatan_ranap_bayi where no_rawat='"+TNoRw.getText()+"' ")>0){
            Suhu.setText(Sequel.cariIsi("select suhu from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            TD.setText(Sequel.cariIsi("select td from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            BB.setText(Sequel.cariIsi("select bb from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            TB.setText(Sequel.cariIsi("select tb from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            Nadi.setText(Sequel.cariIsi("select nadi from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));            
            RR.setText(Sequel.cariIsi("select rr from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            GCS.setText(Sequel.cariIsi("select gcs from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            KeluhanUtama.setText(Sequel.cariIsi("select keluhan_utama from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            RPD.setText(Sequel.cariIsi("select rpd from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            RPK.setText(Sequel.cariIsi("select rpk from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            RPO.setText(Sequel.cariIsi("select rpo from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            RPS.setText(Sequel.cariIsi("select rps from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            Alergi.setText(Sequel.cariIsi("select alergi from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            AlatBantu.setSelectedItem(Sequel.cariIsi("select alat_bantu from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            KetBantu.setText(Sequel.cariIsi("select ket_bantu from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            Prothesa.setSelectedItem(Sequel.cariIsi("select prothesa from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            KetProthesa.setText(Sequel.cariIsi("select ket_pro from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            ADL.setSelectedItem(Sequel.cariIsi("select adl from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            StatusPsiko.setSelectedItem(Sequel.cariIsi("select status_psiko from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            KetPsiko.setText(Sequel.cariIsi("select ket_psiko from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            HubunganKeluarga.setSelectedItem(Sequel.cariIsi("select hub_keluarga from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            TinggalDengan.setSelectedItem(Sequel.cariIsi("select pengasuh from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            KetTinggal.setText(Sequel.cariIsi("select ket_pengasuh from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            Ekonomi.setSelectedItem(Sequel.cariIsi("select ekonomi from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            StatusBudaya.setSelectedItem(Sequel.cariIsi("select budaya from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            KetBudaya.setText(Sequel.cariIsi("select ket_budaya from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            Edukasi.setSelectedItem(Sequel.cariIsi("select edukasi from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            KetEdukasi.setText(Sequel.cariIsi("select ket_edukasi from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            
            resiko.setSelectedItem(Sequel.cariIsi("select resiko from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            TingkatResiko.setText(Sequel.cariIsi("select TingkatResiko from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            SG1.setSelectedItem(Sequel.cariIsi("select sg1 from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            NilaiGizi1.setText(Sequel.cariIsi("select nilai1 from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            SG2.setSelectedItem(Sequel.cariIsi("select sg2 from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            NilaiGizi2.setText(Sequel.cariIsi("select nilai2 from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            SG3.setSelectedItem(Sequel.cariIsi("select sg3 from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            NilaiGizi3.setText(Sequel.cariIsi("select nilai3 from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            SG4.setSelectedItem(Sequel.cariIsi("select sg4 from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            NilaiGizi4.setText(Sequel.cariIsi("select nilai4 from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            TotalNilaiGizi.setText(Sequel.cariIsi("select total_hasil from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            SkalaWajah.setSelectedItem(Sequel.cariIsi("select wajah from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            NilaiWajah.setText(Sequel.cariIsi("select nilaiwajah from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            SkalaKaki.setSelectedItem(Sequel.cariIsi("select kaki from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            NilaiKaki.setText(Sequel.cariIsi("select nilaikaki from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            SkalaAktifitas.setSelectedItem(Sequel.cariIsi("select aktifitas from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            NilaiAktifitas.setText(Sequel.cariIsi("select nilaiaktifitas from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            SkalaMenangis.setSelectedItem(Sequel.cariIsi("select menangis from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            NilaiMenangis.setText(Sequel.cariIsi("select nilaimenangis from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            SkalaBersuara.setSelectedItem(Sequel.cariIsi("select bersuara from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            NilaiBersuara.setText(Sequel.cariIsi("select nilaibersuara from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            SkalaNyeri.setText(Sequel.cariIsi("select hasilnyeri from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            Nyeri.setSelectedItem(Sequel.cariIsi("select nyeri from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            Lokasi.setText(Sequel.cariIsi("select lokasi from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            Durasi.setText(Sequel.cariIsi("select durasi from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            NyeriHilang.setSelectedItem(Sequel.cariIsi("select nyeri_hilang from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            KetNyeri.setText(Sequel.cariIsi("select ket_nyeri from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            mobilitas.setSelectedItem(Sequel.cariIsi("select mobilitas from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            lanjutan.setSelectedItem(Sequel.cariIsi("select lanjutan from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            bantuan.setSelectedItem(Sequel.cariIsi("select bantuan from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            psikologis.setSelectedItem(Sequel.cariIsi("select psikologis from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            perdi.setSelectedItem(Sequel.cariIsi("select perdi from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            perpe.setSelectedItem(Sequel.cariIsi("select perpe from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            pd.setSelectedItem(Sequel.cariIsi("select pd from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            pl.setSelectedItem(Sequel.cariIsi("select pl from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
          
          lf.setSelectedItem(Sequel.cariIsi("select lf from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            eke.setSelectedItem(Sequel.cariIsi("select eke from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            hc.setSelectedItem(Sequel.cariIsi("select hc from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
            bu.setSelectedItem(Sequel.cariIsi("select bu from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText()));
           // ep.setSelectedItem(Sequel.cariIsi("select alergi from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
           // mo.setSelectedItem(Sequel.cariIsi("select alergi from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
           // pr.setSelectedItem(Sequel.cariIsi("select alergi from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
           // kk.setSelectedItem(Sequel.cariIsi("select alergi from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            //rll.setSelectedItem(Sequel.cariIsi("select alergi from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",TNoRw.getText())); 
            //ketrll.setText(Sequel.cariIsi("select alergi from penilaian_medis_ranap_anak where no_rawat=?",TNoRw.getText())); 
        } else if(Sequel.cariInteger("select count(no_rawat) from penilaian_awal_perina where no_rawat='"+TNoRw.getText()+"' ")>0){
            Suhu.setText(Sequel.cariIsi("select pemeriksaan_suhu from penilaian_awal_perina where no_rawat=?",TNoRw.getText()));
            TD.setText(Sequel.cariIsi("select pemeriksaan_td from penilaian_awal_perina where no_rawat=?",TNoRw.getText()));
            BB.setText(Sequel.cariIsi("select pemeriksaan_bb from penilaian_awal_perina where no_rawat=?",TNoRw.getText()));
            TB.setText(Sequel.cariIsi("select pemeriksaan_tb from penilaian_awal_perina where no_rawat=?",TNoRw.getText()));
            Nadi.setText(Sequel.cariIsi("select pemeriksaan_nadi from penilaian_awal_perina where no_rawat=?",TNoRw.getText()));            
            RR.setText(Sequel.cariIsi("select pemeriksaan_rr from penilaian_awal_perina where no_rawat=?",TNoRw.getText()));
            GCS.setText(Sequel.cariIsi("select pemeriksaan_gcs from penilaian_awal_perina where no_rawat=?",TNoRw.getText()));
            KeluhanUtama.setText(Sequel.cariIsi("select rps from penilaian_awal_perina where no_rawat=?",TNoRw.getText()));
          
        } /* else if(Sequel.cariInteger("select count(no_rawat) from pemeriksaan_ranap where no_rawat='"+TNoRw.getText()+"' ")>0){
            Suhu.setText(Sequel.cariIsi("select suhu_tubuh from pemeriksaan_ranap where no_rawat=?",TNoRw.getText()));
            TD.setText(Sequel.cariIsi("select tensi from pemeriksaan_ranap where no_rawat=?",TNoRw.getText()));
            BB.setText(Sequel.cariIsi("select berat from pemeriksaan_ranap where no_rawat=?",TNoRw.getText()));
            TB.setText(Sequel.cariIsi("select tinggi from pemeriksaan_ranap where no_rawat=?",TNoRw.getText()));
            Nadi.setText(Sequel.cariIsi("select nadi from pemeriksaan_ranap where no_rawat=?",TNoRw.getText()));
            RR.setText(Sequel.cariIsi("select respirasi from pemeriksaan_ranap where no_rawat=?",TNoRw.getText()));
            GCS.setText(Sequel.cariIsi("select gcs from pemeriksaan_ranap where no_rawat=?",TNoRw.getText()));
            KeluhanUtama.setText(Sequel.cariIsi("select keluhan from pemeriksaan_ranap where no_rawat=?",TNoRw.getText()));
            Alergi.setText(Sequel.cariIsi("select alergi from pemeriksaan_ranap where no_rawat=?",TNoRw.getText()));
            } */
    }
 
    public void setNoRm(String norwt,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_awal_medis_ralan_anak());
        BtnHapus.setEnabled(akses.getpenilaian_awal_medis_ralan_anak());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_ralan_anak());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
            }
        }            
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_medis_ranap_anak where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("penilaian_medis_ranap_anak","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,keluhan_utama=?,rps=?,rpk=?,rpd=?,rpo=?,alergi=?,keadaan=?,gcs=?,kesadaran=?,td=?,nadi=?,rr=?,suhu=?,"+
                "spo=?,bb=?,tb=?,kepala=?,mata=?,gigi=?,tht=?,thoraks=?,abdomen=?,genital=?,ekstremitas=?,kulit=?,ket_fisik=?,ket_lokalis=?,penunjang=?,diagnosis=?,tata=?,"+
                
                "alat_bantu=?,ket_bantu=?,prothesa=?,ket_pro=?,adl=?,status_psiko=?,ket_psiko=?,hub_keluarga=?,tinggal_dengan=?,ket_tinggal=?, "+
                "ekonomi=?,budaya=?,ket_budaya=?,edukasi=?,ket_edukasi=?, "+
                "skor=?,skala=?,sg1=?,nilai1=?,sg2=?,nilai2=?,sg3=?,nilai3=?,sg4=?,nilai4=?,total_hasil=?,"+
                "wajah=?,nilaiwajah=?,kaki=?,nilaikaki=?,aktifitas=?,nilaiaktifitas=?,menangis=?,nilaimenangis=?,"+
                "bersuara=?,nilaibersuara=?,hasilnyeri=?,nyeri=?,lokasi=?,durasi=?,nyeri_hilang=?,ket_nyeri=?,mobilitas=?,lanjutan=?,"+
                "bantuan=?,psikologis=?,perdi=?,perpe=?,pd=?,pl=?,lf=?,eke=?,hc=?,bu=?",90,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    KeluhanUtama.getText(),RPS.getText(),RPK.getText(),RPD.getText(),RPO.getText(),Alergi.getText(),Keadaan.getSelectedItem().toString(),GCS.getText(),Kesadaran.getSelectedItem().toString(),TD.getText(),
                    Nadi.getText(),RR.getText(),Suhu.getText(),SPO.getText(),BB.getText(),TB.getText(),Kepala.getSelectedItem().toString(),Mata.getSelectedItem().toString(),Gigi.getSelectedItem().toString(),THT.getSelectedItem().toString(),
                    Thoraks.getSelectedItem().toString(),Abdomen.getSelectedItem().toString(),Genital.getSelectedItem().toString(),Ekstremitas.getSelectedItem().toString(),Kulit.getSelectedItem().toString(),KetFisik.getText(),
                    KetLokalis.getText(),Penunjang.getText(),Diagnosis.getText(),Tatalaksana.getText(),
                   
                    AlatBantu.getSelectedItem().toString(),KetBantu.getText(),Prothesa.getSelectedItem().toString(),KetProthesa.getText(),ADL.getSelectedItem().toString(),
                    StatusPsiko.getSelectedItem().toString(),KetPsiko.getText(),HubunganKeluarga.getSelectedItem().toString(),TinggalDengan.getSelectedItem().toString(),
                    KetTinggal.getText(),Ekonomi.getSelectedItem().toString(),
                    StatusBudaya.getSelectedItem().toString(),KetBudaya.getText(),Edukasi.getSelectedItem().toString(),KetEdukasi.getText(),
                    
                    resiko.getSelectedItem().toString(),TingkatResiko.getText(),SG1.getSelectedItem().toString(),
                    NilaiGizi1.getText(),SG2.getSelectedItem().toString(),NilaiGizi2.getText(),
                    SG3.getSelectedItem().toString(),NilaiGizi3.getText(),
                    SG4.getSelectedItem().toString(),NilaiGizi4.getText(),TotalNilaiGizi.getText(),
                    SkalaWajah.getSelectedItem().toString(),NilaiWajah.getText(),SkalaKaki.getSelectedItem().toString(),
                    NilaiKaki.getText(),SkalaAktifitas.getSelectedItem().toString(),NilaiAktifitas.getText(),SkalaMenangis.getSelectedItem().toString(),
                    NilaiMenangis.getText(),SkalaBersuara.getSelectedItem().toString(),NilaiBersuara.getText(),SkalaNyeri.getText(),
                    Nyeri.getSelectedItem().toString(),Lokasi.getText(),Durasi.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),
                    mobilitas.getSelectedItem().toString(),lanjutan.getSelectedItem().toString(),bantuan.getSelectedItem().toString(),
                    psikologis.getSelectedItem().toString(),perdi.getSelectedItem().toString(),perpe.getSelectedItem().toString(),pd.getSelectedItem().toString(),
                    pl.getSelectedItem().toString(),lf.getSelectedItem().toString(),eke.getSelectedItem().toString(),hc.getSelectedItem().toString(),bu.getSelectedItem().toString(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tampil();
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }
}
