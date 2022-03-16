package org.ccffee.utils;

public final class NumberUtils {
    private NumberUtils(){

    }

    public static <T extends Number> String formatAllDigits(T n){
        return addMissingChars(String.valueOf(n), maxLength(n),'0');
    }

    public static <T extends Number> String formatAllHexDigits(T n){
        return addMissingChars(n instanceof Long ? Long.toHexString(n.longValue()):Integer.toHexString(n.intValue()),maxHexLength(n),'0');
    }

    public static <T extends Number> String formatAllMissingChars(T n,char c){
        return addMissingChars(String.valueOf(n), maxLength(n),c);
    }

    public static <T extends Number> String formatAllMissingHexChars(T n,char c){
        return addMissingChars(n instanceof Long ? Long.toHexString(n.longValue()):Integer.toHexString(n.intValue()),maxHexLength(n),c);
    }

    public static String addMissingChars(String n, int maxLength, char c){
        String r = "";
        for(int i = n.length();i<maxLength;i++)r+=c;
        return r+n;
    }


    public static <T extends Number> int maxLength(T n){
        if(n instanceof Long)return String.valueOf(Long.MAX_VALUE).length();
        else if(n instanceof Integer)return String.valueOf(Integer.MAX_VALUE).length();
        else if(n instanceof Short)return String.valueOf(Short.MAX_VALUE).length();
        else if(n instanceof Byte)return String.valueOf(Byte.MAX_VALUE).length();
        else throw new IllegalArgumentException("Only supports Long, Integer, Short and Byte");
    }
    public static <T extends Number> int maxHexLength(T n){
        if(n instanceof Long)return Long.toHexString(Long.MAX_VALUE).length();
        else if(n instanceof Integer)return Integer.toHexString(Integer.MAX_VALUE).length();
        else if(n instanceof Short)return Integer.toHexString(Short.MAX_VALUE).length();
        else if(n instanceof Byte)return Integer.toHexString(Byte.MAX_VALUE).length();
        else throw new IllegalArgumentException("Only supports Long, Integer, Short and Byte");
    }
}
