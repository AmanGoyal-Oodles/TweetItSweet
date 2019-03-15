package in.affle.android.tweetitsweetapplication.home.viewModel;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import in.affle.android.tweetitsweetapplication.R;
import in.affle.android.tweetitsweetapplication.home.adapter.TweetsAdapter;
import in.affle.android.tweetitsweetapplication.home.model.ApiResponse;
import in.affle.android.tweetitsweetapplication.home.model.Statuses;
import in.affle.android.tweetitsweetapplication.utils.constants.AppConstants;
import in.affle.android.tweetitsweetapplication.utils.constants.AuthConstants;
import in.affle.android.tweetitsweetapplication.utils.util.TweetsByPopularityComparator;

public class HomeViewModel extends ViewModel {


    public ObservableInt loading;
    public ObservableInt showEmpty;
    private ApiResponse mApiResponse;
    private TweetsAdapter mTweetsAdapter;
    private ArrayList<Statuses> mTweetList;

    public void initHomeViews() {
        mApiResponse = new ApiResponse();
        mTweetsAdapter = new TweetsAdapter(R.layout.view_tweet, this);
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
        mTweetList = new ArrayList<>();
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
        mTweetList.clear();
        mTweetList.addAll(tweetList);
        mTweetsAdapter.setmTweetList(mTweetList);
    }

    public void sortTweetList() {
        SortAsyncTask task = new SortAsyncTask(mTweetList);
        task.execute(null, null, null);
    }

    public Statuses getTweetAt(Integer index) {
        if (mApiResponse.getTweetData().getValue() != null &&
                index != null &&
                mApiResponse.getTweetData().getValue().size() > index) {
            return mApiResponse.getTweetData().getValue().get(index);
        }
        return null;
    }

    public class SortAsyncTask extends AsyncTask<Void, Void, ArrayList<Statuses>> {

        private ArrayList<Statuses> tweetList;

        public SortAsyncTask(ArrayList<Statuses> list) {
            tweetList = new ArrayList<>();
            tweetList.clear();
            tweetList.addAll(list);
        }

        @Override
        protected ArrayList<Statuses> doInBackground(Void... param) {
            Collections.sort(tweetList, new TweetsByPopularityComparator());
            return tweetList;
        }

        @Override
        protected void onPostExecute(ArrayList<Statuses> list) {
            super.onPostExecute(list);
            setTweetsInAdapter(list);
            loading.set(View.GONE);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.set(View.VISIBLE);
        }
    }

}