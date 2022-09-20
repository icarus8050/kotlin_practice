package com.example.kotlin_practice.functional9

import com.example.kotlin_practice.functional5.FunList
import com.example.kotlin_practice.functional5.concat
import com.example.kotlin_practice.functional5.funListOf
import com.example.kotlin_practice.functional5.printFunList

class FunListMonoid<T> : Monoid<FunList<T>> {
    override fun mempty(): FunList<T> = FunList.Nil

    override fun mappend(m1: FunList<T>, m2: FunList<T>): FunList<T> = when (m1) {
        FunList.Nil -> m2
        is FunList.Cons -> m1 concat m2
    }
}

fun <A> BinaryTree<A>.toFunList(): FunList<A> = foldMap({ funListOf(it) }, FunListMonoid())

fun main() {
    val tree = Node("a",
    Node("b",
        Node("c"), Node("d")),
    Node("e",
        Node("f"), Node("g")))
    printFunList(tree.toFunList()) // c, b, d, a, f, e, g
}