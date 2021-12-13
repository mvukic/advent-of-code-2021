package day13.part1

import getFileLines
import kotlin.math.abs

fun part1() {
    val lines = getFileLines("day13.txt")
    var coordinates = lines.getCoordinates()
    val instructions = lines.getInstructions()

    println("Initial:")
    drawPaper(coordinates)

    instructions.take(1).forEach { instruction ->
        println("Instruction: $instruction")
        coordinates = if (instruction.first == "x") {
            foldLeft(coordinates, instruction.second)
        } else {
            foldUp(coordinates, instruction.second)
        }
        drawPaper(coordinates)
    }

    println("Part 1: ${coordinates.size}")
}

fun drawPaper(coordinates: List<Pair<Int, Int>>) {
    val maxX = coordinates.maxOf { it.first } + 1
    val maxY = coordinates.maxOf { it.second } + 1
    val paper = Array(maxY) { CharArray(maxX) { '.' } }
    coordinates.forEach { (x, y) -> paper[y][x] = '#' }
    paper.forEach { row ->
        println(row.joinToString(""))
    }
    println()
}

fun foldUp(coordinates: List<Pair<Int, Int>>, foldLine: Int): List<Pair<Int, Int>> {
    return coordinates.map { (x, y) ->
        if (y <= foldLine) {
            x to y
        } else {
            x to abs(2 * foldLine - y)
        }
    }.distinctBy { "${it.first},${it.second}" }
}

fun foldLeft(coordinates: List<Pair<Int, Int>>, foldLine: Int): List<Pair<Int, Int>> {
    return coordinates.map { (x, y) ->
        if (x <= foldLine) {
            x to y
        } else {
            abs(2 * foldLine - x) to y
        }
    }.distinctBy { "${it.first}.${it.second}" }
}

fun List<String>.getCoordinates(): List<Pair<Int, Int>> {
    return takeWhile { it.isNotBlank() }.map {
        val (x, y) = it.split(",")
        x.toInt() to y.toInt()
    }
}

fun List<String>.getInstructions(): List<Pair<String, Int>> {
    return this.takeLastWhile { it.isNotBlank() }.map {
        val (_, _, axis, value) = it.split(" ", "=")
        axis to value.toInt()
    }
}
