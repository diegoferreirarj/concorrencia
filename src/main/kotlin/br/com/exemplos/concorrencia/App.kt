package br.com.exemplos.concorrencia

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis


fun main() {
    reactive()
    criandoCorrotinas()
    criandoThreadsJava()
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

    println("${time}ms para concluir ${contador / 2} tarefas usando Coroutines")
}

fun criandoThreadsJava() {
    val inicio = System.currentTimeMillis()
    val latch = CountDownLatch(100_000)
    var contador = AtomicInteger()

    for (i in 1..100_000) {
        thread(start = true) {
            contador.incrementAndGet()
            Thread.sleep(100)
            contador.incrementAndGet()
            latch.countDown()
        }
    }

    latch.await()

    println("${System.currentTimeMillis() - inicio}ms para concluir ${contador.get() / 2} tarefas usando Threads")
}

fun reactive() {
    Mono.just("10a")
            .map { it.toInt() }
            .onErrorReturn(30)
            .subscribe { println("Mono: $it") }

    Flux.range(0, 10)
            .filter { it % 2 == 0 }
            .map { it * 10 }
            .doAfterTerminate { println("Acabou") }
            .subscribe { println("Flux: $it") }
}
