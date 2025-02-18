package ru.descend.desarpg.logic.randomizers

import kotlin.random.Random

private var countRandomGenerator = 0

fun randInt(from: Int, to: Int) : Int {
    return Random(System.currentTimeMillis() + countRandomGenerator++).nextInt(from, to)
}

fun randDouble(from: Double, to: Double) : Double {
    return Random(System.currentTimeMillis() + countRandomGenerator++).nextDouble(from, to)
}