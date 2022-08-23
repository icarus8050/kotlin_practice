package com.example.kotlin_practice.kotlin_7_2

data class Foo(
    val a: Int,
    val b: Int,
) : Comparable<Foo> {
    override fun compareTo(other: Foo): Int {
        return compareValuesBy(this, other, Foo::a, Foo::b)
    }
}

fun main() {
    val foo1 = Foo(1, 3)
    val foo2 = Foo(1, 4)
    println(foo1 > foo2) // false
    println(foo1 < foo2) // true
}
