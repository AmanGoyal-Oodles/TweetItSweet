package in.affle.android.tweetitsweetapplication.home.model;

public class SearchMetaData {

    private double completed_in;
    private long max_id;
    private String max_id_str;
    private String next_results;
    private String query;
    private String refresh_url;
    private int count;
    private long since_id;
    private String since_id_str;

    public double getCompleted_in() {
        return completed_in;
    }

    public void setCompleted_in(double completed_in) {
        this.completed_in = completed_in;
    }

    public long getMax_id() {
        return max_id;
    }

    public void setMax_id(long max_id) {
        this.max_id = max_id;
    }

    public String getMax_id_str() {
        return max_id_str;
    }

    public void setMax_id_str(String max_id_str) {
        this.max_id_str = max_id_str;
    }

    public String getNext_results() {
        return next_results;
    }

    public void setNext_results(String next_results) {
        this.next_results = next_results;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getRefresh_url() {
        return refresh_url;
    }

    public void setRefresh_url(String refresh_url) {
        this.refresh_url = refresh_url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getSince_id() {
        return since_id;
    }

    public void setSince_id(long since_id) {
        this.since_id = since_id;
    }

    public String getSince_id_str() {
        return since_id_str;
    }

    public void setSince_id_str(String since_id_str) {
        this.since_id_str = since_id_str;
    }
}
