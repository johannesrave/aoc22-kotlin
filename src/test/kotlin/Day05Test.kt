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
    fun `process seedRange remains unchanged`() {
        val testRange = listOf(Day05b.Range(1L, 5L))
        val testSteps = listOf(
            Day05a.TransformationStep("test", listOf(Day05a.Transformation(Day05b.Range(6L, 10L), 10)))
        )

        assertEquals(testRange.toList(), Day05b("input/05.test.txt").process(testRange, testSteps).toList())
    }

    @Test
    fun `process seedRange is shifted completely`() {
        val testRange = listOf(Day05b.Range(1L, 5L))
        val testSteps = listOf(
            Day05a.TransformationStep("test", listOf(Day05a.Transformation(Day05b.Range(1L, 5L), 10)))
        )

        assertEquals(listOf(Day05b.Range(11L, 15L)), Day05b("input/05.test.txt").process(testRange, testSteps))
    }

    @Test
    fun `process seedRange partially shifted with two ranges remaining`() {
        val testRange = listOf(Day05b.Range(1L, 10L))
        val testSteps = listOf(
            Day05a.TransformationStep(
                "test", listOf(
                    Day05a.Transformation(Day05b.Range(6L, 10L), 10)
                )
            )
        )

        assertEquals(
            listOf(Day05b.Range(1L, 5L), Day05b.Range(16L, 20L)),
            Day05b("input/05.test.txt").process(testRange, testSteps)
        )
    }

    @Test
    fun `minusMultiple with fully contained ranges returns correct List of SeedRange`() {
        val testRange = Day05b.Range(1L, 10L)
        val testCriterionRanges = listOf(
            Day05b.Range(3L, 5L),
            Day05b.Range(8L, 9L),
        )

        assertEquals(
            listOf(
                Day05b.Range(1L, 2L),
                Day05b.Range(6L, 7L),
                Day05b.Range(10L, 10L),
            ),
            testRange.minus(testCriterionRanges)
        )
    }


    @Test
    fun `minusMultiple with partially contained ranges returns correct List of SeedRange`() {
        val testRange = Day05b.Range(3L, 8L)
        val testCriterionRanges = listOf(
            Day05b.Range(1L, 5L),
            Day05b.Range(8L, 10L),
        )

        assertEquals(
            listOf(
                Day05b.Range(6L, 7L)
            ),
            testRange.minus(testCriterionRanges)
        )
    }


    @Test
    fun `minusMultiple with fully enclosing single range returns empty List`() {
        val testRange = Day05b.Range(3L, 8L)
        val testCriterionRanges = listOf(
            Day05b.Range(1L, 10L),
        )

        assertEquals(
            emptyList<Day05b.Range>(),
            testRange.minus(testCriterionRanges)
        )
    }

    @Test
    fun `minusMultiple with fully enclosing single range from testinput returns empty List`() {
        val testRange = Day05b.Range(55L, 67L)
        val testCriterionRanges = listOf(
            Day05b.Range(50L, 97L),
        )

        assertEquals(
            emptyList<Day05b.Range>(),
            testRange.minus(testCriterionRanges)
        )
    }
}