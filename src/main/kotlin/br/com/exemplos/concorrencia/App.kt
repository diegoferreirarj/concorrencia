package br.com.exemplos.concorrencia

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

fun main() {
    criandoCorrotinas()
    criandoThreads()
}

fun criandoCorrotinas() {
    var contador = 0

    val time = measureTimeMillis {
        runBlocking {
            for (i in 1..100_000) {
                launch {
                    contador++
                    delay(100)
                    contador++
                }
            }
        }
    }

    println("$time milisegundos para concluir ${contador / 2} tarefas")
}

fun criandoThreads() {
    val inicio = System.currentTimeMillis()
    val latch = CountDownLatch(100)
    var contador = 0

    for (i in 1..100) {
        thread(start = true) {
            synchronized(latch) {
                contador++
                Thread.sleep(100)
                contador++
                latch.countDown()
            }
        }
    }

    latch.await()

    println("${System.currentTimeMillis() - inicio} milisegundos para concluir ${contador / 2} tarefas")
}
