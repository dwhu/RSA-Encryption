package rsa;

import java.io.FileNotFoundException;
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

//  Public prime d
//  Public n = d*e
  public BigInteger publicKey,n;
//  Private prime e
//  Private s = (e-1)*(d-1)
  private BigInteger privateKey;
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
  
  /*
   * Creates an encrypted version of the input file
   */
  public void encryptFile(String filename, BigInteger N, BigInteger e) throws IOException{


    
//    Convert to Big Int and encrypt
    BigInteger fileAsNumb = this.readFileAsBigInt(filename);
//    Encrypt File
    BigInteger fileEncrypted = this.encrypt(fileAsNumb, N, e);
    
//    Output new file
    RandomAccessFile write = new RandomAccessFile(filename + "enc", "w");
    write.write(fileEncrypted.toByteArray());
    write.close();
  }
  
  /*
   * Decrypts information from a file
   */
  public void decryptFile(String filename) throws IOException{
    
//    Read File
    BigInteger encrypted = this.readFileAsBigInt(filename);
//    Decrypt
    BigInteger decrypt = this.decrpyt(encrypted);
    
//  Output new file
    RandomAccessFile write = new RandomAccessFile(filename + "enc", "w");
    write.write(decrypt.toByteArray());
    write.close();
    
  }
  
  private BigInteger readFileAsBigInt(String filename) throws IOException{
    //    Access File
    RandomAccessFile read = new RandomAccessFile(filename, "r");
    
//    Make Byte Array of Same size as file
    byte[] b = new byte[(int)read.length()];
//    Read file in as Bytes
    read.read(b);
    read.close();
    return new BigInteger(b);
  }
}
