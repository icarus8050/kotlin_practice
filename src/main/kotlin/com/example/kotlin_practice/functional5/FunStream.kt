package com.example.kotlin_practice.functional5

sealed class FunStream<out T> {
    object Nil : FunStream<Nothing>()
    data class Cons<out T>(val head: () -> T, val tail: () -> FunStream<T>) : FunStream<T>()
}

fun <T> FunStream<T>.getHead(): T = when (this) {
    is FunStream.Nil -> throw NoSuchElementException()
    is FunStream.Cons -> head()
}

fun <T> FunStream<T>.getTail(): FunStream<T> = when (this) {
    is FunStream.Nil -> throw NoSuchElementException()
    is FunStream.Cons -> tail()
}

/*tailrec fun <T, R> FunStream<T>.foldLeft(
    accFunc: () -> R,
    f: (() -> R, () -> T) -> R
): R = when (this) {
    is FunStream.Nil -> accFunc()
    is FunStream.Cons -> tail().foldLeft({ f(accFunc, head) }, f)
}*/

tailrec fun <T, R> FunStream<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
    FunStream.Nil -> acc
    is FunStream.Cons -> tail().foldLeft(f(acc, head()), f)
}

fun FunStream<Int>.sum(): Int = this.foldLeft(0) { acc, x -> acc + x }

fun <T> FunStream<T>.toFunList(): FunList<T> = when (this) {
    FunStream.Nil -> FunList.Nil
    else -> FunList.Cons(getHead(), getTail().toFunList())
}

fun IntProgression.toFunStream(): FunStream<Int> = when {
    step > 0 -> when {
        first > last -> FunStream.Nil
        else -> FunStream.Cons({ first }, { ((first + step)..last step step).toFunStream() })
    }
    else -> when {
        first >= last -> {
            FunStream.Cons({ first },
                { IntProgression.fromClosedRange(first + step, last, step).toFunStream() })
        }
        else -> {
            FunStream.Nil
        }
    }
}

fun <T> FunStream<T>.filter(p: (T) -> Boolean): FunStream<T> = when (this) {
    FunStream.Nil -> FunStream.Nil
    is FunStream.Cons -> {
        val first = dropWhile(p)
        if (first != FunStream.Nil) {
            FunStream.Cons({ first.getHead() }, { first.getTail().filter(p) })
        } else {
            FunStream.Nil
        }
    }
}

tailrec fun <T> FunStream<T>.dropWhile(f: (T) -> Boolean): FunStream<T> = when (this) {
    FunStream.Nil -> this
    is FunStream.Cons -> if (f(head())) {
        this
    } else {
        tail().dropWhile(f)
    }
}

fun <T, R> FunStream<T>.map(f: (T) -> R): FunStream<R> = when (this) {
    FunStream.Nil -> FunStream.Nil
    is FunStream.Cons -> FunStream.Cons({ f(head()) }, { tail().map(f) })
}

fun <T> generateFunStream(seed: T, generate: (T) -> T): FunStream<T> =
    FunStream.Cons({ seed }, { generateFunStream(generate(seed), generate) })
