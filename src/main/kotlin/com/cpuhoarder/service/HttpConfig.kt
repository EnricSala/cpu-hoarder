package com.cpuhoarder.service

import com.cpuhoarder.service.arkintel.ArkIntelApi
import com.cpuhoarder.utils.loggerOf
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Configuration
class HttpConfig {

    @Bean
    fun okHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build()

    @Bean
    fun converterFactory(): JacksonConverterFactory =
            JacksonConverterFactory.create(jacksonObjectMapper())

    @Bean
    fun arkIntelApi(): ArkIntelApi =
            Retrofit.Builder()
                    .baseUrl(ARK_INTEL_URL)
                    .client(okHttpClient())
                    .addConverterFactory(converterFactory())
                    .build()
                    .create(ArkIntelApi::class.java)

    companion object {
        private val log = loggerOf(HttpConfig::class)
        private const val PASSMARK_URL = "https://www.cpubenchmark.net/"
        private const val ARK_INTEL_URL = "https://ark.intel.com/"
        private const val ALI_EXPRESS_URL = "https://es.aliexpress.com/"
    }

}
