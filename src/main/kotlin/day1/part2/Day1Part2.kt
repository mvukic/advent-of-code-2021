package day1.part2

import getFileLines

fun part2() {
    val depths = getFileLines("day1.txt").map { it.toLong() }
    val increases = depths.windowed(4).count() {
        it[0] < it[3]
    }
    println("Part 2: $increases")
}
