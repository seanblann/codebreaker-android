package edu.cnm.deepdive.codebreaker.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.codebreaker.BuildConfig;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import io.reactivex.rxjava3.core.Single;
import java.io.IOException;
import java.util.Properties;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface CodebreakerServiceProxy {

  String ISO_8601_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

  @POST("games/")
  Single<Game> startGame(@Body Game game);

  @POST("games/{gameId}/guesses")
  Single<Guess> submitGuess(@Path("gameId") String gameID, @Body Guess guess);

  static CodebreakerServiceProxy getInstance(){
    return InstanceHolder.INSTANCE;
  }
  class InstanceHolder {

    private static final String PROPERTIES_FILE = "local.properties";
    private static final String BASE_URL_KEY = "base_url";
    private static final CodebreakerServiceProxy INSTANCE;



    static {
      Gson gson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
          .setDateFormat(ISO_8601_DATETIME_FORMAT)
          .create();
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(Level.BODY);
      OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(interceptor)
          .build();
      Retrofit retrofit = new Retrofit.Builder()
          .client(client)
          .baseUrl(BuildConfig.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create(gson))
          .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
          .build();
      INSTANCE = retrofit.create(CodebreakerServiceProxy.class);
    }


  }

}
