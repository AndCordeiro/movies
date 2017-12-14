package br.com.andcordeiro.movies.histories.listMovie

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.andcordeiro.movies.R
import br.com.andcordeiro.movies.entities.PopularityMovieEntity
import br.com.andcordeiro.movies.system.extensions.find
import br.com.andcordeiro.movies.system.extensions.loadImage

class ListMovieAdapter (private var data: MutableList<PopularityMovieEntity>, private val onClickListener: ListMovieAdapter.OnClickListener?, private val searchText: String) : RecyclerView.Adapter<ListMovieAdapter.Holder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_list_movie, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = this.data[position]
        holder.tvTitle.text = data.title
        holder.tvPopularity.text = data.popularity.toString()
        holder.tvNote.text = data.voteAverage.toString()
        holder.tvRelease.text = data.releaseDate
        holder.ivPoster.loadImage(data.posterPath)
        holder.tvVoteCount.text = data.voteCount.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface OnClickListener {
        fun onItemClick(data: PopularityMovieEntity)

        fun onLongItemClick(data: PopularityMovieEntity)
    }

    fun updateItens(popularityMovies: List<PopularityMovieEntity>){
        data.addAll(popularityMovies)
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        val cvListMovie by lazy { itemView.find<CardView>(R.id.cv_list_movie) }
        val tvTitle by lazy { itemView.find<TextView>(R.id.tv_title)}
        val tvPopularity by lazy { itemView.find<TextView>(R.id.tv_popularity) }
        val tvNote by lazy { itemView.find<TextView>(R.id.tv_note) }
        val tvRelease by lazy { itemView.find<TextView>(R.id.tv_release) }
        val ivPoster by lazy { itemView.find<ImageView>(R.id.iv_poster) }
        val tvVoteCount by lazy { itemView.find<TextView>(R.id.tv_vote_count) }

        init{
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            onClickListener?.onItemClick(data[adapterPosition])
        }

        override fun onLongClick(v: View?): Boolean {
            onClickListener?.onLongItemClick(data[adapterPosition])
            return false
        }

    }


}