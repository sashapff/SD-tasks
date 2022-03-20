package actor;

import akka.actor.AbstractActor;
import client.Client;
import com.google.gson.Gson;
import utils.SearcherResult;

import java.util.Map;

public class ChildActor extends AbstractActor {
    private final String searcher;
    private final Client client;

    public ChildActor(String searcher) {
        this.searcher = searcher;
        client = new Client(searcher);
    }

    private final Map<String, Integer> responseDuration = Map.of(
            "google", 100,
            "yandex", 200,
            "bing", 300);

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(String.class, request -> {
            String jsonString = client.sendRequest(request);
            Gson g = new Gson();
            SearcherResult result = g.fromJson(jsonString, SearcherResult.class);

            try {
                Thread.sleep(responseDuration.get(searcher));
                getSender().tell(result, getSender());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).build();
    }
}
