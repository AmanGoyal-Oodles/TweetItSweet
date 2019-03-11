package in.affle.android.tweetitsweetapplication.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchTweetApiResponse {

    private ArrayList<Statuses> statuses;
    @SerializedName("search_metadata")
    private SearchMetaData searchMetaData;

    public ArrayList<Statuses> getStatuses() {
        return statuses;
    }

    public void setStatuses(ArrayList<Statuses> statuses) {
        this.statuses = statuses;
    }

    public SearchMetaData getSearchMetaData() {
        return searchMetaData;
    }

    public void setSearchMetaData(SearchMetaData searchMetaData) {
        this.searchMetaData = searchMetaData;
    }
}
