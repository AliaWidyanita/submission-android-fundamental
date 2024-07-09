package com.dicoding.aplikasigithubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.aplikasigithubuser.R
import com.dicoding.aplikasigithubuser.data.FollowingFollower
import com.dicoding.aplikasigithubuser.databinding.ListUserBinding

class UserAdapter(private val onItemClick: (FollowingFollower) -> Unit) : ListAdapter<FollowingFollower, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FollowingFollower){
            binding.apply {
                tvUsername.text = user.login

                Glide.with(root)
                    .load(user.avatarUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.ic_person)
                    .into(image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onItemClick(user)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowingFollower>() {
            override fun areItemsTheSame(oldItem: FollowingFollower, newItem: FollowingFollower): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: FollowingFollower, newItem: FollowingFollower): Boolean {
                return oldItem == newItem
            }
        }
    }
}