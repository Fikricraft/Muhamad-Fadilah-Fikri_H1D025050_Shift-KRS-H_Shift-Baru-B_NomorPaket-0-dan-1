import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Mesin pertarungan turn-based yang mendukung beberapa musuh
 * sekaligus (party vs gugus musuh). Setiap pemain memilih aksi
 * dari menu:
 *   1. Serang
 *   2. Pertahanan
 *   3. Pakai Item
 *   4. Pakai Skill
 *   5. Lari
 */
public class PartyBattle {

    private final Pahlawan pahlawan;
    private final List<Karakter> musuh;
    private final int rondeMaksimal;
    private final List<String> catatan;

    private int ronde;
    private int indeksPahlawan;   // giliran siapa

    public PartyBattle(Pahlawan pahlawan, List<Karakter> musuh, int rondeMaksimal) {
        this.pahlawan = pahlawan;
        this.musuh = musuh;
        this.rondeMaksimal = rondeMaksimal;
        this.ronde = 0;
        this.indeksPahlawan = 0;
        this.catatan = new ArrayList<String>();
    }

    /**
     * Menjalankan pertarungan sampai salah satu pihak tumbang atau
     * ronde habis. Mengembalikan true bila pahlawan menang.
     */
    public boolean jalankan(Scanner sc) {
        System.out.println("    PERTARUNGAN: " + this.pahlawan.getNama()
                + " vs " + this.musuh.size() + " musuh");
        System.out.println("    ==============================");

        while (this.ronde < this.rondeMaksimal
                && this.pahlawan.isAlive()
                && this.musuhAdaMati()) {
            this.ronde = this.ronde + 1;
            System.out.println("    --- Ronde " + this.ronde + " ---");
            System.out.println("    " + this.pahlawan.getNama()
                    + " [HP " + this.pahlawan.getHp() + "/"
                    + this.pahlawan.getHpMaksimal() + " | Mana "
                    + this.pahlawan.getMana() + "/"
                    + this.pahlawan.getManaMaksimal() + "]");
            System.out.println("    Musuh (" + this.musuh.size() + "):");
            for (Karakter m : this.musuh) {
                if (m.isAlive()) {
                    System.out.println("      - " + m.getNama()
                            + " [HP " + m.getHp() + "/"
                            + m.getHpMaksimal() + "]");
                }
            }
            System.out.println();

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
            int pilihanIdx = 0;
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
            this.pahlawan.prosesAksi(String.valueOf(pilihanIdx + 1), this.musuh);

            if (!this.pahlawan.isAlive()) {
                break;
            }

            // Giliran musuh (satu musuh menyerang per ronde)
            System.out.println("    --- Giliran Musuh ---");
            Karakter penyerangMusuh = pilihMusuhAktif();
            if (penyerangMusuh != null && penyerangMusuh.isAlive()) {
                penyerangMusuh.serang(this.pahlawan);
                System.out.println("    " + penyerangMusuh.getNama()
                        + " menyerang! Sisa HP: "
                        + penyerangMusuh.getHp() + "/"
                        + penyerangMusuh.getHpMaksimal());
            }

            if (!this.pahlawan.isAlive()) {
                break;
            }

            // Auto-use item kalau HP rendah
            if (this.pahlawan.getPersenHp() <= 0.50
                    && this.pahlawan.getJumlahItem() > 0) {
                this.pahlawan.gunakanItem(
                        new Item("ITM-01", "Ramuan Herbal",
                                Item.Jenis.PENYEMBUH, 30));
                System.out.println("    (Otomatis memakai ramuan herbal)");
            }

            this.catatan.add("HP Pahlawan: "
                    + this.pahlawan.getHp() + "/"
                    + this.pahlawan.getHpMaksimal());
            this.catatan.add("HP musuh: "
                    + this.musuh.stream()
                    .mapToInt(Karakter::getHp)
                    .sum() + " total");
        }

        // ringkasan
        boolean menang = !this.musuhAdaMati() && this.pahlawan.isAlive();
        this.catatan.add("Ronde terpakai: " + this.ronde);
        this.catatan.add("Hasil: "
                + (menang ? "PAHLAWAN MENANG" : "PAHLAWAN KALAH"));

        System.out.println("    ==============================");
        System.out.println("    === HASIL AKHIR ===");
        for (String baris : this.catatan) {
            System.out.println("    " + baris);
        }
        if (menang) {
            this.pahlawan.tambahExp(this.musuh.size() * 80);
            kasihReward(menang);
        }
        return menang;
    }

    private boolean musuhAdaMati() {
        for (Karakter m : this.musuh) {
            if (m.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private Karakter pilihMusuhAktif() {
        List<Karakter> hidup = new ArrayList<Karakter>();
        for (Karakter m : this.musuh) {
            if (m.isAlive()) {
                hidup.add(m);
            }
        }
        if (hidup.isEmpty()) {
            return null;
        }
        // pilih satu yang HP-nya paling rendah
        Karakter pilih = hidup.get(0);
        int minHp = pilih.getHp();
        for (int i = 1; i < hidup.size(); i++) {
            Karakter m = hidup.get(i);
            if (m.getHp() < minHp) {
                minHp = m.getHp();
                pilih = m;
            }
        }
        return pilih;
    }

    private void kasihReward(boolean menang) {
        // hadiah koin dan item
        int koinReward = this.ronde * 15 + 10;
        System.out.println("    Pahlawan menerima " + koinReward
                + " koin sebagai hadiah.");
    }

    public Pahlawan getPahlawan() {
        return this.pahlawan;
    }

    public List<Karakter> getMusuh() {
        return this.musuh;
    }

    public int getRonde() {
        return this.ronde;
    }

    public int getRondeMaksimal() {
        return this.rondeMaksimal;
    }
}
