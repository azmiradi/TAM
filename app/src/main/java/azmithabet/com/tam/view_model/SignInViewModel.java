package azmithabet.com.tam.view_model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import azmithabet.com.tam.model.Response;
import azmithabet.com.tam.model.User;
import azmithabet.com.tam.repository.SignInRepository;
import azmithabet.com.tam.util.Constants;

import static azmithabet.com.tam.util.Constants.PASSWORD_LENGTH;

public class SignInViewModel extends ViewModel {

    private final SignInRepository signInRepository =new SignInRepository();

    public LiveData<Response> login(User user) {
        return signInRepository.login(user);
    }

    public void setLogin(boolean isLogin, Context context) {
        signInRepository.setLogin(isLogin,context);
    }

    public LiveData<Constants.ErrorType> isValidEmail(String email) {
        MutableLiveData<Constants.ErrorType> typeMutableLiveData=new MutableLiveData<>();

        if (TextUtils.isEmpty(email))
        {
            typeMutableLiveData.setValue(Constants.ErrorType.IS_EMPTY);
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            typeMutableLiveData.setValue(Constants.ErrorType.ERROR_FORMAT);
        }
        else {
            typeMutableLiveData.setValue(Constants.ErrorType.NO_ERROR);
        }
        return typeMutableLiveData;
    }

    public LiveData<Constants.ErrorType> isValidPassword(String password) {
        MutableLiveData<Constants.ErrorType> typeMutableLiveData=new MutableLiveData<>();
        if (TextUtils.isEmpty(password))
        {
            typeMutableLiveData.setValue(Constants.ErrorType.IS_EMPTY);
        }
        else if (password.length()<PASSWORD_LENGTH)
        {
            typeMutableLiveData.setValue(Constants.ErrorType.ERROR_FORMAT);
        }
        else {
            typeMutableLiveData.setValue(Constants.ErrorType.NO_ERROR);
        }
      return typeMutableLiveData;
    }

    @Override
    protected void onCleared() {
        signInRepository.compositeDisposable.clear();
        super.onCleared();
    }

}