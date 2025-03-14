package com.coco.submissiondicodingexpert.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.coco.core.domain.model.Movie
import com.coco.submissiondicodingexpert.R
import com.coco.submissiondicodingexpert.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var movie: Movie
    private var isFavoriteMovie by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(MOVIE_KEY)?.let { data ->
            movie = data
            setupUi(movie)
            observeFav(data.id)
            favOnPress()
        }
        backOnPress()
    }

    private fun backOnPress() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun favOnPress() {
        binding.btnBookmark.setOnClickListener {
            if (isFavoriteMovie){
                viewModel.deleteFavoriteFromDb(movie)
            }else{
                viewModel.insertFavoriteToDb(movie)
            }
            isFavoriteMovie = !isFavoriteMovie
            favButtonUpdate()
        }
    }

    private fun observeFav(movie: Int?) {
        if (movie != null){
            viewModel.isFavorite(movie).observe(viewLifecycleOwner){ fav ->
                isFavoriteMovie = fav
                favButtonUpdate()
            }
        }
    }

    private fun favButtonUpdate() {
        val iconResource = if (isFavoriteMovie){
            R.drawable.baseline_bookmark_added_24
        }else{
            R.drawable.baseline_bookmark_border_24
        }
        binding.ivFavorite.setImageResource(iconResource)
    }

    private fun setupUi(movie: Movie) {
        binding.apply {
            Glide.with(requireContext()).load(movie.img).into(imgDetail)
            tvNameMovies.text = movie.name
            tvAboutSinopsis.text = movie.overview
        }
    }

    companion object{
        const val MOVIE_KEY = "MOVIE"
    }
}