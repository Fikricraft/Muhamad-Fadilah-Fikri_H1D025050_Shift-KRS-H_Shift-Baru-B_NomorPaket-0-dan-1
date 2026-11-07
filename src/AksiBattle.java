/**
 * Enum yang menyatakan jenis aksi yang bisa dipilih pemain
 * dalam pertarungan turn-based.
 */
public enum AksiBattle {

    SERANG("Serang", "Pendekatan dasar: menyerang musuh dengan senjata."),
    PERTAHANAN("Pertahanan", "Defensif: mengurangi damage yang diterima."),
    PAKAI_ITEM("Pakai Item", "Membuka item ramuan dari inventori."),
    PAKAI_SKILL("Pakai Skill", "Menggunakan skill khusus yang dipelajari."),
    LARI("Lari", "Mencoba melarikan diri dari pertarungan.");

    private final String label;
    private final String keterangan;

    AksiBattle(String label, String keterangan) {
        this.label = label;
        this.keterangan = keterangan;
    }

    public String getLabel() {
        return this.label;
    }

    public String getKeterangan() {
        return this.keterangan;
    }
}
