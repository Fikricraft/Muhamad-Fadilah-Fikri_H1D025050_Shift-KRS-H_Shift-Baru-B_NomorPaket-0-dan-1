/**
 * Weapon: senjata yang meningkatkan kekuatan dasar serangan.
 */
public class Weapon extends Equipment {

    /* [2] atribut instans kekal milik subclass */
    private final int kekuatanSerang;

    public Weapon(String kode, String nama, int kekuatanSerang) {
        // [3] konstruktor memanggil super(...) untuk atribut common
        super(kode, nama, Equipment.Slot.WEAPON, kekuatanSerang);
        this.kekuatanSerang = kekuatanSerang;
    }

    @Override
    public int hitungKekuatanBonus() {
        return this.kekuatanSerang;
    }

    @Override
    public String toString() {
        return "Weapon[" + getKode() + " | " + getNama()
                + " | kekuatan +" + kekuatanSerang + "]";
    }

    public int getKekuatanSerang() {
        return this.kekuatanSerang;
    }
}
