import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day06aTest {

    @Test
    fun solveTestInput() {
        assertEquals(288, Day06a("input/06.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(771628, Day06a("input/06.txt").solve())
    }
}

class Day06bSolutionTest {

    @Test
    fun solveTestInput() {
        assertEquals(46, Day06b("input/06.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(6082852, Day06b("input/06.txt").solve())
    }
}

class Day06bUnitTest {
}