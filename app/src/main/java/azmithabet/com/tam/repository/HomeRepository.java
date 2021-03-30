package azmithabet.com.tam.repository;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import azmithabet.com.tam.localDB.Shared.SharedPreference;
import azmithabet.com.tam.model.Category;
import azmithabet.com.tam.model.HomeResponse;
import azmithabet.com.tam.model.Product;
import azmithabet.com.tam.network.RetrofitClient;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import static azmithabet.com.tam.util.Constants.API_KEY;
import static azmithabet.com.tam.util.Constants.CODE;
import static azmithabet.com.tam.util.Constants.ITEM;
import static azmithabet.com.tam.util.Constants.LANG;
import static azmithabet.com.tam.util.Constants.LIST;
import static azmithabet.com.tam.util.Constants.RESPONSE_MESSAGE;
import static azmithabet.com.tam.util.Constants.TRENDING;
import static azmithabet.com.tam.util.Constants.WHATS_NEW;

public class HomeRepository {
    public CompositeDisposable compositeDisposable;
    private static final String TAG = "CategoryRepository";
    private final Gson gson;

    public HomeRepository() {
        compositeDisposable = new CompositeDisposable();
        gson = new Gson();
    }

    public MutableLiveData<List<Category>>  getCategory() {
        MutableLiveData<List<Category>> responseMutableLiveData = new MutableLiveData<>();
        Single<ResponseBody> observable = RetrofitClient.getService()
                .getCategories(LANG, API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable.add(observable.subscribe( responseBody ->
                        responseMutableLiveData.setValue(getCategoryList(responseBody)) ,
                  throwable -> {
                      Log.d(TAG, "getCategory: "+throwable.getMessage());
                      responseMutableLiveData.setValue(null);
                  }));

        return responseMutableLiveData;
    }

    public MutableLiveData<HomeResponse> getHome() {
        MutableLiveData<HomeResponse> responseMutableLiveData = new MutableLiveData<>();
        Single<ResponseBody> observable = RetrofitClient.getService()
                .getHome(LANG, API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable.add(observable.subscribe( responseBody ->
                        responseMutableLiveData.setValue(getProducts(responseBody)) ,
                throwable -> {
                    Log.d(TAG, "getHome: "+throwable.getMessage());
                    responseMutableLiveData.setValue(null);
                }));

        return responseMutableLiveData;
    }
    private HomeResponse getProducts(ResponseBody responseBody) {
        List<Product> whatsNew=new ArrayList<>();
        List<Product> trending=new ArrayList<>();
        int code=0;
        String msg="";
        try {
            JSONObject jsonObject=new JSONObject(responseBody.string());

            code=jsonObject.getInt(CODE);
            msg=jsonObject.getString(RESPONSE_MESSAGE);

            JSONArray whatsNewList=jsonObject.getJSONObject(ITEM)
                    .getJSONArray(WHATS_NEW);

            JSONArray trendingList=jsonObject.getJSONObject(ITEM)
                    .getJSONArray(TRENDING);

            for (int i = 0; i <whatsNewList.length() ; i++) {
                JSONObject product=whatsNewList.getJSONObject(i);
                whatsNew.add(product(product.toString()));
            }

            for (int j = 0; j <trendingList.length() ; j++) {
                JSONObject product=trendingList.getJSONObject(j);
                trending.add(product(product.toString()));
            }
        }

        catch (JSONException | IOException e) {
            Log.d(TAG, "getProducts: "+e.getMessage());
        }

        return new HomeResponse(whatsNew,trending,code,msg);
    }

    private List<Category> getCategoryList(ResponseBody responseBody) {
       List<Category> categoryList=new ArrayList<>();
        try {

            JSONObject jsonObject=new JSONObject(responseBody.string());
            JSONArray catList=jsonObject.getJSONObject(ITEM)
                    .getJSONArray(LIST);

            for (int i = 0; i <catList.length() ; i++) {
                JSONObject category=catList.getJSONObject(i);
               categoryList.add(category(category.toString()));
            }
        }

        catch (JSONException | IOException e) {
            Log.d(TAG, "getCategoryList: "+e.getMessage());
        }
        return categoryList;
    }

    private Category category(String category) {
        return gson.fromJson(category, Category.class);
    }

    private Product product(String product)
    {
         return gson.fromJson(product, Product.class);
    }

    public MutableLiveData<List<Product>> getProductsByCategory(String categoryID
            , List<Product> products)
    {
        MutableLiveData<List<Product>> productsReMutableLiveData = new MutableLiveData<>();
         Single<List<Product>> observable=  Observable
                .fromIterable(products)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .filter(product -> product.getCategoryID().equals(categoryID))
                .toList();

         compositeDisposable.add(observable.subscribe(productsReMutableLiveData::setValue,
                        throwable -> Log.d(TAG, "getProductsByCategory: "
                                +throwable.getMessage())));
        return productsReMutableLiveData;
    }

    public MutableLiveData<Boolean> isUserLogin(Context context){
       return new MutableLiveData<>(SharedPreference.isLogin(context));
    }

}
