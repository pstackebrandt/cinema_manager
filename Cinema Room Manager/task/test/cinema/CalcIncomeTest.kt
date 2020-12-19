package cinema

import org.junit.Test

import org.junit.Assert.*

class CalcIncomeTest {
    @Test
    fun seats_for_small_room() {
        assertTrue(true)
    }

    class GetCheapSeatsTest {
        @Test
        fun should_return_0_seats_for_small_room() {
            val cinema = Cinema()
            var actual = cinema.getCheapSeatsCount(2, 4)
            assertEquals(0, actual)
        }

        @Test
        fun should_return_seats_for_big_room_with_no_odd_rows() {
            val cinema = Cinema()
            var actual = cinema.getCheapSeatsCount(10, 7)
            assertEquals(35, actual)
        }

        @Test
        fun should_return_seats_for_big_room_with_odd_rows() {
            val cinema = Cinema()
            var actual = cinema.getCheapSeatsCount(9, 7)
            assertEquals(35, actual)
        }
    }

    class GetExpensiveSeatsTest {
        @Test
        fun should_return_seats_for_big_room() {
            val cinema = Cinema()
            var actual = cinema.getExpensiveSeatsCount(2, 4)
            assertEquals(8, actual)
        }

        @Test
        fun should_return_seats_for_big_room_with_no_odd_rows() {
            val cinema = Cinema()
            var actual = cinema.getExpensiveSeatsCount(10, 7)
            assertEquals(35, actual)
        }

        @Test
        fun should_return_seats_for_big_room_with_odd_rows() {
            val cinema = Cinema()
            var actual = cinema.getExpensiveSeatsCount(9, 7)
            assertEquals(28, actual)
        }
    }
}