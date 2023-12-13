import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day09aTest {
    @Test
    fun solveTestInput() {
        assertEquals(6, Day09a("input/09.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(-1, Day09a("input/09.txt").solve())
    }
}

class Day09bSolutionTest {
    @Test
    fun solveTestInput() {
        assertEquals(-1, Day09b("input/09.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(-1, Day09b("input/09.txt").solve())
    }
}