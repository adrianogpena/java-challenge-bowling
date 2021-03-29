package com.util;

public class BowlingUtils {
  public static String padRight(String s, int n) {
    return String.format("%-" + n + "s", s);  
  }

  public static String padLeft(String s, int n) {
    return String.format("%" + n + "s", s);  
  }
}
