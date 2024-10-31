package war4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SomePreparedStatements {
    public static void main(String[] args) {
        String address = "jdbc:postgresql://localhost:5432/zajavka_store";
        String username = "postgres";
        String password = "meow";

        // SQL INJECTION
//        String name = "fdrewe2t' OR 1=1 OR USER_NAME = 'MEOW'";
        String name = "fdrewe2t";

        String deletePurchases = "DELETE FROM PURCHASES WHERE CUSTOMER_ID IN (SELECT ID " +
                "FROM CUSTOMERS WHERE USER_NAME = ?);";
        String deleteOpinions = "DELETE FROM OPINIONS WHERE CUSTOMER_ID IN (SELECT ID " +
                "FROM CUSTOMERS WHERE USER_NAME = ?);";
        String customerDeleteQuery = "DELETE FROM CUSTOMERS WHERE USER_NAME  = ?;";

        try (
                Connection connection = DriverManager.getConnection(address, username, password);
                PreparedStatement statement1 = connection.prepareStatement(deletePurchases); // better!
                PreparedStatement statement2 = connection.prepareStatement(deleteOpinions); // better!
                PreparedStatement statement3 = connection.prepareStatement(customerDeleteQuery) // better!
        ) {
            statement1.setString(1, name);
            statement2.setString(1, name);
            statement3.setString(1, name);
            System.out.println(statement1.executeUpdate());
            System.out.println(statement2.executeUpdate());
            System.out.println(statement3.executeUpdate());


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
