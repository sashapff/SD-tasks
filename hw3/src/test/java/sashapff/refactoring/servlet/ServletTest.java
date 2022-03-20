package sashapff.refactoring.servlet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.Mockito;
import sashapff.refactoring.database.Database;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ServletTest {
    public static HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    public static HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    protected StringWriter stringWriter = new StringWriter();
    private final PrintWriter printWriter = new PrintWriter(stringWriter);

    protected static final Database database = new Database("jdbc:sqlite:test.db");

    protected void verifyResponse() {
        Mockito.verify(response).setContentType("text/html");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @BeforeClass
    public static void beforeClass() {
        database.createProductTable();
    }

    @Before
    public void before() throws IOException {
        Mockito.when(response.getWriter()).thenReturn(printWriter).thenReturn(printWriter).thenReturn(printWriter);
        Mockito.clearInvocations(response);

        database.clearProductTable();
    }

    @After
    public void after() {
        database.clearProductTable();
    }

    @AfterClass
    public static void afterClass() {
        database.dropProductTable();
    }
}
