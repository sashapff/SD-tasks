import java.util.List;
import java.util.Map;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.FindObservable;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import com.mongodb.rx.client.Success;
import driver.MongoDriver;
import handler.HttpHandler;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import rx.Observable;

import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.powermock.api.mockito.PowerMockito.when;

public class CatalogTest {
    private HttpHandler handler;
    private MongoDatabase db;
    private MongoCollection userCollection = PowerMockito.mock(MongoCollection.class);
    private HttpServerRequest request = PowerMockito.mock(HttpServerRequest.class);
    private MongoCollection productCollection = PowerMockito.mock(MongoCollection.class);
    private FindObservable findObservableProduct = PowerMockito.mock(FindObservable.class);
    private FindObservable findObservableUser = PowerMockito.mock(FindObservable.class);

    @Before
    public void setUp() {
        db = PowerMockito.mock(MongoDatabase.class);
        handler = new HttpHandler(new MongoDriver(db));

        when(db.getCollection("user")).thenReturn(userCollection);
        when(db.getCollection("product")).thenReturn(productCollection);
        when(productCollection.find(Filters.eq("id", Mockito.anyString()))).thenReturn(findObservableProduct);
        when(userCollection.find(Filters.eq("id", Mockito.anyString()))).thenReturn(findObservableUser);
        when(userCollection.insertOne(new Document(
                Map.of("id", "3",
                        "currency", "EUR")))).thenReturn(Observable.just(Success.SUCCESS));
        when(productCollection.insertOne(new Document(
                Map.of("id", "3",
                        "price", "100",
                        "currency", "EUR")))).thenReturn(Observable.just(Success.SUCCESS));
    }

    @Test
    public void testUserGet() {
        when(findObservableUser.toObservable())
                .thenReturn(Observable.just(new Document(
                        Map.of("id", "1",
                                "currency", "EUR"))));
        when(request.getDecodedPath()).thenReturn("/user/get");
        when(request.getQueryParameters()).thenReturn(Map.of("id", List.of("1")));
        handler.makeRequest(request).toBlocking().getIterator().forEachRemaining(x ->
                assertEquals("User {id=1, currency=EUR}", x));
    }

    @Test
    public void testGetProduct() {
        when(findObservableProduct.toObservable())
                .thenReturn(Observable.just(new Document(
                        Map.of("id", "1",
                                "price", "1000",
                                "currency", "EUR"))));
        when(request.getDecodedPath()).thenReturn("/product/get");
        when(request.getQueryParameters()).thenReturn(Map.of("id", List.of("1")));
        handler.makeRequest(request).toBlocking().getIterator().forEachRemaining(x -> {
            assertEquals("Product {id=1, price=1000, currency=EUR}", x);
        });
    }

    @Test
    public void testAddUser() {
        when(findObservableUser.toObservable())
                .thenReturn(Observable.just(new Document(
                        Map.of("id", "1",
                                "currency", "EUR"))));

        when(request.getDecodedPath()).thenReturn("/user/add");
        when(request.getQueryParameters()).thenReturn(Map.of(
                "id", List.of("3"),
                "currency", List.of("EUR")));

        handler.makeRequest(request).toBlocking().getIterator().forEachRemaining(x -> {
            assertEquals("true", x);
        });
    }

    @Test
    public void testGetProducts() {
        when(findObservableUser.toObservable())
                .thenReturn(Observable.just(new Document(
                        Map.of("id", "1",
                                "currency", "EUR"))));
        when(findObservableProduct.toObservable())
                .thenReturn(Observable.just(new Document(
                        Map.of("id", "1",
                                "price", "1000",
                                "currency", "EUR"))));

        when(productCollection.find()).thenReturn(findObservableProduct);
        when(findObservableUser.toObservable())
                .thenReturn(Observable.just(new Document(
                        Map.of("id", "1",
                                "price", 10,
                                "currency", "EUR"))));

        when(request.getDecodedPath()).thenReturn("/products");
        when(request.getQueryParameters()).thenReturn(Map.of("user-id", List.of("1"), "limit", List.of("1"), "offset", List.of("0")));

        handler.makeRequest(request).toBlocking().getIterator().forEachRemaining(x -> {
            assertThat(x, startsWith("Product {id=1, price="));
            assertThat(x, endsWith("currency=EUR}"));
        });
    }
}
