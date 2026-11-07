/**
 * Kontrak perilaku untuk entitas yang dapat menyerang dan dapat mati.
 */
public interface Attackable {

    /**
     * Menyerang target dan mengembalikan besar damage yang dihasilkan.
     */
    int serang(Karakter target);

    /**
     * @return true bila objek masih memiliki HP di atas nol.
     */
    boolean isAlive();
}
