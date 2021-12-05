package day1.part1

import getFileLines


fun part1() {
    val depths = getFileLines("day1.txt").map { it.toLong() }
    val increases = depths.windowed(2).count() { (first, second) ->
        first <= second
    }
    println("Part 1: $increases")
}
