import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day07aTest {
    @Test
    fun solveTestInput() {
        assertEquals(6440, Day07a("input/07.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(-1, Day07a("input/07.txt").solve())
    }
}

class Day07bSolutionTest {

    @Test
    fun solveTestInput() {
        assertEquals(-1, Day07b("input/07.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(-1, Day07b("input/07.txt").solve())
    }
}