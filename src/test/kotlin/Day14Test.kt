import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

class Day14aTest {
    @Test
    fun `sortedWith GravityOrdering works correctly `() {

        val toSort = ".O.#......".toCharArray()

        val ordering = Day14a.GravityOrdering()
        val sorted = toSort.sortedWith(ordering).toCharArray()

        val result = "..O#......".toCharArray()

        assertContentEquals(result, sorted)
    }

    @Test
    fun solveTestInput() {
        assertEquals(136, Day14a("input/14.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(0, Day14a("input/14.txt").solve())
    }
}

class Day14bTest {
    @Test
    fun solveTestInput() {
        assertEquals(0, Day14b("input/14.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(0, Day14b("input/14.txt").solve())
    }
}