package com.caique.aetnatestflickr.di

import android.content.Context
import android.content.SharedPreferences
import com.caique.aetnatestflickr.data.network.FlickrApi
import com.caique.aetnatestflickr.data.domain.FlickrRepository
import com.caique.aetnatestflickr.data.domain.RecentSearchRepository
import com.caique.aetnatestflickr.feature.list.domain.interactor.AddRecentSearchUseCase
import com.caique.aetnatestflickr.feature.list.domain.interactor.GetPhotosUseCase
import com.caique.aetnatestflickr.feature.list.domain.interactor.GetRecentSearchesUseCase
import com.caique.aetnatestflickr.feature.list.domain.repository.RecentSearchRepositoryImpl
import com.caique.aetnatestflickr.feature.list.presentation.ListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideRetrofit() }
    single { provideFlickrApi(get()) }
    single { provideSharedPreferences(androidContext()) }
    single { FlickrRepository(get()) }
    single<RecentSearchRepository> {
        RecentSearchRepositoryImpl(get())
    }
    single { AddRecentSearchUseCase(get()) }
    single { GetPhotosUseCase(get()) }
    single { GetRecentSearchesUseCase(get()) }

    viewModel { ListViewModel(get(), get(), get()) }
}

private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.flickr.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideFlickrApi(retrofit: Retrofit): FlickrApi {
    return retrofit.create(FlickrApi::class.java)
}

private fun provideSharedPreferences(context: Context): SharedPreferences? {
    return context.getSharedPreferences("flickr_challenge_preferences",
        Context.MODE_PRIVATE)
}
