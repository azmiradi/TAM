package azmithabet.com.tam.view_model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import azmithabet.com.tam.localDB.Shared.SharedPreference;
import azmithabet.com.tam.model.Response;
import azmithabet.com.tam.model.User;
import azmithabet.com.tam.repository.SignInRepository;

public class SignInViewModel extends ViewModel {

    private final SignInRepository signInRepository =new SignInRepository();
    public LiveData<Response> login(User user) {
        return signInRepository.login(user);
    }

    public void setLogin(boolean isLogin, Context context) {
        signInRepository.setLogin(isLogin,context);
    }
    @Override
    protected void onCleared() {
        signInRepository.compositeDisposable.clear();
        super.onCleared();
    }
}