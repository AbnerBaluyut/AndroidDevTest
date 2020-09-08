package com.example.sampleapp.common.models.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampleapp.common.models.entity.UsersData
import com.example.sampleapp.common.utils.Prefs

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun findAll(): LiveData<List<UsersData>>

    @Query("SELECT * FROM users WHERE username=:username")
    fun getUsername(username: String): LiveData<UsersData>

    @Query("SELECT note FROM users WHERE username=:username")
    fun getNote(username: String): String

    @Query("UPDATE users SET company = :company WHERE username=:username")
    fun updateCompany(username: String = Prefs.username ?: "", company: String)

    @Query("UPDATE users SET blog = :blog WHERE username=:username")
    fun updateBlog(username: String = Prefs.username ?: "", blog: String)

    @Query("UPDATE users SET followers = :followers WHERE username=:username")
    fun updateFollowers(username: String = Prefs.username ?: "", followers: String)

    @Query("UPDATE users SET following = :following WHERE username=:username")
    fun updateFollowing(username: String = Prefs.username ?: "", following: String)

    @Query("UPDATE users SET note = :note WHERE username=:username")
    fun updateNote(username: String = Prefs.username ?: "", note: String)

    @Query("UPDATE users SET avatarUrl=:avatarUrl, company=:company, blog=:blog, followers=:followers, following=:following, note=:note WHERE username=:username")
    fun updateData(
        username: String,
        avatarUrl: String,
        company: String,
        blog: String,
        followers: String,
        following: String,
        note: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(users: List<UsersData>)
}