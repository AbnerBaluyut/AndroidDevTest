package com.example.sampleapp.app.di

import android.app.Application
import androidx.room.Room
import com.example.sampleapp.common.Endpoints
import com.example.sampleapp.common.models.AppDatabase
import com.example.sampleapp.common.models.dao.UserDao
import com.example.sampleapp.presentations.detail.DetailRepository
import com.example.sampleapp.presentations.detail.DetailViewModel
import com.example.sampleapp.presentations.user.UserRepository
import com.example.sampleapp.presentations.user.UserViewModel
import com.example.sampleapp.services.ApiDataSource
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/* Implement retrofit module */
val apiModule = module {

    fun provideUserApi(retrofit: Retrofit): ApiDataSource {
        return retrofit.create(ApiDataSource::class.java)
    }

    single { provideUserApi(get()) }
}

/* Implement web service  */
val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }


    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Endpoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
}

/* Implement room database */
val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "eds.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideUserDao(get()) }
}

/* Implement user repository */
val repositoryModule = module {
    fun provideUserRepository(api: ApiDataSource, dao: UserDao): UserRepository {
        return UserRepository(api, dao)
    }

    fun provideDetailRepository(api: ApiDataSource, dao: UserDao): DetailRepository {
        return DetailRepository(api, dao)
    }

    single { provideUserRepository(get(), get()) }
    factory { provideDetailRepository(get(), get()) }}

/* Implement user view model module */
val viewModelModule = module {
    viewModel { UserViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
