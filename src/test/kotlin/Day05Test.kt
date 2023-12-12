import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.Ignore

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

class Day05bSolutionTest {

    @Test
    fun solveTestInput() {
        assertEquals(46, Day05b("input/05.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(6082852, Day05b("input/05.txt").solve())
    }
}

class Day05bUnitTest {
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
            Day05a.HorticulturalStep(
                "test", listOf(
                    Day05a.CriterionRange(Day05b.SeedRange(6L, 10L), 10)
                )
            )
        )

        assertEquals(
            listOf(Day05b.SeedRange(1L, 5L), Day05b.SeedRange(16L, 20L)),
            testRange.horticulturate(testSteps)
        )
    }

    @Test
    fun `minusMultiple with fully contained ranges returns correct List of SeedRange`() {
        val testSeedRange = Day05b.SeedRange(1L, 10L)
        val testCriterionRanges = listOf(
            Day05b.SeedRange(3L, 5L),
            Day05b.SeedRange(8L, 9L),
        )

        assertEquals(
            listOf(
                Day05b.SeedRange(1L, 2L),
                Day05b.SeedRange(6L, 7L),
                Day05b.SeedRange(10L, 10L),
            ),
            testSeedRange.minusMultiple(testCriterionRanges)
        )
    }


    @Test
    fun `minusMultiple with partially contained ranges returns correct List of SeedRange`() {
        val testSeedRange = Day05b.SeedRange(3L, 8L)
        val testCriterionRanges = listOf(
            Day05b.SeedRange(1L, 5L),
            Day05b.SeedRange(8L, 10L),
        )

        assertEquals(
            listOf(
                Day05b.SeedRange(6L, 7L)
            ),
            testSeedRange.minusMultiple(testCriterionRanges)
        )
    }


    @Test
    fun `minusMultiple with fully enclosing single range returns empty List`() {
        val testSeedRange = Day05b.SeedRange(3L, 8L)
        val testCriterionRanges = listOf(
            Day05b.SeedRange(1L, 10L),
        )

        assertEquals(
            emptyList<Day05b.SeedRange>(),
            testSeedRange.minusMultiple(testCriterionRanges)
        )
    }

    @Test
    fun `minusMultiple with fully enclosing single range from testinput returns empty List`() {
        val testSeedRange = Day05b.SeedRange(55L, 67L)
        val testCriterionRanges = listOf(
            Day05b.SeedRange(50L, 97L),
        )

        assertEquals(
            emptyList<Day05b.SeedRange>(),
            testSeedRange.minusMultiple(testCriterionRanges)
        )
    }
}