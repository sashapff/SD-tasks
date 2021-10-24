package sashapff.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import sashapff.refactoring.database.Database;
import sashapff.refactoring.servlet.AddProductServlet;
import sashapff.refactoring.servlet.GetProductsServlet;
import sashapff.refactoring.servlet.QueryServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        Database database = new Database();
        database.createProductTable();

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(database)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(database)), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(database)), "/query");

        server.start();
        server.join();
    }
}
