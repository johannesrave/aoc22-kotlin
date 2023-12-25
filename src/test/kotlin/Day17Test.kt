import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day17aTest {
    @Test
    fun solveTestInput() {
        assertEquals(102, Day17a("input/17.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(0, Day17a("input/17.txt").solve())
    }
}

class Day17bTest {
    @Test
    fun solveTestInput() {
        assertEquals(0, Day17b("input/17.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(0, Day17b("input/17.txt").solve())
    }
}