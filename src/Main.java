import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class Main dipisahkan dari seluruh class lain (tidak ada logika domain di sini).
 * Berisi dua mode:
 * - default (tanpa argumen) : menjalankan pembuktian 10 persyaratan teknis
 * - "game"                  : menjalankan permainan interaktif
 *
 * Responsi Akhir Praktikum PBO - Muhamad Fadilah Fikri (H1D025050)
 * Tema digit terakhir NIM 0 : SISTEM GAME
 */
public class Main {

    private static int nomorUji = 1;

    public static void main(String[] args) {
        boolean modeGame = args.length > 0 && args[0].equalsIgnoreCase("game");

        if (modeGame) {
            mainkanGame();
        } else {
            jalankanPembuktian();
        }
    }

    /* ==================================================================
     *  HELPER
     * ================================================================== */
    private static void judul(String teks) {
        System.out.println();
        System.out.println("==========================================================");
        System.out.println("  UJI " + (nomorUji++) + " : " + teks);
        System.out.println("==========================================================");
    }

    private static void sub(String teks) {
        System.out.println("  -- " + teks);
    }

    /* ==================================================================
     *  MODE PEMBUKTIAN PERSYARATAN
     * ================================================================== */
    private static void jalankanPembuktian() {
        System.out.println("##########################################################");
        System.out.println("#   RESPONSI AKHIR PBO - SISTEM GAME                     #");
        System.out.println("#   Muhamad Fadilah Fikri - H1D025050                    #");
        System.out.println("#   Tema: digit terakhir NIM 0 -> SISTEM GAME            #");
        System.out.println("#   Dunia: " + Karakter.NAMA_DUNIA);
        System.out.println("##########################################################");

        ujiStateDanBehavior();
        ujiImmutability();
        ujiEnkapsulasiDanOverloading();
        ujiInheritance();
        ujiSuperChaining();
        ujiMethodOverriding();
        ujiAbstractClass();
        ujiPercabangan();
        ujiPolimorfismeInterface();
        ujiMultipleInterface();
        ujiIntegrasi();

        tampilkanChecklist();
    }

    /* ---------- [1] STATE DAN BEHAVIOR ---------- */
    private static void ujiStateDanBehavior() {
        judul("[1] STATE & BEHAVIOR - atribut, method void, method return value");

        Pahlawan hero = new Pahlawan("Arjuna", 30, "Ksatria", 80);

        sub("STATE awal (dibaca lewat getter)");
        System.out.println("    nama     : " + hero.getNama());
        System.out.println("    hp       : " + hero.getHp());
        System.out.println("    level    : " + hero.getLevel());
        System.out.println("    exp      : " + hero.getExp());

        sub("BEHAVIOR: method void (setter) mengubah state");
        hero.setNama("Arjuna Wibawa");
        hero.setMana(20);
        System.out.println("    nama setelah setter : " + hero.getNama());
        System.out.println("    mana setelah setter : " + hero.getMana());

        sub("BEHAVIOR: method void tambahExp() memicu perubahan level");
        hero.tambahExp(250);

        sub("BEHAVIOR: method return value");
        System.out.println("    hitungKekuatanTotal() = " + hero.hitungKekuatanTotal());
        System.out.println("    isAlive()             = " + hero.isAlive());
        System.out.println("    getPersenHp()         = " + hero.getPersenHp());
    }

    /* ---------- [2] IMMUTABILITY ---------- */
    private static void ujiImmutability() {
        judul("[2] IMMUTABILITY - public static final & private final");

        sub("Konstanta global (public static final) pada class Karakter");
        System.out.println("    Karakter.NAMA_DUNIA    = " + Karakter.NAMA_DUNIA);
        System.out.println("    Karakter.MAX_LEVEL     = " + Karakter.MAX_LEVEL);
        System.out.println("    Karakter.HP_DASAR      = " + Karakter.HP_DASAR);
        System.out.println("    Karakter.EXP_PER_LEVEL = " + Karakter.EXP_PER_LEVEL);
        System.out.println("    Karakter.RASIO_KRITIS  = " + Karakter.RASIO_KRITIS);

        Pahlawan hero = new Pahlawan("Bima", 25, "Paladin", 70);
        Monster monster = new Monster("Gondoruwo", 18, "Gua Utara", 2);

        sub("Atribut instans kekal (private final) - hanya punya getter, tanpa setter");
        System.out.println("    hero.getIdKarakter()      = " + hero.getIdKarakter());
        System.out.println("    hero.getElemen()          = " + hero.getElemen());
        System.out.println("    hero.getKelas()           = " + hero.getKelas());
        System.out.println("    hero.getManaMaksimal()    = " + hero.getManaMaksimal());
        System.out.println("    monster.getIdKarakter()   = " + monster.getIdKarakter());
        System.out.println("    monster.getHabitat()      = " + monster.getHabitat());
        System.out.println("    -> tidak ada setter untuk atribut di atas, nilainya tidak dapat diubah.");
    }

    /* ---------- [3] ENKAPSULASI & OVERLOADING ---------- */
    private static void ujiEnkapsulasiDanOverloading() {
        judul("[3] ENKAPSULASI & OVERLOADING - private + getter/setter + this + konstruktor ganda");

        sub("Seluruh atribut private, diakses lewat getter/setter (memakai keyword this)");
        Pahlawan hero = new Pahlawan("Srikandi");
        System.out.println("    hp awal   : " + hero.getHp());
        hero.setHp(55);
        System.out.println("    hp diubah : " + hero.getHp());

        sub("Konstruktor overload #1 : Pahlawan(String nama)");
        Pahlawan p1 = new Pahlawan("Srikandi");
        System.out.println("    " + p1);

        sub("Konstruktor overload #2 : Pahlawan(String, int, String, int)");
        Pahlawan p2 = new Pahlawan("Srikandi", 40, "Pemanah", 120);
        System.out.println("    " + p2);

        sub("Konstruktor overload #3 : Karakter(String nama, int kekuatanDasar) via Monster");
        Monster m1 = new Monster("Jelma");
        System.out.println("    " + m1);

        sub("Konstruktor overload #4 : Monster(String, int, String, int)");
        Monster m2 = new Monster("Jelma", 33, "Rawa Selatan", 4);
        System.out.println("    " + m2);

        sub("Konstruktor overload #5 : Boss(String nama)");
        Boss b1 = new Boss("Rakus");
        System.out.println("    " + b1);

        sub("Konstruktor overload #6 : Boss(String, int, String, int)");
        Boss b2 = new Boss("Rakus", 50, "Raja Bayangan", 4);
        System.out.println("    " + b2);
    }

    /* ---------- [4] INHERITANCE ---------- */
    private static void ujiInheritance() {
        judul("[4] INHERITANCE - satu superclass abstrak, tiga subclass");

        System.out.println("    Karakter (abstract)");
        System.out.println("      |-- Pahlawan  extends Karakter");
        System.out.println("      |-- Monster   extends Karakter");
        System.out.println("      |-- Boss      extends Karakter");
        System.out.println("    Main adalah class terpisah dari seluruh class domain.");

        Pahlawan hero = new Pahlawan("Gatotkaca", 30, "Ksatria", 90);
        Monster monster = new Monster("Buto Ijo", 20, "Hutan Pinus", 2);
        Boss boss = new Boss("Rahwana", 45, "Raja Bayangan", 3);

        sub("Pembuktian relasi pewarisan dengan instanceof");
        System.out.println("    hero    instanceof Karakter : " + (hero instanceof Karakter));
        System.out.println("    monster instanceof Karakter : " + (monster instanceof Karakter));
        System.out.println("    boss    instanceof Karakter : " + (boss instanceof Karakter));

        sub("Subclass mewarisi atribut & method superclass");
        hero.setLevel(5);
        System.out.println("    hero.getLevel() (warisan)   : " + hero.getLevel());
        System.out.println("    boss.getHpMaksimal() (warisan): " + boss.getHpMaksimal());
        System.out.println("    monster.isAlive() (warisan) : " + monster.isAlive());
    }

    /* ---------- [5] SUPER CHAINING ---------- */
    private static void ujiSuperChaining() {
        judul("[5] SUPER CHAINING - super(...) pada konstruktor & super.namaMethod()");

        sub("super(...) pada konstruktor subclass mengisi atribut final superclass");
        Pahlawan hero = new Pahlawan("Kumbakarna", 35, "Berserker", 100);
        System.out.println("    idKarakter = " + hero.getIdKarakter() + "  (dibentuk oleh super(...))");
        System.out.println("    elemen     = " + hero.getElemen() + "  (dibentuk oleh super(...))");

        sub("super.naikLevel() di dalam Pahlawan.naikLevel()");
        hero.setMana(10);
        System.out.println("    level sebelum : " + hero.getLevel() + ", mana sebelum : " + hero.getMana());
        hero.naikLevel();
        System.out.println("    level sesudah : " + hero.getLevel() + ", mana sesudah : " + hero.getMana());
        System.out.println("    -> baris NAIK LEVEL berasal dari super, baris mana dari subclass.");

        sub("super.toString() di dalam toString() subclass");
        System.out.println("    " + hero.toString());

        sub("super.serang() di dalam Boss.serang()");
        Boss boss = new Boss("Rahwana", 40, "Raja Bayangan", 3);
        Monster korban = new Monster("Bayangan Kecil", 10, "Gua", 1);
        boss.setEnrage(true);
        System.out.println("    Boss menyerang dua kali karena memanggil super.serang() dua kali:");
        boss.serang(korban);
    }

    /* ---------- [6] METHOD OVERRIDING ---------- */
    private static void ujiMethodOverriding() {
        judul("[6] METHOD OVERRIDING - @Override pada subclass");

        Pahlawan hero = new Pahlawan("Sena", 28, "Ksatria", 80);
        Monster monster = new Monster("Gondoruwo", 22, "Gua Utara", 3);
        Boss boss = new Boss("Rakus", 42, "Raja Bayangan", 3);

        sub("hitungKekuatanTotal() ditimpa berbeda pada setiap subclass");
        System.out.println("    Pahlawan:");
        System.out.println("      hasil = " + hero.hitungKekuatanTotal());
        System.out.println("    Monster:");
        System.out.println("      hasil = " + monster.hitungKekuatanTotal());
        System.out.println("    Boss:");
        System.out.println("      hasil = " + boss.hitungKekuatanTotal());

        sub("toString() ditimpa dan memanggil super.toString()");
        System.out.println("    " + hero);
        System.out.println("    " + monster);
        System.out.println("    " + boss);

        sub("Boss menimpa serang() milik Karakter");
        Monster target = new Monster("Korban", 10, "Lapangan", 1);
        System.out.println("    damage total = " + boss.serang(target));
    }

    /* ---------- [7] ABSTRACT CLASS ---------- */
    private static void ujiAbstractClass() {
        judul("[7] ABSTRACT CLASS - superclass abstract dengan abstract method");

        System.out.println("    Modifier.isAbstract(Karakter.class) = "
                + Modifier.isAbstract(Karakter.class.getModifiers()));
        System.out.println("    Karakter tidak dapat diinstansiasi langsung:");
        System.out.println("      // Karakter k = new Karakter(\"X\");  -> error: Karakter is abstract");

        sub("Daftar abstract method yang wajib diimplementasikan subclass");
        Method[] daftar = Karakter.class.getDeclaredMethods();
        for (Method m : daftar) {
            if (Modifier.isAbstract(m.getModifiers())) {
                System.out.println("      abstract " + m.getReturnType().getSimpleName()
                        + " " + m.getName() + "(...)");
            }
        }

        sub("Setiap subclass memberikan implementasi konkret");
        Pahlawan hero = new Pahlawan("Werkudara", 30, "Ksatria", 80);
        Monster monster = new Monster("Gondoruwo", 20, "Gua", 2);
        Boss boss = new Boss("Rakus", 40, "Raja", 3);
        System.out.println("    Pahlawan.hitungKekuatanTotal() = " + hero.hitungKekuatanTotal());
        System.out.println("    Monster.hitungKekuatanTotal()  = " + monster.hitungKekuatanTotal());
        System.out.println("    Boss.hitungKekuatanTotal()     = " + boss.hitungKekuatanTotal());
        System.out.println("    ketiga subclass juga mengimplementasikan terimaSerangan().");
    }

    /* ---------- [8] PERCABANGAN ---------- */
    private static void ujiPercabangan() {
        judul("[8] PERCABANGAN - if/else membaca dan mengubah state internal");

        sub("A. Pahlawan: percabangan pada mana dan HP");
        Pahlawan hero = new Pahlawan("Sena", 30, "Ksatria", 80);

        System.out.println("    [state: mana penuh, HP penuh]");
        System.out.println("      kekuatan = " + hero.hitungKekuatanTotal());

        hero.setMana(10);
        System.out.println("    [state: mana menipis setelah setMana(10)]");
        System.out.println("      kekuatan = " + hero.hitungKekuatanTotal());

        hero.setMana(80);
        hero.setHp(20);
        System.out.println("    [state: mana penuh kembali, HP 20/100 - kritis]");
        System.out.println("      kekuatan = " + hero.hitungKekuatanTotal());

        sub("B. Monster: state berubah otomatis saat HP rendah (mengamuk)");
        Monster monster = new Monster("Gondoruwo", 24, "Gua Utara", 3);
        System.out.println("    mengamuk sebelum diserang : " + monster.isMengamuk());
        System.out.println("      kekuatan = " + monster.hitungKekuatanTotal());
        System.out.println("    monster diserang hingga HP tersisa di bawah 40%:");
        monster.terimaSerangan(hero, 70);
        System.out.println("    mengamuk sesudah diserang : " + monster.isMengamuk());
        System.out.println("      kekuatan = " + monster.hitungKekuatanTotal());

        sub("C. Boss: fase berubah mengikuti sisa HP");
        Boss boss = new Boss("Rakus", 30, "Raja Bayangan", 3);
        boss.setHpMaksimal(300);
        boss.setHp(300);
        System.out.println("      HP 300 -> fase " + boss.getFase()
                + ", kekuatan " + boss.hitungKekuatanTotal());
        boss.terimaSerangan(hero, 120);
        System.out.println("      HP " + boss.getHp() + " -> fase " + boss.getFase()
                + ", kekuatan " + boss.hitungKekuatanTotal());
        boss.terimaSerangan(hero, 100);
        System.out.println("      HP " + boss.getHp() + " -> fase " + boss.getFase()
                + ", enrage " + boss.isEnrage()
                + ", kekuatan " + boss.hitungKekuatanTotal());
    }

    /* ---------- [9] POLIMORFISME INTERFACE ---------- */
    private static void ujiPolimorfismeInterface() {
        judul("[9] POLIMORFISME INTERFACE - referensi bertipe interface");

        Pahlawan hero = new Pahlawan("Sena", 30, "Ksatria", 80);
        Monster monster = new Monster("Gondoruwo", 20, "Gua", 2);
        Boss boss = new Boss("Rakus", 40, "Raja", 3);

        sub("Bentuk literal: NamaInterface obj = new Subclass();");
        Attackable penyerang = new Pahlawan("Sena", 30, "Ksatria", 80);
        Levelable penumbuh = new Pahlawan("Budi", 28, "Pemanah", 70);
        ItemUsable pemakai = new Pahlawan("Citra", 26, "Penyihir", 75);

        System.out.println("    Attackable penyerang = new Pahlawan(...);  -> isAlive() = " + penyerang.isAlive());
        penyerang.serang(monster);
        System.out.println("    Levelable penumbuh   = new Pahlawan(...);  -> level = " + penumbuh.getLevel());
        penumbuh.tambahExp(100);
        System.out.println("    ItemUsable pemakai   = new Pahlawan(...);  -> jumlah item = " + pemakai.getJumlahItem());
        pemakai.gunakanItem(new Item("ITM-01", "Ramuan Herbal", Item.Jenis.PENYEMBUH, 25));

        sub("Dynamic dispatch: array bertipe superclass, tiap elemen memakai override-nya sendiri");
        Karakter[] daftar = { hero, monster, boss };
        for (Karakter k : daftar) {
            System.out.println("    " + k.getNama() + " (" + k.getClass().getSimpleName()
                    + ") -> kekuatan " + k.hitungKekuatanTotal());
        }

        sub("Interface sebagai parameter method (Guild menghitung kekuatan seluruh anggota)");
        Guild guild = new Guild("Ksatria Soedirman", "Purwokerto");
        guild.rekrut(hero);
        guild.rekrut(monster);
        guild.rekrut(boss);
        guild.tampilkanAnggota();
        System.out.println("    total kekuatan guild = " + guild.hitungKekuatanGuild());
    }

    /* ---------- [10] MULTIPLE INTERFACE ---------- */
    private static void ujiMultipleInterface() {
        judul("[10] MULTIPLE INTERFACE - implements lebih dari satu interface");

        System.out.println("    public class Pahlawan extends Karakter");
        System.out.println("            implements ItemUsable, Levelable, Attackable");
        System.out.println("    -> satu objek yang sama dipanggil lewat tiga tipe interface berbeda.");

        Pahlawan hero = new Pahlawan("Sena", 30, "Ksatria", 80);
        hero.setHp(40);

        Attackable a = hero;
        ItemUsable i = hero;
        Levelable l = hero;

        sub("Lewat interface Attackable");
        Monster monster = new Monster("Gondoruwo", 20, "Gua", 2);
        a.serang(monster);

        sub("Lewat interface ItemUsable");
        System.out.println("    item sebelum = " + i.getJumlahItem());
        i.gunakanItem(new Item("ITM-01", "Ramuan Herbal", Item.Jenis.PENYEMBUH, 30));
        System.out.println("    item sesudah = " + i.getJumlahItem());

        sub("Lewat interface Levelable");
        System.out.println("    level sebelum = " + l.getLevel());
        l.tambahExp(100);
        System.out.println("    level sesudah = " + l.getLevel());

        sub("Ketiganya menunjuk objek yang sama");
        System.out.println("    (a == i) = " + (a == i) + " , (i == l) = " + (i == l));
        System.out.println("    " + hero);
    }

    /* ---------- [11] INTEGRASI SISTEM ---------- */
    private static void ujiIntegrasi() {
        judul("[11] INTEGRASI - pertarungan penuh memakai seluruh konsep di atas");

        Pahlawan hero = new Pahlawan("Fadilah", 34, "Ksatria Soedirman", 80);
        Boss boss = new Boss("Rakus", 30, "Raja Bayangan", 3);
        boss.setHpMaksimal(90);
        boss.setHp(90);

        Guild guild = new Guild("Ksatria Soedirman", "Purwokerto");
        guild.rekrut(hero);

        System.out.println("    " + hero);
        System.out.println("    " + boss);

        Pertarungan arenaUji = new Pertarungan(hero, boss, 12);
        boolean menang = arenaUji.jalankanOtomatis();

        System.out.println("    Status akhir pahlawan : " + hero);
        System.out.println("    Status akhir lawan    : " + boss);
        System.out.println("    Guild " + guild.getNamaGuild() + " memiliki "
                + guild.getJumlahAnggota() + " anggota, kekuatan total "
                + guild.hitungKekuatanGuild() + ".");
        System.out.println("    Hasil: " + (menang ? "PAHLAWAN MENANG" : "PAHLAWAN KALAH"));
    }

    /* ---------- CHECKLIST AKHIR ---------- */
    private static void tampilkanChecklist() {
        System.out.println();
        System.out.println("==========================================================");
        System.out.println("  RINGKASAN PEMENUHAN 10 PERSYARATAN TEKNIS");
        System.out.println("==========================================================");
        System.out.println("  [1]  State & Behavior      Karakter + 3 subclass");
        System.out.println("  [2]  Immutability          5 konstanta + 2 atribut private final");
        System.out.println("  [3]  Enkapsulasi           semua atribut private + 6 konstruktor");
        System.out.println("  [4]  Inheritance           Pahlawan/Monster/Boss extends Karakter");
        System.out.println("  [5]  Super Chaining        super(...) + super.naikLevel()/serang()");
        System.out.println("  [6]  Method Overriding     hitungKekuatanTotal, terimaSerangan, toString");
        System.out.println("  [7]  Abstract Class        Karakter abstract + 2 abstract method");
        System.out.println("  [8]  Percabangan           mana, HP kritis, mengamuk, fase boss");
        System.out.println("  [9]  Polimorfisme          Attackable/Levelable/ItemUsable + array");
        System.out.println("  [10] Multiple Interface    Pahlawan implements 3 interface");
        System.out.println("==========================================================");
        System.out.println("  SELESAI. Jalankan 'java -cp bin Main game' untuk bermain.");
        System.out.println("==========================================================");
    }

    /* ==================================================================
     *  MODE GAME INTERAKTIF — RPG TURN-BASED
     *  Dunia: peta lokasi + toko + quest board + inn; musuh acak + boss;
     *  Battle turn-based dengan menu aksi (serang/pertahanan/item/skill/lari).
     * ================================================================== */
    private static void mainkanGame() {
        Scanner sc = new Scanner(System.in);

        // ---- buat akun / masuk (sesi akun) ----
        Akun akun = muatSesiAkun(sc);
        if (akun == null) {
            System.out.println("  Sampai jumpa!");
            return;
        }
        System.out.println("  Selamat datang, " + akun.getUsername() + "!");

        System.out.println("############################################################");
        System.out.println("#  RPG TURN-BASED: " + Karakter.NAMA_DUNIA.toUpperCase());
        System.out.println("#  Responsi Akhir PBO - H1D025050");
        System.out.println("#  Pahlawan vs Monster / Boss dalam pertarungan putar.");
        System.out.println("############################################################");

        // ---- setup dunia ----
        World dunia = new World("Fadilah", "Kalahkan Boss Lembah (Rakus)");
        Pahlawan hero = new Pahlawan("Fadilah", 34, "Ksatria Soedirman", 80, "Desa Utama");
        hero.setKoin(120);
        hero.setJumlahRamuan(8);

        // skill awal
        Skill serangDepan = new Skill("SKL-01", "Serang Depan", Skill.Jenis.SERANG_DEPAN, 10, 25);
        Skill penyembuh = new Skill("SKL-02", "Penyembuh", Skill.Jenis.HEAL, 15, 20);
        hero.tambahSkill(serangDepan);
        hero.tambahSkill(penyembuh);

        // peralatan awal
        hero.setSenjata(new Weapon("WPN-01", "Pedang Pemula", 8));
        hero.setArmor(new Armor("ARM-01", "Baju Besi Klasis", 12));

        // rekrut 2 teman (party 3 orang)
        Pahlawan teman1 = new Pahlawan("Srikandi", 20, "Pemanah", 70, "Desa Utama");
        Pahlawan teman2 = new Pahlawan("Gatotkaca", 25, "Ksatria", 90, "Desa Utama");
        teman1.setKoin(40);
        teman2.setKoin(60);
        teman1.setSenjata(new Weapon("WPN-02", "Pentungan Kayu", 5));
        teman1.setArmor(new Armor("ARM-02", "Baju Kulit", 8));
        teman2.setSenjata(new Weapon("WPN-03", "Tongkat Perkakas", 6));
        teman2.setArmor(new Armor("ARM-03", "Baju Zirah", 10));

        Pahlawan[] anggota = {hero, teman1, teman2};

        dunia.tambahKarakter(hero);
        dunia.tambahKarakter(teman1);
        dunia.tambahKarakter(teman2);

        // stok toko
        Shop toko = new Shop("Toko Peralatan Lembah", 30);
        toko.tambahBarang(new Barang("WPN-B01", "Pedang Perang Kecil", "Pedang serbaguna.", 90, false, new Weapon("WPN-B01", "Pedang Perang Kecil", 14)));
        toko.tambahBarang(new Barang("WPN-B02", "Kapak Perang", "Pedang berat.", 110, false, new Weapon("WPN-B02", "Kapak Perang", 18)));
        toko.tambahBarang(new Barang("ARM-B01", "Zirah Perunggu", "Pelindung medium.", 70, false, new Armor("ARM-B01", "Zirah Perunggu", 15)));
        toko.tambahBarang(new Barang("ARM-B02", "Zirah Besi", "Pelindung kuat.", 130, false, new Armor("ARM-B02", "Zirah Besi", 22)));
        toko.tambahBarang(new Barang("ITM-B01", "Ramuan Herbal", "Menyembuhkan 30 HP.", 25, true, null));
        toko.tambahBarang(new Barang("ITM-B02", "Ramuan Magis", "Mengembalikan 25 mana.", 20, true, null));

        // lokasi-lokasi di dunia
        Location desa = new Location("DES-01", "Desa Utama", "Pemukiman warga Lembah Soedirman yang tenang.", 0.0);
        Location hutan = new Location("HUT-01", "Hutan Pinus", "Hutan gila berbahaya, banyak monster.", 0.8);
        Location gua = new Location("GUA-01", "Gua Utara", "Gua gelap dengan kehadiran buta-iyo.", 0.9);
        Location dungeon = new Location("DNG-01", "Dungeon Lembah", "Benteng Kegelapan; musang dan boss.", 1.0);
        Location bossArea = new Location("BOS-01", "Benteng Boss", "Lokasi boss akhir; bahaya maksimum.", 1.0);

        // parameter enkounter
        hutan.setMinEnkounterLebih(2); hutan.setMaxEnkounterLebih(4);
        gua.setMinEnkounterLebih(3); gua.setMaxEnkounterLebih(5);
        dungeon.setMinEnkounterLebih(4); dungeon.setMaxEnkounterLebih(7);

        teman1.tambahSkill(penyembuh);
        teman2.tambahSkill(serangDepan);

        int kemenangan = 0;
        int kekalahan = 0;
        boolean jalan = true;

        LokasiInfo lokasiSekarang = new LokasiInfo(desa, "Desa");
        System.out.println("  Lokasi awal: " + lokasiSekarang.namaLokasi() + ".");

        // ---- loop utama RPG ----
        while (jalan) {
            System.out.println();
            System.out.println("----------------------------------------------------------");
            System.out.println("  " + akun.getUsername() + " - " + Karakter.NAMA_DUNIA);
            System.out.println("  Lokasi : " + lokasiSekarang.namaLokasi() + " (" + lokasiSekarang.kodeLokasi() + ")");
            System.out.println("  Koin party: " + hitungKoinParty(dunia.getParty()));
            System.out.println("  Party:");
            for (Pahlawan p : dunia.getParty()) {
                System.out.println("    - " + p.getNama() + " (Lv." + p.getLevel()
                        + " | HP " + p.getHp() + "/" + p.getHpMaksimal()
                        + " | mana " + p.getMana() + "/" + p.getManaMaksimal()
                        + " | senjata " + (p.getSenjata() != null ? p.getSenjata().getNama() : "tidak ada")
                        + " | armor " + (p.getArmor() != null ? p.getArmor().getNama() : "tidak ada")
                        + " | koin " + p.getKoin() + ")");
            }
            System.out.println("----------------------------------------------------------");

            System.out.println("  Menu di " + lokasiSekarang.namaLokasi() + ":");
            System.out.println("  1. Jelajahi lingkungan");
            System.out.println("  2. Buka Toko Peralatan");
            System.out.println("  3. Papan Quest (tugas)");
            System.out.println("  4. Inn (istirahat & pulihkan)");
            System.out.println("  5. Pindah lokasi");
            System.out.println("  6. Kelola Inventori (senjata, armor, item)");
            System.out.println("  7. Info akun / Logout");

            if (lokasiSekarang.kodeLokasi().equals("DES-01")) {
                System.out.println("     ke: Hutan Pinus / Gua Utara / Dungeon Lembah");
            } else if (lokasiSekarang.kodeLokasi().equals("HUT-01")) {
                System.out.println("     ke: Desa Utama / Gua Utara");
            } else if (lokasiSekarang.kodeLokasi().equals("GUA-01")) {
                System.out.println("     ke: Hutan Pinus / Desa Utama / Dungeon Lembah");
            } else if (lokasiSekarang.kodeLokasi().equals("DNG-01")) {
                System.out.println("     ke: Gua Utara / Benteng Boss");
            } else if (lokasiSekarang.kodeLokasi().equals("BOS-01")) {
                System.out.println("     ke: Dungeon Lembah");
            }

            System.out.println("  0. Keluar ke padepokan");
            System.out.print("  Pilihan: ");

            String pilihan;
            try {
                pilihan = sc.nextLine().trim();
            } catch (RuntimeException e) {
                System.out.println("\n  Input berakhir. Permainan selesai.");
                break;
            }

            if (pilihan.equals("1")) {
                System.out.println("  Menjelajahi " + lokasiSekarang.namaLokasi() + "...");
                if (lokasiSekarang.munculEnkounterAcak(new java.util.Random())) {
                    int jumlahMusuh = new java.util.Random().nextInt(
                            lokasiSekarang.getMaxEnkounterLebih()
                            - lokasiSekarang.getMinEnkounterLebih() + 1)
                            + lokasiSekarang.getMinEnkounterLebih();
                    List<Karakter> musuh = new ArrayList<Karakter>();
                    for (int i = 0; i < jumlahMusuh; i++) {
                        musuh.add(buatkankMusuhAcak(lokasiSekarang, i));
                    }
                    System.out.println("  Muncul " + musuh.size() + " musuh!");
                    for (Karakter m : musuh) {
                        System.out.println("    - " + m);
                    }
                    Pahlawan pemimpin = pilihPemimpinTurns(dunia.getParty());
                    PartyBattle battle = new PartyBattle(pemimpin, musuh, 10);
                    boolean menang = battle.jalankan(sc);
                    if (menang) {
                        kemenangan++;
                        int reward = musuh.size() * 15 + new java.util.Random().nextInt(20);
                        pemimpin.tambahKoin(reward);
                        System.out.println("    > Pahlawan menerima " + reward + " koin.");
                        if (new java.util.Random().nextBoolean()) {
                            Item itemAcak = buatItemAcak();
                            if (itemAcak.getJenis() == Item.Jenis.PENYEMBUH) {
                                pemimpin.setJumlahRamuan(pemimpin.getJumlahItem() + 1);
                                System.out.println("    > mendapatkan " + itemAcak.getNama() + ".");
                            }
                        }
                    }
                } else {
                    System.out.println("  Aman. Tidak ada musuh di sini.");
                }
            } else if (pilihan.equals("2")) {
                System.out.println("  === TOKO PERALATAN LEMBAGH ===");
                System.out.println("  Toko: " + toko.getNamaToko());
                System.out.println("  Stok:");
                for (Barang b : toko.getStok()) {
                    int harga = b.hitungHargaJual(toko.getMarginKeuntungan());
                    System.out.println("    - " + b.getNama() + " (kode " + b.getKode() + ") harga "
                            + harga + " koin. " + b.getDeskripsi());
                }
                System.out.print("  Masukkan kode barang untuk dibeli (kosongkan untuk selesai): ");
                String kode;
                try {
                    kode = sc.nextLine().trim();
                } catch (RuntimeException e) {
                    break;
                }
                if (kode.isEmpty()) {
                    System.out.println("  Kembali.");
                } else {
                    Barang barang = toko.cariBarang(kode);
                    if (barang == null) {
                        System.out.println("  Barang tidak ditemukan.");
                    } else {
                        int harga = barang.hitungHargaJual(toko.getMarginKeuntungan());
                        System.out.println("  Harga " + barang.getNama() + ": " + harga + " koin.");
                        System.out.println("  Pembeli (pilih karakter, 1.." + dunia.getParty().size() + "): ");
                        for (int i = 0; i < dunia.getParty().size(); i++) {
                            Pahlawan p = dunia.getParty().get(i);
                            System.out.println("    " + (i+1) + ". " + p.getNama() + " (" + p.getKoin() + " koin)");
                        }
                        System.out.print("  Nomor karakter: ");
                        String nomorStr;
                        try {
                            nomorStr = sc.nextLine().trim();
                        } catch (RuntimeException e) {
                            break;
                        }
                        int index = Integer.parseInt(nomorStr) - 1;
                        if (index >= 0 && index < dunia.getParty().size()) {
                            Pahlawan pembeli = dunia.getParty().get(index);
                            if (pembeli.getKoin() >= harga) {
                                pembeli.kurangiKoin(harga);
                                if (barang.isAdalahItem()) {
                                    System.out.println("  Pembeli " + pembeli.getNama() + " menerima " + barang.getNama() + ".");
                                    if (barang.getNama().matches(".*Ramuan.*")) {
                                        pembeli.setJumlahRamuan(pembeli.getJumlahItem() + 10);
                                    }
                                } else if (barang.getEquipment() instanceof Weapon) {
                                    System.out.println("  Pembeli " + pembeli.getNama() + " melepaskan senjata lama dan mengenakan " + barang.getEquipment().getNama() + ".");
                                    pembeli.setSenjata((Weapon) barang.getEquipment());
                                } else if (barang.getEquipment() instanceof Armor) {
                                    System.out.println("  Pembeli " + pembeli.getNama() + " melepaskan armor lama dan mengenakan " + barang.getEquipment().getNama() + ".");
                                    pembeli.setArmor((Armor) barang.getEquipment());
                                }
                            } else {
                                System.out.println("  " + pembeli.getNama() + " tidak punya koin cukup (butuh " + harga + ").");
                            }
                        } else {
                            System.out.println("  Nomor tidak valid.");
                        }
                    }
                }
            } else if (pilihan.equals("3")) {
                System.out.println("  === PANITIA QUEST LEMBAGH ===");
                System.out.println("  Tugas utama: " + dunia.getTugasUtama());
                System.out.println("  - [1] Awasi hutan dari monster: 5 kemenangan (sudah: " + kemenangan + ")");
                System.out.println("  - [2] Temukan gua dan lawan gondoruwo: "
                        + (kemenangan >= 0 ? "belum diedarkan" : "selesai"));
                System.out.println("  - [3] Hadapi boss Rakus di Benteng Boss: "
                        + (kekalahan > 0 ? "belum, coba lagi" : "menantimu."));
                System.out.println("  Progress: " + kemenangan + " menang, " + kekalahan + " kalah.");
            } else if (pilihan.equals("4")) {
                System.out.println("  === ISTIRAHT DI INN ===");
                System.out.println("  Daftar anggota party:");
                for (int i = 0; i < anggota.length; i++) {
                    Pahlawan p = anggota[i];
                    System.out.println("    [" + (i + 1) + "] " + p.getNama()
                            + " | HP " + p.getHp() + "/" + p.getHpMaksimal()
                            + " | mana " + p.getMana() + "/" + p.getManaMaksimal()
                            + " | ramuan " + p.getJumlahItem());
                }
                System.out.print("  Pilih yang diistirahatkan (1-" + anggota.length + ", 0 = semua, -1 = batal): ");
                String pilih;
                try {
                    pilih = sc.nextLine().trim();
                } catch (RuntimeException e) {
                    break;
                }
                if (pilih.equals("-1")) {
                    // batalkan, kembali ke menu utama
                }
                if (pilih.equals("0")) {
                    for (Pahlawan p : anggota) {
                        p.setHp(p.getHpMaksimal());
                        p.setMana(p.getManaMaksimal());
                        p.setJumlahRamuan(p.getJumlahItem() + 2);
                        System.out.println("  " + p.getNama() + " dipulihkan (HP penuh, +2 ramuan).");
                    }
                } else {
                    try {
                        int idx = Integer.parseInt(pilih) - 1;
                        if (idx >= 0 && idx < anggota.length) {
                            Pahlawan p = anggota[idx];
                            p.setHp(p.getHpMaksimal());
                            p.setMana(p.getManaMaksimal());
                            p.setJumlahRamuan(p.getJumlahItem() + 2);
                            System.out.println("  " + p.getNama() + " dipulihkan (HP penuh, +2 ramuan).");
                        }
                    } catch (NumberFormatException e) {
                        // ignore
                    }
                }
                System.out.println();
            } else if (pilihan.equals("5")) {
                System.out.print("  Masukkan kode lokasi tujuan (DES-01/HUT-01/GUA-01/DNG-01/BOS-01): ");
                String kode;
                try {
                    kode = sc.nextLine().trim();
                } catch (RuntimeException e) {
                    break;
                }
                Location tujuan = pilihLokasi(kode, lokasiSekarang);
                if (tujuan != null) {
                    lokasiSekarang = new LokasiInfo(tujuan, tujuan.getNama());
                    System.out.println("  Pindah ke " + lokasiSekarang.namaLokasi() + ".");
                } else {
                    System.out.println("  Lokasi tidak dikenal atau tidak terhubung.");
                }
            } else if (pilihan.equals("6")) {
                kelolaInventori(sc, hero, teman1, teman2);
            } else if (pilihan.equals("7")) {
                System.out.println("  === INFO AKUN ===");
                System.out.println("  Username       : " + akun.getUsername());
                System.out.println("  Status sesi    : " + (akun.isLogin() ? "login" : "logout"));
                System.out.println("  Akun terdaftar : " + Akun.jumlahAkun());
                System.out.print("  Logout dan akhiri sesi? (y/n): ");
                String jawab = bacaBaris(sc);
                if (jawab.equalsIgnoreCase("y")) {
                    akun.logout();
                    System.out.println("  Sesi " + akun.getUsername() + " ditutup.");
                    jalan = false;
                }
            } else if (pilihan.equals("0")) {
                jalan = false;
                System.out.println();
                System.out.println("  Duta padepokan. Terima kasih sudah memainkan "
                        + Karakter.NAMA_DUNIA + "!");
                System.out.println("  Skor akhir: " + kemenangan + " menang, " + kekalahan + " kalah.");
                System.out.println("  Level hero: " + hero.getLevel() + ", koin: " + hero.getKoin() + ".");
            } else {
                System.out.println("  [!] Pilihan tidak dikenal, coba lagi.");
            }
        }

        sc.close();
    }

    /**
     * Menu pembuatan akun dan login. Mengembalikan Akun yang sedang dalam sesi
     * login, atau null bila pemain memilih keluar.
     */
    private static Akun muatSesiAkun(Scanner sc) {
        while (true) {
            System.out.println();
            System.out.println("============================================================");
            System.out.println("  LEMBAH SOEDIRMAN - MASUK AKUN");
            System.out.println("============================================================");
            System.out.println("  Terdaftar: " + Akun.jumlahAkun() + " akun");
            System.out.println("  1. Buat akun baru (username)");
            System.out.println("  2. Masuk (login)");
            System.out.println("  0. Keluar");
            System.out.print("  Pilihan: ");

            String pilihan;
            try {
                pilihan = sc.nextLine().trim();
            } catch (RuntimeException e) {
                return null;
            }

            if (pilihan.equals("0")) {
                return null;
            } else if (pilihan.equals("1")) {
                System.out.print("  Username baru : ");
                String nama = bacaBaris(sc);
                System.out.print("  Kata sandi    : ");
                String sandi = bacaBaris(sc);
                Akun baru = Akun.daftar(nama, sandi);
                if (baru == null) {
                    System.out.println("  [!] Username sudah dipakai atau tidak valid.");
                } else {
                    Akun.login(nama, sandi);
                    return baru;
                }
            } else if (pilihan.equals("2")) {
                System.out.print("  Username : ");
                String nama = bacaBaris(sc);
                System.out.print("  Kata sandi : ");
                String sandi = bacaBaris(sc);
                Akun masuk = Akun.login(nama, sandi);
                if (masuk == null) {
                    System.out.println("  [!] Username tidak dikenali atau sandi salah.");
                } else {
                    return masuk;
                }
            } else {
                System.out.println("  [!] Pilihan tidak dikenal.");
            }
        }
    }

    /** Baca satu baris; kembalikan string kosong bila input tidak tersedia. */
    private static String bacaBaris(Scanner sc) {
        try {
            return sc.nextLine().trim();
        } catch (RuntimeException e) {
            return "";
        }
    }

    /* ==================================================================
     *  HELPER RPG
     * ================================================================== */
    /** informasi ringkas lokasi */
    private static class LokasiInfo {
        private final Location lokasi;
        private final String namaLokasi;
        LokasiInfo(Location lokasi, String namaLokasi) {
            this.lokasi = lokasi;
            this.namaLokasi = namaLokasi;
        }
        public String kodeLokasi() { return lokasi.getKode(); }
        public String namaLokasi() { return namaLokasi; }
        public Location getLokasi() { return lokasi; }
        public boolean munculEnkounterAcak(java.util.Random rnd) {
            return lokasi.munculEnkounterAcak(rnd);
        }
        public int getMinEnkounterLebih() { return lokasi.getMinEnkounterLebih(); }
        public int getMaxEnkounterLebih() { return lokasi.getMaxEnkounterLebih(); }
        public double getTingkatBahaya() { return lokasi.getTingkatBahaya(); }
    }

    private static Location pilihLokasi(String kode, LokasiInfo saatIni) {
        if (kode == null) return null;
        if (kode.equals("DES-01")) return new Location("DES-01", "Desa Utama",
                "Pemukiman warga Lembah Soedirman yang tenang.", 0.0);
        if (kode.equals("HUT-01")) return new Location("HUT-01", "Hutan Pinus",
                "Hutan gila berbahaya, banyak monster.", 0.8);
        if (kode.equals("GUA-01")) return new Location("GUA-01", "Gua Utara",
                "Gua gelap dengan kehadiran buta-iyo.", 0.9);
        if (kode.equals("DNG-01")) return new Location("DNG-01", "Dungeon Lembah",
                "Benteng Kegelapan; musang dan boss.", 1.0);
        if (kode.equals("BOS-01")) return new Location("BOS-01", "Benteng Boss",
                "Lokasi boss akhir; bahaya maksimum.", 1.0);
        return null;
    }

    private static Monster buatkankMusuhAcak(LokasiInfo lokasi, int i) {
        String[] namaHutan = {"Gondoruwo", "Wereng", "Kadal Gila", "Serigala Hutan"};
        String[] namaGua = {"Buta-iyo", "Kutu Batu", "Naga Kecil", "Changkrik"};
        String[] namaDungeon = {"Hantu Benteng", "Raksasa", "Dragoi", "Musang Kegelapan"};
        String[] namaBoss = {"Rakus", "Hantu Daerah", "Makhluk Gelap"};
        String[] namaLokasi = namaHutan;
        if (lokasi.kodeLokasi().equals("GUA-01")) namaLokasi = namaGua;
        else if (lokasi.kodeLokasi().equals("DNG-01")) namaLokasi = namaDungeon;
        else if (lokasi.kodeLokasi().equals("BOS-01")) namaLokasi = namaBoss;

        String nama = namaLokasi[new java.util.Random().nextInt(namaLokasi.length)];
        int kekuatan = 8 + new java.util.Random().nextInt(12) + i * 2;
        Monster m = new Monster(nama, kekuatan, lokasi.namaLokasi(), 2);
        if (lokasi.kodeLokasi().equals("BOS-01")) {
            m.setHpMaksimal(120);
            m.setHp(120);
        } else {
            m.setHpMaksimal(60 + new java.util.Random().nextInt(40));
            m.setHp(m.getHpMaksimal());
        }
        return m;
    }

    private static Item buatItemAcak() {
        Item[] items = {
                new Item("ITM-01", "Ramuan Herbal", Item.Jenis.PENYEMBUH, 30),
                new Item("ITM-02", "Ramuan Magis", Item.Jenis.MANA, 25),
                new Item("ITM-03", "Potion Kekuatan", Item.Jenis.SERANGAN, 5)
        };
        return items[new java.util.Random().nextInt(items.length)];
    }

    private static Pahlawan pilihPemimpinTurns(List<Pahlawan> party) {
        Pahlawan pilih = party.get(0);
        int maxHp = pilih.getHp();
        for (int i = 1; i < party.size(); i++) {
            Pahlawan p = party.get(i);
            if (p.getHp() > maxHp) {
                maxHp = p.getHp();
                pilih = p;
            }
        }
        return pilih;
    }

    private static int hitungKoinParty(List<Pahlawan> party) {
        int total = 0;
        for (Pahlawan p : party) total += p.getKoin();
        return total;
    }

    private static void kelolaInventori(Scanner sc, Pahlawan... anggota) {
        System.out.println("  === KELOLA INVENTORI ===");
        System.out.println("  Daftar anggota party:");
        for (int i = 0; i < anggota.length; i++) {
            Pahlawan p = anggota[i];
            System.out.println("    [" + (i + 1) + "] " + p.getNama()
                    + " - senjata: " + (p.getSenjata() != null ? p.getSenjata().getNama() : "tidak ada")
                    + " | armor: " + (p.getArmor() != null ? p.getArmor().getNama() : "tidak ada")
                    + " | ramuan: " + p.getJumlahItem() + " | koin: " + p.getKoin());
        }

        System.out.print("  Pilih anggota (1-" + anggota.length + ", 0 = kembali): ");
        String pilihAnggota;
        try {
            pilihAnggota = sc.nextLine().trim();
        } catch (RuntimeException e) {
            return;
        }
        int idx = -1;
        try {
            idx = Integer.parseInt(pilihAnggota) - 1;
        } catch (NumberFormatException e) {
            return;
        }
        if (idx < 0 || idx >= anggota.length) return;

        Pahlawan target = anggota[idx];
        boolean kembali = false;
        while (!kembali) {
            System.out.println();
            System.out.println("  === INVENTORI " + target.getNama().toUpperCase() + " ===");
            System.out.println("  1. Lihat senjata & armor");
            System.out.println("  2. Ganti senjata");
            System.out.println("  3. Ganti armor");
            System.out.println("  4. Lihat item (ramuan)");
            System.out.println("  0. Kembali");
            System.out.print("  Pilihan: ");

            String sub;
            try {
                sub = sc.nextLine().trim();
            } catch (RuntimeException e) {
                break;
            }

            if (sub.equals("1")) {
                System.out.println("  Senjata  : " + (target.getSenjata() != null ? target.getSenjata().getNama() : "(tidak ada - HP & power dasar)")
                        + (target.getSenjata() != null ? " (kekuatan +" + ((Weapon) target.getSenjata()).getKekuatanSerang() + ")" : ""));
                System.out.println("  Armor    : " + (target.getArmor() != null ? target.getArmor().getNama() : "(tidak ada)")
                        + (target.getArmor() != null ? " (pelindung +" + ((Armor) target.getArmor()).getPelindung() + ")" : ""));
                System.out.println("  Item     : Ramuan Herbal x" + target.getJumlahItem() + " (HP +5)");
                System.out.println("  Koin     : " + target.getKoin());
            } else if (sub.equals("2")) {
                System.out.println("  Pilih senjata baru:");
                System.out.println("    1. Pedang Pemula (kekuatan +8)");
                System.out.println("    2. Pentungan Kayu (kekuatan +5)");
                System.out.println("    3. Tongkat Perkakas (kekuatan +10)");
                System.out.println("    0. Kembali");
                System.out.print("  Pilihan: ");
                String wp;
                try {
                    wp = sc.nextLine().trim();
                } catch (RuntimeException e) {
                    break;
                }
                if (wp.equals("1")) {
                    target.setSenjata(new Weapon("WEAP-01", "Pedang Pemula", 8));
                    System.out.println("  Senjata diganti menjadi Pedang Pemula.");
                } else if (wp.equals("2")) {
                    target.setSenjata(new Weapon("WEAP-02", "Pentungan Kayu", 5));
                    System.out.println("  Senjata diganti menjadi Pentungan Kayu.");
                } else if (wp.equals("3")) {
                    target.setSenjata(new Weapon("WEAP-03", "Tongkat Perkakas", 10));
                    System.out.println("  Senjata diganti menjadi Tongkat Perkakas.");
                }
            } else if (sub.equals("3")) {
                System.out.println("  Pilih armor baru:");
                System.out.println("    1. Baju Besi Klasis (pelindung +12)");
                System.out.println("    2. Baju Kulit (pelindung +6)");
                System.out.println("    3. Baju Zirah (pelindung +15)");
                System.out.println("    0. Kembali");
                System.out.print("  Pilihan: ");
                String ap;
                try {
                    ap = sc.nextLine().trim();
                } catch (RuntimeException e) {
                    break;
                }
                if (ap.equals("1")) {
                    target.setArmor(new Armor("ARM-01", "Baju Besi Klasis", 12));
                    System.out.println("  Armor diganti menjadi Baju Besi Klasis.");
                } else if (ap.equals("2")) {
                    target.setArmor(new Armor("ARM-02", "Baju Kulit", 6));
                    System.out.println("  Armor diganti menjadi Baju Kulit.");
                } else if (ap.equals("3")) {
                    target.setArmor(new Armor("ARM-03", "Baju Zirah", 15));
                    System.out.println("  Armor diganti menjadi Baju Zirah.");
                }
            } else if (sub.equals("4")) {
                System.out.println("  Item: Ramuan Herbal x" + target.getJumlahItem());
                System.out.println("    -> Sembuhkan 5 HP (gunakan di battle: menu 3).");
            } else if (sub.equals("0")) {
                kembali = true;
            }
        }
    }
}
