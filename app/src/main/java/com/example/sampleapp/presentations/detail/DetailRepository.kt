package com.example.sampleapp.presentations.detail

import com.example.sampleapp.common.models.dao.UserDao
import com.example.sampleapp.common.utils.Prefs
import com.example.sampleapp.services.ApiDataSource
import com.pawegio.kandroid.e
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailRepository(private val apiDataSource: ApiDataSource, private val userDao: UserDao) {

    private val username = Prefs.username ?: ""

    fun getUsername() = userDao.getUsername(username)

    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val detail = apiDataSource.getDetailAsync(username).await()
            detail.apply {
                company?.let { userDao.updateCompany(company = it) }
                blog?.let { userDao.updateBlog(blog = it) }
                followers?.let { userDao.updateFollowers(followers = it) }
                following?.let { userDao.updateFollowing(following = it) }
            }
        }
    }

    fun saveNote(note: String) {
        userDao.updateNote(note = note)
    }
}