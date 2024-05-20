import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection sakila_conn = null;
        try{

            sakila_conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila",
                    "root", "");
        }
        catch(SQLException e){
            System.out.println("Cannot connect to database: "
                    + e.getMessage());
        }
//     Insert Statements
        try {
            Statement sakila_statement = sakila_conn.createStatement();
            String category_insert = "INSERT INTO category (name) VALUES ('Romance_Test_4')";
            sakila_statement.executeUpdate(category_insert);
//      TODO: Write an insert statement for one of the other tables in sakila

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//      Query Statements
        try {
            Statement sakila_statement = sakila_conn.createStatement();
            String sakila_query = "select count(distinct store_id ) as store_count from inventory join film on film.film_id = inventory.film_id where title = 'CRAFT OUTFIELD';";
            ResultSet resultSet = sakila_statement.executeQuery(sakila_query);
            while (resultSet.next()){
                System.out.println(resultSet.getInt("store_count"));
            }
//      TODO: Write a query to find what movie is at the most stores. If there are multiple sort by movie title.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//      Query Parameters
        try {
           Scanner scnr = new Scanner(System.in);
           System.out.println("Category?");
           String cat = scnr.nextLine();
           String sakila_category = "INSERT INTO category (name) VALUES (?)";
           PreparedStatement sakila_insert_v2 = sakila_conn.prepareStatement(sakila_category);
           sakila_insert_v2.setString(1,cat);
           sakila_insert_v2.executeUpdate();
//      TODO: Write an insert using user input to add a film to the library.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//      Procedure Statements
        try {
            // Use the procedure we wrote in Python to call it from java
            CallableStatement sakila_cs = sakila_conn.prepareCall("{ call count_film(?,?,?)}");
            sakila_cs.setString(1, "G");
            sakila_cs.setInt(2, 2006);
            sakila_cs.registerOutParameter(3,java.sql.Types.INTEGER);
            sakila_cs.executeQuery();
            System.out.println(sakila_cs.getString(3));
//      TODO: Call the second procedure you set up using the Python API and print the results.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
