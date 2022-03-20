import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import driver.MongoDriver;
import handler.HttpHandler;
import io.reactivex.netty.protocol.http.server.HttpServer;

public class Main {
    private static final MongoClient client = MongoClients.create("mongodb://localhost:27017");
    private static final HttpHandler handler = new HttpHandler(new MongoDriver(client.getDatabase("catalog")));

    public static void main(String[] args) {
        HttpServer.newServer(8080)
                .start((req, resp) -> {
                    System.out.println("Path: " + req.getDecodedPath());
                    return resp.writeString(handler.makeRequest(req));
                })
                .awaitShutdown();
    }
}
