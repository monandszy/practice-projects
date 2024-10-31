package code.databaseRunner;

import code.model.Command;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Value
public class DatabaseRunnerImpl implements DatabaseRunner {
    String address;
    String username;
    String password;

    @Override
    public void run(Command command) {
        log.info("Running Command: %s".formatted(command));
        try {
            switch (command.getType()) {
                case CREATE, UPDATE, DELETE, DELETE_ALL, COMPLETED -> {
                    runUpdate(command);
                }
                case READ, READ_ALL, READ_GROUPED -> {
                    List<String> result = runQuery(command);
                    saveMessage("------------------------------------------------------------QUERY RESULT------------------------------------------------------------");
                    result.forEach(DatabaseRunnerImpl::saveMessage);
                    saveMessage("------------------------------------------------------------QUERY RESULT------------------------------------------------------------");
                }
            }
        } catch (SQLException e) {
            log.error("SQLException thrown: %s".formatted(e.getMessage()));
        }
    }

    public void runUpdate(Command command) throws SQLException {
        try (
                Connection connection = DriverManager.getConnection(address, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(command.getQuery())
        ) {
            prepareQuery(command.getArguments(), preparedStatement);
            log.info("Executing Update: %s".formatted(preparedStatement.toString()));
            preparedStatement.executeUpdate();
        }
    }

    public List<String> runQuery(Command command) throws SQLException {
        List<String> results = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(address, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(command.getQuery())
        ) {
            prepareQuery(command.getArguments(), preparedStatement);
            log.info("Executing Query: %s".formatted(preparedStatement.toString()));

            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                StringBuilder sb = new StringBuilder("| ");
                for (int i = 1; i <= columnCount; i++) {
                    sb.append(metaData.getColumnName(i))
                            .append(": ")
                            .append(resultSet.getString(i))
                            .append(" | ");
                }
                results.add(sb.toString());
            }

        }
        return results;
    }

    private static void prepareQuery(List<String> arguments, PreparedStatement preparedStatement) throws SQLException {
        int counter = 1;
        for (String argument : arguments) {
            preparedStatement.setString(counter, argument);
            counter++;
        }
    }

    public void createTable(String createQuery, String alterQuery) {
        log.info("Creating and Altering Table");
        runPreparedUpdateQuery(createQuery);
        runPreparedUpdateQuery(alterQuery);
    }

    @SneakyThrows
    private void runPreparedUpdateQuery(String query) {
        try (
                Connection connection = DriverManager.getConnection(address, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            log.info("Executing PreparedUpdate: %s".formatted(preparedStatement.toString()));
            preparedStatement.executeUpdate();
        }
    }

    private static void saveMessage(String message) {
//        log.info(message);
        System.out.println(message);
    }
}
