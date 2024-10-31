package war4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
    // Java DataBase Connectivity
    // Connection, Statement, ResultSet
    // driver
    // <protokół>:<baza_danych>://<adres_bazy_daych>/<nazwa_bazy_danych>
    // jdbc:postgresql://localhost:5432/zajavka_store
    //                ://127.0.0.1


    public static void main(String[] args) {

//        Class.forName() deprecated

        String address = "jdbc:postgresql://localhost:5432/zajavka_store";
        String username = "postgres";
        String password = "meow";

        String query1 = "INSERT INTO PRODUCERS (PRODUCER_NAME, ADDRESS)"
                + "VALUES ('Zaj', 'meowina 12');";
        String query2 = "UPDATE PRODUCERS SET ADDRESS = 'New address';";
        String query3 = "DELETE FROM PRODUCERS WHERE ID = 3;";
        String query4 = "SELECT * FROM PRODUCERS";
        try (
                Connection connection = DriverManager.getConnection(address, username, password);
                // parameters - direction of reading, if also write
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query4)
        ) {
//            System.out.println("connection: " + connection);
//            System.out.println("statement: " + statement);
//            System.out.println("resultSet: " + resultSet);
//            boolean execute = statement.execute(query4); // checks if returns result set
//            if (execute) { // also to check if valid
//                ResultSet resultSet1 = statement.getResultSet();
//            } else {
//                int updateCount = statement.getUpdateCount();
//            }
            if (resultSet.next()) {
                System.out.println(resultSet.getString("producer_name"));
            }

//            int i = statement.executeUpdate(query2); // returns number of modified rows
//            System.out.println(i);


            // Connection management - libraries
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
