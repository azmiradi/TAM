package azmithabet.com.tam.repository;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import azmithabet.com.tam.localDB.Shared.SharedPreference;
import azmithabet.com.tam.model.BaseResponse;
import azmithabet.com.tam.model.User;
import azmithabet.com.tam.network.RetrofitClient;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static azmithabet.com.tam.util.Constants.API_KEY;
import static azmithabet.com.tam.util.Constants.LANG;

public class SignInRepository {
    public CompositeDisposable compositeDisposable=new CompositeDisposable();

     public MutableLiveData<BaseResponse> login(User user) {
        MutableLiveData<BaseResponse> responseMutableLiveData=new MutableLiveData<>();

        Single<BaseResponse> observable = RetrofitClient.getService()
                .login(LANG,API_KEY,user)
            .subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread());

         compositeDisposable.add(observable.subscribe(responseMutableLiveData::setValue
                , throwable ->responseMutableLiveData.setValue(null)));

        return responseMutableLiveData;
    }

    public void setLogin(boolean isLogin, Context context)
    {
         SharedPreference.setLogin(isLogin,context);
    }
}


