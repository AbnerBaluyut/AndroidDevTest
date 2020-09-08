package com.example.sampleapp.common.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class UsersData(
    @PrimaryKey val id: Long,
    @SerializedName("login")        val username: String?,
    @SerializedName("avatar_url")   val avatarUrl: String?,
    val company: String?,
    val blog: String?,
    val followers: String?,
    val following: String?,
    val note: String?
)