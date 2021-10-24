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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ServletTest {
    public static HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    public static HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    protected StringWriter stringWriter = new StringWriter();
    private final PrintWriter printWriter = new PrintWriter(stringWriter);

    protected static final Database database = new Database();

    private static void clearProductTable() throws SQLException {
        database.executeUpdate("DELETE FROM PRODUCT");
    }

    private static void dropProductTable() throws SQLException {
        database.executeUpdate("DROP TABLE IF EXISTS PRODUCT");
    }

    protected void verifyResponse() {
        Mockito.verify(response).setContentType("text/html");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    protected void addProductToTable(String name, String price) throws SQLException {
        database.addProduct(name, price);
    }

    @BeforeClass
    public static void beforeClass() throws SQLException {
        database.createProductTable();
    }

    @Before
    public void before() throws IOException, SQLException {
        Mockito.when(response.getWriter()).thenReturn(printWriter).thenReturn(printWriter).thenReturn(printWriter);
        Mockito.clearInvocations(response);

        clearProductTable();
    }

    @After
    public void after() throws SQLException, IOException {
        clearProductTable();
    }

    @AfterClass
    public static void afterClass() throws SQLException {
        dropProductTable();
    }
}
