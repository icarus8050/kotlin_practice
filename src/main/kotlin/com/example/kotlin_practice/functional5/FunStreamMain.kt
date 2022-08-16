package com.example.kotlin_practice.functional5

import com.example.kotlin_practice.functional5.FunStream.Cons
import com.example.kotlin_practice.functional5.FunStream.Nil

class FunStreamMain

fun main() {
    val funStream = Cons({ 1 }) {
        Cons({ 2 }) {
            Cons({ 3 }) {
                Cons({ 4 }) {
                    Cons({ 5 }) {
                        Cons({ 6 }) {
                            Cons({ 7 }) {
                                Cons({ 8 }) {
                                    Cons({ 9 }) {
                                        Cons({ 10 }) {
                                            Nil
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    println(funStream.getHead())    // 1
    println(funStream.getTail())    // Cons(head=() -> kotlin.Int, tail=() -> com.example.kotlin_practice.functional5.FunStream<kotlin.Int>)

    println(funStream.sum())
}
