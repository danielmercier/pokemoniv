package ivcalc.util;

public class Util {
    public static double clamp(double min, double x, double max){
        return Math.min(Math.max(min, x), max);
    }

    public static int round(double x, double places){
        return (int)(Math.round(x * Math.pow(10, places)) / Math.pow(10, places));
    }
}
