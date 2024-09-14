package com.example.jetareader.di

import com.example.jetareader.network.BookApi
import com.example.jetareader.repository.BookRepository
import com.example.jetareader.repository.FireRepository
import com.example.jetareader.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFireRepository() = FireRepository(
        query = FirebaseFirestore.getInstance().collection("books")
    )

    @Singleton
    @Provides
    fun provideBookRepository(bookApi: BookApi) = BookRepository(bookApi)

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Singleton
    @Provides
    fun provideBookApi():BookApi
    {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(BookApi::class.java)
    }
}