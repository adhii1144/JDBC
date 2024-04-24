package DataBase;

import java.lang.ref.PhantomReference;
import java.sql.*;
import java.util.Scanner;

public class Operations {
    static Scanner Sc = new Scanner(System.in);

    public static void display() {
        try {
            String query = "select * from Food";
            Connection con = DBConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("food_id");
                String name = rs.getString("name");
                double carbo = rs.getDouble("carbo");
                double protein = rs.getDouble("protein");
                double fat = rs.getDouble("Fat");
                double cal = rs.getDouble("calories");
                //System.out.println("Item_id : "+id+"    "+"Item Name :"+name+"   "+"Carbohydrates: "+carbo+"    "+"Protein: "+protein+"     "+"Fat :"+fat+"    "+"Calories :"+cal);
                System.out.format("Item_id : %d    Item Name : %-30s   Carbohydrates: %-5.1f    Protein: %-5.1f     Fat :%-5.1f    Calories :%-5.1f%n", id, name, carbo, protein, fat, cal);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void calculate() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter how many items:");
        int n = sc.nextInt();

        double totalCalories = 0.0;

        for (int i = 0; i < n; i++) {
            try {
                System.out.println("Enter the ID of Item " + (i + 1) + ":");
                int id = sc.nextInt();

                System.out.println("Enter the quantity of Item " + (i + 1) + ":");
                int quantity = sc.nextInt();

                String query = "SELECT name, carbo, protein, Fat FROM Food WHERE food_id = ?";

                Connection con = DBConnection.getCon();
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String name = rs.getString("name");
                    double carbo = rs.getDouble("carbo");
                    double pro = rs.getDouble("protein");
                    double fat = rs.getDouble("Fat");

                    double formula = ((pro * 4) + (carbo * 4) + (fat * 9)) * quantity;
                    totalCalories += formula;

                    System.out.println("The No. of calories in " + name + ": " + formula);

                    insertData(name, carbo, pro, fat, quantity);
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Total calories for all items: " + totalCalories);
    }

    public static void insertData(String name, double carbo, double pro, double fat, int quantity) {
        try {
            String query = "INSERT INTO SaveData(name, carbo, protein, Fat, quantity) VALUES (?, ?, ?, ?, ?)";

            Connection con = DBConnection.getCon();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setDouble(2, carbo);
            ps.setDouble(3, pro);
            ps.setDouble(4, fat);
            ps.setInt(5, quantity);

            ps.executeUpdate();

            System.out.println("Data inserted successfully.");

        } catch (SQLException e) {
            System.out.println( e.getMessage());
        }
    }
}

//SaveData SQL query for creating the table
//> CREATE TABLE SaveData (
//    ->         id INT AUTO_INCREMENT PRIMARY KEY,
//        ->         name VARCHAR(255) NOT NULL,
//    -> carbo DOUBLE NOT NULL,
//    -> protein DOUBLE NOT NULL,
//    -> Fat DOUBLE NOT NULL,
//    -> quantity INT NOT NULL,
//    -> timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
//    -> );