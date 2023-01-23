package com.example.huntinboh.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.huntinboh.databinding.ItemTagBinding

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
        item = translateKey(item)
        holder.binding.itemTagKey.text = item
        item = tagValue[position]
        holder.binding.itemTagName.text = item
    }

    private fun translateKey(s: String): String {
        var item = s
        when (item) {
            "operational_status" -> item = "Stato"
            "wheelchair" -> item = "Sedia a rotelle"
            "wheelcahir:description" -> item = "Descrizione sedia a rotelle"
            "fee" -> item = "Tassa"
            "name" -> item = "Nome"
            "charge" -> item = "Addebito"
            "description" -> item = "Descrizione"
            "backrest" -> item = "Schienale"
            "material" -> item = "Materiale"
            "check_date" -> item = "Ultimo controllo"
            "checkdate" -> item = "Ultimo controllo"
            "opening_hours" -> item = "Orario di apertura"
            "recycling:cans" -> item = "Lattine"
            "recycing:clothes" -> item = "Vestiti"
            "recycling:glass" -> item = "Vetro"
            "recycling:paper" -> item = "Carta"
            "recycling:scarp_metal" -> item = "Rottami"
            "defribillator:location" -> item = "Posizione"
            "indoor" -> item = "Interno"
            "addr:district" -> item = "Distretto"
            "addr:housenumber" -> item = "Numero civico"
            "addr:street" -> item = "Indirizzo"
        }

        return item
    }

    override fun getItemCount(): Int {
        return tagKey.size
    }


}