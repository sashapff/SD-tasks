package client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Client {
    private final String searcher;

    public Client(String searcher) {
        this.searcher = searcher;
    }

    public String sendRequest(String request) {
        String response = "";
        try {
            response = new String(Files.readAllBytes(
                    Paths.get(String.format("src/main/resources/result/%s.json", searcher))));
        } catch (IOException e) {
            System.err.println("Unable to fetch search result from server. Searcher: " + searcher);
        }

        return response;
    }
}
