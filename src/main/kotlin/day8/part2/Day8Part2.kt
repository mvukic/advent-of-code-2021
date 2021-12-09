package day8.part2

import getFileLines

fun part2() {
    val lines = getFileLines("day8.txt")
    val sum = lines.sumOf { line ->
        val (input, output) = line.split(" | ")[0].split(" ") to line.split(" | ")[1].split(" ")
        val one = input.find { it.length == 2 }!!.toList().sorted().joinToString("")
        val four = input.find { it.length == 4 }!!.toList().sorted().joinToString("")
        val seven = input.find { it.length == 3 }!!.toList().sorted().joinToString("")
        val eight = input.find { it.length == 7 }!!.toList().sorted().joinToString("")

        val sixOrZeroOrNine = input.filter { it.length == 6 }.map { it.toList().sorted().joinToString("") }
        val (sixList, zeroOrNine) = sixOrZeroOrNine.partition { !it.toList().containsAll(one.toList()) }
        val six = sixList.first()

        val twoOrThreeOrFive = input.filter { it.length == 5 }.map { it.toList().sorted().joinToString("") }
        val (threeList, twoOrFive) = twoOrThreeOrFive.partition { it.toList().containsAll(one.toList()) }
        val three = threeList.first()

        val (zeroList, nineList) = zeroOrNine.partition { !it.toList().containsAll(four.toList()) }
        val zero = zeroList.first()
        val nine = nineList.first()

        val five = twoOrFive.find { (sixList.first().toList() - it.toList().toSet()).size == 1 }
        val two = (twoOrFive - five).first()

        val numbersMap = mapOf(
            zero to 0,
            one to 1,
            two to 2,
            three to 3,
            four to 4,
            five to 5,
            six to 6,
            seven to 7,
            eight to 8,
            nine to 9
        )

        output.map { it.toList().sorted().joinToString("") }.map { numbersMap[it]!! }.joinToString("").toInt()
    }
    println("Part 2: $sum")
}

