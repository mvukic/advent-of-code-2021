package day6.part1

import getFileLines

fun part1() {
    var fishes = getFileLines("day6.txt").first().split(",").map(String::toInt).toMutableList()
    val days = 80

    for (day in (1..days)) {
        println("Day: $day")
        println(fishes.count())
        fishes = fishes.map { it - 1 }.toMutableList()
        val c = fishes.count { it == -1 }
        fishes = fishes.map { if (it == -1) 6 else it }.toMutableList()
        repeat(c) {
            fishes.add(8)
        }
    }

}


