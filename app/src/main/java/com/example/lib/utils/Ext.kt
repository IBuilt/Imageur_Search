package com.example.lib.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatImageView
import com.example.search.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun Activity.hideSoftKeyboard() {

    val view = findViewById<View>(android.R.id.content)

    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}


fun AppCompatImageView.loadUrl(url: String) {


    val oldScaleType = this.scaleType

    this.scaleType = ImageView.ScaleType.CENTER

    Picasso.get()
        .load(url)
        .fit()
        .placeholder(R.drawable.baseline_photo_size_select_actual_black_24dp)
        .into(this, object : Callback {
            override fun onSuccess() {
                this@loadUrl.scaleType = oldScaleType
            }

            override fun onError(e: Exception?) {
                Log.e("Image", e.toString())
            }
        })
}


inline fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? {
    return input?.let(callback)
}


fun EditText.onEditAction(imeAction: Int, onAction: () -> Unit) {

    this.setOnEditorActionListener { _, actionId, _ ->

        if (actionId == imeAction) {

            onAction()

            return@setOnEditorActionListener true
        }


        return@setOnEditorActionListener false
    }
}


fun EditText.onKeyPressed(vararg events: Int, onAction: (action: Int) -> Unit) {

    this.setOnKeyListener { _, _, event ->


        if (events.contains(event.action)) {


            onAction(event.action)


            return@setOnKeyListener true
        }


        return@setOnKeyListener false
    }
}


fun ViewGroup.inflate(@LayoutRes resId: Int): View =
    LayoutInflater.from(this.context).inflate(resId, this, false)


fun EditText.textAsString() = this.text.toString()



