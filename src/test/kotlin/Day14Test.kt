import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertTrue

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
    fun cycle() {
        val rocks = arrayOf(
            "O....#....",
            "O.OO#....#",
            ".....##...",
            "OO.#O....O",
            ".O.....O#.",
            "O.#..O.#.#",
            "..O..#O..O",
            ".......O..",
            "#....###..",
            "#OO..#....",
        ).map { it.toCharArray() }.toTypedArray()

        val cycledRocksA = arrayOf(
            ".....#....",
            "....#...O#",
            "...OO##...",
            ".OO#......",
            ".....OOO#.",
            ".O#...O#.#",
            "....O#....",
            "......OOOO",
            "#...O###..",
            "#..OO#....",
        ).map { it.toCharArray() }.toTypedArray()

        with(Day14b("input/14.test.txt")) {
            rocks.cycle().forEachIndexed { i, row ->
                assertContentEquals(row, cycledRocksA[i])
            }
        }

        val cycledRocksB = arrayOf(
            ".....#....",
            "....#...O#",
            ".....##...",
            "..O#......",
            ".....OOO#.",
            ".O#...O#.#",
            "....O#...O",
            ".......OOO",
            "#..OO###..",
            "#.OOO#...O",
        ).map { it.toCharArray() }.toTypedArray()

        with(Day14b("input/14.test.txt")) {
            rocks.cycle().cycle().forEachIndexed { i, row ->
                assertContentEquals(row, cycledRocksB[i])
            }
        }

        val cycledRocksC = arrayOf(
            ".....#....",
            "....#...O#",
            ".....##...",
            "..O#......",
            ".....OOO#.",
            ".O#...O#.#",
            "....O#...O",
            ".......OOO",
            "#...O###.O",
            "#.OOO#...O",
        ).map { it.toCharArray() }.toTypedArray()

        with(Day14b("input/14.test.txt")) {
            rocks.cycle().cycle().cycle().forEachIndexed { i, row ->
                assertContentEquals(row, cycledRocksC[i])
            }
        }
    }

    @Test
    fun solveTestInput() {
        assertEquals(64, Day14b("input/14.test.txt").solve())
    }

    @Test
    fun solveInput() {
        // i calculated this on paper but lost my thought...
        // the solution is in place 90 which is the remainder of the non-periodic part modulo the period plus the periodic part
        assertEquals(100876, Day14b("input/14.txt").solve())
    }
}