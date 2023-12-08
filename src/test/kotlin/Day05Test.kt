import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day05aTest {

    @Test
    fun solveTestInput() {
        assertEquals(35, Day05a("input/05.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(3374647, Day05a("input/05.txt").solve())
    }
}

class Day05bTest {

    @Test
    fun solveTestInput() {
        assertEquals(46, Day05b("input/05.test.txt").solve())
    }


//    @Test
//    fun solveInput() {
//        assertEquals(46, Day05b("input/05.txt").solve())
//    }

    @Test
    fun `horticulturate seedRange remains unchanged`() {
        val testRange = listOf(Day05b.SeedRange(1L, 5L))
        val testSteps = listOf(
            Day05a.HorticulturalStep("test", listOf(Day05a.CriterionRange(Day05b.SeedRange(6L, 10L), 10)))
        )

        assertEquals(testRange, testRange.horticulturate(testSteps))
    }

    @Test
    fun `horticulturate seedRange is shifted completely`() {
        val testRange = listOf(Day05b.SeedRange(1L, 5L))
        val testSteps = listOf(
            Day05a.HorticulturalStep("test", listOf(Day05a.CriterionRange(Day05b.SeedRange(1L, 5L), 10)))
        )

        assertEquals(listOf(Day05b.SeedRange(11L, 15L)), testRange.horticulturate(testSteps))
    }


    @Test
    fun `horticulturate seedRange partially shifted with two ranges remaining`() {
        val testRange = listOf(Day05b.SeedRange(1L, 10L))
        val testSteps = listOf(
            Day05a.HorticulturalStep("test", listOf(Day05a.CriterionRange(Day05b.SeedRange(6L, 10L), 10)))
        )

        assertEquals(listOf(Day05b.SeedRange(1L, 5L),Day05b.SeedRange(16L, 20L)), testRange.horticulturate(testSteps))
    }
}