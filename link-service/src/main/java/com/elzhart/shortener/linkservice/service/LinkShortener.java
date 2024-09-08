package com.elzhart.shortener.linkservice.service;


public class LinkShortener {
    static String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String shorten(long linkId) {
        StringBuilder sb = new StringBuilder();
        long quotient = linkId;
        int remainder;
        while (quotient > 0) {
            remainder = (int) (quotient % 62);
            sb.append(charset.charAt(remainder));
            quotient /= 62;
        }
        return sb.reverse().toString();
    }

    public static Long encode(String shortLink) {
        char[] ids = shortLink.toCharArray();
        long id = 0L;
        int exp = ids.length - 1;
        for (int i = 0; i < ids.length; ++i, --exp) {
            int base10 = charset.indexOf(ids[i]);
            id += (long) (base10 * Math.pow(62.0, exp));
        }
        return id;
    }

}
