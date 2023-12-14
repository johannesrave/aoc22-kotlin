import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day10aTest {
    @Test
    fun solveTestInput() {
        assertEquals(8, Day10a("input/10.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(0, Day10a("input/10.txt").solve())
    }
}

class Day10bSolutionTest {
    @Test
    fun solveTestInput() {
        assertEquals(0, Day10b("input/10.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(0, Day10b("input/10.txt").solve())
    }
}