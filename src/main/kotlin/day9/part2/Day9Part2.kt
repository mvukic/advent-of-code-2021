package day9.part2

import getFileLines

data class Point(
    val value: Int,
    var basinId: Int
)

fun part2() {
    val lines = getFileLines("day9.txt")
    val field: List<List<Point>> = lines.map {
        it.split("")
            .filter(String::isNotBlank)
            .map(String::toInt)
            .map { Point(it, 1) }
    }

    var basinCounter = 2
    for (row in field.indices) {
        for (column in field[0].indices) {
            val current = field[row][column]
            if (current.value == 9) {
                continue
            }

            val upper: Point? = runCatching { field[row - 1][column] }.fold(
                onSuccess = { it },
                onFailure = { null }
            )
            val lower: Point? = runCatching { field[row + 1][column] }.fold(
                onSuccess = { it },
                onFailure = { null }
            )
            val left: Point? = runCatching { field[row][column - 1] }.fold(
                onSuccess = { it },
                onFailure = { null }
            )
            val right: Point? = runCatching { field[row][column + 1] }.fold(
                onSuccess = { it },
                onFailure = { null }
            )

            if (current.basinId == 1) {
                if (upper != null && upper.value != 9 && upper.basinId != 1) {
                    current.basinId = upper.basinId
                } else if (lower != null && lower.value != 9 && lower.basinId != 1) {
                    current.basinId = lower.basinId
                } else if (left != null && left.value != 9 && left.basinId != 1) {
                    current.basinId = left.basinId
                } else if (right != null && right.value != 9 && right.basinId != 1) {
                    current.basinId = right.basinId
                } else {
                    current.basinId = basinCounter
                    basinCounter++
                }
            }

            if (upper != null && upper.value != 9 && upper.basinId == 1) {
                upper.basinId = current.basinId
            }
            if (lower != null && lower.value != 9 && lower.basinId == 1) {
                lower.basinId = current.basinId
            }
            if (left != null && left.value != 9 && left.basinId == 1) {
                left.basinId = current.basinId
            }
            if (right != null && right.value != 9 && right.basinId == 1) {
                right.basinId = current.basinId
            }

            val neighborBasins = listOfNotNull(left, right, upper, lower)
                .filter { it.value != 9 }
                .map { it.basinId }
            if ((neighborBasins + current.basinId).toSet().size != 1) {
                replaceBasins(field, neighborBasins + current.basinId, basinCounter)
                basinCounter++
            }
        }
    }
    
    val groups = field.asSequence()
        .map { it }
        .flatten()
        .filter { it.basinId != 1 }
        .groupBy { it.basinId }
        .map { (basinId, points) -> basinId to points.size }
        .toList()
        .sortedBy { it.second }
        .reversed()
        .take(3)
        .map { it.second }
        .reduce { acc, i -> i * acc }

    println("Part 2: $groups")

}


fun replaceBasins(field: List<List<Point>>, basins: List<Int>, basin: Int) {
    field.forEach { points ->
        points.forEach {
            if (it.basinId in basins) {
                it.basinId = basin
            }
        }
    }
}
