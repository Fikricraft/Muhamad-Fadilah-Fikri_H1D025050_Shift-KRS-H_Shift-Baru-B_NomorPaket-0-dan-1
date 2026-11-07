/**
 * Subclass Karakter untuk lawan terkuat: bos penjaga Lembah Soedirman.
 * Memiliki fase pertarungan dan mode amukan yang menambah satu serangan.
 */
public class Boss extends Karakter {

    private final String gelar;
    private final int jumlahFase;

    private int fase;
    private boolean enrage;

    public Boss(String nama) {
        super(nama);
        this.gelar = "Penjaga Lembah";
        this.jumlahFase = 3;
        this.fase = 1;
        this.enrage = false;
    }

    public Boss(String nama, int kekuatanDasar, String gelar, int jumlahFase) {
        super("BOS-" + nama.toUpperCase(), nama, "Bayangan", kekuatanDasar);
        this.gelar = gelar;
        this.jumlahFase = jumlahFase;
        this.fase = 1;
        this.enrage = false;
    }

    @Override
    public int hitungKekuatanTotal() {
        int total = getKekuatanDasar() + (getLevel() * 6) + (this.fase * 20);

        if (this.enrage) {
            total = total + 40;
            System.out.println("      [Boss] fase " + this.fase + " AMUKAN -> bonus +40");
        } else {
            System.out.println("      [Boss] fase " + this.fase + " berjalan normal");
        }
        return total;
    }

    @Override
    public void terimaSerangan(Karakter penyerang, int damage) {
        setHp(getHp() - damage);

        if (getHp() <= 0) {
            setHp(0);
            System.out.println("      [Boss] " + getNama() + " akhirnya dikalahkan "
                    + penyerang.getNama() + ".");
            return;
        }

        double persen = getPersenHp();
        int faseBaru = this.jumlahFase - (int) Math.ceil(persen * this.jumlahFase) + 1;
        if (faseBaru < 1) {
            faseBaru = 1;
        }
        if (faseBaru > this.jumlahFase) {
            faseBaru = this.jumlahFase;
        }
        if (faseBaru < this.fase) {
            faseBaru = this.fase;
        }

        if (faseBaru != this.fase) {
            System.out.println("      [Boss] HP " + getHp() + "/" + getHpMaksimal()
                    + " -> FASE BERUBAH dari " + this.fase + " ke " + faseBaru + "!");
            this.fase = faseBaru;
        } else {
            System.out.println("      [Boss] sisa HP " + getHp() + "/" + getHpMaksimal()
                    + " (fase " + this.fase + ").");
        }

        if (persen <= 0.25 && !this.enrage) {
            this.enrage = true;
            System.out.println("      [Boss] " + getNama() + " memasuki MODE AMUKAN!");
        }
    }

    /* [5] SUPER CHAINING : memanggil super.serang() lalu menambah serangan kedua */
    @Override
    public int serang(Karakter target) {
        int totalDamage = super.serang(target);

        if (this.enrage && target != null && target.isAlive()) {
            System.out.println("      [Boss] mode amukan -> serangan kedua menyusul!");
            totalDamage = totalDamage + super.serang(target);
        }
        return totalDamage;
    }

    @Override
    public String toString() {
        return super.toString() + " <" + this.gelar + " | fase " + this.fase + "/"
                + this.jumlahFase + " | enrage=" + this.enrage + ">";
    }

    public String getGelar() {
        return this.gelar;
    }

    public int getJumlahFase() {
        return this.jumlahFase;
    }

    public int getFase() {
        return this.fase;
    }

    public void setFase(int fase) {
        this.fase = fase;
    }

    public boolean isEnrage() {
        return this.enrage;
    }

    public void setEnrage(boolean enrage) {
        this.enrage = enrage;
    }
}
