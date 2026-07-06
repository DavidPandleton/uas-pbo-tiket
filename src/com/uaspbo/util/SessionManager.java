package com.uaspbo.util;

/**
 * SessionManager - menyimpan data user yang sedang login.
 *
 * CATATAN: File ini pada README merupakan bagian dari branch
 * fitur/login-auth (dikerjakan Komang). Versi di sini adalah
 * STUB minimal supaya Main.java & AboutPanel.java bisa dikompilasi
 * dan ditest secara mandiri di branch fitur/about-us.
 *
 * Saat merge ke main, pastikan method di bawah ini konsisten
 * (nama & signature) dengan implementasi asli dari Komang,
 * atau ganti isi file ini dengan punya Komang.
 */
public class SessionManager {

    private static String namaUser;
    private static String username;
    private static boolean loggedIn = false;

    private SessionManager() {
        // utility class, tidak boleh diinstansiasi
    }

    public static void login(String username, String namaUser) {
        SessionManager.username = username;
        SessionManager.namaUser = namaUser;
        SessionManager.loggedIn = true;
    }

    public static void logout() {
        username = null;
        namaUser = null;
        loggedIn = false;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static String getUsername() {
        return username;
    }

    public static String getNamaUser() {
        return namaUser != null ? namaUser : "Guest";
    }
}