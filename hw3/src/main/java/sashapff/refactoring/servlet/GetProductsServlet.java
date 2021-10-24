package sashapff.refactoring.servlet;

import sashapff.refactoring.database.Database;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class GetProductsServlet extends HttpServlet {
    private final Database database;

    public GetProductsServlet(Database database) {
        this.database = database;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        database.executeQuery("SELECT * FROM PRODUCT",
                (resultSet) -> {
                    try {
                        response.getWriter().println("<html><body>");

                        while (resultSet.next()) {
                            String name = resultSet.getString("name");
                            long price = resultSet.getInt("price");
                            response.getWriter().println(name + "\t" + price + "</br>");
                        }
                        response.getWriter().println("</body></html>");
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                });

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
