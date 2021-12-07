package day7.part1

import getFileLines
import kotlin.math.max
import kotlin.math.min

fun part1() {
    val positions = getFileLines("day7.txt").first().split(",").map(String::toInt)
    val sorted = positions.sorted()
    val median = if (sorted.size % 2 == 0) {
        (sorted[sorted.size / 2] + sorted[sorted.size / 2 - 1]) / 2
    } else {
        sorted[sorted.size / 2]
    }

    val fuel = sorted.sumOf { max(it, median) - min(it, median) }
    println(fuel)

}


