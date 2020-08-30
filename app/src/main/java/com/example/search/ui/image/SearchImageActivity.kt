package com.example.search.ui.image

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.example.search.R
import com.example.search.constants.Constant
import com.example.lib.utils.ItemOffsetDecoration
import com.example.search.ui.load_state.LoadStateAdapter
import com.example.search.model.image.Image
import com.example.search.ui.image_detail.detail.ImageDetailActivity
import com.example.lib.utils.onEditAction
import com.example.lib.utils.onKeyPressed
import com.example.lib.utils.textAsString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_image.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class SearchImageActivity : AppCompatActivity() {


    private val imageVm: SearchImageViewModel by viewModels()


    private var searchJob: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_image)


        initUi()


        handleOnChange()


        initSearchSettings()


        search(resources.getString(R.string.search_title))

    }


    private fun initUi() {


        rvImage.apply {


            this.setHasFixedSize(true)


            this.adapter = imageVm.imageAdapter


            this.addItemDecoration(
                    ItemOffsetDecoration(
                            context,
                            R.dimen.recycler_spacing)
            )
        }


        initAdapter()
    }


    private fun handleOnChange() {


        etSearch.onEditAction(EditorInfo.IME_ACTION_GO) {
            search(etSearch.textAsString())
        }


        etSearch.onKeyPressed(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER) {
            search(etSearch.textAsString())
        }


        btnRetry.setOnClickListener { imageVm.imageAdapter.retry() }


        imageVm.imageAdapter.onClicked = fun(image: Image?) {
            showImageDetailActivity(image!!)
        }
    }


    private fun initAdapter() {


        rvImage.adapter = imageVm.imageAdapter.withLoadStateHeaderAndFooter(
                header = LoadStateAdapter { imageVm.imageAdapter.retry() },
                footer = LoadStateAdapter { imageVm.imageAdapter.retry() }
        )


        imageVm.imageAdapter.addLoadStateListener { loadState ->


            // Only show the list if refresh succeeds.
            rvImage.isVisible = loadState.source.refresh is LoadState.NotLoading


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

            imageVm.imageAdapter.loadStateFlow

                    .debounce(Constant.Debounce.TIMEOUT)


                    // Only emit when REFRESH LoadState for RemoteMediator changes.
                    .distinctUntilChangedBy { it.refresh }


                    // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                    .filter { it.refresh is LoadState.NotLoading }


                    .collect { rvImage.scrollToPosition(0) }
        }
    }


    private fun search(query: String) {


        if (query.trim().isEmpty())
            return


        searchJob?.cancel()


        searchJob = lifecycleScope.launch {


            delay(250L)


            imageVm.searchRepo(query).collectLatest {
                imageVm.imageAdapter.submitData(it)
            }
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }


    private fun showImageDetailActivity(image: Image) {

        val intent = Intent(this, ImageDetailActivity::class.java)

        val bundle = Bundle().apply {

            this.putString(Constant.BundleParam.IMAGE_ID, image.id)

            this.putString(Constant.BundleParam.IMAGE_URL, image.link)

            this.putString(Constant.BundleParam.IMAGE_TITLE, image.title)
        }

        intent.putExtras(bundle)

        this.startActivity(intent)
    }
}