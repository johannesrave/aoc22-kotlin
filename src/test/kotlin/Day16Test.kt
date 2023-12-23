import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day16aTest {
    @Test
    fun solveTestInput() {
        assertEquals(21, Day16a("input/16.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(0, Day16a("input/16.txt").solve())
    }
}

class Day16bTest {
    @Test
    fun solveTestInput() {
        assertEquals(0, Day16b("input/16.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(0, Day16b("input/16.txt").solve())
    }
}