package day14.part1

import getFileLines

fun part1() {
    val lines = getFileLines("day14.txt")
    val rules = lines.takeLastWhile { it.isNotBlank() }.associate {
        val (pair, result) = it.split(" -> ")
        pair to result
    }

    val pairCounter = rules.mapValues { 0L }.toMutableMap()
    val letterCounter = ('A'..'Z').associateWith { 0L }.toMutableMap()

    // Fill for initial input
    lines.first().forEach { letterCounter[it] = letterCounter[it]!! + 1 }
    lines.first().split("").windowed(2, 1).forEach { (left, right) ->
        val key = "$left$right"
        if (rules.containsKey(key)) {
            pairCounter[key] = pairCounter[key]!! + 1
        }
    }

    for (step in (1..40)) {
        val tempPairCounter: MutableMap<String, Long> = pairCounter.map { it.key to it.value }.toMap().toMutableMap()
        pairCounter.filter { it.value != 0L }.forEach { (pair, count) ->
//            println("Pair: $pair")
            val (left, right) = pair.toList()
//            println("Left: $left Right: $right")
            val letter = rules[pair]!!.toCharArray().first()
            // Update letter count
            letterCounter[letter] = letterCounter[letter]!! + count
            // Update pair count
            tempPairCounter[pair] = tempPairCounter[pair]!! - count

            // Increase left pair count
            val leftPair = "$left$letter"
            if (tempPairCounter.containsKey(leftPair)) {
                tempPairCounter[leftPair] = tempPairCounter[leftPair]!! + count
            }
//            println(leftPair)

            // Increase right pair count
            val rightPair = "$letter$right"
            if (tempPairCounter.containsKey(rightPair)) {
                tempPairCounter[rightPair] = tempPairCounter[rightPair]!! + count
            }
//            println(rightPair)
        }
        // Update original pair counter map
        pairCounter.putAll(tempPairCounter)
    }

    println(pairCounter.filterValues { it != 0L })
    println(letterCounter.filterValues { it != 0L })

    val result = letterCounter.values.maxOf { it } - letterCounter.values.filter { it != 0L }.minOf { it }
    println("Part 1: $result")
}
