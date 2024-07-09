package com.dicoding.aplikasigithubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.aplikasigithubuser.data.local.UserFavorite
import com.dicoding.aplikasigithubuser.databinding.ListUserBinding
import com.dicoding.aplikasigithubuser.ui.DetailUserActivity

class FavoriteAdapter(private val onItemClick: (UserFavorite) -> Unit) :
    ListAdapter<UserFavorite, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteUser = getItem(position)
        holder.bind(favoriteUser)
        holder.itemView.setOnClickListener {
            onItemClick(favoriteUser)
        }
    }

    inner class FavoriteViewHolder(private val binding: ListUserBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        @Suppress("DEPRECATION")
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val favoriteUser = getItem(position)
                val intent = Intent(v?.context, DetailUserActivity::class.java)
                intent.putExtra("username", favoriteUser.username)
                intent.putExtra("avatarUrl", favoriteUser.avatarUrl)
                v?.context?.startActivity(intent)
            }
        }

        fun bind(favoriteUser: UserFavorite) {
            binding.tvUsername.text = favoriteUser.username
            Glide.with(binding.root)
                .load(favoriteUser.avatarUrl)
                .into(binding.image)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserFavorite>() {
            override fun areItemsTheSame(oldItem: UserFavorite, newItem: UserFavorite): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserFavorite, newItem: UserFavorite): Boolean {
                return oldItem == newItem
            }
        }
    }
}