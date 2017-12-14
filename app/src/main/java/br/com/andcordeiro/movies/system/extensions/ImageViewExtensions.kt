package br.com.andcordeiro.movies.system.extensions

import android.support.v4.content.ContextCompat
import android.widget.ImageView
import br.com.andcordeiro.movies.R
import br.com.andcordeiro.movies.system.Consts
import com.bumptech.glide.Glide

fun ImageView.loadImage(image: String?) {
    Glide.with(this.context)
            .load(Consts.Url.URL_IMAGE + image)
            .centerCrop()
            .error(ContextCompat.getDrawable(this.context, R.mipmap.ic_launcher))
            .placeholder(ContextCompat.getDrawable(this.context, R.mipmap.ic_launcher))
            .crossFade()
            .fitCenter()
            .into(this)
}

