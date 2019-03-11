package in.affle.android.tweetitsweetapplication.home.model;

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

    public MutableLiveData<ArrayList<Statuses>> getTweetData() {
        return tweetData;
    }

    public void fetchTweets(String authorization, String query) {
        String resultType = "recent";
        ServerClientApi.getApi().getTweets(authorization, query, resultType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Function<Throwable, Response<SearchTweetApiResponse>>() {
                    @Override
                    public Response<SearchTweetApiResponse> apply(Throwable throwable) {
                        return null;
                    }
                }).subscribe(new DisposableObserver<Response<SearchTweetApiResponse>>() {
            @Override
            public void onNext(Response<SearchTweetApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getSearchMetaData() != null && response.body().getStatuses() != null) {
                        tweetData.setValue(response.body().getStatuses());
                        mSearchTweetApiResponse = response.body();
                    }
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
