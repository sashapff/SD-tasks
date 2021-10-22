package sashapff.refactoring.servlet;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class AddProductTest extends ServletTest {

    @Test
    public void testDoGet() throws IOException {
        AddProductServlet servlet = new AddProductServlet();
        Mockito.when(request.getParameter("name")).thenReturn("product");
        Mockito.when(request.getParameter("price")).thenReturn("10");
        servlet.doGet(request, response);
        assertTrue(stringWriter.toString().contains("OK"));
    }
}
