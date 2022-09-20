package com.example.kotlin_practice.functional9

import com.example.kotlin_practice.functional5.funListOf

fun main() {
    println(ProductMonoid().mconcat(funListOf(1, 2, 3, 4, 5))) // 120 (1 * 2 * 3 * 4 * 5)
    println(SumMonoid().mconcat(funListOf(1, 2, 3, 4, 5))) // 15 (1 + 2 + 3 + 4 + 5)
}
