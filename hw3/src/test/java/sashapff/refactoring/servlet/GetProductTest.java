package sashapff.refactoring.servlet;

import org.junit.Test;
import sashapff.refactoring.entity.Product;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class GetProductTest extends ServletTest {
    private final GetProductsServlet servlet = new GetProductsServlet(database);

    @Test
    public void testEmptyTable() throws IOException {
        servlet.doGet(request, response);
        verifyResponse();
        assertEquals(
                "<html><body>\n" +
                        "</body></html>\n",
                stringWriter.toString()
        );
    }

    @Test
    public void testGetProduct() throws IOException, SQLException {
        database.addProduct(new Product("product", 10L));
        servlet.doGet(request, response);
        verifyResponse();
        assertEquals(
                "<html><body>\n" +
                        "product\t10</br>\n" +
                        "</body></html>\n",
                stringWriter.toString()
        );
    }

    @Test
    public void testGetManyProducts() throws IOException, SQLException {
        database.addProduct(new Product("productOne", 10L));
        database.addProduct(new Product("productTwo", 11L));
        database.addProduct(new Product("productThree", 28L));
        servlet.doGet(request, response);
        verifyResponse();
        assertEquals(
                "<html><body>\n" +
                        "productOne\t10</br>\n" +
                        "productTwo\t11</br>\n" +
                        "productThree\t28</br>\n" +
                        "</body></html>\n",
                stringWriter.toString()
        );
    }
}
