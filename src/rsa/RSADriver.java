package rsa;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.math.BigInteger;

public class RSADriver
{
        
    public static void main(String[] args) throws IOException 
    {
        FileReader inputStream = null;
        int c;
        String message = "";     // textual message
        String intMessage = "";  // corresponding integer message to encrypt
        
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Enter filename: ");
        String filename = scan.next();
        
        message = readMessageFile(filename);
        System.out.println("original message = " + message);
        for(int i = 0; i < message.length(); i++)
        {
            c = message.charAt(i);
            System.out.print(String.format("%1$03d ", c));
            intMessage = intMessage + String.format("%1$03d", c);
        }
        System.out.println();
        System.out.println("m = " + intMessage);
        
        // generate private and public keys
        //RSA key = new RSA(512, "privKey.txt", "pubKey.txt");

        // load private and public keys from files
        //System.out.print("Enter public key filename: ");
        //String pubKeyFilename = console.next();
        RSA key = new RSA("pubKeys.txt");
                
        // Now encrypt the integer message using public key
        BigInteger cMes = key.encrypt((InintMessage.toString());
        System.out.println("c = " + cMes);

        // Now decrypt cMes to recover message
        BigInteger m = key.decrypt(cMes.toString());
        String mstr = m.toString();
        
        if (mstr.length() % 3 == 1)
        {
            System.out.println("error in decryption");
            return;
        }
        else if (mstr.length() % 3 == 2)
        {
            mstr = "0"+mstr;
        }
        System.out.println("m = " + mstr);
        String decryptedMessage = "";
        int a = 0;
        int b = 0;
        for (int i = 0; i < mstr.length()/3; i++)
        {
            a = mstr.charAt(3*i);   // decimal of charAt(3*i)
                        // e.g. 48 for '1', 52 for '4'
            a = (a-48)*100;     
            b = mstr.charAt(3*i+1);
            a += (b-48)*10;
            b = mstr.charAt(3*i+2);
            a += (b-48);
            System.out.print(a + ", "); // desired decimal rep of
                            // of the encrypted char
            decryptedMessage += (char)a;
        }
        System.out.println("\ndecrypted message = " + decryptedMessage);
        
    }
    
    public static String readMessageFile(String filename)
    {
        String message = null;
        BufferedReader input = null;
        try
        {
            input = new BufferedReader(new FileReader(filename));
            message = input.readLine();
            input.close();
        } catch(IOException e)
        {
            System.out.println("RSADriver.readMessageFile: problems reading from file "
              + filename);
        }
        return message;
    }
}    