package com.example.projectpenelitian.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectpenelitian.InformationDetailActivity
import com.example.projectpenelitian.KeyIntent
import com.example.projectpenelitian.databinding.ItemInfoBinding
import com.example.projectpenelitian.model.Information

class InformationAdapter(private val list: List<Information>) : RecyclerView.Adapter<InformationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InformationAdapter.ViewHolder {
        val binding = ItemInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InformationAdapter.ViewHolder, position: Int) {
        with(holder) {
            binding.infoImg.setImageResource(list[position].infoImg)
            binding.infoNameTxt.text = list[position].infoName

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, InformationDetailActivity::class.java)
                intent.putExtra(KeyIntent.INFORMATION_KEY,list[position])
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ItemInfoBinding): RecyclerView.ViewHolder(binding.root)
}