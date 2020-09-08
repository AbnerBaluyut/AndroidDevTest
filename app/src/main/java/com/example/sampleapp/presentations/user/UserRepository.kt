package com.example.sampleapp.presentations.user

import com.example.sampleapp.common.models.dao.UserDao
import com.example.sampleapp.common.utils.Prefs
import com.example.sampleapp.services.ApiDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val apiDataSource: ApiDataSource, private val userDao: UserDao) {

    val data = userDao.findAll()

    suspend fun refresh() {

        withContext(Dispatchers.IO) {
            val users = apiDataSource.getAllUsersAsync().await()

            if (Prefs.isLoaded) {

                users.map {
                    userDao.updateData(
                        username = it.username ?: "",
                        avatarUrl = it.avatarUrl ?: "",
                        company = it.company ?: "",
                        blog = it.blog ?: "",
                        followers = it.followers ?: "",
                        following = it.following ?: "",
                        note = userDao.getNote(it.username ?: ""))
                }

            } else {
                userDao.add(users)
            }
        }
    }
}