package sashapff.refactoring.servlet;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class QueryTest extends ServletTest {
    private void testTemplate(String command, String body) throws IOException {
        QueryServlet servlet = new QueryServlet();
        Mockito.when(request.getParameter("command")).thenReturn(command);
        servlet.doGet(request, response);

        assertTrue(stringWriter.toString().contains("<html><body>"));
        assertTrue(stringWriter.toString().contains("</body></html>"));
        assertTrue(stringWriter.toString().contains(body));
    }

    @Test
    public void testDoGetMax() throws IOException {
        testTemplate("max", "<h1>Product with max price: </h1>");
    }

    @Test
    public void testDoGetMin() throws IOException {
        testTemplate("min", "<h1>Product with min price: </h1>");
    }

    @Test
    public void testDoGetSum() throws IOException {
        testTemplate("sum", "Summary price: ");
    }

    @Test
    public void testDoGetCount() throws IOException {
        testTemplate("count", "Number of products: ");
    }
}
