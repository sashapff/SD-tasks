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

public class GetProductTest {

    @Test
    public void testDoGet() throws IOException {
        GetProductsServlet servlet = new GetProductsServlet();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        Mockito.when(response.getWriter()).thenReturn(printWriter);
        Mockito.clearInvocations(response);

        servlet.doGet(request, response);

        Mockito.verify(response).setContentType("text/html");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}
