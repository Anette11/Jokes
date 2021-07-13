package com.example.jokes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jokes.databinding.OneItemBinding
import com.example.jokes.models.Value

class JokeAdapter : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {
    private var list: List<Value> = ArrayList()

    class JokeViewHolder(private val oneItemBinding: OneItemBinding) :
        RecyclerView.ViewHolder(oneItemBinding.root) {
        fun bind(value: Value) = with(oneItemBinding) {
            textView.text = value.joke
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val oneItemBinding: OneItemBinding =
            OneItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JokeViewHolder(oneItemBinding)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        val value = list[position]
        holder.bind(value)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<Value>) {
        this.list = newList
        notifyDataSetChanged()
    }
}