package com.example.sampleapp.common.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.pawegio.kandroid.e
import com.pawegio.kandroid.inflateLayout
import kotlinx.android.synthetic.main.activity_detail.*

/** View Group **/
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, root: Boolean = false): View {
    return context.inflateLayout(layoutRes, this, root)
}

/** Setup Recycler View **/
fun setupRecyclerView(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager, viewAdapter: RecyclerView.Adapter<*>?) {
    with(recyclerView) {
        this.layoutManager = layoutManager
        adapter = viewAdapter
    }
}

/** On text changed **/
fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onTextChanged.invoke(p0.toString().sanitized().trim())
        }

        override fun afterTextChanged(editable: Editable?) { }
    })
}

/** Sanitized **/
fun String.sanitized(): String {
    return this
        .replace("\\ \\ +".toRegex(), " ")
        .replace("\n\n\n+".toRegex(), "\n\n")
        .trim()
}

/** Get text **/
fun EditText.text() : String {
    return this.text.toString().trim()
}

/** Hide keyboard **/
fun Activity.hideSoftKeyboard() {
    val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
}

/** Open activity **/
inline fun <T> Activity.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun handler(action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({
        action()
    }, 100)
}

