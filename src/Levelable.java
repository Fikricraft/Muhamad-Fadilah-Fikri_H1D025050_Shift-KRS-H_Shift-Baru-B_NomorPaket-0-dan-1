/**
 * Kontrak perilaku untuk entitas yang memiliki level dan dapat naik level.
 */
public interface Levelable {

    /**
     * Menaikkan level satu tingkat bila belum mencapai batas maksimum.
     */
    void naikLevel();

    /**
     * @return level saat ini.
     */
    int getLevel();

    /**
     * Menambahkan pengalaman dan memicu kenaikan level bila ambang tercapai.
     */
    void tambahExp(int jumlah);
}
