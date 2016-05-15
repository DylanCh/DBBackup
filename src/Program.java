// usage java DBBackup [filepath]
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Program {
	public static void main(String[] args) {
        DBase db = new DBase();
        Connection conn = db.connect(
                "jdbc:mysql://localhost:3306/test","root","caspian");
         
        if (args.length != 1) {
            System.out.println(
                    "Usage: java Program [outputfile path] ");
            return;
        }
        // exporting to a csv file with the filname 
        db.exportData(conn,args[0]);
        
        //compression
        String gzipFile =new StringBuilder().append(args[0]).append(".gz").toString();
        GzipCompressor.compressGzipFile(args[0], gzipFile);
        
        FileEncryptor fileEncryptor;
        BufferedWriter out = null;
        
        try {
			 fileEncryptor = new FileEncryptor(args[0]);
			 FileWriter fstream = new FileWriter("out.txt", false); //true tells to append data.
	            out = new BufferedWriter(fstream);
	            out.write(fileEncryptor.getPassword());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        finally
        {
            if(out != null) {
                try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
    }
}
