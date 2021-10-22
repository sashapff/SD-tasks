package sashapff.refactoring;

import org.junit.Test;
import org.mockito.Mockito;
import sashapff.refactoring.servlet.AddProductServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class AddProductTest extends ServletTest {

    @Test
    public void testDoGet() throws IOException {
        AddProductServlet servlet = new AddProductServlet();
        Mockito.when(request.getParameter("name")).thenReturn("product");
        Mockito.when(request.getParameter("price")).thenReturn("10");
        servlet.doGet(request, response);
    }
}
