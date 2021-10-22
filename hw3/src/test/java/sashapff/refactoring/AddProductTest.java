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

public class AddProductTest {

    @Test
    public void testDoGet() throws IOException {
        AddProductServlet servlet = new AddProductServlet();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        Mockito.when(request.getParameter("name")).thenReturn("product");
        Mockito.when(request.getParameter("price")).thenReturn("10");
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        Mockito.clearInvocations(response);

        servlet.doGet(request, response);

        Mockito.verify(response).setContentType("text/html");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals("OK\n", stringWriter.toString());
    }
}
