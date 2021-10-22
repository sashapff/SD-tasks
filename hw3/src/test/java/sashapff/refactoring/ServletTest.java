package sashapff.refactoring;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ServletTest {
    public HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    public static HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    @Before
    public void before() throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        Mockito.when(response.getWriter()).thenReturn(printWriter);
        Mockito.clearInvocations(response);
    }

    @After
    public void after() {
        Mockito.verify(response).setContentType("text/html");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}
