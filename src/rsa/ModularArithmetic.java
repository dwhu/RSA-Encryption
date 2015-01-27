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
     BigInteger x = a.multiply(b);
     return mod(x,n);
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
    BigInteger aDivB = mod(abInverse,n);
    System.out.println("a: "+ a + " b: "+ b + " n " + n+ " "+ bInverse +" " + aDivB);
    return aDivB;
  }
  
  
  /*
   *  c = modexp(a,b,n)
   *  c = a^b (mod n)
   */
  public static BigInteger modexp(BigInteger a, BigInteger b, BigInteger n){
    
    final BigInteger two = new BigInteger("2");
    
    if(b.compareTo(BigInteger.ZERO) == 0){
      return BigInteger.ONE;
    }
    
    // b/2
    BigInteger bdiv2 = b.divide(two);
    BigInteger z = modexp(a,bdiv2,n);
    
    //z^2 mod n
    z = modmult(z,z,n);
    
    //If Odd (z^2)mod(n)
    if(mod(b,two).compareTo(BigInteger.ONE) == 0){
      return z;
    }else{
      //If Even ((z^2)*a)mod(n)
      
      return mod(z.multiply(a),n);
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
      
      //Mod it early before it gets really expensive when it is taken to 
      //a huge power
      BigInteger currentBig = new BigInteger(currentInt.toString());
   
      //i^(n-1) mod n
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
  public static BigInteger genPrime(final int n){
    
    if(n == 0){
      return BigInteger.ONE;
    }
    
    BigInteger two = new BigInteger("2");
    BigInteger smallestPossibleNum = new BigInteger("2"); 

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
  
  private static BigInteger mod(BigInteger a, BigInteger n){

    if(a.compareTo(n) > 0 ){
      while(a.compareTo(n) > 0 && a.compareTo(BigInteger.ZERO) > 0 ){
        a = a.subtract(n);
      }
    }else if(a.compareTo(n) < 0){
      while(a.compareTo(n) < 0 && a.compareTo(BigInteger.ZERO) < 0){
        a = a.add(n);
      }
    }else{
      return BigInteger.ZERO;
    }
    return a;
  }
  
  
  /*
   * Extend Euclid Algorithm
   */
  public static BigInteger [] extendedEuclid(BigInteger a, BigInteger n){
    
    // a >= n >=0
    if(n.compareTo(BigInteger.ZERO) == 0){
      return new EuclidObject(BigInteger.ONE,BigInteger.ZERO,a).toArray();
    }
    
    EuclidObject prevStep = new EuclidObject(extendedEuclid(n,a.mod(n)));
    
    //x'-floor(a/b)*y'
    BigInteger aDivBMultY = (a.divide(n)).multiply(prevStep.y);
    BigInteger tmp = prevStep.x.add(aDivBMultY.negate());
    
    //return [y',x'-floor(a/b)*y',d']
    return new EuclidObject(prevStep.y,tmp,prevStep.d).toArray();
   
  }
  
  /**
   * Class to allow me to pass multiple vars in a function
   *Class maps to an Euclid object where ax+by=d
   */
  public static class EuclidObject {
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
