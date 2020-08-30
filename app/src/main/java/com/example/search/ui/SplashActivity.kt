package com.example.search.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.example.search.R
import com.example.search.ui.image.SearchImageActivity

@ExperimentalPagingApi
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_splash)

        showSearchImageActivityWithDelay()
    }


    private fun showSearchImageActivityWithDelay() {

        Handler().postDelayed({
            showSearchImageActivity()
        }, 300)
    }


    private fun showSearchImageActivity() {

        this.startActivity(Intent(this, SearchImageActivity::class.java))

        this.finish()
    }
}