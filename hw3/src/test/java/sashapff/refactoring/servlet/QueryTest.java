package sashapff.refactoring.servlet;

import org.junit.Test;
import org.mockito.Mockito;
import sashapff.refactoring.entity.Product;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class QueryTest extends ServletTest {
    private final QueryServlet servlet = new QueryServlet(database);

    private void testTemplate(String command, String body) throws IOException {
        Mockito.when(request.getParameter("command")).thenReturn(command);
        servlet.doGet(request, response);

        assertEquals(
                "<html><body>\n" +
                        body +
                        "</body></html>\n",
                stringWriter.toString()
        );
        stringWriter.flush();
    }

    @Test
    public void testGetMaxEmptyTable() throws IOException {
        testTemplate("max", "<h1>Product with max price: </h1>\n");
    }

    @Test
    public void testGetMinEmptyTable() throws IOException {
        testTemplate("min", "<h1>Product with min price: </h1>\n");
    }

    @Test
    public void testGetSumEmptyTable() throws IOException {
        testTemplate("sum", "Summary price: \n0\n");
    }

    @Test
    public void testGetCountEmptyTable() throws IOException {
        testTemplate("count", "Number of products: \n0\n");
    }

    private void addProduct() throws SQLException {
        database.addProduct(new Product("product", 10L));
    }

    @Test
    public void testGetMaxOneProduct() throws IOException, SQLException {
        addProduct();
        testTemplate("max", "<h1>Product with max price: </h1>\nproduct\t10</br>\n");
    }

    @Test
    public void testGetMinOneProduct() throws IOException, SQLException {
        addProduct();
        testTemplate("min", "<h1>Product with min price: </h1>\nproduct\t10</br>\n");
    }

    @Test
    public void testGetSumOneProduct() throws IOException, SQLException {
        addProduct();
        testTemplate("sum", "Summary price: \n10\n");
    }

    @Test
    public void testGetCountOneProduct() throws IOException, SQLException {
        addProduct();
        testTemplate("count", "Number of products: \n1\n");
    }

    private void addProducts() throws SQLException {
        database.addProduct(new Product("productOne", 10L));
        database.addProduct(new Product("productTwo", 11L));
        database.addProduct(new Product("productThree", 28L));
    }

    @Test
    public void testGetMaxManyProducts() throws IOException, SQLException {
        addProducts();
        testTemplate("max", "<h1>Product with max price: </h1>\n" + "productThree\t28</br>\n");
    }

    @Test
    public void testGetMinManyProducts() throws IOException, SQLException {
        addProducts();
        testTemplate("min", "<h1>Product with min price: </h1>\n" + "productOne\t10</br>\n");
    }

    @Test
    public void testGetSumManyProducts() throws IOException, SQLException {
        addProducts();
        testTemplate("sum", "Summary price: \n49\n");
    }

    @Test
    public void testGetCountManyProducts() throws IOException, SQLException {
        addProducts();
        testTemplate("count", "Number of products: \n3\n");
    }

    @Test
    public void testUnknownCommand() throws IOException {
        Mockito.when(request.getParameter("command")).thenReturn("unknown");
        servlet.doGet(request, response);
        assertEquals("Unknown command: unknown\n", stringWriter.toString());
    }
}
