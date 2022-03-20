package driver;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoDatabase;
import org.bson.Document;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;
import utils.Product;
import utils.User;

import javax.money.Monetary;
import javax.money.convert.MonetaryConversions;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Executors;

public class MongoDriver {
    private final MongoDatabase mongodb;
    private final Scheduler scheduler;

    public MongoDriver(MongoDatabase database) {
        mongodb = database;
        scheduler = Schedulers.from(Executors.newFixedThreadPool(4));
    }

    public Observable<Boolean> addUser(User user) {
        String id = user.getId();
        String currency = user.getCurrency();
        return getUser(id).flatMap(x -> {
                    if (x != null) {
                        return mongodb.getCollection("user")
                                .insertOne(new Document(Map.of(
                                        "id", id,
                                        "currency", currency)))
                                .asObservable()
                                .isEmpty()
                                .map(y -> !y);
                    } else {
                        return Observable.just(false);
                    }
                })
                .subscribeOn(scheduler);
    }

    public Observable<User> getUser(String id) {
        return mongodb.getCollection("user")
                .find(Filters.eq("id", id))
                .toObservable()
                .map(user -> new User(
                        user.getString("id"),
                        user.getString("currency")))
                .subscribeOn(scheduler);
    }

    public Observable<Boolean> addProduct(Product product) {
        String id = product.getId();
        return getProduct(id).flatMap(user -> {
                    if (user == null) {
                        return mongodb.getCollection("product")
                                .insertOne(new Document(Map.of(
                                        "id", id,
                                        "price", product.getPrice(),
                                        "currency", product.getCurrency())))
                                .asObservable()
                                .isEmpty()
                                .map(y -> !y);
                    } else {
                        return Observable.just(false);
                    }
                })
                .subscribeOn(scheduler);
    }

    public Observable<Product> getProduct(String id) {
        return mongodb.getCollection("product")
                .find(Filters.eq("id", id))
                .toObservable()
                .map(product -> new Product(
                        product.getString("id"),
                        Integer.parseInt(product.getString("price")),
                        product.getString("currency")))
                .subscribeOn(scheduler);
    }

    public Observable<Product> getProducts(String id, int limit, int offset) {
        return getUser(id).flatMap(user ->
                mongodb.getCollection("product")
                        .find()
                        .toObservable()
                        .map(product -> new Product(
                                product.getString("id"),
                                Integer.parseInt(product.getString("price")),
                                product.getString("currency"))
                        ).skip(offset).take(limit)
                        .map(product -> new Product(
                                product.getId(),
                                convertCurrency(product.getCurrency(), user.getCurrency(), product.getPrice()),
                                user.getCurrency()))
                        .subscribeOn(scheduler));
    }

    private int convertCurrency(String currencyFrom, String currencyTo, int price) {
        return Monetary.getDefaultAmountFactory()
                .setCurrency(currencyFrom)
                .setNumber(price)
                .create()
                .with(MonetaryConversions.getConversion(currencyTo))
                .getNumber()
                .intValueExact();
    }
}
