import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Toko di dalam lokasi. Penjual menjual peralatan kepada
 * karakter yang memiliki koin yang cukup, dan membeli barang
 * dari karakter.
 */
public class Shop {

    /* [2] atribut instans kekal */
    private final String namaToko;
    private final int marginKeuntungan;

    /* stok barang yang dijual */
    private final List<Barang> stok;          // Barang yang dijual

    public Shop(String namaToko, int marginKeuntungan) {
        this.namaToko = namaToko;
        this.marginKeuntungan = marginKeuntungan;
        this.stok = new ArrayList<Barang>();
    }

    /**
     * @return nama toko.
     */
    public String getNamaToko() {
        return this.namaToko;
    }

    public int getMarginKeuntungan() {
        return this.marginKeuntungan;
    }

    public List<Barang> getStok() {
        return this.stok;
    }

    /**
     * Menambahkan barang ke stok toko.
     */
    public boolean tambahBarang(Barang barang) {
        if (barang == null) {
            return false;
        }
        this.stok.add(barang);
        return true;
    }

    /**
     * Mencari barang di stok berdasarkan kode.
     */
    public Barang cariBarang(String kode) {
        for (Barang barang : this.stok) {
            if (barang.getKode().equals(kode)) {
                return barang;
            }
        }
        return null;
    }

    /**
     * Menjual barang kepada karakter (karakter harus punya cukup koin).
     *
     * @return true bila penjualan berhasil.
     */
    public boolean jual(Pahlawan pembeli, String kodeBarang) {
        Barang barang = cariBarang(kodeBarang);
        if (barang == null) {
            System.out.println("  Toko: barang tidak ada.");
            return false;
        }
        int harga = barang.hitungHargaJual(marginKeuntungan);
        if (harga <= 0) {
            harga = 1;
        }
        if (pembeli.getKoin() < harga) {
            System.out.println("  Toko: " + pembeli.getNama()
                    + " tidak punya koin cukup (butuh " + harga
                    + ", punya " + pembeli.getKoin() + ").");
            return false;
        }
        pembeli.kurangiKoin(harga);
        // pindahkan barang ke karakter (setelah jual, barang dihapus dari stok)
        // (implementasi: karakter mendapat barang lewat mekanisme lain)
        System.out.println("  Toko: " + pembeli.getNama() + " membeli "
                + barang.getNama() + " seharga " + harga + " koin.");
        return true;
    }
}
