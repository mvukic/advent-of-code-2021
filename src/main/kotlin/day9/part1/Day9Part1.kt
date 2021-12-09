package day9.part1

import getFileLines

fun part1() {
    val lines = getFileLines("day9.txt")
    val field: List<List<Int>> = lines.map {
        it.split("").filter(String::isNotBlank).map(String::toInt)
    }
    var sum = 0

    for (row in field.indices) {
        for (column in field[0].indices) {
            val current = field[row][column]
            val upper = runCatching { field[row - 1][column] }.fold(
                onSuccess = { it },
                onFailure = { Int.MAX_VALUE }
            )
            val lower = runCatching { field[row + 1][column] }.fold(
                onSuccess = { it },
                onFailure = { Int.MAX_VALUE }
            )
            val left = runCatching { field[row][column - 1] }.fold(
                onSuccess = { it },
                onFailure = { Int.MAX_VALUE }
            )
            val right = runCatching { field[row][column + 1] }.fold(
                onSuccess = { it },
                onFailure = { Int.MAX_VALUE }
            )
            if (listOf(upper, lower, left, right).all { it > current }) {
                sum += current
                sum += 1
            }
        }
    }
    println("Part 1: $sum")
}


