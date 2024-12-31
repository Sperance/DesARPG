package ru.descend.desarpg

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun Double.to1Digits() = String.format("%.1f", this).replace(",", ".").toDouble()

@SuppressLint("DefaultLocale")
fun Double.to0Digits() = String.format("%.0f", this).replace(",", ".").toDouble()

fun Double.to0Text(): String {
    return this.to1Digits().toString().replace(".0", "")
}

fun Double.getPercent(value: Double) : Double {
    return ((this / 100.0) * value).to1Digits()
}

fun Double.addPercent(value: Double) : Double {
    return (this + getPercent(value)).to1Digits()
}

fun Double.removePercent(value: Double) : Double {
    return (this - getPercent(value)).to1Digits()
}