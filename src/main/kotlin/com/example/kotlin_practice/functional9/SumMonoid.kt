package com.example.kotlin_practice.functional9

class SumMonoid : Monoid<Int> {
    override fun mempty(): Int = 0

    override fun mappend(m1: Int, m2: Int): Int = m1 + m2
}
