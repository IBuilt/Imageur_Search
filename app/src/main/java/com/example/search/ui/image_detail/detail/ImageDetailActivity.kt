package com.example.search.ui.image_detail.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lib.utils.hideSoftKeyboard
import com.example.lib.utils.loadUrl
import com.example.lib.utils.textAsString
import com.example.lib.utils.whenNotNull
import com.example.search.R
import com.example.search.constants.Constant
import com.example.search.ui.load_state.LoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_image_detail.*
import kotlinx.android.synthetic.main.layout_app_bar_short.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


@AndroidEntryPoint
@ExperimentalPagingApi
class ImageDetailActivity : AppCompatActivity() {


    private val imageVm: ImageDetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        this.setContentView(R.layout.activity_image_detail)


        doFindAndApplyDataInBundle()


        initUi()


        handleOnChange()


        initAndObserverSearch()

    }


    private fun initUi() {


        setSupportActionBar(topAppBar)


        enableBackButtonOnToolbar()


        rvComment.apply {


            this.setHasFixedSize(true)


            this.adapter = imageVm.commentAdapter


            this.layoutManager = LinearLayoutManager(context)


            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            decoration.setDrawable(getDrawable(R.drawable.recycler_divider)!!)


            this.addItemDecoration(decoration)

        }


        initAdapter()
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)


        topAppBar.title = imageVm.imageTitle


        imageView.loadUrl(imageVm.imageUrl)

    }


    private fun doFindAndApplyDataInBundle() {

        whenNotNull(intent.extras) { nsBundle ->

            imageVm.imageId = nsBundle.getString(Constant.BundleParam.IMAGE_ID, "")

            imageVm.imageUrl = nsBundle.getString(Constant.BundleParam.IMAGE_URL, "")

            imageVm.imageTitle = nsBundle.getString(Constant.BundleParam.IMAGE_TITLE, "")
        }
    }


    private fun initAndObserverSearch() {


        observeAdapter()


        initSearchSettings()
    }


    private fun handleOnChange() {

        btnRetry.setOnClickListener { imageVm.commentAdapter.retry() }


        btnAddComment.setOnClickListener {
            doHandleAddComment()
        }
    }


    private fun doHandleAddComment() {

        imageVm.insertComment(etComment.textAsString())

        etComment.setText("")

        hideSoftKeyboard()
    }


    private fun initAdapter() {


        rvComment.adapter = imageVm.commentAdapter.withLoadStateHeaderAndFooter(

            header = LoadStateAdapter { imageVm.commentAdapter.retry() },

            footer = LoadStateAdapter { imageVm.commentAdapter.retry() }
        )


        imageVm.commentAdapter.addLoadStateListener { loadState ->


            // Only show the list if refresh succeeds.
            rvComment.isVisible = loadState.source.refresh is LoadState.NotLoading


            // Show loading spinner during initial load or refresh.
            progressBar.isVisible = loadState.source.refresh is LoadState.Loading


            // Show the retry state if initial load or refresh fails.
            btnRetry.isVisible = loadState.source.refresh is LoadState.Error


            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error


            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    private fun initSearchSettings() {

        lifecycleScope.launch {

            imageVm.commentAdapter.loadStateFlow

                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }


                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }


                .collect { rvComment.scrollToPosition(0) }
        }
    }


    private fun observeAdapter() {

        lifecycleScope.launch {

            imageVm.comments(imageVm.imageId).collectLatest {

                imageVm.commentAdapter.submitData(it)
            }
        }
    }


    private fun enableBackButtonOnToolbar() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {

            onBackPressed()

            return true
        }


        return super.onOptionsItemSelected(item)
    }
}