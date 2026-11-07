/**
 * Subclass Karakter untuk monster biasa penghuni Lembah Soedirman.
 * Memiliki mekanisme "mengamuk" ketika HP menipis.
 */
public class Monster extends Karakter {

    private final String habitat;
    private final int tingkatKeganasan;

    private boolean mengamuk;

    public Monster(String nama) {
        super(nama);
        this.habitat = "Hutan Pinus";
        this.tingkatKeganasan = 1;
        this.mengamuk = false;
    }

    public Monster(String nama, int kekuatanDasar, String habitat, int tingkatKeganasan) {
        super("MNS-" + nama.toUpperCase(), nama, "Kegelapan", kekuatanDasar);
        this.habitat = habitat;
        this.tingkatKeganasan = tingkatKeganasan;
        this.mengamuk = false;
    }

    @Override
    public int hitungKekuatanTotal() {
        int total = getKekuatanDasar() + (getLevel() * 4) + (this.tingkatKeganasan * 3);

        if (this.mengamuk) {
            total = total * 2;
            System.out.println("      [Monster] sedang MENGAMUK -> kekuatan dilipatgandakan");
        } else {
            System.out.println("      [Monster] masih tenang -> kekuatan normal");
        }
        return total;
    }

    @Override
    public void terimaSerangan(Karakter penyerang, int damage) {
        setHp(getHp() - damage);

        if (getHp() <= 0) {
            setHp(0);
            System.out.println("      [Monster] " + getNama() + " tumbang oleh "
                    + penyerang.getNama() + ".");
        } else if (getPersenHp() <= 0.40 && !this.mengamuk) {
            this.mengamuk = true;
            System.out.println("      [Monster] HP tersisa " + getHp()
                    + " -> " + getNama() + " MENGAMUK!");
        } else {
            System.out.println("      [Monster] sisa HP " + getHp() + "/" + getHpMaksimal() + ".");
        }
    }

    @Override
    public String toString() {
        return super.toString() + " <habitat=" + this.habitat + " | keganasan="
                + this.tingkatKeganasan + " | mengamuk=" + this.mengamuk + ">";
    }

    public String getHabitat() {
        return this.habitat;
    }

    public int getTingkatKeganasan() {
        return this.tingkatKeganasan;
    }

    public boolean isMengamuk() {
        return this.mengamuk;
    }

    public void setMengamuk(boolean mengamuk) {
        this.mengamuk = mengamuk;
    }
}
