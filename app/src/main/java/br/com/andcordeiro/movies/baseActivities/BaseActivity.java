package br.com.andcordeiro.movies.baseActivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public abstract class BaseActivity extends AppCompatActivity {

    public static String TAG = BaseActivity.class.getSimpleName();
    private static Toast toast;
    private static boolean isAlertShow = false;

    @NonNull
    protected abstract Integer getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void setIsAlertShow(boolean isShow) {
        isAlertShow = isShow;
    }

    public static boolean isIsAlertShow() {
        return isAlertShow;
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

    protected void showAlert(
            View view,
            String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        alert.setView(view);
        alert.setMessage(msg);
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


    public Context getContext() {
        return this;
    }
}
