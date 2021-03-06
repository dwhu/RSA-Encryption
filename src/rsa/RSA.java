package rsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Class to implement RSA Encryption of Messages
 * @author hughdw11
 */
public class RSA {

  private static final BigInteger ONE = BigInteger.ONE;
  
  //  Public prime e
  public BigInteger publicKey,
  //Public n = d*e
    n;
  //Private prime d
  private BigInteger privateKey,
  //Private s = (e-1)*(d-1)
    s;
  
  /*
   * Class Initializer for RSA Encryption
   * Generates Public N,e and private d of b bit length
   */
  public RSA(int b){
    generateKeys(b);
  }
  
  /*
   * Creates Set up for public and private keys 
   * Writes keys to the file
   */
  public RSA(int b,String filename){
    generateKeys(b);
   
    //Print public key and N to a file
    try {
      PrintWriter file = new PrintWriter(filename);
      file.write(n + "\n" + publicKey);
      file.close();
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } 
  }
  
  /*
   * Creates Set up for public and private keys 
   * Writes keys to the file
   */
  public RSA(int b,String pubFilename, String privFilename){
    generateKeys(b);
    
    //Print public key and N to a file
    try {
      PrintWriter file = new PrintWriter(pubFilename);
      file.write(n + "\n" + publicKey);
      file.close();
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } 
    //Print privateKey to a file
    try {
      PrintWriter file = new PrintWriter(privFilename);
      file.write(n + "\n" + privateKey);
      file.close();
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } 
  }
  
  /*
   * Reads in private key
   */
  public RSA(String filename){
    Scanner scan = new Scanner(filename);
    n = scan.nextBigInteger();
    privateKey = scan.nextBigInteger();
  }
  
  /*
   * Method called by all constructors to generate keys
   */
  private void generateKeys(int b){
    //  Generate some Prime p and q
    BigInteger p = ModularArithmetic.genPrime(b),
        q = ModularArithmetic.genPrime(b);
    
    //Generate some prime public key
    publicKey = ModularArithmetic.genPrime(b);
    //Debug -- Make public Ket sane
    //publicKey = new BigInteger("11");

    
    //n = p*q
    n = p.multiply(q);
    
    //(p-1)(q-1)
    s = p.subtract(ONE).multiply(q.subtract(ONE));
        
    //publicKey^-1 mod s =  1/publicKey mod s  
    privateKey = ModularArithmetic.moddiv(ONE,publicKey,s);
  }
  
  /*
   * Encrypts a message using a private keys
   * For a public key (e,N) and some integer m 
   * Return a message = m^e (mode N)
   */
  public BigInteger encrypt(BigInteger message, BigInteger N, BigInteger pub){
    BigInteger val = ModularArithmetic.modexp(message, pub, N);
    return val;
  }
  
  public BigInteger encrypt(String s){
    return encrypt(stringToInt(s),n,publicKey);
  }
  
  /*
   * Decrypts a message
   * For a message c for the given instance
   * m = c^d (mod n)
   */
  public BigInteger decrpyt(BigInteger encrypted){
    BigInteger val = ModularArithmetic.modexp(encrypted, privateKey, n);
    return val;
  }
  
  public BigInteger decrpyt(String encrpt) {
    return decrpyt(stringToInt(encrpt));
  }
  
  /*
   * Creates an encrypted version of the input file
   */
  public void encryptFile(String filename, BigInteger N, BigInteger e) throws IOException{
    
    //Convert to Big Int and encrypt
    BigInteger fileAsNumb = this.readFileAsBigInt(filename);
    //Encrypt File
    BigInteger fileEncrypted = this.encrypt(fileAsNumb, N, e);
    
    //Output new file
    RandomAccessFile write = new RandomAccessFile(filename + "enc", "w");
    write.write(fileEncrypted.toByteArray());
    write.close();
  }
  
  /*
   * Decrypts information from a file
   */
  public void decryptFile(String filename) throws IOException{
    
    //Read File
    BigInteger encrypted = this.readFileAsBigInt(filename);
    //Decrypt
    BigInteger decrypt = this.decrpyt(encrypted);
    
    //Output new file
    FileOutputStream write = new FileOutputStream(new File(filename + "enc", "w"));
    write.write(decrypt.toByteArray());
    write.close();
  }
  
  /*
   * Convert String Message to Int
   * Taken from RSADriver.java
   */
  public static BigInteger stringToInt(String message){
    return new BigInteger(message.getBytes());   
  }
  
  /*
   * Convert Big Int Message to String
   * Taken from RSADriver.java
   */
  public static String intToString(BigInteger m){
    return new String(m.toByteArray());
  }
  
  /*
   * Helper function to convert file to a BigInteger value
   */
  private BigInteger readFileAsBigInt(String filename) throws IOException{
    FileInputStream stream = new FileInputStream(new File(filename));
    byte[] data = new byte[(int) ((CharSequence) stream).length()];
    stream.read(data);
    stream.close();
    return new BigInteger(data);
  }
}
