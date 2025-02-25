package ru.descend.desarpg.logic.randomizers

import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.random.nextLong

private var countRandomGenerator = 0

fun randInt(from: Int, to: Int) : Int {
    return Random(System.currentTimeMillis() + countRandomGenerator++).nextInt(from..to)
}

fun randLong(from: Long, to: Long) : Long {
    return Random(System.currentTimeMillis() + countRandomGenerator++).nextLong(from..to)
}

fun randDouble(from: Double, to: Double) : Double {
    return Random(System.currentTimeMillis() + countRandomGenerator++).nextDouble(from, to)
}