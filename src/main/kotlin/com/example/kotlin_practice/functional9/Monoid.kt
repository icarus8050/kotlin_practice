package com.example.kotlin_practice.functional9

import com.example.kotlin_practice.functional5.FunList
import com.example.kotlin_practice.functional5.foldRight

interface Monoid<T> {
    fun mempty(): T
    fun mappend(m1: T, m2: T): T
}

fun <T> Monoid<T>.mconcat(list: FunList<T>): T = list.foldRight(mempty(), ::mappend)
