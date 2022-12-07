package com.example.huntinbolo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.huntinbolo.databinding.ItemTagBinding

class TagAdapter : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    private var tagValue = ArrayList<String>()
    private var tagKey = ArrayList<String>()

    fun setData(list: HashMap<String, String>) {
        tagKey.clear()
        tagValue.clear()

        for (item in list) {
            tagKey.add(item.key)
            tagValue.add(item.value)
        }
        notifyItemRangeChanged(0, tagKey.size)
    }

    inner class TagViewHolder(val binding: ItemTagBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(
            ItemTagBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        var item = tagKey[position]
        holder.binding.itemTagKey.text = item
        item = tagValue[position]
        holder.binding.itemTagName.text = item
    }

    override fun getItemCount(): Int {
        return tagKey.size
    }


}