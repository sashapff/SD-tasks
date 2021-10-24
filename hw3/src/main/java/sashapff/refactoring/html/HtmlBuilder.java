package sashapff.refactoring.html;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HtmlBuilder {
    private void buildResponse(HttpServletResponse response, StringBuilder content) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().print(content);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void buildHeadersResponse(HttpServletResponse response, StringBuilder content) {
        buildResponse(response, new StringBuilder("<html><body>\n").append(content).append("</body></html>\n"));
    }

    private StringBuilder buildManyResultSet(ResultSet resultSet) {
        StringBuilder content = new StringBuilder();

        try {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                long price = resultSet.getInt("price");
                content.append(name).append("\t").append(price).append("</br>\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return content;
    }

    private StringBuilder buildOneResultSet(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return new StringBuilder(resultSet.getInt(1) + "\n");
            } else {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void buildOkResponse(HttpServletResponse response) {
        buildResponse(response, new StringBuilder("OK\n"));
    }

    public void buildGetResponse(HttpServletResponse response, ResultSet resultSet) {
        buildHeadersResponse(response, buildManyResultSet(resultSet));
    }

    public void buildMaxResponse(HttpServletResponse response, ResultSet resultSet) {
        buildHeadersResponse(response,
                new StringBuilder("<h1>Product with max price: </h1>\n").append(buildManyResultSet(resultSet)));
    }

    public void buildMinResponse(HttpServletResponse response, ResultSet resultSet) {
        buildHeadersResponse(response,
                new StringBuilder("<h1>Product with min price: </h1>\n").append(buildManyResultSet(resultSet)));
    }


    public void buildSumResponse(HttpServletResponse response, ResultSet resultSet) {
        buildHeadersResponse(response,
                new StringBuilder("Summary price: \n").append(buildOneResultSet(resultSet)));
    }

    public void buildCountResponse(HttpServletResponse response, ResultSet resultSet) {
        buildHeadersResponse(response,
                new StringBuilder("Number of products: \n").append(buildOneResultSet(resultSet)));
    }

    public void buildErrorResponse(HttpServletResponse response, String message) {
        buildResponse(response, new StringBuilder(message).append("\n"));
    }
}
