package azmithabet.com.tam.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.anshulthakur.networkstatechecker.InternetStateChecker;
import com.novoda.merlin.Merlin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import azmithabet.com.tam.R;
import azmithabet.com.tam.interfaces.OnConnectionChange;

public abstract  class  BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private Merlin merlin;
    private InternetStateChecker internetStateChecker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void checkInternet(Context context,
                                      OnConnectionChange onConnectionChange) {
        internetStateChecker=   new InternetStateChecker.
                Builder(context)
                .setDialogTitle(context.getString(R.string.check_network))
                .setCancelable(false)
                .setDialogBgColor(R.color.text_color)
                .setDialogTextColor(R.color.white)
                .setDialogIcon(R.drawable.ic_mood_bad_black_46dp)
                .setDialogMessage(context.getString(R.string.no_internet)).build();
        merlin= new Merlin.Builder().withConnectableCallbacks().build(context);
        merlin.registerConnectable(onConnectionChange::onConnectionChange);

    }

    public void unbindListener(){
        if (merlin!=null)
        {
            merlin.unbind();
        }
        if (internetStateChecker!=null)
        {
            internetStateChecker.stop();
        }
    }

    public  void  bindListener(){
        if (merlin!=null)
        {
            merlin.bind();
        }
    }
    public void showProgressDialog(String title, @NonNull String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this,R.style.progress_dialog);
            if (title != null)
                mProgressDialog.setTitle(title);
            mProgressDialog.setIcon(R.drawable.logo_main);
            mProgressDialog.setCancelable(false);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }


    public void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    protected void showToast(String mToastMsg) {
        Toast.makeText(this, mToastMsg, Toast.LENGTH_LONG).show();
    }

}