package br.com.andcordeiro.movies.system.retrofit.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class Callback {

    @SerializedName("status_message")
    @Expose
    private String message;
    @SerializedName("sucess")
    @Expose
    private boolean sucess;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }
}
