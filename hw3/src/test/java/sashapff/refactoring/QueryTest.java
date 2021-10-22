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

public class QueryTest extends ServletTest {
    private void testTemplate(String command) throws IOException {
        QueryServlet servlet = new QueryServlet();
        Mockito.when(request.getParameter("command")).thenReturn(command);
        servlet.doGet(request, response);
    }

    @Test
    public void testDoGetMax() throws IOException {
        testTemplate("max");
    }

    @Test
    public void testDoGetMin() throws IOException {
        testTemplate("min");
    }

    @Test
    public void testDoGetSum() throws IOException {
        testTemplate("sum");
    }

    @Test
    public void testDoGetCount() throws IOException {
        testTemplate("count");
    }
}
