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
            val actual = Room.getCheapSeatsCount(2, 4)
            assertEquals(0, actual)
        }

        @Test
        fun should_return_seats_for_big_room_with_no_odd_rows() {
            val actual = Room.getCheapSeatsCount(10, 7)
            assertEquals(35, actual)
        }

        @Test
        fun should_return_seats_for_big_room_with_odd_rows() {
            val actual = Room.getCheapSeatsCount(9, 7)
            assertEquals(35, actual)
        }
    }

    class GetExpensiveSeatsTest {
        @Test
        fun should_return_seats_for_big_room() {
            val actual = Room.getExpensiveSeatsCount(2, 4)
            assertEquals(8, actual)
        }

        @Test
        fun should_return_seats_for_big_room_with_no_odd_rows() {
            val actual = Room.getExpensiveSeatsCount(10, 7)
            assertEquals(35, actual)
        }

        @Test
        fun should_return_seats_for_big_room_with_odd_rows() {
            val actual = Room.getExpensiveSeatsCount(9, 7)
            assertEquals(28, actual)
        }
    }
}