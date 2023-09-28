package newcodecoffee;
//library koneksi
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Koneksi {
    //variable koneksi
    Connection koneksi=null;
    
    public static Connection koneksiDb()
            {
                try{
                    String url = "jdbc:mysql://localhost/codecoffeedb";
                    String user = "root";
                    String pass = "";
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection koneksi = DriverManager.getConnection(url, user, pass);
                    return koneksi;
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(null,e);
                    return null;
                }
            }
}
