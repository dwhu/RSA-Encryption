package rsa;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Class to implement RSA Encryption of Messages
 * @author hughdw11
 */
public class RSA {

//  Public prime d
//  Public n = d*e
  public BigInteger publicKey,n;
//  Private prime e
//  Private s = (e-1)*(d-1)
  private BigInteger privateKey,s;
  ModularArithmetic modArith;
  
  /*
   * Class Initializer for RSA Encryption
   * Generates Public N,e and private d of b bit length
   */
  public RSA(int b){
    modArith = new ModularArithmetic();
    publicKey = modArith.genPrime(b);
    privateKey = modArith.genPrime(b);
    n = privateKey.multiply(publicKey);
    s = (publicKey.subtract(BigInteger.ONE)).multiply(privateKey.subtract(BigInteger.ONE));
  }
  
  /*
   * Creates Set up for public and private keys 
   * Writes keys to the file
   */
  public RSA(int b,String filename){
    modArith = new ModularArithmetic();
    publicKey = modArith.genPrime(b);
    privateKey = modArith.genPrime(b);
    n = publicKey.multiply(privateKey);
    s = (publicKey.subtract(BigInteger.ONE)).multiply(privateKey.subtract(BigInteger.ONE));
    
    try {
      PrintWriter file = new PrintWriter(filename);
      file.write("Public Key: "+ publicKey + "\n Private Key: " + privateKey + "\n N: " + n);
      file.close();
    } catch (FileNotFoundException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    } 
  }
  
  /*
   * Reads in private key
   */
  public RSA(String filename){
    Scanner scan = new Scanner(filename);
    privateKey = scan.nextBigInteger();
  }
  
  /*
   * Encrypts a message using a private keys
   * For a public key (e,N) and some integer m 
   * Return a message = m^e (mode N)
   */
  public BigInteger encrypt(BigInteger m, BigInteger N, BigInteger e){
    return modArith.modexp(m, N, e);
  }
  
  /*
   * Decrypts a message
   * For a message c for the given instance
   * m = c^d (mod n)
   */
  public BigInteger decrpyt(BigInteger c){
    return modArith.modexp(c, privateKey, n);
  }
}
