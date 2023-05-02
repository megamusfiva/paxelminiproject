package com.example.paxelminiproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paxelminiproject.adapter.MainAdapter
import com.example.paxelminiproject.databinding.ActivityMainBinding
import com.example.paxelminiproject.extension.exhaustive
import com.example.paxelminiproject.utils.Status
import com.example.paxelminiproject.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModel()
        setupUserInput()
        binding.swipeContainer.setOnRefreshListener {
            val searchText = binding.edSearch.text.toString().trim()
            viewModel.search(searchText)
            if (binding.swipeContainer.isRefreshing) {
                binding.swipeContainer.isRefreshing = false
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.htmlUrl))
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter
    }
    private fun setupViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.queryResultFlow.collect { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            binding.pbMain.visibility = View.VISIBLE
                            adapter.updateList(resource.data?.items)
                        }
                        Status.SUCCESS -> {
                            binding.pbMain.visibility = View.GONE
                            adapter.updateList(resource.data?.items)
                        }
                        else -> {
                            binding.pbMain.visibility = View.GONE
                            resource.message?.let { msg ->
                                Snackbar.make(binding.pbMain, msg, Snackbar.LENGTH_LONG).show()
                            }
                        }
                    }.exhaustive
                }
            }
        }
    }

    private fun setupUserInput() {
        binding.btnSearch.setOnClickListener {
            val searchText = binding.edSearch.text.toString().trim()
            hideKeyboard(it)
            viewModel.search(searchText)
        }
    }

    private fun hideKeyboard(view: View?) {
        if (view !is EditText) {
            val inputMethodManager: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}
