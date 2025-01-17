package com.momen.orangetask.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.momen.orangetask.databinding.FragmentHomeBinding
import com.momen.orangetask.ui.adapter.BooksAdapter
import com.momen.orangetask.viewmodel.BooksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val booksViewModel: BooksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = booksViewModel
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BooksAdapter { book ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(book.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter


        booksViewModel.books.observe(viewLifecycleOwner) { books ->
            adapter.submitList(books)
        }

        booksViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                booksViewModel.onErrorMessageShown()
            }
        }

        booksViewModel.noInternet.observe(viewLifecycleOwner) { noInternet ->
            if (noInternet) {
                showRetryDialog()
                booksViewModel.onNoInternetShown()
            }
        }

        binding.apply {
            searchInputLayout.setEndIconOnClickListener {
                searchBooks()
            }
            searchEditText.setOnEditorActionListener { _, _, _ ->
                searchBooks()
                true
            }
        }

    }

    private fun searchBooks() {
        val query = binding.searchEditText.text.toString()
        if (query.isNotEmpty()) {
            booksViewModel.searchBooks(query)
        }
    }

    private fun showRetryDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("No Internet")
            .setMessage("Please check your connection and try again.")
            .setPositiveButton("Retry") { _, _ ->
                searchBooks()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}