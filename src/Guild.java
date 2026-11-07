import java.util.ArrayList;
import java.util.List;

/**
 * Guild tempat para karakter bernaung. Menampung objek Karakter dari berbagai
 * subclass dan menghitung kekuatan total secara polimorfik.
 */
public class Guild {

    private final String namaGuild;
    private final String wilayah;

    private final List<Karakter> anggota;

    public Guild(String namaGuild, String wilayah) {
        this.namaGuild = namaGuild;
        this.wilayah = wilayah;
        this.anggota = new ArrayList<>();
    }

    /**
     * Menambahkan karakter ke guild.
     *
     * @return true bila berhasil direkrut.
     */
    public boolean rekrut(Karakter karakter) {
        if (karakter == null) {
            System.out.println("    [!] Karakter tidak valid, rekrutmen dibatalkan.");
            return false;
        }
        if (this.anggota.contains(karakter)) {
            System.out.println("    [!] " + karakter.getNama() + " sudah menjadi anggota "
                    + this.namaGuild + ".");
            return false;
        }
        this.anggota.add(karakter);
        System.out.println("    [+] " + karakter.getNama() + " bergabung dengan "
                + this.namaGuild + ".");
        return true;
    }

    /**
     * Mengeluarkan karakter dari guild.
     */
    public boolean keluarkan(Karakter karakter) {
        if (this.anggota.remove(karakter)) {
            System.out.println("    [-] " + karakter.getNama() + " keluar dari "
                    + this.namaGuild + ".");
            return true;
        }
        System.out.println("    [!] " + karakter.getNama() + " bukan anggota guild.");
        return false;
    }

    /**
     * Menghitung total kekuatan seluruh anggota. Setiap elemen memanggil
     * versi hitungKekuatanTotal() miliknya sendiri (polimorfisme).
     */
    public int hitungKekuatanGuild() {
        int total = 0;
        for (Karakter k : this.anggota) {
            total = total + k.hitungKekuatanTotal();
        }
        return total;
    }

    /**
     * @return jumlah anggota yang masih hidup.
     */
    public int hitungAnggotaHidup() {
        int jumlah = 0;
        for (Karakter k : this.anggota) {
            if (k.isAlive()) {
                jumlah = jumlah + 1;
            }
        }
        return jumlah;
    }

    public void tampilkanAnggota() {
        System.out.println("    Daftar anggota " + this.namaGuild + " ("
                + this.anggota.size() + " orang):");
        for (Karakter k : this.anggota) {
            System.out.println("      - " + k);
        }
    }

    public Karakter[] getAnggotaArray() {
        return this.anggota.toArray(new Karakter[0]);
    }

    public String getNamaGuild() {
        return this.namaGuild;
    }

    public String getWilayah() {
        return this.wilayah;
    }

    public int getJumlahAnggota() {
        return this.anggota.size();
    }
}
