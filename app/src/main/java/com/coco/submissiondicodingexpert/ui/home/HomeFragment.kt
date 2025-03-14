package com.coco.submissiondicodingexpert.ui.home

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.coco.core.data.source.Resource
import com.coco.core.ui.MovieAdapter
import com.coco.submissiondicodingexpert.R
import com.coco.submissiondicodingexpert.databinding.FragmentHomeBinding
import com.coco.submissiondicodingexpert.ui.detail.DetailFragment
import com.google.android.material.internal.ViewUtils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter{data ->
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment,
                Bundle().apply {
                    putParcelable(DetailFragment.MOVIE_KEY,data)
                })
        }
    }
    private var isSearchMode = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        favOnPress()
        initRv()
        setupSearch()
    }

    private fun setupSearch() {
        // Menambahkan action untuk search
        binding.edSearchView.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = textView.text.toString().trim()
                if (query.isNotEmpty()) {
                    isSearchMode = true
                    performSearch(query)
                    hideKeyboard()
                }
                return@setOnEditorActionListener true
            }
            false
        }

        // Reset ketika search field kosong
        binding.edSearchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty() && isSearchMode) {
                    isSearchMode = false
                    // Reset ke daftar film utama
                    observeData()
                }
            }
        })
    }

    private fun performSearch(query: String) {
        binding.pbHome.visibility = View.VISIBLE
        binding.rvMovies.visibility = View.GONE

        // Unsubscribe dari observer sebelumnya jika ada
        if (viewModel.searchResult.hasObservers()) {
            viewModel.searchResult.removeObservers(viewLifecycleOwner)
        }

        viewModel.searchMovie(query)

        viewModel.searchResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.rvMovies.visibility = View.GONE
                    binding.pbHome.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.rvMovies.visibility = View.VISIBLE
                    binding.pbHome.visibility = View.GONE
                    result.data?.let { movies ->
                        if (movies.isEmpty()) {
                            Toast.makeText(requireContext(), "No movies found", Toast.LENGTH_SHORT).show()
                        } else {
                            movieAdapter.setItems(movies)
                        }
                    }
                }
                is Resource.Error -> {
                    binding.rvMovies.visibility = View.GONE
                    binding.pbHome.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${result.message}", Toast.LENGTH_SHORT).show()
                    Log.e("HomeFragment", "Search Error: ${result.message}")
                }
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.edSearchView.windowToken, 0)
    }

    private fun initRv() {
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun favOnPress() {
        binding.btnBookmark.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
    }

    private fun observeData() {
        viewModel.getMovie.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    binding.rvMovies.visibility = View.GONE
                    binding.pbHome.visibility = View.VISIBLE

                }
                is Resource.Success ->{
                    binding.rvMovies.visibility = View.VISIBLE
                    binding.pbHome.visibility = View.GONE
                    it.data?.let { movie ->
                        movieAdapter.setItems(movie)
                    }
                }
                is Resource.Error ->{
                    binding.rvMovies.visibility = View.GONE
                    binding.pbHome.visibility = View.GONE
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvMovies.adapter = null
        _binding = null
    }
}