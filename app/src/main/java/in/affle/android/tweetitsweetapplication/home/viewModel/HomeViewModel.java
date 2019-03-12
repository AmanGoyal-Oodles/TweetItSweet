package in.affle.android.tweetitsweetapplication.home.viewModel;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import in.affle.android.tweetitsweetapplication.R;
import in.affle.android.tweetitsweetapplication.home.adapter.TweetsAdapter;
import in.affle.android.tweetitsweetapplication.home.model.ApiResponse;
import in.affle.android.tweetitsweetapplication.home.model.Statuses;
import in.affle.android.tweetitsweetapplication.utils.constants.AppConstants;
import in.affle.android.tweetitsweetapplication.utils.constants.AuthConstants;

public class HomeViewModel extends ViewModel {

    public ObservableInt loading;
    public ObservableInt showEmpty;
    private ApiResponse mApiResponse;
    private TweetsAdapter mTweetsAdapter;

    public void init() {
        mApiResponse = new ApiResponse();
        mTweetsAdapter = new TweetsAdapter(R.layout.view_tweet, this);
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
    }

    public void fetchTweetList(String query) {
        long timestamp = System.currentTimeMillis() / 1000;
        String authorization = "OAuth " + AppConstants.KEY_CONSUMER_KEY + "=\"" + AuthConstants.CONSUMER_KEY + "\"" +
                "," + AppConstants.KEY_ACCESS_TOKEN + "=\"" + AuthConstants.ACCESS_TOKEN + "\"" +
                "," + AppConstants.KEY_SIGNATURE_METHOD + "=\"" + AuthConstants.SIGNATURE_METHOD + "\"" +
                "," + AppConstants.KEY_TIMESTAMP + "=\"" + timestamp + "\"" +
                "," + AppConstants.KEY_OAUTH_NONCE + "=\"" + AuthConstants.OAUTH_NONCE + "\"" +
                "," + AppConstants.KEY_OAUTH_VERSION + "=\"" + AuthConstants.OAUTH_VERSION + "\"" +
                "," + AppConstants.KEY_OAUTH_SIGNATURE + "=\"" + AuthConstants.OAUTH_SIGNATURE + "\"";
        mApiResponse.fetchTweets(authorization, query);
    }

    public MutableLiveData<ArrayList<Statuses>> getUpdatedTweetList() {
        return mApiResponse.getTweetData();
    }

    public TweetsAdapter getAdapter() {
        return mTweetsAdapter;
    }

    public void onSearchKeyTextChanged(CharSequence s, int start, int before, int count) {
        String searchKey = s.toString();
        if (searchKey.trim().length() > 2) {
            loading.set(View.VISIBLE);
            fetchTweetList(searchKey);
        }
        Log.d("hello", s.toString());
    }

    public void setTweetsInAdapter(ArrayList<Statuses> tweetList) {
        mTweetsAdapter.setmTweetList(tweetList);
    }

    public Statuses getTweetAt(Integer index) {
        if (mApiResponse.getTweetData().getValue() != null &&
                index != null &&
                mApiResponse.getTweetData().getValue().size() > index) {
            return mApiResponse.getTweetData().getValue().get(index);
        }
        return null;
    }

    //logic for sort list by popularity
    private void sortListByPopularity(ArrayList<Statuses> tweetList) {
        ArrayList<Statuses> list = new ArrayList<>();
        for (Statuses tweet : tweetList) {
            if (tweet.getMetadata().getResult_type().equalsIgnoreCase("popular")) {
                list.add(tweet);
            }
        }
    }

}