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
  public static BigInteger modadd(BigInteger a, BigInteger b, BigInteger n){    
    return mod(a.add(b),n);
  }
  
  
  /*
   *  c = modmult(a,b,n)
   *  c = a*b (mod n)
   */
  public static BigInteger modmult(BigInteger a, BigInteger b, BigInteger n){
     a = mod(a,n);
     b = mod(b,n);
     return mod(a.multiply(b),n);
  }
  
  
  /*
   *  c = moddiv(a,b,n)
   *  c = a/b (mod n)
   */
  public static BigInteger moddiv(BigInteger a, BigInteger b, BigInteger n){
    
//    nx+by=d
    EuclidObject eo = new EuclidObject(extendedEuclid(n,b));
    System.out.println(eo.toString());
    return mod(a.multiply(mod(eo.y,n)), n);
  }
  
  
  /*
   *  c = modexp(a,b,n)
   *  c = a^b (mod n)
   */
  public static BigInteger modexp(BigInteger a, BigInteger b, BigInteger n){
    
    if(b.compareTo(BigInteger.ZERO) == 0){
      return BigInteger.ONE;
    }
    
    BigInteger z = modexp(a,b.divide(new BigInteger("2")),n);
    z = modmult(z,z,n);
//    If Odd (z^2)mod(n)
    if(!b.testBit(0)){
      return z;
    }else{
//      If Even ((z^2)*a)mod(n)
      return mod(z.multiply(mod(a,n)),n);
    }
  }
  
  
  /*
   * True if probability of n is prime <= (1/2)^k
   * False otherwise
   */
  public static boolean isPrime(BigInteger n, int k){
    
    BigInteger nMinusOne = n.subtract(BigInteger.ONE);
    Random rand = new Random();

    for(int i = 0; i < k; i++){
      
      Integer currentInt = rand.nextInt(Math.abs(nMinusOne.intValue()))+ 1;
      
//      Mod it early before it gets really expensive when it is taken to 
//      a huge power
      BigInteger currentBig = new BigInteger(currentInt.toString());
//      
//      i^(n-1) mod n
      BigInteger modExp = modexp(currentBig,nMinusOne,n);
            
      if(modExp.compareTo(BigInteger.ONE) != 0){
        return false;
      }
    }
    
    return true;
  }
  
  
  /*
   * Generates Prime Numbers of n bits
   */
  public static BigInteger genPrime(int n){
    
    if(n == 0)
      return BigInteger.ONE;
    
    Integer largestPossibleNum = 2;
    
    Integer smallestPossibleNum = null; 

//    2^n
    for(int i = 1; i < n; i++){
      if(i == n-1){
//      (2^n)-1
        smallestPossibleNum = largestPossibleNum;
      }
      largestPossibleNum = largestPossibleNum*2;
    }
    
    Integer primeNumber = null;
    Random rand = new Random();
    
    while(true){
      primeNumber = rand.nextInt(largestPossibleNum);

      if(primeNumber >= smallestPossibleNum &&
          isPrime(new BigInteger(primeNumber.toString()),n)){
        return new BigInteger(primeNumber.toString());
      }
      
    }
  } 
  
  private static BigInteger mod(BigInteger a, BigInteger n){
    a = a.abs();
    while(a.compareTo(n) >=0){
      a = a.subtract(n);
    }
    return a;
  }
  
  
  /*
   * Extend Euclid Algorithm
   */
  public static BigInteger [] extendedEuclid(BigInteger a, BigInteger b){
    
//    a >= n >=0
    if(b.equals(BigInteger.ZERO)){
      return new EuclidObject(BigInteger.ONE,BigInteger.ZERO,a).toArray();
    }
    
    EuclidObject prevStep = new EuclidObject(extendedEuclid(b,a.mod(b)));
    
//    x'-floor(a/b)*y'
    BigInteger tmp = prevStep.x.add((a.divide(b)).multiply(prevStep.y).multiply(new BigInteger("-1")));
    
//    return [y',x'-floor(a/b)*y',d']
    return new EuclidObject(prevStep.y,tmp,prevStep.d).toArray();
  }
  
  
//  Greatest Common Divisor
//  d=gcd(a,b) d|a d|b where d>= all other divisors
// AKA a =d(q1) b=d(q2)
  private BigInteger gcd(BigInteger a, BigInteger b){
//    a>=b>=0
    if(b.compareTo(BigInteger.ZERO) == 0) return a;
    return gcd(b,a.mod(b));
  }
  
  
  /**Class to allow me to pass multiple vars in a function
   Class maps to an Euclid object where ax+by=d
  */
  private static class EuclidObject {
      public BigInteger x,y,d;
      
      public EuclidObject(BigInteger x1, BigInteger y1, BigInteger d1){
        x=x1;
        y=y1;
        d=d1;
      }
      
      public EuclidObject(BigInteger [] arry){
        x = arry[0];
        y = arry[1];
        d = arry[2];
      }
      
      public BigInteger [] toArray(){
        BigInteger [] arry = new BigInteger[3]; 
        arry[0] = x;
        arry[1] = y;
        arry[2] = d;
        return arry;
      }
      
      @Override
      public String toString(){
        return "["+x+","+y+","+d+"]";
      }
  }
}
