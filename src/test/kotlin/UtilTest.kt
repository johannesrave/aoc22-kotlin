import kotlin.test.Test
import kotlin.test.assertContentEquals

class UtilTest {
    @Test
    fun rotate() {
        val board = arrayOf(
            "ABC".toCharArray(),
            "111".toCharArray(),
            "111".toCharArray(),
        )

        val boardRotatedClockwise = arrayOf(
            "11A".toCharArray(),
            "11B".toCharArray(),
            "11C".toCharArray(),
        )

        board.rotateCW().forEachIndexed { i, row ->
            assertContentEquals(row, boardRotatedClockwise[i])
        }

        val boardRotatedCounterClockwise = arrayOf(
            "C11".toCharArray(),
            "B11".toCharArray(),
            "A11".toCharArray(),
        )

        board.rotateCCW().forEachIndexed { i, row ->
            assertContentEquals(row, boardRotatedCounterClockwise[i])
        }
    }
}