import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12aTest {
    @Test
    fun `findAll`() {
        "(1.1)".toRegex()
            .findAll("1111")
            .map { it.groupValues }.forEach { println(it) }
    }

    @Test
    fun `find`() {
        var result = "(1.1)".toRegex().find("1111")
        while (result != null) {
            println(result.range)
            result = result.next()
        }
    }


    @Test
    fun `findValidVariants matches more than one`() {
        val day12a = Day12a("input/12.test.txt")
        val variant = day12a.findValidVariants(".??..??...?##." to listOf(1, 1, 3))

        assertEquals(4, variant.count())
    }

    @Test
    fun `findValidVariants matches more than one 2`() {
        val day12a = Day12a("input/12.test.txt")

        val variant = day12a.findValidVariants("??..??...?##." to listOf(1, 1, 3))

        assertEquals(4, variant.count())
    }

    @Test
    fun solveTestInput() {
        assertEquals(21, Day12a("input/12.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(0, Day12a("input/12.txt").solve())
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