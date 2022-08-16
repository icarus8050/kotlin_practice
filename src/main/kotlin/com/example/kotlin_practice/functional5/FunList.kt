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

fun <T> funListOf(vararg elements: T): FunList<T> = elements.toFunList()

private fun <T> Array<out T>.toFunList(): FunList<T> = when {
    this.isEmpty() -> Nil
    else -> Cons(this[0], this.copyOfRange(1, this.size).toFunList())
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

fun <T, R> FunList<T>.mapByFoldList(f: (T) -> R): FunList<R> = foldLeft(Nil) { acc: FunList<R>, x ->
    acc.appendTail(f(x))
}

fun <T, R> FunList<T>.mapByFoldRight(f: (T) -> R): FunList<R> = foldRight(Nil) { x, acc: FunList<R> ->
    acc.addHead(f(x))
}

tailrec fun <T1, T2, R> FunList<T1>.zipWith(
    f: (T1, T2) -> R,
    list: FunList<T2>,
    acc: FunList<R> = Nil,
): FunList<R> = when {
    this === Nil || list === Nil -> acc.reverse()
    else -> getTail().zipWith(
        f = f,
        list = list.getTail(),
        acc = acc.addHead(
            f(getHead(), list.getHead())
        ),
    )
}

fun <T, R> FunList<T>.associate(f: (T) -> Pair<T, R>): Map<T, R> {
    return foldRight(mapOf()) { value, acc ->
        acc.plus(f(value))
    }
}

fun <T, K> FunList<T>.groupBy(f: (T) -> K): Map<K, FunList<T>> =
    foldRight(emptyMap()) { value, acc ->
        acc.plus(
            f(value) to (acc.getOrElse(f(value)) { Nil }.addHead(value))
        )
    }

tailrec fun IntProgression.toFunList(acc: FunList<Int> = FunList.Nil): FunList<Int> = when {
    step > 0 -> when {
        first > last -> acc.reverse()
        else -> ((first + step)..last step step).toFunList(acc.addHead(first))
    }
    else -> when {
        first >= last -> {
            IntProgression.fromClosedRange(first + step, last, step).toFunList(acc.addHead(first))
        }
        else -> {
            acc.reverse()
        }
    }
}

tailrec fun <T> FunList<T>.toString(acc: String): String = when (this) {
    is Nil -> "[${acc.drop(2)}]"
    is Cons -> tail.toString("$acc, $head")
}

fun <T> printFunList(list: FunList<T>) = list.toStringByFoldLeft()

fun <T> FunList<T>.toStringByFoldLeft(): String =
    "[${foldLeft("") { acc, x -> "$acc, $x" }.drop(2)}]"

// 명령형 방식
fun imperativeWay(intList: List<Int>): Int {
    for (value in intList) {
        val doubleValue = value * value
        if (doubleValue < 10) {
            return doubleValue
        }
    }

    throw NoSuchElementException("There is no value")
}

// 함수형 방식
fun functionalWay(intList: List<Int>): Int =
    intList.map { n -> n * n }
        .first { n -> n < 10 }

fun realFunctionalWay(intList: List<Int>): Int = intList.asSequence()
    .map { n -> n * n }
    .first { n -> n < 10 }
