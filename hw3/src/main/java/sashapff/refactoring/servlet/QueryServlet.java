package sashapff.refactoring.servlet;

import sashapff.refactoring.database.Database;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.function.Consumer;

public class QueryServlet extends HttpServlet {
    private final Database database;

    public QueryServlet(Database database) {
        this.database = database;
    }

    private Consumer<ResultSet> buildMaxConsumer(HttpServletResponse response) {
        return resultSet -> {
            try {
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with max price: </h1>");

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    long price = resultSet.getInt("price");
                    response.getWriter().println(name + "\t" + price + "</br>");
                }
                response.getWriter().println("</body></html>");
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        };
    }

    private Consumer<ResultSet> buildMinConsumer(HttpServletResponse response) {
        return resultSet -> {
            try {
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with min price: </h1>");

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    long price = resultSet.getInt("price");
                    response.getWriter().println(name + "\t" + price + "</br>");
                }
                response.getWriter().println("</body></html>");
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        };
    }

    private Consumer<ResultSet> buildSumConsumer(HttpServletResponse response) {
        return resultSet -> {
            try {
                response.getWriter().println("<html><body>");
                response.getWriter().println("Summary price: ");

                if (resultSet.next()) {
                    response.getWriter().println(resultSet.getInt(1));
                }
                response.getWriter().println("</body></html>");
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        };
    }

    private Consumer<ResultSet> buildCountConsumer(HttpServletResponse response) {
        return resultSet -> {
            try {
                response.getWriter().println("<html><body>");
                response.getWriter().println("Number of products: ");

                if (resultSet.next()) {
                    response.getWriter().println(resultSet.getInt(1));
                }
                response.getWriter().println("</body></html>");
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        };
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            database.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1",
                    buildMaxConsumer(response));

        } else if ("min".equals(command)) {
            database.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1",
                    buildMinConsumer(response));
        } else if ("sum".equals(command)) {
            database.executeQuery("SELECT SUM(price) FROM PRODUCT",
                    buildSumConsumer(response));
        } else if ("count".equals(command)) {
            database.executeQuery("SELECT COUNT(*) FROM PRODUCT",
                    buildCountConsumer(response));
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
