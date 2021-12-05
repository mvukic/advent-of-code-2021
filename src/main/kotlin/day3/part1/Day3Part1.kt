package day3.part1

import getFileLines
import kotlin.math.pow

fun part1() {
    val numbers = getFileLines("day3.txt")
    val bitLength = numbers.first().length
    val bitCount = IntArray(bitLength) { 0 }
    numbers.forEach { binaryNumber ->
        binaryNumber.forEachIndexed { index, bit ->
            when (bit) {
                '0' -> bitCount[index]--
                '1' -> bitCount[index]++
            }
        }
    }
    bitCount.forEachIndexed { index, bit ->
        when {
            bit < 0 -> bitCount[index] = 0
            bit > 0 -> bitCount[index] = 1
        }
    }

    val gammaRate = bitCount.joinToString("").toInt(2)
    val epsilonRate = 2.0.pow(bitLength) - gammaRate - 1
    println("Part 1: ${gammaRate * epsilonRate.toInt()}")
}
