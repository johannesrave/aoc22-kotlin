import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day17aTest {
    @Test
    fun solveTestInput() {
        assertEquals(102, Day17a("input/17.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(742, Day17a("input/17.txt").solve())
    }
}

class Day17bTest {
    @Test
    fun solveTestBInput() {
        assertEquals(71, Day17b("input/17b.test.txt").solve())
    }

    @Test
    fun solveTestInput() {
        assertEquals(94, Day17b("input/17.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(918, Day17b("input/17.txt").solve())
    }
}