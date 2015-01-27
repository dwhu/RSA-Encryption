package rsa;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    RSA rsa = new RSA(1024,"/Users/hughdw11/code/CSC222/rsa/pubKeys.txt","/Users/hughdw11/code/CSC222/rsa/privKeys.txt");
    
    Scanner scan = new Scanner(System.in);
    System.out.println("Public Key: "+ rsa.publicKey + "\n");
    System.out.println("Private Key: " + rsa.privateKey + "\n");
    System.out.println("N: " + rsa.n + "\n");

//    Read in Message
    System.out.println("Encrypted Message: ");
    String en = scan.nextLine();
    
    BigInteger encrypt = rsa.encrypt(en);
//    System.out.println("Encrypted Message: ");
//    String encrpt = scan.next();
    BigInteger decrypt = rsa.decrpyt(encrypt);
    
    System.out.println("\n\n Message Decrypts to: "+ RSA.intToString(decrypt));
    
//    Prove that these methods works
    System.out.println("Origional message: " + RSA.intToString(RSA.stringToInt(en)));
  }

}
