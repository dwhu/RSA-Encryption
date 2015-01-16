package rsa;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    RSA rsa = new RSA(1024,"/Users/hughdw11/code/CSC222/rsa/kets.txt");
    
    Scanner scan = new Scanner(System.in);
    System.out.println("Public Key: "+ rsa.publicKey + "\n");
    
    System.out.println("Encrypted Message: ");
    BigInteger encrpt = scan.nextBigInteger();
    BigInteger decrypt = rsa.decrpyt(encrpt);
    
    System.out.println("\n\n Message Decrypts to: "+ decrypt);
  }

}
