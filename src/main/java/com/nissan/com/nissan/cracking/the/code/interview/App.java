package com.nissan.com.nissan.cracking.the.code.interview;

public class App {
  public static void main(String[] args) {
    int n = 20;
    int[] meno  = new int[n +1];
    for (int i = 0 ; i < n ; i++) {
      System.out.println("result: " + fib(i, meno));
    }
  }

  static int fib(int n, int[] memo) {
    if (n <= 0) { return 0; }
    else if (n == 1) { return 1; }
    else if (memo[n] > 0) { return memo[n];} // it has been calculated
    else {
      memo[n] = fib(n-1, memo) + fib(n-2, memo); 
    }
    return n;
  }
}
