package com.example.projectpenelitian.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectpenelitian.R
import com.example.projectpenelitian.api.response.BookmarkDataItem
import com.example.projectpenelitian.databinding.ItemBookmarksBinding
import com.example.projectpenelitian.ui.camera.ResultActivity2
import com.example.projectpenelitian.ui.miniklopedia.BookmarkFragment

class BookmarkAdapter(bookmarkFragment: BookmarkFragment) : ListAdapter<BookmarkDataItem, BookmarkAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemBookmarksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    class MyViewHolder(val binding: ItemBookmarksBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: BookmarkDataItem){

            val id = review.id.replace("\"", "")
            val namaTanaman = review.namaTanaman.replace("\"", "")
            val jenisPenyakit = review.jenisPenyakit.replace("\"", "")
            val imageUrl = review.imageUrl.replace("\"", "")

            binding.textView6.text = namaTanaman
            binding.textView7.text = jenisPenyakit

            Glide.with(binding.root)
                .load(imageUrl)
                .into(binding.imageView8)

            val imageId: ImageView = itemView.findViewById(R.id.imageView8)
            val judulId: TextView = itemView.findViewById(R.id.textView6)
            val jenisPenyakitId: TextView = itemView.findViewById(R.id.textView7)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ResultActivity2::class.java)
                intent.putExtra("extra_id", id)
                intent.putExtra("extra_image", imageUrl)
                intent.putExtra("extra_classifications", "${namaTanaman} - ${jenisPenyakit}")
                intent.putExtra("extra_status", "bookmark")

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(imageId, "img"),
                        Pair(judulId, "tanaman"),
                        Pair(jenisPenyakitId, "penyakit"),
                    )

                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookmarkDataItem>() {
            override fun areItemsTheSame(oldItem: BookmarkDataItem, newItem: BookmarkDataItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: BookmarkDataItem, newItem: BookmarkDataItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}