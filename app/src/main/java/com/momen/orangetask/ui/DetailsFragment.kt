package com.momen.orangetask.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.momen.orangetask.databinding.FragmentDetailsBinding
import com.momen.orangetask.viewmodel.BookDetailsViewModel
import com.momen.orangetask.viewmodel.BooksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    private val bookViewModel: BookDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = bookViewModel
        binding.lifecycleOwner = this
        bookViewModel.getBookById(args.id)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                bookViewModel.onErrorMessageShown()
            }
        }

        bookViewModel.noInternet.observe(viewLifecycleOwner) { noInternet ->
            if (noInternet) {
                showRetryDialog()
                bookViewModel.onNoInternetShown()
            }
        }
    }
    private fun showRetryDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("No Internet")
            .setMessage("Please check your connection and try again.")
            .setPositiveButton("Retry") { _, _ ->
                bookViewModel.getBookById(args.id)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}