import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day08aTest {
    @Test
    fun solveTestInput() {
        assertEquals(6, Day08a("input/08.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(21883, Day08a("input/08.txt").solve())
    }
}

class Day08bSolutionTest {
    @Test
    fun solveTestInput() {
        assertEquals(6, Day08b("input/08b.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(-1, Day08b("input/08.txt").solve())
    }
}

class Day08bUnitTest {
}