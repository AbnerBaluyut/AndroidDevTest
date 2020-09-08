package com.example.sampleapp.presentations.user.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.sampleapp.R
import com.example.sampleapp.common.models.entity.UsersData
import com.example.sampleapp.common.utils.GlideApp
import com.example.sampleapp.common.utils.inflate
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private var items: List<UsersData>,
                  private val itemClick: (String) -> Unit) :
    RecyclerView.Adapter<UserAdapter.AcceptedViewHolder>() {

    override fun getItemCount(): Int
            = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcceptedViewHolder
            = AcceptedViewHolder(parent.inflate(R.layout.item_user, false))

    override fun onBindViewHolder(holder: AcceptedViewHolder, position: Int)
            = holder.bind(items[position])

    inner class AcceptedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: UsersData) {
            with(user) {
                itemView.apply {

                    txv_user.text = user.username

                    cl_user.setOnClickListener {
                        itemClick(user.username ?: "")
                    }

                    img_note.apply {
                        visibility = if (note.isNullOrEmpty()) {
                            View.GONE
                        } else {
                            View.VISIBLE
                        }
                    }

                    GlideApp.with(this)
                        .load(user.avatarUrl)
                        .apply(
                            RequestOptions().override(Target.SIZE_ORIGINAL, 600).diskCacheStrategy(
                                DiskCacheStrategy.RESOURCE).timeout(10000).fitCenter())
                        .error(R.drawable.ic_default_user)
                        .into(img_url)
                }
            }
        }
    }

    fun updateList(list: List<UsersData>) {
        items = list
        notifyDataSetChanged()
    }
}