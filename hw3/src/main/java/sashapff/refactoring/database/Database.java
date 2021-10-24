package sashapff.refactoring.database;

import sashapff.refactoring.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Product> executeQuery(String sqlCommand) {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                long price = resultSet.getLong("price");
                products.add(new Product(name, price));
            }

            resultSet.close();
            statement.close();

            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long executeQueryResult(String sqlCommand) {
        long answer;

        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);

            if (resultSet.next()) {
                answer = resultSet.getInt(1);
            } else {
                throw new RuntimeException();
            }

            resultSet.close();
            statement.close();

            return answer;
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

    public void addProduct(Product product) {
        executeUpdate("INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")");
    }

    public List<Product> getProducts() {
        return executeQuery("SELECT * FROM PRODUCT");
    }

    public List<Product> findMaxPriceProduct() {
        return executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
    }

    public List<Product> findMinPriceProduct() {
        return executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
    }

    public long getSum() {
        return executeQueryResult("SELECT SUM(price) FROM PRODUCT");
    }

    public long getCount() {
        return executeQueryResult("SELECT COUNT(*) FROM PRODUCT");
    }
}
