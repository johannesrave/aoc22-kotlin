import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class Day11aTest {
    @Test
    fun solveTestInput() {
        assertEquals(374, Day11a("input/11.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(-1, Day11a("input/11.txt").solve())
    }
}

class Day11bTest {
    @Test
    fun solveTestInput() {
        assertEquals(-1, Day11b("input/11.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(-1, Day11b("input/11.txt").solve())
    }
}