package day4.part2

import getFileLines

fun part2() {
    val lines = getFileLines("day4.txt")
    val numbers = getNumbers(lines)
    val boards: List<Board> = getBoards(lines.drop(2))

    numbers@ for (number in numbers) {
        println("Number is $number")
        for (board in boards) {
            // Skip boards that have won already
            if (board.done) {
                continue
            }
            // Check the number
            board.check(number)
            // If the board is bingo
            if (board.isBingo()) {
                // If it is last to win
                if (boards.count { it.done } == boards.size - 1) {
                    val unmarkedNumbers = board.getUnmarkedNumbers()
                    println(unmarkedNumbers.sum() * number)
                    break@numbers
                } else {
                    board.done = true
                }
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

    var done: Boolean = false

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
