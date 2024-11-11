package com.example.pet_shop.di

import androidx.room.Room
import com.example.pet_shop.data.api.Api
import com.example.pet_shop.data.api.BASE_URL
import com.example.pet_shop.data.local.Dao
import com.example.pet_shop.data.local.Database
import com.example.pet_shop.data.local.TypeConverter
import com.example.pet_shop.data.repository.RepositoryImpl
import com.example.pet_shop.domain.repository.Repository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(Api::class.java) }

    single {
        Room.databaseBuilder(
            get(),
            Database::class.java,
            "app_database"
        ).addTypeConverter(TypeConverter())
            .build()
    }

    single<Dao> { get<Database>().dao() }

    single<Repository> { RepositoryImpl(get()) }

}