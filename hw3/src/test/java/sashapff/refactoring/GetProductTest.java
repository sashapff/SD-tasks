package sashapff.refactoring;

import org.junit.Test;
import sashapff.refactoring.servlet.GetProductsServlet;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class GetProductTest extends ServletTest {

    @Test
    public void testDoGet() throws IOException {
        GetProductsServlet servlet = new GetProductsServlet();
        servlet.doGet(request, response);
        assertTrue(stringWriter.toString().contains("<html><body>"));
        assertTrue(stringWriter.toString().contains("</body></html>"));
    }
}
