package com.example.borrowmybook

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class BookAdapter (private val onCardClick: (position: Int)-> Unit,
                    private val bookList: List<Books>):RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list_item_layout,parent,false)
        return ViewHolder(view, onCardClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemVM = bookList[position]
        holder.bookName.text = itemVM.bookName
        holder.author.text = itemVM.author
        holder.borrowName.text = itemVM.borrowerName
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

}

class ViewHolder(view: View, private val onCardClick: (position: Int) -> Unit)
    :RecyclerView.ViewHolder(view), View.OnClickListener{
    init{
        itemView.setOnClickListener(this)
    }
    val bookName : TextView = view.findViewById(R.id.bookName)
    val author : TextView= view.findViewById(R.id.author)
    val borrowName : TextView = view.findViewById(R.id.borrowName)

    override fun onClick(p0: View?) {
        val position = adapterPosition
        onCardClick(position)
    }
}