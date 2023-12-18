import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class Day11aTest {
    @Test
    fun solveTestInput() {
        assertEquals(374, Day11a("input/11.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(10313550, Day11a("input/11.txt").solve())
    }
}

class Day11bTest {
    @Test
    fun solveTestInputWithFactor10() {
        val day11b = Day11b("input/11.test.txt")

        val cosmos = day11b.parseCosmos(day11b.input)
        val expandedCosmos = day11b.expandReallyFast(cosmos)

        expandedCosmos.onEach { println(it) }

        val galaxies = Day11a.Galaxy.setFrom(expandedCosmos)
        val pairedGalaxies = day11b.pairUpGalaxies(galaxies)

        val distances = day11b.getExpandedDistances(pairedGalaxies, expandedCosmos, 10)

        assertEquals(1030, distances.sum())
    }

    @Test
    fun solveTestInputWithFactor100() {
        val day11b = Day11b("input/11.test.txt")

        val cosmos = day11b.parseCosmos(day11b.input)
        val expandedCosmos = day11b.expandReallyFast(cosmos)

        expandedCosmos.onEach { println(it) }

        val galaxies = Day11a.Galaxy.setFrom(expandedCosmos)
        val pairedGalaxies = day11b.pairUpGalaxies(galaxies)

        val distances = day11b.getExpandedDistances(pairedGalaxies, expandedCosmos, 100)

        assertEquals(8410, distances.sum())
    }

    @Test
    fun solveInput() {
        assertEquals(611998089572, Day11b("input/11.txt").solve())
    }
}