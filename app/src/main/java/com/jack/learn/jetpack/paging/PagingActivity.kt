package com.jack.learn.jetpack.paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.jack.learn.databinding.ActivityPagingBinding
import kotlinx.coroutines.launch

class PagingActivity : AppCompatActivity() {

    private val mAdapter = RepositoryAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityPagingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewBinding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@PagingActivity)
            recyclerView.adapter = mAdapter
            lifecycleScope.launch {
                mainViewModel.getPagingData().collect { pagingData ->
                    // 此方法调用就会让Paging的分页功能开始执行
                    mAdapter.submitData(pagingData)
                }
            }
            mAdapter.addLoadStateListener {
                when (it.refresh) {
                    is LoadState.NotLoading -> {
                        progressBar.visibility = View.INVISIBLE
                        recyclerView.visibility = View.VISIBLE
                        refreshLayout.isRefreshing = false

                    }
                    is LoadState.Loading -> {
                        refreshLayout.isRefreshing = true
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.INVISIBLE
                    }
                    is LoadState.Error -> {
                        progressBar.visibility = View.INVISIBLE
                        refreshLayout.isRefreshing = false
                    }
                }
            }

            refreshLayout.setOnRefreshListener {
                recyclerView.swapAdapter(mAdapter,true)
                mAdapter.refresh()
            }

        }
    }
}