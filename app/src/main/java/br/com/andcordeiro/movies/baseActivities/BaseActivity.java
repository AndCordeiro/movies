package br.com.andcordeiro.movies.baseActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


public abstract class BaseActivity extends AppCompatActivity {

    public static String TAG = BaseActivity.class.getSimpleName();
    private static ProgressDialog progressDialog;
    private static Toast toast;
    private static boolean isAlertShow = false;

    @NonNull
    protected abstract Integer getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setContentView(getLayout());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        if (progressDialog != null && progressDialog.isShowing()) {
            hideLoadingDialog();
        }
    }

    /**
     * Show loading dialog
     *
     * @param title
     * @param msg
     */
    public void showLoadingDialog(String title, String msg) {
        this.showLoadingDialog(title, msg, false, false, null);
    }

    public void showLoadingDialog(String title, String msg, boolean indeterminate) {
        this.showLoadingDialog(title, msg, indeterminate, false, null);
    }

    public void showLoadingDialog(String title, String msg, boolean indeterminate, boolean cancelable) {
        this.showLoadingDialog(title, msg, indeterminate, cancelable, null);
    }

    public void showLoadingDialog(String title, String msg, boolean indeterminate, boolean cancelable, DialogInterface.OnCancelListener onCancelListener) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.setTitle(title);
            progressDialog.setMessage(msg);
            progressDialog.setIndeterminate(indeterminate);
            progressDialog.setCancelable(cancelable);
            progressDialog.setOnCancelListener(onCancelListener);
        } else {
            progressDialog = ProgressDialog.show(this, title, msg, indeterminate, cancelable, onCancelListener);
        }
    }

    public void updateLoadingDialog(String title, String msg) {
        if (!progressDialog.isShowing()) {
            showLoadingDialog(title, msg);
        } else {
            if (title != null) {
                progressDialog.setTitle(title);
            }
            if (msg != null) {
                progressDialog.setMessage(msg);
            }
        }
    }

    public void hideLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void setIsAlertShow(boolean isShow){
        isAlertShow = isShow;
    }

    public static boolean isIsAlertShow() {
        return isAlertShow;
    }

    /**
     * User is moved to playstore app
     */
    protected void goPlayStoreApp() {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    /**
     * Lock the device orientation
     */
    protected void lockOrientation() {
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }

    /**
     * Unlock the device orientation
     */
    protected void unlockOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    protected void showSimpleAlert(String title, String msg, String btnText, boolean cancelable) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setNeutralButton(btnText, null);
        alert.setCancelable(cancelable);
        alert.show();
    }

    protected void showAlert(
            String title,
            String msg,
            String positiveButton,
            String negativeButton,
            DialogInterface.OnClickListener onClickPositive,
            DialogInterface.OnClickListener onClickNegative,
            boolean cancelable) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setPositiveButton(positiveButton, onClickPositive);
        alert.setNegativeButton(negativeButton, onClickNegative);
        alert.setCancelable(cancelable);
        alert.show();
    }

    protected void showAlert(
            String title,
            String msg,
            String positiveButton,
            DialogInterface.OnClickListener onClickPositive,
            boolean cancelable) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setPositiveButton(positiveButton, onClickPositive);
        alert.setCancelable(cancelable);
        alert.show();
    }

    /**
     * Show the material snackbar
     *
     * @param message will be shown to user
     * @param view    where to be shown
     */
    protected void showSnackBar(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Call this method if you want to show a simple info message.
     * Like "Internet Unnavailable" or "Connection error"
     * Don't worry this method will subscribe if has anyother showing.
     *
     * @param msg Message to show
     */
    public void showToast(String msg) {
        try {
            toast.getView().isShown();
            toast.setText(msg);
        } catch (Exception e) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    protected void setLayoutManager(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void setLayoutManager(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    public Context getContext() {
        return this;
    }
}
