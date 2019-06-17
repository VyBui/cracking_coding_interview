package com.nissan.com.nissan.cracking.the.code.interview;

class GFG {
  public static void main (String[] args) {
      GFG obj = new GFG(); 
      String input1 = "GeeksforGeeks".toLowerCase(); 
      String input2 = "forgeeksgeeks".toLowerCase();

    if (obj.isAnagram(input1, input2)) 
        System.out.println("YES"); 
    else
        System.out.println("NO"); 
  }
  
  public boolean isAnagram(String input1, String input2) {
      int totalSum1 = 0;
      int totalSum2 = 0;
      
      for (int i=0; i < input1.length(); i++) {
          totalSum1 += (int) input1.charAt(i);
      }
      
      for (int i=0; i < input2.length(); i++) {
          totalSum2 += (int) input2.charAt(i);
      }
      
      return totalSum1 == totalSum2 ? true : false;
  }
}
