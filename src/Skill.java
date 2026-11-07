/**
 * Keterampilan (skill) yang dapat dipelajari karakter setelah naik level.
 * Bersifat immutable; dipilih saat karakter mendaftar dan tidak berubah.
 */
public class Skill {

    /**
     * Jenis skill yang menentukan cara penggunaannya dalam pertarungan.
     */
    public enum Jenis {
        SERANG_DEPAN,
        SERANG_DANGER,
        HEAL,
        PELINDUNG,
        KEHANCURAN,
        PEMADAM,
        PEMBUNUH,
        PENGHANCUR
    }

    /* [2] atribut instans kekal */
    private final String kode;
    private final String nama;
    private final Jenis jenis;
    private final int biayaMana;
    private final int tingkatSkill;     // kekuatan dasar skill

    /* ---- [3] OVERLOADED CONSTRUCTOR ---- */

    public Skill(String kode, String nama, Jenis jenis, int biayaMana) {
        this(kode, nama, jenis, biayaMana, 20);
    }

    public Skill(String kode, String nama, Jenis jenis,
                 int biayaMana, int tingkatSkill) {
        this.kode = kode;
        this.nama = nama;
        this.jenis = jenis;
        this.biayaMana = biayaMana;
        this.tingkatSkill = tingkatSkill;
    }

    // [3] getter & setter (immutable → hanya getter)

    public String getKode() {
        return this.kode;
    }

    public String getNama() {
        return this.nama;
    }

    public Jenis getJenis() {
        return this.jenis;
    }

    public int getBiayaMana() {
        return this.biayaMana;
    }

    public int getTingkatSkill() {
        return this.tingkatSkill;
    }

    @Override
    public String toString() {
        return "Skill[" + getJenis() + " | " + getKode()
                + " | " + getNama()
                + " | mana " + this.biayaMana
                + " | kekuatan " + this.tingkatSkill + "]";
    }
}
