package com.uaspbo.util;

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