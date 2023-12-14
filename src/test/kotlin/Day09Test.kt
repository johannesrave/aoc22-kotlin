import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day09aTest {
    @Test
    fun solveTestInput() {
        assertEquals(114, Day09a("input/09.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(2038472161, Day09a("input/09.txt").solve())
    }
}

class Day09bSolutionTest {
    @Test
    fun solveTestInput() {
        assertEquals(2, Day09b("input/09.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(1091, Day09b("input/09.txt").solve())
    }
}