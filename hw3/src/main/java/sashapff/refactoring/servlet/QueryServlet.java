package sashapff.refactoring.servlet;

import sashapff.refactoring.database.Database;
import sashapff.refactoring.entity.Product;
import sashapff.refactoring.html.HtmlBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.function.Consumer;

public class QueryServlet extends HttpServlet {
    private final Database database;
    private final HtmlBuilder htmlBuilder = new HtmlBuilder();

    public QueryServlet(Database database) {
        this.database = database;
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String command = request.getParameter("command");
        if ("max".equals(command)) {
            htmlBuilder.buildMaxResponse(response, database.findMaxPriceProduct());
        } else if ("min".equals(command)) {
            htmlBuilder.buildMinResponse(response, database.findMinPriceProduct());
        } else if ("sum".equals(command)) {
            htmlBuilder.buildSumResponse(response, database.getSum());
        } else if ("count".equals(command)) {
            htmlBuilder.buildCountResponse(response, database.getCount());
        } else {
            htmlBuilder.buildErrorResponse(response, "Unknown command: " + command);
        }
    }

}
