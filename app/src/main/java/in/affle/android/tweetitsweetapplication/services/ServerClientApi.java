package in.affle.android.tweetitsweetapplication.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import in.affle.android.tweetitsweetapplication.BuildConfig;
import in.affle.android.tweetitsweetapplication.home.model.SearchTweetApiResponse;
import in.affle.android.tweetitsweetapplication.utils.constants.AuthConstants;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class ServerClientApi {

    private static RestApiInterface api;

    public static RestApiInterface getApi() {
        if (api == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

            OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(AuthConstants.CONSUMER_KEY, AuthConstants.CONSUMER_SECRET);
            consumer.setTokenWithSecret(AuthConstants.ACCESS_TOKEN, AuthConstants.TOKEN_SECRET);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new SigningInterceptor(consumer))
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(buildGsonConverterFactory())
                    .build();

            api = retrofit.create(RestApiInterface.class);
        }
        return api;
    }

    public static Gson buildGsonConverter() {
        return new GsonBuilder().setDateFormat(AuthConstants.DATE_FORMAT).create();
    }

    private static GsonConverterFactory buildGsonConverterFactory() {
        return GsonConverterFactory.create(buildGsonConverter());
    }

    public interface RestApiInterface {

        @GET(ApiEndPointUrl.SEARCH_TWEETS)
        @retrofit2.http.Headers("Content-Type: application/json")
        Observable<Response<SearchTweetApiResponse>> getTweets(@Header("Authorization") String authorization, @Query("q") String q, @Query("result_type") String resultType, @Query("count") int count);

    }

}