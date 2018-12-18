package com.kgcorner.springsecuritydemo.utility;

public final class Strings {
    private Strings(){}

    public static boolean isNullOrEmpty (String value) {
        return value == null || value.trim().equals("");
    }
}
