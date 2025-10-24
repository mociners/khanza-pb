package rekammedis;

public class TriasePemeriksaanData {
    private String nama_pemeriksaan;
    private String pengkajian;

    public TriasePemeriksaanData(String nama_pemeriksaan, String pengkajian) {
        this.nama_pemeriksaan = nama_pemeriksaan;
        this.pengkajian = pengkajian;
    }

    public String getNama_pemeriksaan() {
        return nama_pemeriksaan;
    }

    public void setNama_pemeriksaan(String nama_pemeriksaan) {
        this.nama_pemeriksaan = nama_pemeriksaan;
    }

    public String getPengkajian() {
        return pengkajian;
    }

    public void setPengkajian(String pengkajian) {
        this.pengkajian = pengkajian;
    }
}