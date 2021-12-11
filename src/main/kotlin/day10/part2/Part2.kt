package day10.part2

import getFileLines
import java.util.*

val bracketPoints = mapOf(
    ')' to 1,
    ']' to 2,
    '}' to 3,
    '>' to 4
)

val brackets = mapOf(
    ']' to '[',
    '}' to '{',
    '>' to '<',
    ')' to '('
)
val reverseBrackets = brackets.map { (k, v) -> v to k }.toMap()
val closingBrackets = brackets.keys
val openingBrackets = brackets.values

fun part2() {
    val lines = getFileLines("day10.txt")
    val p1 = lines
        .filter { getWrongBracket2(it) == null }
        .map { getWrongBracket(it) }

    val sorted = p1
        .map { getPointsForBrackets(it) }
        .sorted()

    println("Part 2: ${sorted[27]}")
}


fun getWrongBracket2(line: String): Char? {
    val stack = Stack<Char>()
    for (char in line) {

        if (char in openingBrackets) {
            stack.push(char)
            continue
        }

        if (char in closingBrackets) {
            if (brackets[char] == stack.last()) {
                stack.pop()
            } else {
                return char
            }
        }
    }
    return null
}

fun getWrongBracket(line: String): String {
    val stack = Stack<Char>()
    for (char in line) {

        if (char in openingBrackets) {
            stack.push(char)
            continue
        }

        if (char in closingBrackets) {
            if (brackets[char] == stack.last()) {
                stack.pop()
            }
        }
    }

    return stack.reversed().map { reverseBrackets[it]!! }.joinToString("")
}

fun getPointsForBrackets(brackets: String): Long {
    var result = 0L
    for (bracket in brackets) {
        result = result * 5 + bracketPoints[bracket]!!
    }
    return result
}
