package day5.part2

import getFileLines
import kotlin.math.max

fun part2() {
    val lines = getFileLines("day5.txt").map(::getLines)
    val maxCoordinates = getMaxCoordinates(lines)
    val field = Array(maxCoordinates.x + 1) { IntArray(maxCoordinates.y + 1) }
    lines.forEach {
        it.getPoints().forEach { (x, y) ->
            field[y][x]++
        }
    }

    var count = 0
    field.forEach { row ->
        count += row.count { it > 1 }
    }
    println("Part 2: $count")
}

fun getLines(line: String): Line {
    val (x1, y1, x2, y2) = line.split(" -> ", ",").map(String::trim).map(String::toInt)
    return Line(
        start = Coordinate(x1, y1),
        end = Coordinate(x2, y2)
    )
}

fun getMaxCoordinates(lines: List<Line>): Coordinate {
    return Coordinate(
        x = max(lines.maxOf { it.start.x }, lines.maxOf { it.end.x }),
        y = max(lines.maxOf { it.start.y }, lines.maxOf { it.end.y })
    )
}

data class Line(
    val start: Coordinate,
    val end: Coordinate
) {
    fun getPoints() = sequence {
        if (start.x == end.x) {
            val x = start.x
            val range = if (start.y < end.y) start.y..end.y else end.y..start.y
            for (y in range) {
                yield(Coordinate(x, y))
            }
        } else if (start.y == end.y) {
            val y = start.y
            val range = if (start.x < end.x) start.x..end.x else end.x..start.x
            for (x in range) {
                yield(Coordinate(x, y))
            }
        } else {
            val slopeY = end.y - start.y
            val slopX = end.x - start.x
            val a = slopeY / slopX
            val b = start.y - a * start.x
            val range = if (start.x < end.x) start.x..end.x else end.x..start.x
            for (x in range) {
                yield(Coordinate(x, a * x + b))
            }
        }
    }

    override fun toString() = "start=$start, end=$end)"

}

data class Coordinate(
    val x: Int,
    val y: Int
) {

    override fun toString() = "($x, $y)"
}

