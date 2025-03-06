package com.wisnua.starterproject.di.modules

import android.content.Context
import androidx.room.Room
import com.wisnua.starterproject.data.local.AppDatabase
import com.wisnua.starterproject.data.local.dao.UserDao
import com.wisnua.starterproject.data.remote.ApiService
import com.wisnua.starterproject.data.repository.UserRepositoryImpl
import com.wisnua.starterproject.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.github.com/"
    private const val API_KEY = "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"

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val apiKeyInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("apiKey", API_KEY)
                .build()

            val requestBuilder = originalRequest.newBuilder()
                .url(url)

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            // Add any other interceptors or configurations as needed
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "github_users.db"
        ).build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService, userDao: UserDao): UserRepository {
        return UserRepositoryImpl(apiService, userDao)
    }
}
