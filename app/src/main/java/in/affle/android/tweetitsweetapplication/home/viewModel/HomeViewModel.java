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
        String authorization = "OAuth oauth_consumer_key=\"Ql5CsdpJgjUnkwUafmfm0oWlq\"" +
                ",oauth_token=\"1103961594202148865-s6IF4CAgHF3fmOHMGXbgVCVgokR3Jo\"" +
                ",oauth_signature_method=\"HMAC-SHA1\"" +
                ",oauth_timestamp=\"" + timestamp + "\"" +
                ",oauth_nonce=\"TbuuG453pJx\"" +
                ",oauth_version=\"1.0\"" +
                ",oauth_signature=\"dcF0iybUCsyOAJrA9R%2F%2B9D%2FQ60k%3D\"";

        mApiResponse.fetchTweets(authorization, query);
    }

    public MutableLiveData<ArrayList<Statuses>> getmTweetList() {
        return mApiResponse.getTweetData();
    }

    public TweetsAdapter getAdapter() {
        return mTweetsAdapter;
    }

    public void onSearchKeyTextChanged(CharSequence s, int start, int before, int count) {
        String searchKey = s.toString();
        if (searchKey.trim().length() > 2) {
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

}