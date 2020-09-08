package com.example.sampleapp.presentations.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.sampleapp.R
import com.example.sampleapp.common.utils.*
import com.example.sampleapp.presentations.user.UserActivity
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val detailViewModel by viewModel<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        toolbar_title.text = Prefs.username

        detailViewModel.data.observe(this, Observer { item ->
            setupImage(item.avatarUrl ?: "")
            txv_followers.text = ("Followers: ${item.followers}")
            txv_following.text = ("Following: ${item.following}")
            txv_name.text = ("Name: ${item.username}")
            txv_company.text = ("Company: ${item.company}")
            txv_blog.text = ("Blog: ${item.blog}")
            edt_notes.setText(item.note)
        })

        detailViewModel.loadingState.observe(this, Observer {
            when (it.status) {
                LoadingState.Status.FAILED ->  Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                LoadingState.Status.RUNNING -> Toast.makeText(baseContext, "Loading", Toast.LENGTH_SHORT).show()
                LoadingState.Status.SUCCESS -> Toast.makeText(baseContext, "Success", Toast.LENGTH_SHORT).show()
            }
        })

        btn_save.setOnClickListener {
            Toast.makeText(baseContext, "Saved note!", Toast.LENGTH_SHORT).show()
            detailViewModel.saveNote(edt_notes.text())
        }
    }

    private fun setupImage(item: String) {

        GlideApp.with(this)
            .load(item)
            .apply(
                RequestOptions().override(Target.SIZE_ORIGINAL, 600).diskCacheStrategy(
                    DiskCacheStrategy.RESOURCE).timeout(10000).fitCenter())
            .error(R.drawable.ic_default_user)
            .into(img_avatar)
    }
}