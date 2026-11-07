import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Mesin pertarungan turn-based sederhana antara seorang Pahlawan dan satu lawan.
 */
public class Pertarungan {

    private final Pahlawan pahlawan;
    private final Karakter lawan;
    private final int rondeMaksimal;
    private final List<String> catatan;

    private int ronde;

    public Pertarungan(Pahlawan pahlawan, Karakter lawan) {
        this(pahlawan, lawan, 12);
    }

    public Pertarungan(Pahlawan pahlawan, Karakter lawan, int rondeMaksimal) {
        this.pahlawan = pahlawan;
        this.lawan = lawan;
        this.rondeMaksimal = rondeMaksimal;
        this.ronde = 0;
        this.catatan = new ArrayList<>();
    }

    /**
     * Menjalankan pertarungan sampai salah satu pihak tumbang atau ronde habis.
     *
     * @return true bila pahlawan menang.
     */
    public boolean jalankan(Scanner sc) {
        System.out.println("    PERTARUNGAN: " + this.pahlawan.getNama()
                + " vs " + this.lawan.getNama()
                + " (" + this.rondeMaksimal + " ronde maksimal)");
        System.out.println("    ==============================");

        while (this.ronde < this.rondeMaksimal
                && this.pahlawan.isAlive()
                && this.lawan.isAlive()) {
            this.ronde = this.ronde + 1;
            System.out.println("    --- Ronde " + this.ronde + " ---");
            System.out.println("    " + this.pahlawan.getNama()
                    + " [HP " + this.pahlawan.getHp() + "/"
                    + this.pahlawan.getHpMaksimal() + " | Mana "
                    + this.pahlawan.getMana() + "/"
                    + this.pahlawan.getManaMaksimal() + "]");
            System.out.println("    Lawan: " + this.lawan.getNama()
                    + " [HP " + this.lawan.getHp() + "/"
                    + this.lawan.getHpMaksimal() + "]\n");

            // Tampilkan menu aksi
            System.out.println("    === AKSI PAHLAWAN ===");
            for (AksiBattle aksi : AksiBattle.values()) {
                System.out.println("    " + (aksi.ordinal() + 1)
                        + ". " + aksi.getLabel());
            }
            System.out.print("    Pilih aksi (1-5): ");
            // tahan EOF / input habis: mode pembuktian berjalan tanpa input
            String pilihan;
            try {
                pilihan = sc.nextLine().trim();
            } catch (RuntimeException e) {
                pilihan = "1";
                System.out.println("(tanpa input -> otomatis Serang)");
            }
            // default ke serang kalau input kosong / invalid
            int pilihanIdx = -1;
            try {
                pilihanIdx = Integer.parseInt(pilihan) - 1;
            } catch (NumberFormatException e) {
                pilihanIdx = 0;
            }
            if (pilihanIdx < 0 || pilihanIdx >= AksiBattle.values().length) {
                pilihanIdx = 0;
            }
            AksiBattle pilihanAksi = AksiBattle.values()[pilihanIdx];
            System.out.println("    -> Kamu memilih: " + pilihanAksi.getLabel());
            this.pahlawan.prosesAksi(String.valueOf(pilihanIdx + 1), java.util.List.of(this.lawan));

            if (!this.lawan.isAlive()) {
                break;
            }

            // Giliran lawan (motorik)
            System.out.println("    --- Giliran Lawan ---");
            this.lawan.serang(this.pahlawan);
            System.out.println("    " + this.lawan.getNama()
                    + " menyerang! Sisa HP: "
                    + this.lawan.getHp() + "/" + this.lawan.getHpMaksimal());

            if (!this.pahlawan.isAlive()) {
                break;
            }

            // Auto-use item kalau HP lambat
            if (this.pahlawan.getPersenHp() <= 0.50) {
                Item ramuan = new Item("ITM-01", "Ramuan Herbal",
                        Item.Jenis.PENYEMBUH, 30);
                this.pahlawan.gunakanItem(ramuan);
                System.out.println("    (Otomatis memakai ramuan herbal)");
            }
        }

        boolean menang = !this.lawan.isAlive() && this.pahlawan.isAlive();
        this.catatan.add("Ronde terpakai: " + this.ronde);
        this.catatan.add("Sisa HP pahlawan: " + this.pahlawan.getHp());
        this.catatan.add("Sisa HP lawan: " + this.lawan.getHp());
        this.catatan.add("Hasil: "
                + (menang ? "PAHLAWAN MENANG" : "PAHLAWAN KALAH"));

        System.out.println("    ==============================");
        System.out.println("    === HASIL AKHIR ===");
        for (String baris : this.catatan) {
            System.out.println("    " + baris);
        }

        if (menang) {
            this.pahlawan.tambahExp(150);
        }
        return menang;
    }

    /**
     * Menjalankan battle otomatis tanpa membaca terminal.
     * Dipakai mode pembuktian agar tidak menunggu input pengguna.
     */
    public boolean jalankanOtomatis() {
        Scanner tanpaInput = new Scanner(
                new java.io.ByteArrayInputStream(new byte[0]));
        try {
            return jalankan(tanpaInput);
        } finally {
            tanpaInput.close();
        }
    }

    public Pahlawan getPahlawan() {
        return this.pahlawan;
    }

    public Karakter getLawan() {
        return this.lawan;
    }

    public int getRonde() {
        return this.ronde;
    }

    public int getRondeMaksimal() {
        return this.rondeMaksimal;
    }

    public List<String> getCatatan() {
        return this.catatan;
    }
}
