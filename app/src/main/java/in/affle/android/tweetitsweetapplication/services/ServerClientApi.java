package in.affle.android.tweetitsweetapplication.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import java.io.IOException;
import java.text.DateFormat;

import androidx.annotation.NonNull;
import in.affle.android.tweetitsweetapplication.BuildConfig;
import in.affle.android.tweetitsweetapplication.home.model.SearchTweetApiResponse;
import in.affle.android.tweetitsweetapplication.utils.util.DateDeserializer;
import io.reactivex.Observable;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

            OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET);
            consumer.setTokenWithSecret(BuildConfig.ACCESS_TOKEN, BuildConfig.TOKEN_SECRET);


            OkHttpClient client = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new SigningInterceptor(consumer))
                    .addInterceptor(new DecryptionInterceptor())
                    .build();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(
                            SearchTweetApiResponse.class,
                            new DateDeserializer())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(buildGsonConverterFactory())
                    .build();

            api = retrofit.create(RestApiInterface.class);
        }
        return api;
    }

    public static Gson buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
        // Adding custom deserializers
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateDeserializer());
        gsonBuilder.setDateFormat(DateFormat.LONG);
        gsonBuilder.setLenient();
        return gsonBuilder.create();
    }

    private static GsonConverterFactory buildGsonConverterFactory() {
        return GsonConverterFactory.create(buildGsonConverter());
    }

    public interface RestApiInterface {

        @GET(ApiEndPointUrl.SEARCH_TWEETS)
        @retrofit2.http.Headers("Content-Type: application/json")
        Observable<Response<Object>> getTweets(@Header("Authorization") String authorization, @Query("q") String q, @Query("result_type") String resultType);

    }

    public static class CustomInterceptor implements Interceptor {

        private static final boolean DEBUG = true;
        private final String TAG = CustomInterceptor.class.getSimpleName();

        @Override
        public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {

            //encryption
            Request request = chain.request();
            RequestBody oldBody = request.body();
            Headers oldHeader = request.headers();
            /*String authorization="OAuth oauth_consumer_key=\"Ql5CsdpJgjUnkwUafmfm0oWlq\"" +
                    ",oauth_token=\"1103961594202148865-s6IF4CAgHF3fmOHMGXbgVCVgokR3Jo\"" +
                    ",oauth_signature_method=\"HMAC-SHA1\"" +
                    ",oauth_timestamp=\""+System.currentTimeMillis()+"\"" +
                    ",oauth_nonce=\"tVtgmnKQys0\"" +
                    ",oauth_version=\"1.0\"" +
                    ",oauth_signature=\"i4Yhh%2BdvdKQL41RaKflf%2B6uhLG0%3D\"";
            Buffer buffer = new Buffer();
                //oldHeader.newBuilder().add("useEncryption", "YES");
                request.newBuilder().addHeader("Authorization", authorization).build();*/
            //request = request.newBuilder().header("Content-Type", body.contentType().toString()).header("Content-Length", String.valueOf(body.contentLength())).method(request.method(), body).build();
            //Logger.LogDebug(TAG, request.toString());
            return chain.proceed(request);
        }

    }

    public static class DecryptionInterceptor implements Interceptor {

        private static final boolean DEBUG = true;
        private final String TAG = DecryptionInterceptor.class.getSimpleName();

        @Override
        public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {

            //decryption
            boolean exceptionRaised = false;
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            ResponseBody body = originalResponse.body();
            String result = null;
            if (body != null) {
                result = body.string();
                try {
                    Gson gson = new Gson();
                    SearchTweetApiResponse searchTweetApiResponse = buildGsonConverter().fromJson(result, SearchTweetApiResponse.class);
                    gson.fromJson(result, JsonObject.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    exceptionRaised = true;
                    result = "";
                } finally {
                    if (exceptionRaised)
                        result = "";
                }
            }
            Log.d(TAG, result);
            return originalResponse.newBuilder().body(ResponseBody.create(MediaType.parse("application/json"), result))
                    .build();
        }

    }

}