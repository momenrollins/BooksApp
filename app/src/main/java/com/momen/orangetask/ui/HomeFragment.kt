package com.momen.orangetask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.momen.orangetask.BuildConfig
import com.momen.orangetask.databinding.FragmentHomeBinding
import com.momen.orangetask.ui.adapter.BooksAdapter
import com.momen.orangetask.viewmodel.BooksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BooksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BooksAdapter { book ->
/*            val action = HomeFragmentDirections.actionHomeToDetails(book.id)
            findNavController().navigate(action)*/
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.books.observe(viewLifecycleOwner) { books ->
            adapter.submitList(books)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            if (query.isNotEmpty()) {
                viewModel.searchBooks(query, BuildConfig.API_KEY)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}