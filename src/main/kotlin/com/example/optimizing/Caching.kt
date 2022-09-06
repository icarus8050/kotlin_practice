package com.example.optimizing

const val ARR_SIZE = 2 * 1024 * 1024
val testData: Array<Int> = Array(ARR_SIZE) { 0 }
class Caching {
    fun run() {
        println("WarmUp Start")
        val warmUpStart = System.currentTimeMillis()
        for (i in 0 until 15_000) {
            touchEveryLine()
            touchEveryItem()
        }
        println("Warmup Finished: ${System.currentTimeMillis() - warmUpStart}")
        println("Item Line")
        for (i in 0 until 100) {
            val t0 = System.currentTimeMillis()
            touchEveryLine()
            val t1 = System.currentTimeMillis()
            touchEveryItem()
            val t2 = System.currentTimeMillis()
            val elItem = t2 - t1
            val elLine = t1 - t0
            val diff = elItem - elLine
            println("$elItem $elLine $diff")
        }
    }

    private fun touchEveryItem() {
        for (i in 0 until testData.size) {
            testData[i]++
        }
    }

    private fun touchEveryLine() {
        for (i in testData.indices step 16) {
            testData[i]++
        }
    }
}

fun main() {
    val c = Caching()
    c.run()
}
