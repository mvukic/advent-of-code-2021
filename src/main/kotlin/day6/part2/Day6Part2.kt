package day6.part2

import getFileLines

fun part2() {
    val fishes = getFileLines("day6.txt").first().split(",").map(String::toInt)
    println(solve(fishes, 80))
    println(solve(fishes, 256))
}

fun solve(fishes: List<Int>, days: Int): Long {
    val school = School(fishes)
    for (day in 1..days) school.tick()
    return school.count()
}

class School(fishes: List<Int>) {
    var school = init()

    init {
        fishes.forEach {
            school[it] = school[it] + 1
        }
    }

    private fun init(): MutableList<Long> {
        return (0..8).map { 0L }.toMutableList()
    }

    fun count(): Long {
        return school.sum()
    }

    fun tick() {
        val schoolClone = init()
        school.forEachIndexed { k, v ->
            if (k != 0) schoolClone[k - 1] = schoolClone[k - 1] + v
        }
        schoolClone[6] = schoolClone[6] + school[0]
        schoolClone[8] = school[0]
        school = schoolClone
    }
}
