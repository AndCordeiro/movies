package br.com.andcordeiro.movies.system.util;

import android.util.Log;

import java.util.concurrent.Callable;

import rx.Observable;

public class RxUtils {

    private static final String TAG = RxUtils.class.getName();

    public static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(func.call());
            } catch (Exception ex) {
                Log.e(TAG, "RxError: ", ex);
            }
        });
    }

}
