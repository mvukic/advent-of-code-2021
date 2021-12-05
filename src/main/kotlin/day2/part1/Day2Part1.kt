package day2.part1

import getFileLines

data class Position(
    val horizontalPosition: Int = 0,
    var depth: Int = 0
) {
    val result = horizontalPosition * depth
}

sealed interface Command {
    val units: Int
    fun move(current: Position): Position
}

class ForwardCommand(
    override val units: Int
) : Command {
    override fun move(current: Position): Position {
        return current.copy(horizontalPosition = current.horizontalPosition + units)
    }
}

class UpCommand(
    override val units: Int
) : Command {
    override fun move(current: Position): Position {
        return current.copy(depth = current.depth - units)
    }
}

class DownCommand(
    override val units: Int
) : Command {
    override fun move(current: Position): Position {
        return current.copy(depth = current.depth + units)
    }
}

fun String.toCommand(): Command? {
    val (direction, units) = split(" ")
    return when (direction) {
        "forward" -> ForwardCommand(units.toInt())
        "down" -> DownCommand(units.toInt())
        "up" -> UpCommand(units.toInt())
        else -> null
    }
}

fun part1() {
    val commands = getFileLines("day2.txt").mapNotNull(String::toCommand)
    val position = commands.fold(Position()) { acc, command ->
        command.move(acc)
    }
    println("Part 1: ${position.result}")
}
