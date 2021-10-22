package sashapff.refactoring;

import org.junit.Test;
import org.mockito.Mockito;
import sashapff.refactoring.servlet.GetProductsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class GetProductTest extends ServletTest {

    @Test
    public void testDoGet() throws IOException {
        GetProductsServlet servlet = new GetProductsServlet();
        servlet.doGet(request, response);
    }
}
