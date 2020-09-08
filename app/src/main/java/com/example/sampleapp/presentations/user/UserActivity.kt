package com.example.sampleapp.presentations.user

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleapp.R
import com.example.sampleapp.common.models.entity.UsersData
import com.example.sampleapp.common.utils.*
import com.example.sampleapp.presentations.detail.DetailActivity
import com.example.sampleapp.presentations.user.adapter.UserAdapter
import com.pawegio.kandroid.e
import kotlinx.android.synthetic.main.activity_user.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class UserActivity : AppCompatActivity() {

    private val userViewModel by viewModel<UserViewModel>()
    private var userAdapter: UserAdapter? = null
    private var usersDataList: List<UsersData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        initMain()
    }

    private fun initMain() {

        edt_search.apply {
            onTextChanged {
                when {
                    it.isEmpty() -> usersDataList?.let { items -> setupAdapter(items) }
                    else -> searchFilter(it)
                }
            }
            setOnEditorActionListener { t_, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) { hideSoftKeyboard() }
                true
            }
        }

        fetch()

        sw_indicator.setOnRefreshListener { fetch() }
    }

    private fun fetch() {

        userViewModel.data.observe(this, Observer {
            usersDataList = it
            setupAdapter(it)
        })

        userViewModel.loadingState.observe(this, Observer {
            when (it.status) {
                LoadingState.Status.FAILED ->  {
                    skeletonLayout.showOriginal() // hide skeleton
                    sw_indicator.isRefreshing = false
                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                }
                LoadingState.Status.RUNNING -> {
                    skeletonLayout.showSkeleton()  // show skeleton
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
                LoadingState.Status.SUCCESS -> {
                    skeletonLayout.showOriginal() // hide skeleton
                    sw_indicator.isRefreshing = false
                    Prefs.isLoaded = true
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun setupAdapter(data: List<UsersData>) {

        this.userAdapter = UserAdapter(data) {
            openActivity(DetailActivity::class.java) {
                Prefs.username = it
                handler { edt_search.text.clear() }
            }
        }

        setupRecyclerView(
            recyclerView = rcv_users,
            layoutManager = LinearLayoutManager(this),
            viewAdapter = this.userAdapter
        )
    }

    private fun searchFilter(text: String) {

        val filteredList: ArrayList<UsersData> = ArrayList()
        usersDataList?.let { list ->
            for (item in list) {
                item.username?.let {
                    if (it.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                        filteredList.add(item)
                    }
                }
            }
            userAdapter?.updateList(filteredList)
        }
    }
}