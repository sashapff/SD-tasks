package sashapff.refactoring.servlet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.Mock;
import org.mockito.Mockito;

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

    private static void executeCommand(String command) throws SQLException {
        String databaseUrl = "jdbc:sqlite:test.db";
        try (Connection c = DriverManager.getConnection(databaseUrl)) {
            Statement stmt = c.createStatement();

            stmt.executeUpdate(command);
            stmt.close();
        }
    }

    private static void createProductTable() throws SQLException {
        executeCommand("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    private static void clearProductTable() throws SQLException {
        executeCommand("DELETE FROM PRODUCT");
    }

    private static void dropProductTable() throws SQLException {
        executeCommand("DROP TABLE IF EXISTS PRODUCT");
    }

    protected void verifyResponse() {
        Mockito.verify(response).setContentType("text/html");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    protected void addProductToTable(String name, String price) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        }
    }

    @BeforeClass
    public static void beforeClass() throws SQLException {
        createProductTable();
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
