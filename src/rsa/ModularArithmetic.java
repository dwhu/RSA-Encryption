package rsa;

import java.math.BigInteger;
import java.util.Random;

import com.sun.xml.internal.bind.util.Which;

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
    return this.mod(a.add(b),n);
  }
  
  /*
   *  c = modmult(a,b,n)
   *  c = a*b (mod n)
   */
  public BigInteger modmult(BigInteger a, BigInteger b, BigInteger n){
    
    if (b.equals(BigInteger.ZERO)) return BigInteger.ZERO;
    
    BigInteger c = this.modmult(a, b.divide(new BigInteger("2")), n);
    if(c.testBit(0)){
//      C is odd
      return this.mod(c.multiply(new BigInteger("2")),n);
    }
//    C is even
    return this.mod(a.add(c.multiply(new BigInteger("2"))),n);
  }
  
  /*
   *  c = moddiv(a,b,n)
   *  c = a/b (mod n)
   */
  public BigInteger moddiv(BigInteger a, BigInteger b, BigInteger n){
    
    EuclidObject eo = this.extend_euclid(b,n);
    return this.modmult(a, eo.x, n);
  }
  
  /*
   *  c = modexp(a,b,n)
   *  c = a^b (mod n)
   */
  public BigInteger modexp(BigInteger a, BigInteger b, BigInteger n){
    
    if(b.compareTo(BigInteger.ZERO) == 0){
      return BigInteger.ONE;
    }
    
    BigInteger z = modexp(a,b.divide(new BigInteger("2")),n);
    
//    If Odd (z^2)mod(n)
    if(b.testBit(0)){
      return this.modmult(z, z, n);
    }else{
//      If Even ((z^2)*a)mod(n)
      return this.modmult(z.multiply(z), a, n);
    }
  }
  
//  NOT WORKING YET
//  /*
//   * True if probability of n is prime <= (1/2)^k
//   * False otherwise
//   */
//  public boolean isPrime(BigInteger n, int k){
//    return n.isProbablePrime(k);
//  }
//  
//  /*
//   * Generates Prime Numbers of n bits
//   */
//  public BigInteger genPrime(int n){
//    Integer largestNum = new Integer(2^n-1);
//    
//    for(int i = largestNum; i >0; i--){
//      for(int x = i; x<i;x++){
//        BigInteger 
//        if() 
//      }
//    }
//     
//  }
  
  private BigInteger mod(BigInteger a, BigInteger n){
    while(a.compareTo(n) == -1){
      a = a.subtract(n);
    }
    return a;
  }
  
  /*
   * Extend Euclid Algorithm
   */
  private EuclidObject extend_euclid(BigInteger a, BigInteger b){
    
//    a >= n >=0
    if(b.equals(BigInteger.ZERO)){
      return new EuclidObject(BigInteger.ONE,BigInteger.ZERO,a);
    }
    
    EuclidObject prevStep = extend_euclid(b,a.mod(b));
    
//    x'-floor(a/b)*y'
    BigInteger tmp = prevStep.x.add(a.not().divide(b).multiply(prevStep.y));
//    return [y',x'-floor(a/b)*y',d']
    return new EuclidObject(prevStep.y,tmp,prevStep.d);
  }
  
//  Class to allow me to pass multiple vars in a function
//  Class maps to an Euclid object where ax+by=d
  class EuclidObject {
    public BigInteger x,y,d;
    
    public EuclidObject(BigInteger x1, BigInteger y1, BigInteger d1){
      x=x1;
      y=y1;
      d=d1;
    }
  }
  
  
//  Greatest Common Divisor
//  d=gcd(a,b) d|a d|b where d>= all other divisors
// AKA a =d(q1) b=d(q2)
  private BigInteger gcd(BigInteger a, BigInteger b){
//    a>=b>=0
    if(b.compareTo(BigInteger.ZERO) == 0) return a;
    return gcd(b,a.mod(b));
  }
}
