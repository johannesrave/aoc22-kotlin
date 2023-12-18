import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12aTest {
    @Test
    fun `findValidVariants matches more than one`() {
        val day12a = Day12a("input/12.test.txt")
        val patternToSprings = ".??..??...?##." to listOf(1, 1, 3)
        val variant = day12a.findValidVariants(
            patternToSprings,
            day12a.buildVariantGenerator(listOf(patternToSprings))
        )

        assertEquals(4, variant.count())
    }

    @Test
    fun solveTestInput() {
        assertEquals(21, Day12a("input/12.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(7792, Day12a("input/12.txt").solve())
    }
}

class Day12bTest {
    @Test
    fun solveTestInput() {
        assertEquals(0, Day12b("input/12.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(0, Day12b("input/12.txt").solve())
    }
}