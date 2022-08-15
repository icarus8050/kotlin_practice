package com.example.kotlin_practice.functional5

import com.example.kotlin_practice.functional5.FunList.*

class ZipWithMain

fun main() {
    val intList = Cons(1, Cons(2, Cons(3, Nil)))
    val intList2 = Cons(1, Cons(3, Cons(10, Nil)))
    val lowerCharList = Cons('a', Cons('b', Cons('c', Nil)))

    println(intList.zipWith({ x, y -> x + y }, intList2)) // 2, 5, 13
    println(intList.zipWith({ x, y -> if (x > y) x else y }, intList2)) // 1, 3, 10
    println(intList.zipWith({ x, y -> x to y }, lowerCharList)) // (1, a), (2, b), (3, c)
}