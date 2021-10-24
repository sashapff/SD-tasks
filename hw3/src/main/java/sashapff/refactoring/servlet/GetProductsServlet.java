package sashapff.refactoring.servlet;

import sashapff.refactoring.database.Database;
import sashapff.refactoring.html.HtmlBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetProductsServlet extends HttpServlet {
    private final Database database;
    private final HtmlBuilder htmlBuilder = new HtmlBuilder();

    public GetProductsServlet(Database database) {
        this.database = database;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
//        htmlBuilder.buildGetResponse(response, database.getProducts());
        database.executeQuery("SELECT * FROM PRODUCT",
                resultSet -> {
                    htmlBuilder.buildGetResponse(response, resultSet);
                });

    }
}
