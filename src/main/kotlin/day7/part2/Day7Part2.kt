package day7.part2

import getFileLines
import kotlin.math.abs

fun part2() {
    val positions = getFileLines("day7.txt").first().split(",").map(String::toInt)
    val average = (positions.sum().toDouble() / positions.size.toDouble()).toInt()
    val fuel = positions.sumOf { (1..abs(it - average)).sum() }
    println("Part 2: $fuel")

}

