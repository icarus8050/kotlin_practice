package com.example.kotlin_practice.functional5

class PerformanceFunStreamMain

fun main() {
    val bigIntList = (1..10000000).toFunList()
    var start = System.currentTimeMillis()
    funListWay(bigIntList)
    println("${System.currentTimeMillis() - start} ms") // 5430 ms

    val bigIntStream = (1..10000000).toFunStream()
    start = System.currentTimeMillis()
    funStreamWay(bigIntStream)
    println("${System.currentTimeMillis() - start} ms") // 4 ms
}

fun funListWay(intList: FunList<Int>): Int =
    intList.map { n -> n * n }
        .filter { n -> n < 1000000 }
        .map { n -> n - 2 }
        .filter { n -> n < 1000 }
        .map { n -> n * 10 }
        .getHead()

fun funStreamWay(intList: FunStream<Int>): Int =
    intList.map { n -> n * n }
        .filter { n -> n < 1000000 }
        .map { n -> n - 2 }
        .filter { n -> n < 1000 }
        .map { n -> n * 10 }
        .getHead()
