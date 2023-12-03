import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day03aTest {

    @Test
    fun solve() {
        assertEquals(4361, Day03a("input/03.test.txt").solve())
    }
}

class Day03bTest {

    @Test
    fun solve() {
        assertEquals(467835, Day03b("input/03.test.txt").solve())
    }
}