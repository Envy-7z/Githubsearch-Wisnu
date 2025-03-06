package com.wisnua.starterproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wisnua.starterproject.databinding.ContentRepoBinding
import com.wisnua.starterproject.domain.model.RepoResponseItem

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    private val repoList = mutableListOf<RepoResponseItem>()

    fun submitList(newList: List<RepoResponseItem>) {
        repoList.clear()
        repoList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = ContentRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repoList[position])
    }

    override fun getItemCount(): Int = repoList.size

    class RepositoryViewHolder(private val binding: ContentRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: RepoResponseItem) {
            binding.tvRepoName.text = repo.name
            binding.tvRepoDesc.text = repo.description ?: "No Description"
            binding.tvRepoStar.text = repo.stargazersCount.toString()
            binding.tvRepoUpdate.text = "Updated ${repo.updatedAt}"
        }
    }
}
