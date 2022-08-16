package com.example.kotlin_practice.functional5

class PerformanceMain

fun main() {
    val bigIntList = (1..10000000).toList()

    var start = System.currentTimeMillis()
    imperativeWay(bigIntList)
    println("${System.currentTimeMillis() - start} ms") // 1ms

    start = System.currentTimeMillis()
    functionalWay(bigIntList)
    println("${System.currentTimeMillis() - start} ms") // 157ms

    start = System.currentTimeMillis()
    realFunctionalWay(bigIntList)
    println("${System.currentTimeMillis() - start} ms") // 5ms
}
