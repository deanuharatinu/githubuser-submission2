package com.deanu.githubuser.following.usecase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deanu.githubuser.common.domain.model.User
import com.deanu.githubuser.databinding.ItemUserBinding

class AdapterFollowingList(private val followingList: List<User>) :
    RecyclerView.Adapter<AdapterFollowingList.ListViewHolder>() {

    class ListViewHolder(binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvUsername = binding.tvUsername
        val tvGithubUrl = binding.tvGitUrl
        val ivAvatar = binding.ivAvatar
        val itemUserBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (username, avatarUrl, githubUrl) = followingList[position]
        holder.tvUsername.text = username
        holder.tvGithubUrl.text = githubUrl
        Glide.with(holder.itemUserBinding.root)
            .load(avatarUrl)
            .circleCrop()
            .into(holder.ivAvatar)
    }

    override fun getItemCount(): Int = followingList.size
}