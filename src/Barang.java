/**
 * Barang yang dijual di toko: bisa berupa Item (konsumsi) atau Equipment
 * (senjata/armor). Kelas ini sengaja dipisah dari Shop agar dapat
 * digunakan lintas file tanpa auxiliary-class warning.
 */
public class Barang {

    private final String kode;
    private final String nama;
    private final String deskripsi;
    private final int hargaDasar;
    private final boolean adalahItem;        // true = Item, false = Equipment
    private final Equipment equipment;       // null bila ini Item

    public Barang(String kode, String nama, String deskripsi,
                  int hargaDasar, boolean adalahItem, Equipment equipment) {
        this.kode = kode;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.hargaDasar = hargaDasar;
        this.adalahItem = adalahItem;
        this.equipment = equipment;
    }

    public String getKode() {
        return this.kode;
    }

    public String getNama() {
        return this.nama;
    }

    public String getDeskripsi() {
        return this.deskripsi;
    }

    public int getHargaDasar() {
        return this.hargaDasar;
    }

    public boolean isAdalahItem() {
        return this.adalahItem;
    }

    public Equipment getEquipment() {
        return this.equipment;
    }

    public int hitungHargaJual(int marginKeuntungan) {
        return this.hargaDasar + (this.hargaDasar * marginKeuntungan / 100);
    }
}
