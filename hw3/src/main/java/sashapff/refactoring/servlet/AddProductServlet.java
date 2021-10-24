package sashapff.refactoring.servlet;

import sashapff.refactoring.database.Database;
import sashapff.refactoring.html.HtmlBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProductServlet extends HttpServlet {
    private final Database database;
    private final HtmlBuilder htmlBuilder = new HtmlBuilder();

    public AddProductServlet(Database database) {
        this.database = database;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        database.addProduct(name, price);
        htmlBuilder.buildOkResponse(response);
    }
}
