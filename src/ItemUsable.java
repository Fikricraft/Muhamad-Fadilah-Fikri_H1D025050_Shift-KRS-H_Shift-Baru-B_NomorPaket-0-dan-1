/**
 * Kontrak perilaku untuk entitas yang dapat memakai item dari tas.
 */
public interface ItemUsable {

    /**
     * Memakai satu item dari persediaan.
     *
     * @return true bila item berhasil dipakai.
     */
    boolean gunakanItem(Item item);

    /**
     * @return jumlah item yang masih tersisa di tas.
     */
    int getJumlahItem();
}
