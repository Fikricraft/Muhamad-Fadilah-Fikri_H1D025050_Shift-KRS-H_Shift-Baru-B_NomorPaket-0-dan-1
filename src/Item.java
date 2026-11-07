/**
 * Item yang dapat dipakai karakter selama pertarungan.
 * Seluruh atribut bersifat final karena item tidak berubah setelah dibuat.
 */
public class Item {

    /**
     * Jenis item menentukan efek yang diberikan saat dipakai.
     */
    public enum Jenis {
        PENYEMBUH,
        MANA,
        SERANGAN
    }

    private final String kode;
    private final String nama;
    private final Jenis jenis;
    private final int nilaiPulih;

    public Item(String kode, String nama, Jenis jenis, int nilaiPulih) {
        this.kode = kode;
        this.nama = nama;
        this.jenis = jenis;
        this.nilaiPulih = nilaiPulih;
    }

    public String getKode() {
        return this.kode;
    }

    public String getNama() {
        return this.nama;
    }

    public Jenis getJenis() {
        return this.jenis;
    }

    public int getNilaiPulih() {
        return this.nilaiPulih;
    }

    @Override
    public String toString() {
        return "Item[" + this.kode + " | " + this.nama + " | " + this.jenis
                + " | nilai=" + this.nilaiPulih + "]";
    }
}
