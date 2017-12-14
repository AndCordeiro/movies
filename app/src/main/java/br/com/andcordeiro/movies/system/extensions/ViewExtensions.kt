package br.com.andcordeiro.movies.system.extensions

import android.view.View

inline fun <reified T : View> View.find(id: Int): T = this.findViewById<T>(id)

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}