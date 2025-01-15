package com.momen.orangetask.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.momen.orangetask.databinding.ItemBookBinding
import com.momen.orangetask.models.Book

class BooksAdapter(private val onItemClick: (Book) -> Unit) :
    ListAdapter<Book, BooksAdapter.BookViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
    }

    inner class BookViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            binding.book = book
            binding.cardView.setOnClickListener {
                onItemClick(book)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}
