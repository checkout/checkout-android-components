package com.checkout.android.components.sample.core.di

import com.checkout.android.components.sample.core.Constants
import com.checkout.android.components.sample.core.api.PaymentSessionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

  @Singleton
  @Provides
  fun provideSandboxPaymentSessionApi(
    okHttpClient: OkHttpClient,
    factory: Converter.Factory,
  ): PaymentSessionApi = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(Constants.API_SANDBOX_URL)
    .addConverterFactory(factory)
    .build()
    .create(PaymentSessionApi::class.java)

  @Singleton
  @Provides
  fun providesOkhttpClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(
      HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
      },
    )
    .build()

  @Singleton
  @Provides
  fun providesJson(): Converter.Factory {
    val json = Json {
      encodeDefaults = true
      ignoreUnknownKeys = true
    }

    return json.asConverterFactory(Constants.CONTENT_TYPE_JSON.toMediaType())
  }
}
