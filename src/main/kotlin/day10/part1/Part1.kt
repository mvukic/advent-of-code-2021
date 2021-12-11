package day10.part1

import getFileLines
import java.util.*

val bracketPoints = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137
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

fun part1() {
    val lines = getFileLines("day10.txt")
    val sum = lines
        .mapNotNull { getWrongBracket(it) }
        .sumOf { bracketPoints[it]!! }
    println("Part 1: $sum")
}

fun getWrongBracket(line: String): Char? {
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
                println("Expected ${reverseBrackets[stack.last()]} but got $char")
                return char
            }
        }
    }
    return null
}

