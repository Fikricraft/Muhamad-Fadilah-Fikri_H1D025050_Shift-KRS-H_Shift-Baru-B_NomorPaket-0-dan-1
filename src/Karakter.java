/**
 * Superclass abstrak untuk seluruh entitas bertarung di Lembah Soedirman.
 * Menyimpan state dasar (HP, level, pengalaman) dan perilaku umum
 * (menyerang, menerima serangan, naik level).
 */
public abstract class Karakter implements Attackable, Levelable {

    /* ================= [2] IMMUTABILITY : konstanta global ================= */
    public static final String NAMA_DUNIA = "Lembah Soedirman";
    public static final int MAX_LEVEL = 50;
    public static final int HP_DASAR = 100;
    public static final int EXP_PER_LEVEL = 100;
    public static final double RASIO_KRITIS = 0.30;

    /* ================= [2] IMMUTABILITY : atribut instans kekal ================= */
    private final String idKarakter;
    private final String elemen;

    /* ================= [1] STATE ================= */
    private String nama;
    private int hp;
    private int hpMaksimal;
    private int level;
    private int exp;
    private int kekuatanDasar;

    /* ================= [3] OVERLOADED CONSTRUCTOR ================= */
    public Karakter(String nama) {
        this("KAR-000", nama, "Netral", 10);
    }

    public Karakter(String nama, int kekuatanDasar) {
        this("KAR-" + nama.toUpperCase(), nama, "Netral", kekuatanDasar);
    }

    public Karakter(String idKarakter, String nama, String elemen, int kekuatanDasar) {
        this.idKarakter = idKarakter;
        this.nama = nama;
        this.elemen = elemen;
        this.kekuatanDasar = kekuatanDasar;
        this.hpMaksimal = HP_DASAR;
        this.hp = HP_DASAR;
        this.level = 1;
        this.exp = 0;
    }

    /* ================= [7] ABSTRACT METHOD ================= */

    /**
     * Menghitung kekuatan total karakter. Wajib diimplementasikan subclass
     * karena setiap jenis karakter memiliki rumus dan percabangan sendiri.
     */
    public abstract int hitungKekuatanTotal();

    /**
     * Memproses serangan yang diterima, termasuk percabangan perubahan state
     * (misalnya mengamuk, masuk fase baru, atau memasang perisai).
     */
    public abstract void terimaSerangan(Karakter penyerang, int damage);

    /* ================= [1] METHOD PROSEDUR (void) ================= */

    /**
     * Menyerang target dan mencetak jalannya serangan.
     */
    @Override
    public int serang(Karakter target) {
        if (target == null || !target.isAlive()) {
            System.out.println("    [!] " + this.nama + " tidak menemukan target yang sah.");
            return 0;
        }
        int damage = hitungKekuatanTotal() / 4;
        if (damage < 1) {
            damage = 1;
        }
        System.out.println("    > " + this.nama + " menyerang " + target.getNama()
                + " dengan damage " + damage + ".");
        target.terimaSerangan(this, damage);
        return damage;
    }

    @Override
    public boolean isAlive() {
        return this.hp > 0;
    }

    @Override
    public void naikLevel() {
        if (this.level >= MAX_LEVEL) {
            System.out.println("    [-] " + this.nama + " sudah berada di level maksimum ("
                    + MAX_LEVEL + ").");
            return;
        }
        this.level = this.level + 1;
        this.hpMaksimal = this.hpMaksimal + 20;
        this.hp = this.hpMaksimal;
        System.out.println("    [+] " + this.nama + " NAIK LEVEL menjadi " + this.level
                + " (HP maksimal " + this.hpMaksimal + ").");
    }

    @Override
    public void tambahExp(int jumlah) {
        if (jumlah <= 0) {
            System.out.println("    [-] Pengalaman harus bernilai positif.");
            return;
        }
        this.exp = this.exp + jumlah;
        System.out.println("    [*] " + this.nama + " memperoleh " + jumlah
                + " exp (total " + this.exp + ").");
        while (this.exp >= EXP_PER_LEVEL && this.level < MAX_LEVEL) {
            this.exp = this.exp - EXP_PER_LEVEL;
            naikLevel();
        }
    }

    /**
     * @return rasio HP terhadap HP maksimal dalam rentang 0.0 - 1.0.
     */
    public double getPersenHp() {
        if (this.hpMaksimal <= 0) {
            return 0.0;
        }
        return (double) this.hp / (double) this.hpMaksimal;
    }

    /* ================= [3] ENKAPSULASI : getter & setter ================= */

    public String getIdKarakter() {
        return this.idKarakter;
    }

    public String getElemen() {
        return this.elemen;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHpMaksimal() {
        return this.hpMaksimal;
    }

    public void setHpMaksimal(int hpMaksimal) {
        this.hpMaksimal = hpMaksimal;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return this.exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getKekuatanDasar() {
        return this.kekuatanDasar;
    }

    public void setKekuatanDasar(int kekuatanDasar) {
        this.kekuatanDasar = kekuatanDasar;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + this.idKarakter + " | " + this.nama
                + " | elemen=" + this.elemen
                + " | Lv." + this.level
                + " | HP " + this.hp + "/" + this.hpMaksimal
                + " | kekuatan=" + hitungKekuatanTotal() + "]";
    }
}
