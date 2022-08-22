package com.example.kotlin_practice.kotlin_7_1

data class Money(
    val value: Long,
) {
    companion object {
        fun of(value: Long): Money {
            return Money(value)
        }
    }

    operator fun plus(
        other: Money
    ): Money {
        return Money(value + other.value)
    }
}

fun main() {
    val sum = Money.of(5000) + Money.of(8000)
    println(sum.value) // 13_000
}
