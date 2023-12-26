package com.ll;

public class Calc {
  public static int run(String exp){

   String[] bits =  exp.split(" \\+ ");
   int a = Integer.parseInt(bits[0]);
   int b = Integer.parseInt(bits[1]);

   return a + b;

    String[] bits2 = exp.split(" \\- ");
    int c = Integer.parseInt(bits2[0]);
    int d = Integer.parseInt(bits2[1]);

    return c - d;
  }
}

