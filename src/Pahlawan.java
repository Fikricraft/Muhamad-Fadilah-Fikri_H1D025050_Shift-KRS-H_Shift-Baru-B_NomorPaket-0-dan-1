import java.util.ArrayList;
import java.util.List;

/**
 * Subclass Karakter yang dikendalikan pemain.
 * Mengimplementasikan tiga interface sekaligus: Attackable, Levelable (warisan
 * dari Karakter) dan ItemUsable, serta memiliki sistem peralatan, skill,
 * koin, dan lokasi.
 */
public class Pahlawan extends Karakter implements ItemUsable, Levelable, Attackable {

    /* [2] atribut instans kekal milik subclass */
    private final String kelas;
    private final int manaMaksimal;
    private final String lokasiLahir;     // kota kelahiran karakter

    /* [1] state yang boleh berubah */
    private int mana;
    private int jumlahRamuan;
    private boolean modeBertahan;

    /* sistem peralatan */
    private Equipment senjata;
    private Equipment armor;
    private Equipment aksesori;

    /* skill yang telah dipelajari */
    private final List<Skill> skillDipelajari;

    /* koin yang dimiliki karakter */
    private int koin;

    /* [3] konstruktor overload 1 */
    public Pahlawan(String nama) {
        this(nama, 10, "Petarung", 60, "Kota A");
    }

    /* [3] konstruktor overload 2 - kompatibilitas lama (tanpa lokasi) */
    public Pahlawan(String nama, int kekuatanDasar, String kelas, int manaMaksimal) {
        this(nama, kekuatanDasar, kelas, manaMaksimal, "Kota A");
    }

    /* [3] konstruktor overload 3 - memakai super(...) lima argumen */
    public Pahlawan(String nama, int kekuatanDasar, String kelas,
                   int manaMaksimal, String lokasiLahir) {
        super("PHL-" + nama.toUpperCase(), nama, "Cahaya", kekuatanDasar);
        this.kelas = kelas;
        this.manaMaksimal = manaMaksimal;
        this.lokasiLahir = lokasiLahir;
        this.mana = manaMaksimal;
        this.jumlahRamuan = 5;
        this.modeBertahan = false;
        this.senjata = null;
        this.armor = null;
        this.aksesori = null;
        this.skillDipelajari = new ArrayList<Skill>();
        this.koin = 0;
    }

    /* ---- [7][8] overriding + implementasi abstract method + percabangan ---- */

    @Override
    public int hitungKekuatanTotal() {
        int total = getKekuatanDasar() + (getLevel() * 5);

        if (this.senjata != null) {
            int bonus = this.senjata.hitungKekuatanBonus();
            total = total + bonus;
            System.out.println("      [Pahlawan] senjata " + this.senjata.getNama()
                    + " memberikan bonus kekuatan +" + bonus);
        }

        if (this.mana >= this.manaMaksimal / 2) {
            total = total + 15;
            System.out.println("      [Pahlawan] mana " + this.mana
                    + " cukup -> bonus sihir +15");
        } else {
            total = total - 10;
            if (total < 1) { total = 1; }
            System.out.println("      [Pahlawan] mana " + this.mana
                    + " menipis -> penalti -10");
        }

        if (this.armor != null) {
            int pelindung = ((Armor) this.armor).getPelindung();
            System.out.println("      [Pahlawan] armor " + this.armor.getNama()
                    + " sedang terpasang (pelindung " + pelindung + ")");
        }

        if (this.modeBertahan) {
            total = total / 2;
            System.out.println("      [Pahlawan] mode bertahan aktif -> kekuatan dibagi dua");
        }

        if (getPersenHp() <= RASIO_KRITIS) {
            total = total + 25;
            System.out.println("      [Pahlawan] HP kritis -> semangat juang +25");
        }

        return total;
    }

    @Override
    public void terimaSerangan(Karakter penyerang, int damage) {
        int diterima = damage;

        if (this.armor != null) {
            int berkurang = ((Armor) this.armor).hitungPenurunanDamage(damage);
            diterima = damage - berkurang;
            System.out.println("      [Pahlawan] armor " + this.armor.getNama()
                    + " mengurangi damage dari " + damage + " menjadi " + diterima + ".");
        }

        if (this.modeBertahan) {
            diterima = diterima / 2;
            System.out.println("      [Pahlawan] perisai menahan serangan ("
                    + damage + " menjadi " + diterima + ").");
        }

        setHp(getHp() - diterima);

        if (getHp() <= 0) {
            setHp(0);
            System.out.println("      [Pahlawan] " + getNama()
                    + " gugur melawan " + penyerang.getNama() + ".");
        } else if (getPersenHp() <= RASIO_KRITIS) {
            this.modeBertahan = true;
            System.out.println("      [Pahlawan] HP tersisa " + getHp()
                    + " -> mode bertahan otomatis AKTIF.");
        } else {
            System.out.println("      [Pahlawan] sisa HP "
                    + getHp() + "/" + getHpMaksimal() + ".");
        }
    }

    /* ---- [5] SUPER CHAINING : super.naikLevel() ---- */
    @Override
    public void naikLevel() {
        super.naikLevel();
        this.mana = this.manaMaksimal;
        System.out.println("      [Pahlawan] super.naikLevel() selesai"
                + " -> mana dipulihkan ke " + this.mana + ".");
    }

    /* ---- [10] implementasi interface ItemUsable ---- */
    @Override
    public boolean gunakanItem(Item item) {
        if (item == null) {
            System.out.println("      [Pahlawan] item tidak valid.");
            return false;
        }
        if (this.jumlahRamuan <= 0) {
            System.out.println("      [Pahlawan] persediaan item habis.");
            return false;
        }
        this.jumlahRamuan = this.jumlahRamuan - 1;
        if (item.getJenis() == Item.Jenis.PENYEMBUH) {
            int hpBaru = getHp() + item.getNilaiPulih();
            if (hpBaru > getHpMaksimal()) { hpBaru = getHpMaksimal(); }
            setHp(hpBaru);
            System.out.println("      [Pahlawan] memakai " + item.getNama()
                    + " -> HP menjadi " + getHp() + "/"
                    + getHpMaksimal() + ".");
        } else if (item.getJenis() == Item.Jenis.MANA) {
            this.mana = this.mana + item.getNilaiPulih();
            if (this.mana > this.manaMaksimal) { this.mana = this.manaMaksimal; }
            System.out.println("      [Pahlawan] memakai " + item.getNama()
                    + " -> mana menjadi " + this.mana + ".");
        } else {
            setKekuatanDasar(getKekuatanDasar() + item.getNilaiPulih());
            System.out.println("      [Pahlawan] memakai " + item.getNama()
                    + " -> kekuatan dasar menjadi " + getKekuatanDasar() + ".");
        }
        return true;
    }

    @Override
    public int getJumlahItem() { return this.jumlahRamuan; }

    /**
     * Memproses aksi pemain dalam pertarungan.
     * pilihan: "1"=serang, "2"=pertahanan, "3"=item, "4"=skill, "5"=lari
     */
    public void prosesAksi(String pilihan, List<Karakter> musuh) {
        if (pilihan == null) { pilihan = "1"; }
        switch (pilihan) {
            case "1":
                System.out.println("    [Pahlawan] memilih Serang...");
                serangMusuhTerbesar(musuh);
                break;
            case "2":
                System.out.println("    [Pahlawan] memilih Pertahanan...");
                this.modeBertahan = true;
                System.out.println("      mode bertahan diaktifkan.");
                break;
            case "3":
                System.out.println("    [Pahlawan] memilih Pakai Item...");
                this.gunakanItem(new Item("ITM-01", "Ramuan Herbal",
                        Item.Jenis.PENYEMBUH, 30));
                break;
            case "4":
                System.out.println("    [Pahlawan] memilih Pakai Skill...");
                if (this.skillDipelajari.isEmpty()) {
                    System.out.println("      tidak ada skill yang dipelajari.");
                } else {
                    Skill s = this.skillDipelajari.get(0);
                    System.out.println("      menggunakan skill " + s.getNama()
                            + " (biaya mana " + s.getBiayaMana() + ")");
                    if (this.mana >= s.getBiayaMana()) {
                        this.mana = this.mana - s.getBiayaMana();
                        System.out.println("      skill berhasil digunakan.");
                        for (Karakter m : musuh) {
                            int dmg = s.getTingkatSkill() + (getLevel() * 3);
                            m.terimaSerangan(this, dmg);
                        }
                    } else {
                        System.out.println("      mana tidak cukup untuk skill ini.");
                    }
                }
                break;
            case "5":
                System.out.println("    [Pahlawan] mencoba melarikan diri...");
                if (Math.random() < 0.25) {
                    System.out.println("      berhasil melarikan diri!");
                } else {
                    System.out.println("      gagal melarikan diri,"
                            + " musuh menyerang.");
                    if (!musuh.isEmpty()) { musuh.get(0).serang(this); }
                }
                break;
            default:
                System.out.println("    [Pahlawan] pilihan tidak dikenal,"
                        + " menyerang sebagai default...");
                serangMusuhTerbesar(musuh);
                break;
        }
        this.modeBertahan = false;
    }

    private void serangMusuhTerbesar(List<Karakter> musuh) {
        if (musuh == null || musuh.isEmpty()) {
            System.out.println("      tidak ada musuh yang menyerang.");
            return;
        }
        Karakter target = musuh.get(0);
        int maxHp = target.getHp();
        for (Karakter m : musuh) {
            if (m.getHp() > maxHp) { maxHp = m.getHp(); target = m; }
        }
        this.serang(target);
    }

    /* ---- method peralatan ---- */
    public void setSenjata(Equipment senjata) {
        this.senjata = senjata;
        System.out.println("  Pahlawan: senjata diganti menjadi "
                + senjata.getNama() + ".");
    }
    public Equipment getSenjata() { return this.senjata; }
    public void setArmor(Equipment armor) {
        this.armor = armor;
        System.out.println("  Pahlawan: armor diganti menjadi "
                + armor.getNama() + ".");
    }
    public Equipment getArmor() { return this.armor; }
    public void setAksesori(Equipment aksesori) {
        this.aksesori = aksesori;
        System.out.println("  Pahlawan: aksesori diganti menjadi "
                + aksesori.getNama() + ".");
    }
    public Equipment getAksesori() { return this.aksesori; }

    public void tambahSkill(Skill skill) {
        if (skill == null) return;
        if (this.skillDipelajari.contains(skill)) return;
        this.skillDipelajari.add(skill);
        System.out.println("  Pahlawan: mempelajari skill "
                + skill.getNama() + ".");
    }
    public List<Skill> getSkillDipelajari() { return this.skillDipelajari; }

    /* ---- getter & setter lama yang masih dipakai Main ---- */
    public String getKelas() { return this.kelas; }
    public int getManaMaksimal() { return this.manaMaksimal; }
    public int getMana() { return this.mana; }
    public void setMana(int mana) { this.mana = mana; }
    public boolean isModeBertahan() { return this.modeBertahan; }
    public void setModeBertahan(boolean modeBertahan) {
        this.modeBertahan = modeBertahan;
    }

    /* ---- koin ---- */
    public int getKoin() { return this.koin; }
    public void setKoin(int koin) { this.koin = koin; }
    public void tambahKoin(int koin) { this.koin = this.koin + koin; }
    public void kurangiKoin(int koin) { this.koin = this.koin - koin; }

    public String getLokasiLahir() { return this.lokasiLahir; }
    public void setJumlahRamuan(int jumlahRamuan) { this.jumlahRamuan = jumlahRamuan; }

    /* ---- [6][5] override + super chaining pada toString ---- */
    @Override
    public String toString() {
        return super.toString() + " <" + this.kelas + " | mana "
                + this.mana + "/" + this.manaMaksimal + " | ramuan "
                + this.jumlahRamuan + " | koin " + this.koin
                + " | lahir di " + this.lokasiLahir + ">";
    }
}
