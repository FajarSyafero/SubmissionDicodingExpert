package com.coco.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coco.core.databinding.ItemMovieBinding
import com.coco.core.domain.model.Movie
import com.bumptech.glide.Glide

class MovieAdapter(private val itemClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieDiscoverViewHolder>() {


    private var items: MutableList<Movie> = mutableListOf()

    fun setItems(items: List<Movie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDiscoverViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieDiscoverViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: MovieDiscoverViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class MovieDiscoverViewHolder(
        private val binding: ItemMovieBinding,
        val itemClick: (Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Movie) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.run {
                    tvMovieTitle.text = name
                    Glide.with(itemView).load(item.img).into(imgMoviePoster)
                }
            }

        }
    }

}