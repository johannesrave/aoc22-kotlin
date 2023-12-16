import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class Day10aTest {
    @Test
    fun solveTestInput() {
        assertEquals(8, Day10a("input/10.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(6979, Day10a("input/10.txt").solve())
    }
}

class Day10bSolutionTest {
    @Test
    fun solveTestInput() {
        assertEquals(4, Day10b("input/10b2.test.txt").solve())
        assertEquals(8, Day10b("input/10b1.test.txt").solve())
        assertEquals(10, Day10b("input/10b.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertTrue(Day10b("input/10.txt").solve() > 441)
        assertEquals(0, Day10b("input/10.txt").solve())
        // 441 is too low
    }
}