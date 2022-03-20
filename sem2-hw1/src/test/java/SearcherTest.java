import actor.MasterActor;
import utils.SearcherResult;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import akka.testkit.javadsl.TestKit;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static akka.pattern.Patterns.ask;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearcherTest {
    private static ActorSystem system;
    private static Gson gson;

    private static final Duration TIMEOUT_1S = Duration.ofSeconds(1);
    private static final Duration TIMEOUT_250MS = Duration.ofMillis(250);

    @BeforeAll
    public static void setup() {
        system = ActorSystem.create();
        gson = new Gson();
    }

    @AfterAll
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testFinished() throws IOException {
        new TestKit(system) {
            {
                final ActorRef master = system.actorOf(Props.create(MasterActor.class, TIMEOUT_1S));

                List<SearcherResult> actualResults = (ArrayList<SearcherResult>) ask(
                        master,
                        "lol kek",
                        Duration.ofMinutes(1))
                        .toCompletableFuture()
                        .join();

                List<SearcherResult> expectedResults = List.of(
                        getResult("google"),
                        getResult("yandex"),
                        getResult("bing"));

                assertEquals(actualResults.size(), 3);
                assertTrue(actualResults.size() == expectedResults.size() &&
                        actualResults.containsAll(expectedResults) && expectedResults.containsAll(actualResults));
            }
        };
    }

    @Test
    public void testTimeout() throws IOException {
        new TestKit(system) {
            {
                final ActorRef master = system.actorOf(Props.create(MasterActor.class, TIMEOUT_250MS));

                List<SearcherResult> actualResults = (ArrayList<SearcherResult>) ask(
                        master,
                        "pff mem",
                        Duration.ofMinutes(1))
                        .toCompletableFuture()
                        .join();

                List<SearcherResult> expectedResults = List.of(
                        getResult("google"),
                        getResult("yandex"));

                assertEquals(actualResults.size(), 2);
                assertTrue(actualResults.size() == expectedResults.size() &&
                        actualResults.containsAll(expectedResults) && expectedResults.containsAll(actualResults));
            }
        };
    }

    SearcherResult getResult(String searcher) throws IOException {
        String filename = String.format("src/main/resources/result/%s.json", searcher);

        String jsonString = new String(Files.readAllBytes(Paths.get(filename)));
        return gson.fromJson(jsonString, SearcherResult.class);

    }
}
