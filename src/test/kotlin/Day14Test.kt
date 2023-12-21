import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

class Day14aTest {
    @Test
    fun solveTestInput() {
        assertEquals(136, Day14a("input/14.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(108935, Day14a("input/14.txt").solve())
    }
}

class Day14bTest {
    @Test
    fun solveTestInput() {
        assertEquals(0, Day14b("input/14.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(0, Day14b("input/14.txt").solve())
    }
}