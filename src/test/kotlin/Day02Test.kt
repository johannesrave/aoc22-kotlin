import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day02aTest {

    @Test
    fun solve() {
        assertEquals(8, Day02a("input/02.test.txt").solve())
    }
}

class Day02bTest {

    @Test
    fun solve() {
        assertEquals(2286, Day02b("input/02.test.txt").solve())
    }
}