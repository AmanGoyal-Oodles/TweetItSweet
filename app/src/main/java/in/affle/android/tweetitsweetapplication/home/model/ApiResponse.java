package in.affle.android.tweetitsweetapplication.home.model;

import android.util.Log;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;
import in.affle.android.tweetitsweetapplication.services.ServerClientApi;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ApiResponse extends BaseObservable {


    private SearchTweetApiResponse mSearchTweetApiResponse;
    private MutableLiveData<ArrayList<Statuses>> tweetData = new MutableLiveData<>();
    private ArrayList<Statuses> tweetList = new ArrayList<>();

    public MutableLiveData<ArrayList<Statuses>> getTweetData() {
        return tweetData;
    }

    public void setTweetList() {
        tweetList.clear();
        tweetList.add(new Statuses("My Best Tweet", 50, 473));
        tweetList.add(new Statuses("My Best Tweet", 50, 473));
        tweetList.add(new Statuses("My Best Tweet", 50, 473));
        tweetList.add(new Statuses("My Best Tweet", 50, 473));
        tweetList.add(new Statuses("My Best Tweet", 50, 473));
        tweetData.setValue(tweetList);
    }

    public void fetchTweets(String authorization, String query) {
        //String q = "nasa";
        String resultType = "recent";
        ServerClientApi.getApi().getTweets(authorization, query, resultType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Function<Throwable, Response<Object>>() {
                    @Override
                    public Response<Object> apply(Throwable throwable) {
                        setTweetList();
                        return null;
                    }
                }).subscribe(new DisposableObserver<Response<Object>>() {
            @Override
            public void onNext(Response<Object> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().toString();
                    Log.d("Json Data", jsonData);
                    SearchTweetApiResponse searchTweetApiResponse = ServerClientApi.buildGsonConverter().fromJson(jsonData, SearchTweetApiResponse.class);
                    Log.d("Json Data Object", searchTweetApiResponse + "");
                    //if (response.body().getSearchMetaData() != null && response.body().getStatuses() != null) {
                    //tweetData.setValue(response.body().getStatuses());
                    //searchMetaData = response.body();
                    //}
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
