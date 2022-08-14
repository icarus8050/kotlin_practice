package com.example.kotlin_practice.functional5

import com.example.kotlin_practice.functional5.FunList.Cons
import com.example.kotlin_practice.functional5.FunList.Nil

sealed class FunList<out T> {
    object Nil : FunList<Nothing>()

    data class Cons<out T>(
        val head: T,
        val tail: FunList<T>
    ) : FunList<T>()
}

fun <T> FunList<T>.addHead(head: T): FunList<T> = Cons(head, this)

fun <T> FunList<T>.appendTail(value: T): FunList<T> = when (this) {
    is Nil -> Cons(value, Nil)
    is Cons -> Cons(head, tail.appendTail(value))
}

tailrec fun <T> FunList<T>.appendTailByTailRec(
    value: T,
    acc: FunList<T> = Nil
): FunList<T> = when (this) {
    is Nil -> Cons(value, acc).reverse()
    is Cons -> tail.appendTailByTailRec(value, acc.addHead(head))
}

tailrec fun <T> FunList<T>.reverse(
    acc: FunList<T> = Nil
): FunList<T> = when (this) {
    is Nil -> acc
    is Cons -> tail.reverse(acc.addHead(head))
}

fun <T> FunList<T>.getHead(): T = when (this) {
    is Nil -> throw NoSuchElementException()
    is Cons -> head
}

fun <T> FunList<T>.getTail(): FunList<T> = when (this) {
    is Nil -> throw NoSuchElementException()
    is Cons -> tail
}

fun imperativeFilter(numList: List<Int>): List<Int> {
    val newList = mutableListOf<Int>()
    for (num in numList) {
        if (num % 2 == 0) {
            newList.add(num)
        }
    }
    return newList
}

fun functionalFilter(numList: List<Int>): List<Int> = numList.filter { it % 2 == 0 }

tailrec fun <T> FunList<T>.filter(acc: FunList<T> = Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    is Nil -> acc.reverse()
    is Cons -> if (p(head)) {
        tail.filter(acc.addHead(head), p)
    } else {
        tail.filter(acc, p)
    }
}

tailrec fun <T> FunList<T>.drop(n: Int): FunList<T> = when {
    n < 0 -> throw IllegalArgumentException()
    n == 0 || this is Nil -> this
    else -> getTail().drop(n - 1)
}

tailrec fun <T> FunList<T>.dropWhile(p: (T) -> Boolean): FunList<T> = when (this) {
    is Nil -> this
    is Cons -> if (p(head)) {
        this
    } else {
        tail.dropWhile(p)
    }
}

tailrec fun <T> FunList<T>.take(n: Int, acc: FunList<T> = Nil): FunList<T> = when {
    n < 0 -> throw IllegalArgumentException()
    n == 0 || this is Nil -> acc.reverse()
    else -> getTail().take(n - 1, acc.addHead(getHead()))
}

tailrec fun <T> FunList<T>.takeWhile(acc: FunList<T> = Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    is Nil -> acc.reverse()
    is Cons -> if (p(head)) {
        tail.takeWhile(acc.addHead(head), p)
    } else {
        acc.reverse()
    }
}

tailrec fun <T, R> FunList<T>.map(acc: FunList<R> = Nil, f: (T) -> R): FunList<R> = when (this) {
    is Nil -> acc.reverse()
    is Cons -> tail.map(acc.addHead(f(head)), f)
}

tailrec fun <T, R> FunList<T>.indexedMap(
    index: Int = 0,
    acc: FunList<R> = Nil,
    f: (Int, T) -> R
): FunList<R> = when (this) {
    is Nil -> acc.reverse()
    is Cons -> tail.indexedMap(index + 1, acc.addHead(f(index, head)), f)
}

tailrec fun <T, R> FunList<T>.foldLeft(
    acc: R,
    f: (R, T) -> R
): R = when (this) {
    is Nil -> acc
    is Cons -> tail.foldLeft(f(acc, head), f)
}

fun FunList<Int>.sum(): Int = foldLeft(0) { acc, x -> acc + x }

fun <T> FunList<T>.filterByFoldLeft(
    p: (T) -> Boolean
): FunList<T> = this.foldLeft(Nil) { acc: FunList<T>, v ->
    if (p(v)) {
        acc.appendTail(v)
    } else {
        acc
    }
}

fun <T, R> FunList<T>.foldRight(acc: R, f: (T, R) -> R): R = when (this) {
    is Nil -> acc
    is Cons -> f(head, tail.foldRight(acc, f))
}
