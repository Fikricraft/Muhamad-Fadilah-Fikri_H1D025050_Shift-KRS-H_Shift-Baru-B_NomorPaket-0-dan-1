import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manajer seluruh dunia permainan: memuat lokasi-lokasi,
 * karakter pemain (party), koin, inventori, skill yang dipelajari,
 * dan state permainan (di lokasi mana, dalam pertarungan atau tidak).
 */
public class World {

    /* ---- konstanta & immutability ---- */
    public static final String NAMA_DUNIA = Karakter.NAMA_DUNIA;
    public static final String KODE_TOKO_UTAMA = "TOK-01";
    public static final String KODE_DUNGEON = "DNG-01";
    public static final String KODE_HUTAN = "HUT-01";
    public static final String KODE_GUA = "GUA-01";
    public static final String KODE_BOSS = "BOS-01";

    /* ---- state yang bisa berubah ---- */
    private final String namaPlayer;      // nama pemain (final)
    private final List<Pahlawan> party;   // daftar karakter pemain
    private final List<Item> inventori;   // barang yang dibawa
    private final List<String> skillDipelajari; // kode skill yang telah dipelajari
    private int koin;
    private Location lokasiSekarang;
    private boolean dalamPertarungan;
    private Pahlawan heroAktif;      // karakter yang sedang dipakai pemain
    private final String tugasUtama;     // misi utama yang harus diselesaikan

    /* ---- konstruktor ---- */

    public World(String namaPlayer, String tugasUtama) {
        this.namaPlayer = namaPlayer;
        this.tugasUtama = tugasUtama;
        this.party = new ArrayList<Pahlawan>();
        this.inventori = new ArrayList<Item>();
        this.skillDipelajari = new ArrayList<String>();
        this.koin = 100;
        this.heroAktif = null;  // diisi saat karakter pertama bergabung
        // lokasi awal: Desa Utama (aman)
        this.lokasiSekarang = new Location("DES-01", "Desa Utama",
                "Pemukiman warga Lembah Soedirman yang tenang.",
                0.0);
        this.dalamPertarungan = false;
    }

    /* ---- getter & setter ---- */

    public String getNamaPlayer() {
        return this.namaPlayer;
    }

    public List<Pahlawan> getParty() {
        return this.party;
    }

    public List<Item> getInventori() {
        return this.inventori;
    }

    public List<String> getSkillDipelajari() {
        return this.skillDipelajari;
    }

    public int getKoin() {
        return this.koin;
    }

    public void setKoin(int koin) {
        this.koin = koin;
    }

    public Location getLokasiSekarang() {
        return this.lokasiSekarang;
    }

    public void setLokasiSekarang(Location lokasi) {
        this.lokasiSekarang = lokasi;
    }

    public boolean isDalamPertarungan() {
        return this.dalamPertarungan;
    }

    public void setDalamPertarungan(boolean dalamPertarungan) {
        this.dalamPertarungan = dalamPertarungan;
    }

    public String getTugasUtama() {
        return this.tugasUtama;
    }

    /* ---- method yang mengubah state ---- */

    /**
     * Menambah karakter ke party (maksimal 3).
     */
    public boolean tambahKarakter(Pahlawan karakter) {
        if (karakter == null) {
            return false;
        }
        if (this.party.size() >= 3) {
            System.out.println("  World: party sudah penuh (maksimal 3).");
            return false;
        }
        if (this.party.contains(karakter)) {
            System.out.println("  World: " + karakter.getNama()
                    + " sudah ada di party.");
            return false;
        }
        this.party.add(karakter);
        System.out.println("  World: " + karakter.getNama()
                + " bergabung dengan party.");
        if (this.heroAktif == null) {
            this.heroAktif = karakter;
        }
        return true;
    }

    /**
     * Memindahkan karakter dari party.
     */
    public Pahlawan pindahkanDariParty(Pahlawan karakter) {
        if (this.party.remove(karakter)) {
            System.out.println("  World: " + karakter.getNama()
                    + " dikeluarkan dari party.");
            if (this.heroAktif == karakter) {
                this.heroAktif = this.party.isEmpty() ? null : this.party.get(0);
            }
            return karakter;
        }
        return null;
    }

    /* ---- getter & setter hero aktif (switch character) ---- */

    /** @return karakter yang sedang dipakai pemain. */
    public Pahlawan getHeroAktif() {
        if (this.heroAktif == null && !this.party.isEmpty()) {
            this.heroAktif = this.party.get(0);
        }
        return this.heroAktif;
    }

    /**
     * Menukar karakter yang sedang dipakai. Hanya anggota party yang boleh
     * dijadikan hero aktif; karakter yang pingsan (HP 0) tidak bisa dipilih.
     *
     * @return true bila pergantian berhasil.
     */
    public boolean setHeroAktif(Pahlawan karakter) {
        if (karakter == null) {
            System.out.println("  World: karakter tidak valid.");
            return false;
        }
        if (!this.party.contains(karakter)) {
            System.out.println("  World: " + karakter.getNama()
                    + " bukan anggota party.");
            return false;
        }
        if (!karakter.isAlive()) {
            System.out.println("  World: " + karakter.getNama()
                    + " pingsan (HP 0), tidak bisa dipakai. Pulihkan di Inn dulu.");
            return false;
        }
        if (karakter == this.heroAktif) {
            System.out.println("  World: " + karakter.getNama()
                    + " sudah menjadi karakter aktif.");
            return false;
        }
        this.heroAktif = karakter;
        System.out.println("  World: karakter aktif diganti ke "
                + karakter.getNama() + ".");
        return true;
    }

    public void tambahKoin(int jumlah) {
        if (jumlah < 0) {
            this.koin = this.koin + jumlah;  // jumlah negatif = pengurangan
        } else {
            this.koin = this.koin + jumlah;
        }
        System.out.println("  World: mendapatkan " + Math.abs(jumlah)
                + " koin. Total: " + this.koin + " koin.");
    }

    /**
     * Mengurangi koin.
     */
    public boolean kurangiKoin(int jumlah) {
        if (jumlah < 0) {
            return false;
        }
        if (this.koin < jumlah) {
            return false;
        }
        this.koin = this.koin - jumlah;
        return true;
    }

    /**
     * Menambah item ke inventori.
     */
    public boolean tambahItem(Item item) {
        if (item == null) {
            return false;
        }
        // batas inventori: maksimal 20 item
        if (this.inventori.size() >= 20) {
            System.out.println("  World: inventori penuh (maksimal 20 item).");
            return false;
        }
        this.inventori.add(item);
        System.out.println("  World: item " + item.getNama()
                + " ditambahkan ke inventori.");
        return true;
    }

    /**
     * Mengambil item dari inventori berdasarkan kode.
     */
    public Item ambilItem(String kode) {
        for (int i = 0; i < this.inventori.size(); i++) {
            Item item = this.inventori.get(i);
            if (item.getKode().equals(kode)) {
                this.inventori.remove(i);
                return item;  // succes
            }
        }
        return null;
    }

    /**
     * Mencatat skill yang telah dipelajari.
     */
    public boolean catatSkillDipelajari(String kodeSkill) {
        if (kodeSkill == null || kodeSkill.isEmpty()) {
            return false;
        }
        if (this.skillDipelajari.contains(kodeSkill)) {
            return true;  // sudah tercatat
        }
        this.skillDipelajari.add(kodeSkill);
        return true;
    }

    /**
     * Memindahkan ke lokasi baru.
     */
    public boolean pindahLokasi(Location lokasiBaru) {
        if (lokasiBaru == null) {
            return false;
        }
        if (this.dalamPertarungan) {
            System.out.println("  World: tidak bisa pindah lokasi saat"
                    + " dalam pertarungan.");
            return false;
        }
        this.lokasiSekarang = lokasiBaru;
        return true;
    }

    /**
     * Menampilkan status dunia saat ini.
     */
    public void tampilkanStatus() {
        System.out.println("  === STATUS DUNIA ===");
        System.out.println("  Pemain           : " + this.namaPlayer);
        System.out.println("  Tugas utama      : " + this.tugasUtama);
        System.out.println("  Lokasi sekarang  : "
                + (this.lokasiSekarang != null
                        ? this.lokasiSekarang.getNama()
                        : "tidak ada"));
        System.out.println("  Koin             : " + this.koin);
        System.out.println("  Party            : "
                + this.party.size() + " anggota");
        System.out.println("  Inventori        : "
                + this.inventori.size() + " item");
        System.out.println("  Skill dipelajari : "
                + this.skillDipelajari.size());
        System.out.println("  Dalam pertarungan: " + this.dalamPertarungan);
    }
}
