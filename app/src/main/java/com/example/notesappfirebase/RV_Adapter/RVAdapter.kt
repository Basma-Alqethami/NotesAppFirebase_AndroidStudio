package com.example.notesappfirebase.RV_Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfirebase.Firebase.Data
import com.example.notesappfirebase.MainFragment
import com.example.notesappfirebase.R
import com.example.notesappfirebase.databinding.RowBinding

class RVAdapter (private val mainFragment: MainFragment, private var list: ArrayList<Data>): RecyclerView.Adapter<RVAdapter.Holder>() {

    class Holder( val Binding: RowBinding): RecyclerView.ViewHolder(Binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(RowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list[position]
        holder.Binding.apply {
            tvTitle.text = data.title
            tvNote.text = data.note

            holder.itemView.setOnClickListener{
                mainFragment.findNavController().navigate(R.id.action_mainFragment_to_showFragment, Bundle().apply {
                    putString("id", list[position].id)
                    putString("title", list[position].title)
                    putString("note", list[position].note)
                })
            }
        }
    }

    override fun getItemCount() = list.size
}