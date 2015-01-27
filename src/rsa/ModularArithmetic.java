package rsa;

import java.math.BigInteger;
import java.util.Random;

/**
 * Class to Implement basic Modular Arithmetic functions
 * @author hughdw11
 *
 */
public class ModularArithmetic {
 
  private static final BigInteger ZERO = BigInteger.ZERO;
  private static final BigInteger ONE = BigInteger.ONE;
  private static final BigInteger TWO = new BigInteger("2");
  
  
  /*
   *  c = modadd(a,b,n)
   *  c = a+b (mod n)
   */
  public static BigInteger modadd(BigInteger a, BigInteger b, BigInteger n){    
    return a.add(b).mod(n);
  }
  
  
  /*
   *  c = modmult(a,b,n)
   *  c = a*b (mod n)
   */
  public static BigInteger modmult(BigInteger a, BigInteger b, BigInteger n){
     return a.mod(n).multiply(b.mod(n)).mod(n);
  }
  
  
  /*
   *  c = moddiv(a,b,n)
   *  c = a/b (mod n)
   */
  public static BigInteger moddiv(BigInteger a, BigInteger b, BigInteger n){
    
    //nx+by=d
    BigInteger bInverse = extendedEuclid(b,n)[0];
    BigInteger abInverse = a.multiply(bInverse);
    //a*(b^-1) mod n
    BigInteger aDivB = abInverse.mod(n);
    System.out.println("a: "+ a + " b: "+ b + " n " + n+ " "+ bInverse +" " + aDivB);
    return aDivB;
  }
  
  
  /*
   *  c = modexp(a,b,n)
   *  c = a^b (mod n)
   */
  public static BigInteger modexp(BigInteger a, BigInteger b, BigInteger n){
  
    //Start with base of 1
    BigInteger result = ONE;
    
    //Tame a by n
    a = a.mod(n);
    
    //For each bit in b
    for (int i = 0; i < b.bitLength(); i++) { 
      
        //If the bit is set then we want to multiply
        // result*a mod n
        if (b.testBit(i)) { 
            result = result.multiply(a).mod(n);
        }
        
        //Square a because we have shifted up another power of b
        // same as a^2 mod n
        a = 
            a.multiply(a).mod(n);
    }
    return result; //return result, does what it says 
  }
  
  
  /*
   * True if probability of n is prime <= (1/2)^k
   * False otherwise
   */
  public static boolean isPrime(BigInteger n, int k){
    
    BigInteger nMinusOne = n.subtract(ONE);
    Random rand = new Random();

    for(int i = 0; i < k; i++){
      
      Integer currentInt = rand.nextInt(Math.abs(nMinusOne.intValue()))+ 1;
      
      //Mod it early before it gets really expensive when it is taken to 
      //a huge power
      BigInteger currentBig = new BigInteger(currentInt.toString());
   
      //i^(n-1) mod n
      BigInteger modExp = modexp(currentBig,nMinusOne,n);
            
      if(modExp.compareTo(ONE) != 0){
        return false;
      }
    }
    
    return true;
  }
  
  
  /*
   * Generates Prime Numbers of n bits
   */
  public static BigInteger genPrime(final int n){
    
    if(n == 0){
      return ONE;
    }
    
    BigInteger two = TWO;
    BigInteger smallestPossibleNum = TWO; 

    //2^n
    for(int i = 1; i < n-1; i++){
      smallestPossibleNum = smallestPossibleNum.multiply(two);
    }
    
    BigInteger primeNumber = null;
    Random rand = new Random();
    
    while(true){ 
      //Generates using the constructor a random number of n bits
      primeNumber = new BigInteger(n,rand);
      
      if(primeNumber.compareTo(smallestPossibleNum) >= 0 &&
          isPrime(primeNumber,n)){
        return primeNumber;
      }
      
    }
  } 
  
  /*
   * Extend Euclid Algorithm
   */
  public static BigInteger [] extendedEuclid(BigInteger a, BigInteger n){
    
    // a >= n >=0
    if(n.compareTo(ZERO) == 0){
      return new BigInteger [] {ONE,ZERO,a};
    }
    
    BigInteger [] prevStep = extendedEuclid(n,a.mod(n));
    
    //x'-floor(a/b)*y'
    BigInteger aDivBMultY = (a.divide(n)).multiply(prevStep[1]);
    BigInteger tmp = prevStep[0].add(aDivBMultY.negate());
    
    //return [y',x'-floor(a/b)*y',d']
    return new BigInteger [] {prevStep[2],tmp,prevStep[2]};
   
  }
}
