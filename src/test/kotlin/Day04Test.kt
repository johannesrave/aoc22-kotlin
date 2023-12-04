import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day04aTest {

    @Test
    fun solve() {
        assertEquals(13, Day04a("input/04.test.txt").solve())
    }
}

class Day04bTest {

    @Test
    fun solve() {
        assertEquals(467835, Day04b("input/04.test.txt").solve())
    }
}