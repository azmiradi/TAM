package azmithabet.com.tam.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import azmithabet.com.tam.R;
import azmithabet.com.tam.databinding.ActivitySignInBinding;
import azmithabet.com.tam.model.Response;
import azmithabet.com.tam.model.User;
import azmithabet.com.tam.view_model.SignInViewModel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.textfield.TextInputEditText;
import static azmithabet.com.tam.util.Constants.COREECT_RESPONSE_CODE;
import static azmithabet.com.tam.util.Constants.FCM_TOKEN;
import static azmithabet.com.tam.util.Constants.INCOREECT_RESPONSE_CODE;
import static azmithabet.com.tam.util.ValidUi.isValidEmail;
import static azmithabet.com.tam.util.ValidUi.isValidPassword;

public class SignIn extends BaseActivity implements View.OnClickListener {
   private ActivitySignInBinding binding;
   private SignInViewModel signInViewModel;
    private static final String TAG = "SignIn";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hidStatusBar();
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_sign_in);

        internetListener();
        initialization();
        clickListenerInitialization();
    }

    private void internetListener() {
         checkInternet(this, () -> Log.d(TAG, "internetListener: "));
    }

    private void hidStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void initialization() {
        signInViewModel = new ViewModelProvider(this)
                .get(SignInViewModel.class);

    }


    private void clickListenerInitialization() {
        binding.loginBu.setOnClickListener(this);
        binding.skip.setOnClickListener(this);
        binding.notHaveAccount.setOnClickListener(this);
        binding.forgetPassword.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.login_bu:
                  signIn();
                break;
            case R.id.not_have_account:

                break;
            case R.id.forget_password:

                break;

            case R.id.skip:
                signInViewModel.setLogin(false,this);
                startMainActivity();
                break;
        }
    }

    private void signIn() {
       if (validtionDone()){
           showProgressDialog(null,getString(R.string.loading_login));
           signInViewModel.login(new User(getText(binding.email),
                   getText(binding.password),FCM_TOKEN))
                   .observe(this, this::checkLoginResult);
       }
    }

    private boolean validtionDone() {
        boolean isTrue=true;
        switch (isValidEmail(getText(binding.email)))
        {
            case IS_EMPTY:
                binding.email.setError(getString(R.string.is_empty));
                isTrue=false;
                break;

            case ERROR_FORMAT:
                binding.email.setError(getString(R.string.error_email_format));
                isTrue=false;
                break;
        }
        switch (isValidPassword(getText(binding.password)))
        {
            case IS_EMPTY:
                binding.password.setError(getString(R.string.is_empty));
                isTrue=false;
                break;


            case ERROR_FORMAT:
                binding.password.setError(getString(R.string.password_invalid));
                isTrue=false;
                break;
        }
        return isTrue;
    }

   private String getText(TextInputEditText editText)
   {
       return editText.getText().toString();
   }

    private void checkLoginResult(Response response) {
        hideDialog();
        if (response!=null)
        {
            if (response.getCode()==COREECT_RESPONSE_CODE)
            {
                signInViewModel.setLogin(true,this);
                startMainActivity();
            }

            else if (response.getCode()==INCOREECT_RESPONSE_CODE){
                showToast(getString(R.string.incoreect_login));
            }
            else {
                showToast(getString(R.string.login_error));
            }
        }
        else {
            showToast(getString(R.string.login_error));
        }
    }

    private void startMainActivity() {
        Intent intent=new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
         bindListener();
    }

    @Override
    protected void onDestroy() {
         unbindListener();
        super.onDestroy();
    }
}