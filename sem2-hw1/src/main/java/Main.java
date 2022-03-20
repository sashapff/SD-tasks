import actor.MasterActor;
import utils.SearcherResult;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static akka.pattern.Patterns.ask;

public class Main {
    private static final Duration TIMEOUT = Duration.ofSeconds(1);

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("MySystem");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));

        while (true) {

            System.out.println("Enter search request:");
            try {
                String request = in.readLine();

                ActorRef master = system.actorOf(
                        Props.create(MasterActor.class, TIMEOUT), "master");

                List<SearcherResult> results = (ArrayList<SearcherResult>) ask(
                        master,
                        request,
                        Duration.ofMinutes(1))
                        .toCompletableFuture()
                        .join();

                for (SearcherResult result : results) {
                    System.out.println("Searcher: " + result.getSearcher());
                    for (String response : result.getResponses()) {
                        System.out.println("\tResponse: " + response);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error: can't read request :(");
            }
        }
    }
}
