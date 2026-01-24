package fungsi;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import org.apache.commons.codec.binary.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Helper class for generating QR codes locally using ZXing.
 * Replaces dependencies on external scripts like generateqrcode.php.
 */
public class QRCodeHelper {

    /**
     * Generate doctor signature QR code as Base64 data URL.
     * 
     * @param kodeDokter The doctor's code
     * @param size       QR code size in pixels
     * @return Base64 data URL of QR code image
     */
    public static String getDoctorQRBase64(String kodeDokter, int size) {
        String content = generateDoctorQRContent(kodeDokter);
        return generateQRCodeBase64(content, size);
    }

    /**
     * Generate petugas signature QR code as Base64 data URL.
     * 
     * @param nip  The staff NIP
     * @param size QR code size in pixels
     * @return Base64 data URL of QR code image
     */
    public static String getPetugasQRBase64(String nip, int size) {
        String content = generatePetugasQRContent(nip);
        return generateQRCodeBase64(content, size);
    }

    private static String generateDoctorQRContent(String kodeDokter) {
        sekuel Sequel = new sekuel();
        try {
            String namaInstansi = Sequel.cariIsi("SELECT nama_instansi FROM setting LIMIT 1");
            String kabupaten = Sequel.cariIsi("SELECT kabupaten FROM setting LIMIT 1");
            String namaDokter = Sequel.cariIsi("SELECT nm_dokter FROM dokter WHERE kd_dokter=?", kodeDokter);
            String idSidikJari = Sequel.cariIsiSmc(
                    "SELECT IFNULL(SHA1(sidikjari.sidikjari), ?) FROM sidikjari INNER JOIN pegawai ON pegawai.id=sidikjari.id WHERE pegawai.nik=?",
                    kodeDokter, kodeDokter);
            if (idSidikJari == null || idSidikJari.isEmpty()) {
                idSidikJari = kodeDokter;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String tanggal = sdf.format(new Date());

            return "Dikeluarkan di " + namaInstansi + ", Kabupaten/Kota " + kabupaten + "\n" +
                    "Ditandatangani secara elektronik oleh " + namaDokter + "\n" +
                    "ID " + idSidikJari + "\n" + tanggal;
        } catch (Exception e) {
            System.out.println("Error generating doctor QR content: " + e);
            return "TTD Elektronik - " + kodeDokter;
        }
    }

    private static String generatePetugasQRContent(String nip) {
        sekuel Sequel = new sekuel();
        try {
            String namaInstansi = Sequel.cariIsi("SELECT nama_instansi FROM setting LIMIT 1");
            String kabupaten = Sequel.cariIsi("SELECT kabupaten FROM setting LIMIT 1");
            String namaPetugas = Sequel.cariIsi("SELECT nama FROM petugas WHERE nip=?", nip);
            String idSidikJari = Sequel.cariIsiSmc(
                    "SELECT IFNULL(SHA1(sidikjari.sidikjari), ?) FROM sidikjari INNER JOIN pegawai ON pegawai.id=sidikjari.id WHERE pegawai.nik=?",
                    nip, nip);
            if (idSidikJari == null || idSidikJari.isEmpty()) {
                idSidikJari = nip;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String tanggal = sdf.format(new Date());

            return "Dikeluarkan di " + namaInstansi + ", Kabupaten/Kota " + kabupaten + "\n" +
                    "Ditandatangani secara elektronik oleh " + namaPetugas + "\n" +
                    "ID " + idSidikJari + "\n" + tanggal;
        } catch (Exception e) {
            System.out.println("Error generating petugas QR content: " + e);
            return "TTD Elektronik - " + nip;
        }
    }

    /**
     * Generate QR code as Base64 PNG data URL.
     */
    public static String generateQRCodeBase64(String content, int size) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            String base64 = Base64.encodeBase64String(imageBytes);

            return "data:image/png;base64," + base64;
        } catch (WriterException | java.io.IOException e) {
            System.out.println("Error generating QR code: " + e);
            return "";
        }
    }
}
