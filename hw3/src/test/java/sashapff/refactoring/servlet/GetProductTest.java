package sashapff.refactoring.servlet;

import org.junit.Test;

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
