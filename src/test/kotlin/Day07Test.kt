import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day07aTest {
    @Test
    fun solveTestInput() {
        assertEquals(6440, Day07a("input/07.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(253910319, Day07a("input/07.txt").solve())
    }
}

class Day07bSolutionTest {

    @Test
    fun solveTestInput() {
        assertEquals(5905, Day07b("input/07.test.txt").solve())
    }

    @Test
    fun solveInput() {
        assertEquals(254083736, Day07b("input/07.txt").solve())
    }
}

class Day07bUnitTest {

    @Test
    fun `Hand_occurrencesFrom with five Jokers`() {
        val testOccs = Day07b.Hand.occurrencesFrom(
            listOf(
                Day07b.Card.Joker,
                Day07b.Card.Joker,
                Day07b.Card.Joker,
                Day07b.Card.Joker,
                Day07b.Card.Joker,
            )
        )
        assertEquals(listOf(5), testOccs)
    }
}