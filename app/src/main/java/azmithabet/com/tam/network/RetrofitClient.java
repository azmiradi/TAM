package azmithabet.com.tam.network;


 import com.google.gson.GsonBuilder;

 import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

 import static azmithabet.com.tam.util.Constants.BASE_URL;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static ApiDataService getService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(gsonConverter())
                     .build();
        }
        return retrofit.create(ApiDataService.class);
    }

    private static Converter.Factory gsonConverter(){
      return GsonConverterFactory.create(new GsonBuilder().setLenient().create());
    }


}