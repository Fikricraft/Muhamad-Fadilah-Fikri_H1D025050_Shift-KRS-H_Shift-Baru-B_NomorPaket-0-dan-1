import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
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
    private static final Path FILE_AKUN = Path.of("akun.properties");

    /* akun demo untuk pemeriksaan asprak: user / pass */
    static {
        muatAkun();
        if (!DAFTAR_AKUN.containsKey("user")) {
            Akun akunDemo = new Akun("user", hash("pass"));
            DAFTAR_AKUN.put(akunDemo.username, akunDemo);
            simpanAkun();
        }
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

    private static String hash(String teks) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hasil = md.digest(teks.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hasil);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 tidak tersedia", e);
        }
    }

    private static void muatAkun() {
        if (!Files.exists(FILE_AKUN)) {
            return;
        }
        try {
            for (String baris : Files.readAllLines(FILE_AKUN, StandardCharsets.UTF_8)) {
                String[] bagian = baris.split("\\t", 2);
                if (bagian.length == 2 && !bagian[0].trim().isEmpty()) {
                    DAFTAR_AKUN.put(bagian[0], new Akun(bagian[0], bagian[1]));
                }
            }
        } catch (Exception e) {
            System.out.println("  [!] Data akun tidak dapat dibaca; mulai dengan akun baru.");
        }
    }

    private static void simpanAkun() {
        try {
            StringBuilder isi = new StringBuilder();
            for (Akun akun : DAFTAR_AKUN.values()) {
                isi.append(akun.username).append('\t').append(akun.password).append(System.lineSeparator());
            }
            Files.writeString(FILE_AKUN, isi.toString(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("  [!] Data akun gagal disimpan: " + e.getMessage());
        }
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
        if (DAFTAR_AKUN.containsKey(username.trim())) {
            return null;
        }
        Akun akun = new Akun(username.trim(), hash(password));
        DAFTAR_AKUN.put(username.trim(), akun);
        simpanAkun();
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
        if (akun == null || !akun.password.equals(hash(password))) {
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