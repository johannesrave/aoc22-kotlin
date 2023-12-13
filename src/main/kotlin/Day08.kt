import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day08a: ${Day08a("input/08.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day08b: ${Day08b("input/08.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day08a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Long {
        val nodeMap = Node.parseMap(input)
        val instructionSequence = Instruction.sequenceFrom(input)

        var currentLabel = "AAA"
        val finalLabel = "ZZZ"

        instructionSequence.forEachIndexed { i, inst ->
            if (currentLabel == finalLabel) return i.toLong()
            currentLabel = nodeMap[currentLabel]!!.move(inst)
        }

        return -1
    }

    data class Node(val label: String, val left: String, val right: String) {
        fun move(instruction: Instruction) = when (instruction) {
            Instruction.L -> this.left
            Instruction.R -> this.right
        }

        companion object {
            fun parseMap(input: String): Map<String, Node> =
                """(.{3}) = \((.{3}), (.{3})\)""".toRegex().findAll(input).map {
                    val (_, label, left, right) = it.groupValues
                    label to Node(label, left, right)
                }.toMap()
        }
    }

    enum class Instruction {
        L, R;

        companion object {
            private fun parseList(input: String): List<Instruction> = input.lines().first().map {
                when (it) {
                    'L' -> L
                    'R' -> R
                    else -> throw IllegalArgumentException("'$it' is not a valid Instruction.")
                }
            }

            fun sequenceFrom(input: String): Sequence<Instruction> =
                parseList(input).asSequence().repeat()
        }
    }
}

class Day08b(inputFileName: String) : Day08a(inputFileName) {
    override fun solve(): Long {
        val nodeMap = Node.parseMap(input)
        val initialLabels = nodeMap.filter { (label, _) -> label[2] == 'A' }.map { (label) -> label }
        val finalLabels = nodeMap.filter { (label, _) -> label[2] == 'Z' }.map { (label) -> label }.toSet()

        return initialLabels.map { initialLabel ->
            val instructions = Instruction.sequenceFrom(input).iterator()
            var moves = 0L
            var currentLabel = initialLabel
            while(currentLabel !in finalLabels){
                moves++
                val instruction = instructions.next()
                currentLabel = nodeMap[currentLabel]!!.move(instruction)
            }
            moves
        }.findLCM()
    }
}