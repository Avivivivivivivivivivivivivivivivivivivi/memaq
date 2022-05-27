package com.avi.memaq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MemaqApplication

fun main(args: Array<String>) {
  runApplication<MemaqApplication>(*args)
}
