/**
 * Accessory: aksesori pelengkap yang memberikan bonus tambahan
 * (misalnya koin yang lebih banyak saat drop, atau HP istirahat).
 */
public class Accessory extends Equipment {

    private final boolean mengurangiKerusakan;// akibat kebanyakan equipment
    private final String efek;

    public Accessory(String kode, String nama, int bonus, String efek) {
        super(kode, nama, Equipment.Slot.ACCESSORY, bonus);
        this.efek = efek;
        this.mengurangiKerusakan = false;
    }

    public Accessory(String kode, String nama, int bonus,
                     String efek, boolean mengurangiKerusakan) {
        super(kode, nama, Equipment.Slot.ACCESSORY, bonus);
        this.efek = efek;
        this.mengurangiKerusakan = mengurangiKerusakan;
    }

    @Override
    public int hitungKekuatanBonus() {
        // accessory tidak menambah kekuatan serangan;
        // nilai tambahan dihitung sesuai efek.
        return 0;
    }

    public String getEfek() {
        return this.efek;
    }

    @Override
    public String toString() {
        return "Accessory[" + getKode() + " | " + getNama()
                + " | bonus +" + getNilaiTambah()
                + " | efek " + this.efek + "]";
    }
}
