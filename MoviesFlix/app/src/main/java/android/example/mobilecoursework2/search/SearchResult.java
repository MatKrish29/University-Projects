package android.example.mobilecoursework2.search;

// POJO class for search results.
public class SearchResult implements Comparable<SearchResult>{
    private String resultTitle;
    private String resultType;

    public SearchResult(String resultTitle, String resultType) {
        this.resultTitle = resultTitle;
        this.resultType = resultType;
    }

    public String getResultTitle() {
        return resultTitle;
    }

    public String getResultType() {
        return resultType;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "resultTitle='" + resultTitle + '\'' +
                ", resultType='" + resultType + '\'' +
                '}';
    }

    @Override
    public int compareTo(SearchResult searchResult) {
        return this.getResultTitle().compareTo(searchResult.getResultTitle());
    }
}
