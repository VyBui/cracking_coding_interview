package com.nissan.com.nissan.cracking.the.code.interview;

import java.util.ArrayList;
import java.util.Arrays;

public class PalindromePermutation {

  static int NO_OF_CHARS = 256;
  public static void main(String[] args) {

    String testPalinString = "taco cat";
    testPalinString = removeSpecialCharacter(testPalinString);
    System.out.println("Is this word palindrome?: " + isPalindrome(testPalinString.toCharArray()));
  }


  /**
   * Remove all special characters like !@#$%^&* ... with empty
   * 
   * 
   * @param testPalinString
   * @return 100% lower-case of a given string
   */
  private static String removeSpecialCharacter(String testPalinString) {
    return testPalinString.replaceAll("[^A-Za-z]", "");
  }

  /**
   * Check if the char array input is a sequence of characters with the same backward and forward
   * Assume the char array is 100% lower case with characters from a -> z
   * 
   * @param stringInCharsArray
   * @return true if "tacoocat" is a palindrome
   */
  public static boolean isPalindrome(char[] stringInCharsArray) {

    if (stringInCharsArray == null) {

      // the array is not valid, however for the sake of brevity we will return false. The array is
      // not palindrome

      return false;
    }

    boolean isPalinddrome = true;

    int length = stringInCharsArray.length;
    int j = length - 1;
    
    System.out.println("length divide to 2: " + (length - 1));

    for (int i = 0; i < length / 2; i++) { // 7 divide 2 equals 3

      // j starts from backward

      if (stringInCharsArray[i] != stringInCharsArray[j]) {
        isPalinddrome = false;
      }
      j--;
    }

    return isPalinddrome;
  }

  
  public static ArrayList<String> generatePermutationList(String wordsString) {
    
    if (wordsString == null) {
      
      return null;
    }
    
    ArrayList<String> listPermutation = new ArrayList<String>();
    
    for (int i = 0; i <= wordsString.length(); i++) {

      if (arePermutation(wordsString.toCharArray(), wordsString.toCharArray())) {
        listPermutation.add(wordsString);
      }
    }
    return listPermutation;
  }
  
  static boolean arePermutation(char[] str1, char[] str2) {
   
    // Create 2 count arrays and initialize all value as 0
    int count1[] = new int[NO_OF_CHARS];
    Arrays.fill(count1  , 0);
    
    int count2[] = new int[NO_OF_CHARS];
    Arrays.fill(count2  , 0);
    
    int i;
    
    // For each character in input string,
    // increment count in the correcspoding
    // count array
    for (i = 0; i < str1.length && i < str2.length; i++) {
      
      count1[str1[i]]++;
      count1[str1[i]]++;
    }
   
    // If both strings are in different length.
    // Remove this condition will make the program
    // fail for strings like "aaca" and "aca"
    
    if (str1.length != str2.length) 
      return false;
   
    // Compare count array
    for  (i = 0; i < NO_OF_CHARS; i++) {
      if (count1[i] != count2[i]) {
        return false;
      }
    }
    
    return true;
  }
}
