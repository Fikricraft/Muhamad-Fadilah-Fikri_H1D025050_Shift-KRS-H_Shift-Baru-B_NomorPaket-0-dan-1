import java.util.Random;

/**
 * Lokasi di dalam dunia Lembah Soedirman. Karakter dapat berpindah
 * antar lokasi dan setiap lokasi memiliki aktivitas berbeda.
 */
public class Location {

    /* [2] atribut instans kekal */
    private final String kode;
    private final String nama;
    private final String deskripsi;
    private final double tingkatBahaya;     // 0.0 = aman, 1.0 = berbahaya

    /* state yang berubah-ubah */
    private int minEnkounterLebih;
    private int maxEnkounterLebih;

    public Location(String kode, String nama, String deskripsi) {
        this(kode, nama, deskripsi, 0.2);
    }

    public Location(String kode, String nama, String deskripsi,
                    double tingkatBahaya) {
        this.kode = kode;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.tingkatBahaya = tingkatBahaya;
        this.minEnkounterLebih = 1;
        this.maxEnkounterLebih = 2;
    }

    // [3] getter & setter

    public String getKode() {
        return this.kode;
    }

    public String getNama() {
        return this.nama;
    }

    public String getDeskripsi() {
        return this.deskripsi;
    }

    public double getTingkatBahaya() {
        return this.tingkatBahaya;
    }

    public int getMinEnkounterLebih() {
        return this.minEnkounterLebih;
    }

    public int getMaxEnkounterLebih() {
        return this.maxEnkounterLebih;
    }

    public void setMinEnkounterLebih(int minEnkounterLebih) {
        this.minEnkounterLebih = minEnkounterLebih;
    }

    public void setMaxEnkounterLebih(int maxEnkounterLebih) {
        this.maxEnkounterLebih = maxEnkounterLebih;
    }

    /**
     * Menentukan apakah karakter yang memasuki lokasi
     * bersyarat: jika bahaya tinggi, akan muncul enkounter.
     */
    public boolean munculEnkounterAcak(Random rnd) {
        if (this.tingkatBahaya <= 0.0) {
            return false;
        }
        return rnd.nextDouble() < this.tingkatBahaya;
    }

    @Override
    public String toString() {
        return "Lokasi[" + this.kode + " | " + this.nama
                + " | bahaya " + String.format("%.1f", this.tingkatBahaya) + "]";
    }
}
