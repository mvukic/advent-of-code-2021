package day7.part2

import getFileLines
import kotlin.math.max
import kotlin.math.min

fun part2() {
    val positions = getFileLines("day7.txt").first().split(",").map(String::toInt)
//    val average = positions.sum().toDouble() / positions.size.toDouble()
    val average = 484
    println(average)

    val fuel = positions.sumOf { (1..(max(it, average) - min(it, average))).sum() }
    println(fuel)

}

