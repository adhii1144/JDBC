package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/ProjectData";
    private static final String username ="root";
    private static  final String password ="adhii1144@";
    public static Connection con = null;
    public static Connection getCon(){
        try{
            con = DriverManager.getConnection(url,username,password);
            //System.out.println("Connection done");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return con;
    }

}
