package azmithabet.com.tam.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.material.textfield.TextInputEditText;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import azmithabet.com.tam.R;
import azmithabet.com.tam.databinding.ActivitySignInBinding;
import azmithabet.com.tam.model.Response;
import azmithabet.com.tam.model.User;
import azmithabet.com.tam.util.Constants;
import azmithabet.com.tam.view_model.SignInViewModel;

import static azmithabet.com.tam.util.Constants.COREECT_RESPONSE_CODE;
import static azmithabet.com.tam.util.Constants.FCM_TOKEN;
import static azmithabet.com.tam.util.Constants.INCOREECT_RESPONSE_CODE;

public class SignIn extends BaseActivity {
    private ActivitySignInBinding binding;
    private SignInViewModel signInViewModel;
    private boolean isTrue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hidStatusBar();
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_sign_in);

        internetListener();
        initialization();
    }



    private void initialization() {
        signInViewModel = new ViewModelProvider(this)
                .get(SignInViewModel.class);
        binding.setSignIn(this);
    }

    public void signIn() {
        if (correctValidation()) {
            showProgressDialog(null, getString(R.string.loading_login));
            signInViewModel.login(new User(getText(binding.email),
                    getText(binding.password), FCM_TOKEN))
                    .observe(this, this::checkLoginResult);
        }
    }


    private boolean correctValidation() {
        isTrue = true;

        signInViewModel.isValidEmail(getText(binding.email))
                .observe(this, errorType -> isTrue = isValidEmail(errorType));

        signInViewModel.isValidPassword(getText(binding.password))
                .observe(this, errorType -> isTrue = isValidPassword(errorType));

        return isTrue;
    }

    private boolean isValidPassword(Constants.ErrorType errorType) {
        switch (errorType) {
            case IS_EMPTY:
                binding.password.setError(getString(R.string.is_empty));
                isTrue = false;
                break;


            case ERROR_FORMAT:
                binding.password.setError(getString(R.string.password_invalid));
                isTrue = false;
                break;
        }
        return isTrue;
    }

    private boolean isValidEmail(Constants.ErrorType errorType) {
        switch (errorType) {
            case IS_EMPTY:
                binding.email.setError(getString(R.string.is_empty));
                isTrue = false;
                break;

            case ERROR_FORMAT:
                binding.email.setError(getString(R.string.error_email_format));
                isTrue = false;
                break;
        }
        return isTrue;
    }

    private String getText(TextInputEditText editText) {
        return editText.getText().toString();
    }

    private void checkLoginResult(Response response) {
        hideDialog();
        if (response != null) {
            if (response.getCode() == COREECT_RESPONSE_CODE) {
                signInViewModel.setLogin(true, this);
                startMainActivity();
            } else if (response.getCode() == INCOREECT_RESPONSE_CODE) {
                showToast(getString(R.string.incoreect_login));
            } else {
                showToast(getString(R.string.login_error));
            }
        } else {
            showToast(getString(R.string.login_error));
        }
    }

    public void skip() {
        signInViewModel.setLogin(false, this);
        startMainActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        unbindListener();
        super.onDestroy();
    }
}