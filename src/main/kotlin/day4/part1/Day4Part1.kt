package day4.part1

import getFileLines

fun part1() {
    val lines = getFileLines("day4.txt")
    val numbers = getNumbers(lines)
    val boards: List<Board> = getBoards(lines.drop(2))

    numbers@ for (number in numbers) {
        for (board in boards) {
            board.check(number)
            if (board.isBingo()) {
                val unmarkedNumbers = board.getUnmarkedNumbers()
                println(unmarkedNumbers.sum() * number)
                break@numbers
            }
        }
    }
}

fun getNumbers(lines: List<String>): List<Int> {
    return lines.first().split(",").map { it.toInt() }
}

fun getBoards(lines: List<String>): List<Board> {
    val numberOfBoards = lines.count { it.isBlank() } + 1
    val numberOfRows = (lines.size - numberOfBoards + 1) / numberOfBoards
    return lines
        .filter { it.isNotBlank() }
        .windowed(numberOfRows, numberOfRows)
        .map(::getRows)
        .map(::Board)
}

private fun getRows(rows: List<String>): List<BoardRow> {
    val regex = "[ ]+".toRegex()
    return rows.map { row ->
        row.split(regex)
            .filterNot(String::isBlank)
            .map(String::toInt)
            .map(::BoardPosition)
    }.map(::BoardRow)
}

data class Board(
    val rows: List<BoardRow>
) {
    fun check(number: Int) {
        rows.forEach { it.check(number) }
    }

    fun getUnmarkedNumbers(): List<Int> {
        return rows.map { row -> row.getUnmarkedNumbers() }.flatten()
    }

    fun isBingo(): Boolean {
        return isBingoRows() || isBingoColumns()
    }

    private fun isBingoRows(): Boolean {
        return rows.any(BoardRow::isBingo)
    }

    private fun isBingoColumns(): Boolean {
        val size = rows.first().row.size
        for (index in (0 until size)) {
            if (rows.map { it.row[index] }.all { it.checked }) {
                return true
            }
        }
        return false
    }

}

data class BoardRow(
    val row: List<BoardPosition>
) {

    fun check(number: Int) {
        row.forEach {
            if (it.value == number) {
                it.checked = true
            }
        }
    }

    fun isBingo(): Boolean {
        return row.all { it.checked }
    }

    fun getUnmarkedNumbers(): List<Int> {
        return row.filterNot { it.checked }.map { it.value }
    }

}

data class BoardPosition(
    val value: Int,
    var checked: Boolean = false
) {

    override fun toString() = " ($value, $checked) "

}
