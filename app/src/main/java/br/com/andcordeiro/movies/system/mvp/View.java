package br.com.andcordeiro.movies.system.mvp;

import android.content.Context;
import android.content.Intent;

public interface View {
    Intent getIntent();

    Context getContext();
}
