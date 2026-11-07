/**
 * Armor: baju pelindung yang mengurangi damage yang diterima.
 */
public class Armor extends Equipment {

    private final int pelindung;

    public Armor(String kode, String nama, int pelindung) {
        super(kode, nama, Equipment.Slot.ARMOR, pelindung);
        this.pelindung = pelindung;
    }

    @Override
    public int hitungKekuatanBonus() {
        // armor tidak menambah kekuatan serangan, melainkan
        // memberikan nilai perlindungan (dilaporkan sebagai bonus).
        return 0;
    }

    public int getPelindung() {
        return this.pelindung;
    }

    public int hitungPenurunanDamage(int damage) {
        int berkurang = damage * this.pelindung / 100;
        if (berkurang < 0) {
            berkurang = 0;
        }
        if (berkurang > damage) {
            berkurang = damage;
        }
        return berkurang;
    }

    @Override
    public String toString() {
        return "Armor[" + getKode() + " | " + getNama()
                + " | darah +" + getNilaiTambah()
                + " | tingkat pelindung " + this.pelindung + "]";
    }
}
