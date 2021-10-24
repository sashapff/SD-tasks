package sashapff.refactoring.html;

import sashapff.refactoring.entity.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

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

    private StringBuilder buildProductsResult(List<Product> products) {
        StringBuilder content = new StringBuilder();
        for (Product product : products) {
            content.append(product.getName()).append("\t").append(product.getPrice()).append("</br>\n");
        }
        return content;
    }

    public void buildOkResponse(HttpServletResponse response) {
        buildResponse(response, new StringBuilder("OK\n"));
    }

    public void buildGetResponse(HttpServletResponse response, List<Product> products) {
        buildHeadersResponse(response, buildProductsResult(products));
    }

    public void buildMaxResponse(HttpServletResponse response, List<Product> products) {
        buildHeadersResponse(response,
                new StringBuilder("<h1>Product with max price: </h1>\n").append(buildProductsResult(products)));
    }

    public void buildMinResponse(HttpServletResponse response, List<Product> products) {
        buildHeadersResponse(response,
                new StringBuilder("<h1>Product with min price: </h1>\n").append(buildProductsResult(products)));
    }


    public void buildSumResponse(HttpServletResponse response, long answer) {
        buildHeadersResponse(response,
                new StringBuilder("Summary price: \n").append(answer).append('\n'));
    }

    public void buildCountResponse(HttpServletResponse response, long answer) {
        buildHeadersResponse(response,
                new StringBuilder("Number of products: \n").append(answer).append('\n'));
    }

    public void buildErrorResponse(HttpServletResponse response, String message) {
        buildResponse(response, new StringBuilder(message).append('\n'));
    }
}
