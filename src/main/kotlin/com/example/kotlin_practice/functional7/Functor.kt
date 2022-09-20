package com.example.kotlin_practice.functional7

interface Functor<out A> {
    fun <B> fmap(f: (A) -> B): Functor<B>
}
