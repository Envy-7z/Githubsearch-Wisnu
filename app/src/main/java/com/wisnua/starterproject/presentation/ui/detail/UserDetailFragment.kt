package com.wisnua.starterproject.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.wisnua.starterproject.databinding.FragmentUserDetailBinding
import com.wisnua.starterproject.presentation.adapter.RepositoryAdapter
import com.wisnua.starterproject.presentation.viewModel.UserDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserDetailViewModel by viewModels()
    private val args: UserDetailFragmentArgs by navArgs()
    private lateinit var adapter: RepositoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        val username = args.username
        viewModel.getUserDetail(username)
        viewModel.getUserRepos(username)
    }

    private fun setupRecyclerView() {
        adapter = RepositoryAdapter()
        binding.rvRepo.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@UserDetailFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.userDetail.collect { user ->
                    user?.let {
                        binding.tvDetailName.text = it.name ?: "No Name"
                        binding.tvDetailLogin.text = "@${it.login}"
                        binding.tvDetailDesc.text = it.bio ?: "No Bio Available"
                        binding.tvFollowers.text = "${it.followers} Followers"
                        binding.tvFollowing.text = "${it.following} Following"
                        binding.tvDetailCountry.text = it.location ?: "Unknown"
                        binding.tvDetailEmail.text = it.email ?: "No Email"

                        Glide.with(this@UserDetailFragment)
                            .load(it.avatarUrl)
                            .into(binding.ivDetailName)
                    }
                }
            }

            launch {
                viewModel.repositories.collect { repos ->
                    adapter.submitList(repos)
                }
            }

            launch {
                viewModel.isLoading.collect { isLoading ->
                    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                }
            }

            launch {
                viewModel.errorMessage.collect { error ->
                    error?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
