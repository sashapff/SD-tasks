package handler;

import driver.MongoDriver;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;
import utils.Product;
import utils.User;

import java.util.List;
import java.util.Map;

public class HttpHandler {
    private final MongoDriver db;

    public HttpHandler(MongoDriver database) {
        db = database;
    }

    public Observable<String> makeRequest(HttpServerRequest<?> req) {
        Object result;
        Map<String, List<String>> params = req.getQueryParameters();
        String request = req.getDecodedPath();
        if (request.equals("/user/add")) {
            result = db.addUser(new User(getParamValue(params, "id"), getParamValue(params, "currency")));
        } else if (request.equals("/user/get")) {
            result = db.getUser(getParamValue(params, "id"));
        } else if (request.equals("/product/add")) {
            result = db.addProduct(new Product(
                    getParamValue(params, "id"),
                    Integer.parseInt(getParamValue(params, "price")),
                    getParamValue(params, "currency")
            ));
        } else if (request.equals("/product/get")) {
            result = db.getProduct(getParamValue(params, "id"));
        } else if (request.equals("/products")) {
            result = db.getProducts(getParamValue(params, "user-id"),
                    Integer.parseInt(getParamValue(params, "limit")),
                    Integer.parseInt(getParamValue(params, "offset")));
        } else {
            result = Observable.just("Error in query: " + req.getDecodedPath());
        }
        return ((Observable<?>) result).map(Object::toString);
    }

    String getParamValue(Map<String, List<String>> params, String param) {
        return params.get(param).get(0);
    }

}
