/**
 * Barang *peralatan* yang dapat dikenakan karakter untuk meningkatkan
 * statistik. Bersifat abstract; konkretnya ada Weapon, Armor, Accessory.
 */
public abstract class Equipment {

    /**
     * Kejelasan: senjata meningkatkan kekuatan serangan, armor
     * mengurangi damage yang diterima, aksesori memberikan efek lain.
     */
    public enum Slot {
        WEAPON,
        ARMOR,
        ACCESSORY
    }

    private final String kode;
    private final String nama;
    private final Slot slot;
    private final int nilaiTambah;   // peningkatan statistik utama

    public Equipment(String kode, String nama, Slot slot, int nilaiTambah) {
        this.kode = kode;
        this.nama = nama;
        this.slot = slot;
        this.nilaiTambah = nilaiTambah;
    }

    public abstract int hitungKekuatanBonus();

    /* ---- [3] getter & setter (ini immutable, hanya getter) ---- */

    public String getKode() {
        return this.kode;
    }

    public String getNama() {
        return this.nama;
    }

    public Slot getSlot() {
        return this.slot;
    }

    public int getNilaiTambah() {
        return this.nilaiTambah;
    }

    @Override
    public String toString() {
        return "E" + this.slot.toString().toUpperCase() + "[" + this.kode
                + " | " + this.nama + " | +" + this.nilaiTambah + "]";
    }
}
