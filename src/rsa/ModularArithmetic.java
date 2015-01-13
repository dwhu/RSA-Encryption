package rsa;

import java.math.BigInteger;
import java.util.Random;

/**
 * Class to Implement basic Modular Arithmetic functions
 * @author hughdw11
 *
 */
public class ModularArithmetic {
  
  /*
   *  c = modadd(a,b,n)
   *  c = a+b (mod n)
   */
  public BigInteger modadd(BigInteger a, BigInteger b, BigInteger n){
    return ( a.add(b) ).mod(n);
  }
  
  /*
   *  c = modmult(a,b,n)
   *  c = a*b (mod n)
   */
  public BigInteger modmult(BigInteger a, BigInteger b, BigInteger n){
    return ( a.multiply(b) ).mod(n);
  }
  
  /*
   *  c = moddiv(a,b,n)
   *  c = a/b (mod n)
   */
  public BigInteger moddiv(BigInteger a, BigInteger b, BigInteger n){
    return ( a.divide(b) ).mod(n);
  }
  
  /*
   *  c = modexp(a,b,n)
   *  c = a^b (mod n)
   */
  public BigInteger modexp(BigInteger a, BigInteger b, BigInteger n){
    return a.modPow(b, n);
  }
  
  /*
   * True if probability of n is prime <= (1/2)^k
   * False otherwise
   */
  public boolean isPrime(BigInteger n, int k){
    return n.isProbablePrime(k);
  }
  
  /*
   * Generates Prime Numbers of n bits
   */
  public BigInteger genPrime(int n){
    return BigInteger.probablePrime(n, (new Random()));
  }
}
