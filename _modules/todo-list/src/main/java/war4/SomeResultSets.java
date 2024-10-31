package war4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SomeResultSets {
    public static void main(String[] args) {
        String address = "jdbc:postgresql://localhost:5432/zajavka_store";
        String username = "postgres";
        String password = "meow";

        String query = "SELECT * FROM CUSTOMERS WHERE NAME LIKE ?;";
        String parameter = "%me%";

        try (
                Connection connection = DriverManager.getConnection(address, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, parameter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Customer> customers = mapToCustomer(resultSet);
                customers.forEach(c -> System.out.println("Customer: " + c));
            }

        } catch (SQLException e) { // can be exception - data is lost
            e.printStackTrace();
            System.out.println(e.getSQLState());
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
        }
    }

    private static List<Customer> mapToCustomer(ResultSet resultSet) {
        List<Customer> result = new ArrayList<>();

        try { // direction of read important!
            while (resultSet.next()) { // Group and Limit in Database Query
                result.add(new Customer(
                        resultSet.getString(1), // index
                        resultSet.getString("USER_NAME"), // column name
                        resultSet.getString(3),
                        resultSet.getString("NAME"),
                        resultSet.getObject("SURNAME").toString(),
//                        LocalDate.parse(Optional.ofNullable(resultSet.getString("DATE_OF_BIRTH")).orElse("0001-01-01"))
                        resultSet.getDate(6).toLocalDate()
                ));
            } // JPA - higher abstraction level - #SOON

        } catch (Exception e) {
            System.out.println("Error while mapping resultSet to List<Customer>: " + e.getMessage());
        }
        return result;
    }
}
