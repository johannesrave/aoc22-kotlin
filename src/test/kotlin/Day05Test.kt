import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day05aTest {

    @Test
    fun solveTestInput() {
        assertEquals(35, Day05a("input/05.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(3374647, Day05a("input/05.txt").solve())
    }
}

class Day05bTest {

//    @Test
//    fun solve() {
//        assertEquals(30, Day05b("input/05.test.txt").solve())
//    }
}