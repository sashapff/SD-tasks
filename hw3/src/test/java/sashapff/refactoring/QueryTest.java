package sashapff.refactoring;

import org.junit.Test;
import org.mockito.Mockito;
import sashapff.refactoring.servlet.GetProductsServlet;
import sashapff.refactoring.servlet.QueryServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

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
