package com.example.kotlin_practice.functional5

import com.example.kotlin_practice.functional5.FunList.Cons
import com.example.kotlin_practice.functional5.FunList.Nil

fun main() {
    // 5_1 FunList를 사용해서 [1, 2, 3, 4, 5]를 가지는 intList를 생성하자
    val intList = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
    println(intList)

    // 5_2 FunList를 사용해서 [1.0, 2.0, 3.0, 4.0, 5.0]를 가지는 doubleList를 생성하자
    val doubleList = Cons(1.0, Cons(2.0, Cons(3.0, Cons(4.0, Cons(5.0, Nil)))))
    println(doubleList)

    val addedToHeadCons = intList.addHead(100)
    println(addedToHeadCons)

    val appendedToTailCons = addedToHeadCons.appendTail(50)
    println(appendedToTailCons)

    val tailrecList = Cons(1, Cons(2, Nil))
    val appendTailByTailRec = tailrecList.appendTailByTailRec(3)
    println(appendTailByTailRec)

    println(intList.drop(3))
    println(intList.dropWhile {
        it > 3
    })

    println(intList.takeWhile {
        it < 3
    })
}
