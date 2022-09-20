package com.example.kotlin_practice.functional9

fun <A> BinaryTree<A>.contains(value: A) = foldMap({ it == value}, AnyMonoid())

fun main() {
    val tree = Node("a",
    Node("b",
        Node("c"), Node("d")),
    Node("e",
        Node("f"), Node("g")))

    println(tree.contains("c")) // true
    println(tree.contains("z")) // false
}

class AnyMonoid : Monoid<Boolean> {
    override fun mempty(): Boolean = false
    override fun mappend(m1: Boolean, m2: Boolean): Boolean = m1 || m2
}
