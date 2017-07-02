package com.sothawo.kovasbak

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableKafka
class KovasbakApplication

fun main(args: Array<String>) {
    SpringApplication.run(KovasbakApplication::class.java, *args)
}
