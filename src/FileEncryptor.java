import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class FileEncryptor {
	//private static String filename;
	   private static FileInputStream inFile;
	   private static FileOutputStream outFile;
	   static String result;
	   private String password;
	   
	   public String getPassword(){
		   return password;
	   }
	 
	public FileEncryptor(File file, String filename) throws NoSuchAlgorithmException,
	InvalidKeySpecException, NoSuchPaddingException, IOException,
	IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
	InvalidAlgorithmParameterException {
	 
	      // File to encrypt.
	      //this.filename = filename;
	 
		PasswordGenerator passGen = new PasswordGenerator();
        
//        System.out.println("FileDecryptor " + filename);
 
           password = passGen.generatePassword();
	 
	        inFile = new FileInputStream(filename);
	 
	      outFile = new FileOutputStream(filename);
	 
	      // Use PBEKeySpec to create a key based on a password.
	      // The password is passed as a character array
	 
	      PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
	      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
	      SecretKey passwordKey = keyFactory.generateSecret(keySpec);
	 
	      // PBE = hashing + symmetric encryption.  A 64 bit random
	      // number (the salt) is added to the password and hashed
	      // using a Message Digest Algorithm (MD5 in this example.).
	      // The number of times the password is hashed is determined
	      // by the interation count.  Adding a random number and
	      // hashing multiple times enlarges the key space.
	 
	      byte[] salt = new byte[8];
	      Random rnd = new Random();
	      rnd.nextBytes(salt);
	      int iterations = 100;
	 
	      //Create the parameter spec for this salt and interation count
	 
	      PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iterations);
	 
	      // Create the cipher and initialize it for encryption.
	 
	      Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
	      cipher.init(Cipher.ENCRYPT_MODE, passwordKey, parameterSpec);
	 
	      // Need to write the salt to the (encrypted) file.  The
	      // salt is needed when reconstructing the key for decryption.
	 
	      outFile.write(salt);
	 
	      // Read the file and encrypt its bytes.
	 
	      byte[] input = new byte[64];
	      int bytesRead;
	      while ((bytesRead = inFile.read(input)) != -1)
	      {
	         byte[] output = cipher.update(input, 0, bytesRead);
	         if (output != null) outFile.write(output);
	      }
	 
	      byte[] output = cipher.doFinal();
	      if (output != null) outFile.write(output);
	 
	      inFile.close();
	      outFile.flush();
	      outFile.close();
	 
	    result = "File " + filename + " Encrypted";
	  }
}
