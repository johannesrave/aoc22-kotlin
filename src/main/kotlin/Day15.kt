import kotlin.system.measureTimeMillis

fun main() {
    val durationA = measureTimeMillis { println("Solution for Day15a: ${Day15a("input/15.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day15b: ${Day15b("input/15.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day15a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int = parseLine(input).sumOf { step -> step.hash() }

    fun String.hash() = fold(0) { value, c -> ((value + c.code) * 17) % 256 }

    protected open fun parseLine(input: String): List<String> = input.split(",")
}

class Day15b(inputFileName: String) : Day15a(inputFileName) {
    override fun solve(): Int = parseSteps(input)
        .fold(
            (0..255).associateWith { mutableMapOf<String, Int>() }
        ) { boxes, step ->
            val box = boxes[step.hash]!!
            when (step) {
                is Step.MoveStep -> box[step.label] = step.focalLength
                is Step.RemoveStep -> box.remove(step.label)
            }
            boxes
        }
        .asIterable()
        .mapIndexed { boxNo, (_, lenses) ->
            lenses.asIterable().mapIndexed { lensNo, (_, focalLength) ->
                (boxNo + 1) * (lensNo + 1) * (focalLength)
            }.sum()
        }.sum()

    private fun parseSteps(input: String): List<Step> = input
        .split(",")
        .map { Step.from(it) }

    open class Step(open val label: String, open val hash: Int) {
        data class MoveStep(override val label: String, override val hash: Int, val focalLength: Int) :
            Step(label, hash)

        data class RemoveStep(override val label: String, override val hash: Int) : Step(label, hash)

        companion object {
            private val moveStepRegex = "(\\w+)=(\\d)".toRegex()
            private val removeStepRegex = "(\\w+)-".toRegex()
            fun from(s: String): Step {

                return when {
                    s.matches(moveStepRegex) -> {
                        val (label, rawFocalLength) = moveStepRegex.find(s)!!.destructured
                        MoveStep(label, label.hash(), rawFocalLength.toInt())
                    }

                    s.matches(removeStepRegex) -> {
                        val (label) = removeStepRegex.find(s)!!.destructured
                        RemoveStep(label, label.hash())
                    }

                    else -> throw IllegalStateException()
                }
            }

            private fun String.hash(): Int = fold(0) { value, c -> ((value + c.code) * 17) % 256 }
        }
    }
}