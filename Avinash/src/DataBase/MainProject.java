package DataBase;

public class MainProject {

    public static void main(String[] args) {
        DBConnection d = new DBConnection();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        d.getCon();
        Operations o = new Operations();
        o.display();
        o.calculate();
    }
}
