package br.com.andcordeiro.movies.system.mvp;

public interface Presenter {
    void create();
    void destroy();
    void setView(Object view);
}
