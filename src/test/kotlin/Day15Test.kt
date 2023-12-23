import kotlin.test.Test
import kotlin.test.assertEquals

class Day15aTest {
    @Test
    fun solveTestInput() {
        assertEquals(1320, Day15a("input/15.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(0, Day15a("input/15.txt").solve())
    }
}

class Day15bTest {
    @Test
    fun solveTestInput() {
        assertEquals(0, Day15b("input/15.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(0, Day15b("input/15.txt").solve())
    }
}