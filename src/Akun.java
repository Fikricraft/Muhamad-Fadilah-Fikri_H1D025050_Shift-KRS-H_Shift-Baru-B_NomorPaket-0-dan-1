import java.util.HashMap;
import java.util.Map;

/**
 * Akun pemain untuk sistem game. Menyimpan username unik beserta kata sandi
 * dan mengelola status sesi (login/logout). Seluruh akun terdaftar disimpan
 * dalam penyimpanan statis (HashMap) sehingga tetap tersedia selama program
 * berjalan.
 */
public class Akun {

    /* penyimpanan seluruh akun terdaftar, dibagikan lintas objek */
    private static final Map<String, Akun> DAFTAR_AKUN = new HashMap<>();

    /* akun demo untuk pemeriksaan asprak: user / pass */
    static {
        Akun akunDemo = new Akun("user", "pass");
        DAFTAR_AKUN.put(akunDemo.username, akunDemo);
    }

    /* username bersifat kekal, tidak bisa diubah setelah akun dibuat */
    private final String username;

    private String password;
    private boolean login;

    /* konstruktor privat: akun hanya dibuat lewat daftar(...) */
    private Akun(String username, String password) {
        this.username = username;
        this.password = password;
        this.login = false;
    }

    /**
     * Mendaftarkan akun baru. Gagal bila username kosong atau sudah terpakai.
     *
     * @return objek Akun baru, atau null bila gagal.
     */
    public static Akun daftar(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        if (password == null || password.isEmpty()) {
            return null;
        }
        if (DAFTAR_AKUN.containsKey(username)) {
            return null;
        }
        Akun akun = new Akun(username.trim(), password);
        DAFTAR_AKUN.put(username.trim(), akun);
        return akun;
    }

    /**
     * Masuk ke akun yang sudah terdaftar.
     *
     * @return objek Akun dalam keadaan login, atau null bila username tidak
     *         dikenali atau sandi salah.
     */
    public static Akun login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        Akun akun = DAFTAR_AKUN.get(username.trim());
        if (akun == null || !akun.password.equals(password)) {
            return null;
        }
        akun.login = true;
        return akun;
    }

    /** Mengakhiri sesi akun ini. */
    public void logout() {
        this.login = false;
    }

    /** @return true bila akun sedang dalam sesi login. */
    public boolean isLogin() {
        return this.login;
    }

    public String getUsername() {
        return this.username;
    }

    /** @return true bila username sudah terdaftar. */
    public static boolean terdaftar(String username) {
        return username != null && DAFTAR_AKUN.containsKey(username.trim());
    }

    /** @return jumlah akun yang sudah terdaftar. */
    public static int jumlahAkun() {
        return DAFTAR_AKUN.size();
    }
}