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
        other: Money,
    ): Money {
        return Money(value + other.value)
    }

    operator fun minus(other: Money) = Money(value - other.value)
    operator fun times(other: Money) = Money(value * other.value)
    operator fun div(other: Money) = Money(value / other.value)
    operator fun rem(other: Money) = Money(value % other.value)

    operator fun plus(other: Long) = Money(value + other)
}

fun main() {
    println(Money.of(100) + Money.of(50)) // 150
    println(Money.of(5000) - Money.of(3000)) // 2_000
    println(Money.of(10) * Money.of(20)) // 200
    println(Money.of(500) / Money.of(10)) // 50
    println(Money.of(5) % Money.of(3)) // 2

    println(Money.of(100) + 500) // 600
}
