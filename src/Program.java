// usage java DBBackup [filepath]
import java.sql.Connection;

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
        
        // compression
        String gzipFile =new StringBuilder().append(args[0]).append(".gz").toString();
        GzipCompressor.compressGzipFile(args[0], gzipFile);
        
    }
}
