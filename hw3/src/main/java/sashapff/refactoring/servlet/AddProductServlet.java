package sashapff.refactoring.servlet;

import sashapff.refactoring.database.Database;
import sashapff.refactoring.entity.Product;
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

    private Product getProduct(HttpServletRequest request) {
        return new Product(request.getParameter("name"), Long.parseLong(request.getParameter("price")));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        database.addProduct(getProduct(request));
        htmlBuilder.buildOkResponse(response);
    }
}
