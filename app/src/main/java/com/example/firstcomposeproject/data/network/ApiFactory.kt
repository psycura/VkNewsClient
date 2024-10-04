package com.example.firstcomposeproject.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor

object ApiFactory {
    val client = HttpClient(OkHttp) {
        expectSuccess = true
        engine {
            config {
                followRedirects(true)
            }
            addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
//        defaultRequest {
//            url("https://api.vk.com/method")
//        }
        install(ContentNegotiation) {
            json(
                json = Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }

}
