package br.com.andcordeiro.movies.system.mvp;

import android.content.Context;

public abstract class ModelAbstract {

    protected final String TAG = ModelAbstract.class.getSimpleName();
    protected String lastError = null;
    protected Exception lastException = null;


    public String getLastError() {
        return lastError;
    }

    public Exception getLastException() {
        return lastException;
    }

    protected abstract Context getContext();

}
