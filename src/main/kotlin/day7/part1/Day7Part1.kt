package day7.part1

import getFileLines
import kotlin.math.abs

fun part1() {
    val positions = getFileLines("day7.txt").first().split(",").map(String::toInt).sorted()
    val median = if (positions.size % 2 == 0) {
        (positions[positions.size / 2] + positions[positions.size / 2 - 1]) / 2
    } else {
        positions[positions.size / 2]
    }

    val fuel = positions.sumOf { abs(it - median) }
    println("Part 1: $fuel")

}


