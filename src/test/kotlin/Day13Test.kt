import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertTrue

class Day13aTest {
    @Test
    fun transpose() {
        val board = arrayOf(
            arrayOf('X', 'X', 'X').toCharArray(),
            arrayOf('O', 'O', 'O').toCharArray(),
            arrayOf('O', 'O', 'O').toCharArray(),
        )

        val transposedBoard = arrayOf(
            arrayOf('X', 'O', 'O').toCharArray(),
            arrayOf('X', 'O', 'O').toCharArray(),
            arrayOf('X', 'O', 'O').toCharArray(),
        )

        board.transpose().forEachIndexed { i, row ->
            assertContentEquals(row, transposedBoard[i])
        }
    }

    @Test
    fun solveTestInput() {
        assertEquals(405, Day13a("input/13.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(34772, Day13a("input/13.txt").solve())
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