package day3.part2

import getFileLines

fun part2() {
    val numbers = getFileLines("day3.txt")

    val oxygenGeneratorRatingNumbers = numbers.map { it }.toMutableList()
    var oxygenCommonBits = getCommonBits(oxygenGeneratorRatingNumbers)
    var oxygenIndex = 0
    while (oxygenGeneratorRatingNumbers.size > 1) {
        val bit = oxygenCommonBits[oxygenIndex]
        oxygenGeneratorRatingNumbers.removeAll {
            if (bit == -1) {
                it[oxygenIndex] != '1'
            } else {
                it[oxygenIndex] != bit.digitToChar()
            }
        }
        oxygenIndex++
        oxygenCommonBits = getCommonBits(oxygenGeneratorRatingNumbers)
    }

    val co2ScrubberRatingNumbers = numbers.map { it }.toMutableList()
    var co2UncommonBits = getUncommonBits(co2ScrubberRatingNumbers)
    var co2Index = 0
    while (co2ScrubberRatingNumbers.size > 1) {
        val bit = co2UncommonBits[co2Index]
        co2ScrubberRatingNumbers.removeAll {
            if (bit == -1) {
                it[co2Index] != '0'
            } else {
                it[co2Index] != bit.digitToChar()
            }
        }
        co2Index++
        co2UncommonBits = getUncommonBits(co2ScrubberRatingNumbers)
    }
    val oxygenGeneratorRating = oxygenGeneratorRatingNumbers.joinToString().toInt(2)
    val co2ScrubberRating = co2ScrubberRatingNumbers.joinToString().toInt(2)
//    println(oxygenGeneratorRating)
//    println(co2ScrubberRating)
    println("Part 2: ${oxygenGeneratorRating * co2ScrubberRating}")

}

fun getCommonBits(numbers: List<String>): IntArray {
    val bitLength = numbers.first().length
    val bitCount = IntArray(bitLength) { 0 }
    val commonBits = IntArray(bitLength) { 0 }
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
            bit < 0 -> commonBits[index] = 0
            bit > 0 -> commonBits[index] = 1
            else -> commonBits[index] = -1
        }
    }
    return commonBits
}

fun getUncommonBits(numbers: List<String>): IntArray {
    val bitLength = numbers.first().length
    val bitCount = IntArray(bitLength) { 0 }
    val commonBits = IntArray(bitLength) { 0 }
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
            bit < 0 -> commonBits[index] = 1
            bit > 0 -> commonBits[index] = 0
            else -> commonBits[index] = -1
        }
    }
    return commonBits
}
