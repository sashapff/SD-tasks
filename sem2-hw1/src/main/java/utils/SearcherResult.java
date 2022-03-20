package utils;

import java.util.List;

public class SearcherResult {
    private final List<String> responses;
    private final String searcher;

    public SearcherResult(String searcher, List<String> responses) {
        this.responses = responses;
        this.searcher = searcher;
    }

    public List<String> getResponses() {
        return responses;
    }

    public String getSearcher() {
        return searcher;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        SearcherResult result = (SearcherResult) obj;
        if (responses.size() != result.responses.size()) return false;

        for (int i = 0; i < responses.size(); i++) {
            if (!responses.get(i).equals(result.responses.get(i))) return false;
        }
        return searcher.equals(result.searcher);
    }
}
