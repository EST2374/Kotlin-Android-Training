package com.example.coroutinestraining

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

suspend fun ladeDaten(): String {
    delay(1000)
    return "Daten"
}

// Flows
fun zahlenFlow(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(500)
        emit(i) // sendet an den Sammler
    }
}

fun temperaturFlow(): Flow<Int> = flow {
    val temperaturen = listOf<Int>(18,21,19,25,30,17)
    for (t in temperaturen) {
        delay(400)
        emit(t)
    }
}

fun sensorFlow(): Flow<Int> = flow {
    var wert = 0
    while (true) {
        delay(300)
        wert += 5
       emit(wert)
    }
}

fun main() = runBlocking {
    val ergebnis = ladeDaten()
    println(ergebnis)

    val zeit = measureTimeMillis {


        val job: Job = launch {
            delay(1000)
            println("Launch fertig")
        }

        val deferred: Deferred<String> = async {
            delay(1000)
            "async Ergebnis"
        }

        val deferred2: Deferred<String> = async {
            delay(1000)
            "async Ergebnis 2"
        }

        val job2 = launch {
            repeat(5) { i ->
                println("Arbeite... $i")
                delay(300)
            }
        }
        delay(700)
        job.cancel()
        println("Job abgebrochen")

        job.join()
        println(deferred.await())
        println(deferred2.await())

        zahlenFlow().collect { wert ->
            println("Emfangen: $wert")
        }

        temperaturFlow().collect { wert ->
            if (wert <= 20) {
                println("Normal: $wert")
            } else {
                println("Warm: $wert")
            }
        }

        temperaturFlow().filter { it > 20 }.collect { wert ->
            println("Warm $wert")
        }

        val sensorJob = launch {
            sensorFlow().collect { wert ->
                println(wert)
            }
        }
        delay(1500)
        sensorJob.cancel()
        println("Sensor gestoppt")

        // Combine
        val nameFlow = flow {
            emit("Alice")
            delay(200)
            emit("Bob")
        }

        val ageFlow = flow {
            delay(100)
            emit(25)
            delay(300)
            emit(30)
        }

        nameFlow.combine(ageFlow) { name, age ->
            "$name is $age years old"
        }.collect { println(it) }

    }
    println("Dauer: $zeit ms")

}