package org.ccffee.utils;

public final class MathUtils {
    public static double root(double n, double exponent,int accuracy){
        double r = 0;
        double ro=0;
        int localAccuracy = 0;
        w: while(accuracy<=localAccuracy){
            double f = Math.pow(10,localAccuracy);
            int i;
            for (i = 1;Math.pow(r+i*f,exponent)<n;i++);
            r+=--i*f;
            if(Math.pow(ro=round(r,accuracy),exponent)==n)return ro;
            localAccuracy--;
        }
        return ro;
    }
    public static double round(double n, int accuracy){
        double f = Math.pow(10,accuracy);
        return Math.round(n/f)*f;
    }
    public static String roundString(double n, int accuracy){
        return cut(round(n,accuracy),accuracy);
    }
    public static String cut(double n, int accuracy){
        String s = Double.toString(n);
        int i = s.indexOf('.')-accuracy;
        if(accuracy<0)i++;
        s = s.substring(0,i);
        for (i=0; i<accuracy;i++)s+='0';
        return s;
    }
}
