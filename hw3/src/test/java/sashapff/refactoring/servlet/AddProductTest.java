package sashapff.refactoring.servlet;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AddProductTest extends ServletTest {
    private final AddProductServlet servlet = new AddProductServlet(database);

    @Test
    public void testAddProduct() throws IOException {
        Mockito.when(request.getParameter("name")).thenReturn("product");
        Mockito.when(request.getParameter("price")).thenReturn("10");
        servlet.doGet(request, response);
        verifyResponse();
        assertEquals("OK\n", stringWriter.toString());
    }

    @Test
    public void testAddManyProducts() throws IOException {
        Mockito.when(request.getParameter("name"))
                .thenReturn("productOne")
                .thenReturn("productTwo")
                .thenReturn("ProductThree");
        Mockito.when(request.getParameter("price"))
                .thenReturn("10")
                .thenReturn("11")
                .thenReturn("28");
        servlet.doGet(request, response);
        verifyResponse();
        assertEquals("OK\n", stringWriter.toString());
    }

    @Test(expected = NumberFormatException.class)
    public void testAddInvalidProduct() throws IOException {
        Mockito.when(request.getParameter("name")).thenReturn("product");
        Mockito.when(request.getParameter("price")).thenReturn("one dollar");
        servlet.doGet(request, response);
    }

}
