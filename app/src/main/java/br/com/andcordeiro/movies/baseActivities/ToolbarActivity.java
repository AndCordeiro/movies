package br.com.andcordeiro.movies.baseActivities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import br.com.andcordeiro.movies.R;


public abstract class ToolbarActivity extends BaseActivity {

    Toolbar toolbar;
    ViewGroup container;

    @NonNull
    protected abstract Integer getContentLayout();

    @NonNull
    protected abstract String getToolbarTitle();

    protected Integer getMenuLayout() {
        return null;
    }

    @NonNull
    @Override
    protected Integer getLayout() {
        return R.layout.activity_toolbar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initContainer();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getToolbarTitle());
        }
    }

    private void initContainer() {
        container = (FrameLayout) findViewById(R.id.activity_content);
        getLayoutInflater().inflate(getContentLayout(), container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuLayout() != null) {
            getMenuInflater().inflate(getMenuLayout(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
}
