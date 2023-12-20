import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class Day13aTest {
    @Test
    fun solveTestInput() {
        assertEquals(405, Day13a("input/13.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertTrue(39526 > Day13a("input/13.txt").solve())
        assertEquals(0, Day13a("input/13.txt").solve())
    }
}

class Day13bTest {
    @Test
    fun solveTestInput() {
        assertEquals(0, Day13b("input/13.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(0, Day13b("input/13.txt").solve())
    }
}