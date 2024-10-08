package com.example.firstcomposeproject.samples.lesson3

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

suspend fun main() {


    getFlowByBuilderFlow().filter { it.isPrime() }
        .map {
            println("MAP")
            "Number: $it"
        }
        .collect { println(it) }
}

fun getFlowByFlowBuilder(): Flow<Int> {
      return flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
}

fun getFlowByBuilderFlow(): Flow<Int> {
    return flow {
        for(i in 1..20) {
            emit(i)
        }
    }
}

suspend fun Int.isPrime(): Boolean {
    if (this <= 1) return false
    for (i in 2 until this) {
        delay(50)
        if (this % i == 0) return false
    }
    return true
}