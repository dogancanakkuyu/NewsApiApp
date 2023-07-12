package com.example.newsapiapp.di

import com.example.newsapiapp.api.NewsApiService
import com.example.newsapiapp.data.BookmarkRepositoryImpl
import com.example.newsapiapp.data.LoginRepositoryImpl
import com.example.newsapiapp.data.RegisterRepositoryImpl
import com.example.newsapiapp.data.ServiceRepositoryImpl
import com.example.newsapiapp.data.repo.BookmarkRepository
import com.example.newsapiapp.data.repo.LoginRepository
import com.example.newsapiapp.data.repo.RegisterRepository
import com.example.newsapiapp.data.repo.ServiceRepository
import com.example.newsapiapp.utils.Constant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    fun providesUserId(): String? {
        return Firebase.auth.uid
    }
    @Provides
    fun providesFirestore() : FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    fun providesRetrofitService(): NewsApiService {
        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(NewsApiService::class.java)
    }

    @Provides
    fun providesLoginRepository(impl: LoginRepositoryImpl): LoginRepository = impl

    @Provides
    fun providesServiceRepository(impl: ServiceRepositoryImpl): ServiceRepository = impl

    @Provides
    fun providesRegisterRepository(impl: RegisterRepositoryImpl): RegisterRepository = impl

    @Provides
    fun providesBookmarkRepository(impl : BookmarkRepositoryImpl) : BookmarkRepository = impl
}