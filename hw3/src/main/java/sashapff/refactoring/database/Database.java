package sashapff.refactoring.database;

import java.sql.*;
import java.util.function.Consumer;

public class Database {
    private final String databaseUrl;

    public Database(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void executeUpdate(String sqlCommand) {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeQuery(String sqlCommand, Consumer<ResultSet> consumer) {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);

            consumer.accept(resultSet);

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createProductTable() {
        executeUpdate("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          LONG     NOT NULL)");
    }

    public void addProduct(String name, long price) {
        executeUpdate("INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")");
    }
}
