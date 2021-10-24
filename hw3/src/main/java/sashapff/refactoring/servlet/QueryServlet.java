package sashapff.refactoring.servlet;

import sashapff.refactoring.database.Database;
import sashapff.refactoring.html.HtmlBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.function.Consumer;

public class QueryServlet extends HttpServlet {
    private final Database database;
    private final HtmlBuilder htmlBuilder = new HtmlBuilder();

    public QueryServlet(Database database) {
        this.database = database;
    }

    private Consumer<ResultSet> buildMaxConsumer(HttpServletResponse response) {
        return resultSet -> htmlBuilder.buildMaxResponse(response, resultSet);
    }

    private Consumer<ResultSet> buildMinConsumer(HttpServletResponse response) {
        return resultSet -> htmlBuilder.buildMinResponse(response, resultSet);
    }

    private Consumer<ResultSet> buildSumConsumer(HttpServletResponse response) {
        return resultSet -> htmlBuilder.buildSumResponse(response, resultSet);
    }

    private Consumer<ResultSet> buildCountConsumer(HttpServletResponse response) {
        return resultSet -> htmlBuilder.buildCountResponse(response, resultSet);
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
            htmlBuilder.buildErrorResponse(response, "Unknown command: " + command);
        }
    }

}
