package com.wisnua.starterproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wisnua.starterproject.databinding.ContentUserBinding
import com.wisnua.starterproject.domain.model.UserItem

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val userList = mutableListOf<UserItem>()

    fun submitList(users: List<UserItem>) {
        userList.clear()
        userList.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ContentUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserItem) {
            binding.usernameTextView.text = user.login
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.avatarImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ContentUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size
}
