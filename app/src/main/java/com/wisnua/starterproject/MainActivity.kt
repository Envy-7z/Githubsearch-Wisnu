package com.wisnua.starterproject

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.wisnua.starterproject.databinding.ActivityMainBinding
import com.wisnua.starterproject.presentation.adapter.UserAdapter
import com.wisnua.starterproject.presentation.viewModel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            if (query.isNotEmpty()) {
                viewModel.searchUsers(query)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.searchResults.collectLatest { users ->
                adapter.submitList(users)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isLoading.collectLatest { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.errorMessage.collectLatest { error ->
                error?.let {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
