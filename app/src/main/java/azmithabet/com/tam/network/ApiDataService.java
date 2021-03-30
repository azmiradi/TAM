package azmithabet.com.tam.network;

import azmithabet.com.tam.model.Response;
import azmithabet.com.tam.model.User;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import static azmithabet.com.tam.util.Constants.CATEGORY_PATH;
import static azmithabet.com.tam.util.Constants.HOME_PATH;
import static azmithabet.com.tam.util.Constants.LOGIN_PATH;

public interface ApiDataService {

     @POST(LOGIN_PATH)
     Single<Response> login(@Header("lang") String lang,
                            @Header("apiKey") String apiKey, @Body User user);
     @GET(CATEGORY_PATH)
     Single<ResponseBody> getCategories(@Header("lang") String lang,
                                            @Header("apiKey") String apiKey);
     @GET(HOME_PATH)
     Single<ResponseBody> getHome(@Header("lang") String lang,
                                        @Header("apiKey") String apiKey);

}