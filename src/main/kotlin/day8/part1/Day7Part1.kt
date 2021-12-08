package day8.part1

import getFileLines

fun part1() {
    val lines = getFileLines("day8.txt")
    val count = lines.sumOf { line ->
        val (_, output) = line.split(" | ")
        output.split(" ").count { it.length in listOf(2, 3, 4, 7) }
    }
    println("Part 1: $count")

}


