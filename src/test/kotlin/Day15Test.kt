import kotlin.test.Test
import kotlin.test.assertEquals

class Day15aTest {
    @Test
    fun solveTestInput() {
        assertEquals(1320, Day15a("input/15.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(494980, Day15a("input/15.txt").solve())
    }
}

class Day15bTest {
    @Test
    fun solveTestInput() {
        assertEquals(145, Day15b("input/15.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(247933, Day15b("input/15.txt").solve())
    }
}