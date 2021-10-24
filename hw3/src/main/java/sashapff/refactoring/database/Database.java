package sashapff.refactoring.database;

import java.sql.*;
import java.util.function.Consumer;

public class Database {
    private final String databaseUrl;

    public Database() {
        this.databaseUrl = "jdbc:sqlite:test.db";
    }

    public Database(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void executeUpdate(String sqlCommand) throws SQLException {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
            statement.close();
        }
    }

    public void executeQuery(String sqlCommand, Consumer<ResultSet> consumer) throws SQLException {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);

            consumer.accept(resultSet);

            resultSet.close();
            statement.close();
        }
    }

    public void createProductTable() throws SQLException {
        executeUpdate("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    public void addProduct(String name, String price) throws SQLException {
        executeUpdate("INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")");
    }
}
