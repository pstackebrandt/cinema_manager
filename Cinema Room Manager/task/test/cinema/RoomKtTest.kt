package cinema

import org.junit.Assert.assertEquals
import org.junit.Test

@Suppress("unused")
class RoomKtTest {
    class GetCountOfPurchasedTicketsTest {
        @Test
        fun should_count_0_tickets() {
            val room = Room(3, 2)
            val actual = room.bookedSeatsCount
            assertEquals(0, actual)
        }

        @Test
        fun should_count_2_tickets() {
            val room = Room(3, 2)

            val actual = room.bookedSeatsCount
            assertEquals(0, actual)
        }
    }

    class BookSeatTest {
        @Test
        fun should_book_1_seat() {
            // arrange
            val room = Room(3, 2)
            assertEquals(0, room.bookedSeatsCount)
            // act
            room.bookSeat(Seat(businessRowNumber = 1, businessSeatNumber = 2))
            // assert
            assertEquals(1, room.bookedSeatsCount)
        }
    }

    class PercentageOfBookedSeatsTes {
        @Test
        fun should_return_25() {
            val room = Room(4, 2)
            room.bookSeat(Seat(1, 1))
            room.bookSeat(Seat(4, 2))

            val actual = room.percentageOfBookedSeats
            assertEquals(25.0, actual, 0.1)
        }

        @Test
        fun should_return_0() {
            val room = Room(2, 2)
            val actual = room.percentageOfBookedSeats
            assertEquals(0.0, actual, 0.1)
        }
    }

    class GetBookedExpensiveSeatsCountTest {
        @Test
        fun should_return_0() {
            val room = Room(2, 2)
            val actual = Room.getBookedExpensiveSeatsCount(room)
            assertEquals(0, actual)
        }
    }

    class GetBookedCheapSeatsCountTest {
        @Test
        fun should_return_0() {
            val room = Room(2, 2)
            room.bookSeat(Seat(1, 1))
            room.bookSeat(Seat(2, 2))
            val actual = Room.getBookedCheapSeatsCount(room)
            assertEquals(0, actual)
        }
    }

    class GetCheapRowNumbersTest {
        @Test
        fun should_return_no_rows_in_small_room() {
            val room = Room(5, 2)
            val actual = room.getBusinessRowNumbersOfCheapRows()
            assertEquals(0..0, actual)
        }

        @Test
        fun should_return_rows_of_large_room_with_even_rows_count() {
            val room = Room(10, 10)
            val actual = room.getBusinessRowNumbersOfCheapRows()
            assertEquals(6..10, actual)
        }

        @Test
        fun should_return_rows_0f_large_room_with_odd_rows_count() {
            val room = Room(11, 10)
            val actual = room.getBusinessRowNumbersOfCheapRows()
            assertEquals(6..11, actual)
        }
    }

    class GetExpensiveRowNumbersTest {
        @Test
        fun should_return_rows_0f_large_room_with_even_rows_count() {
            val room = Room(10, 10)
            val actual = room.getBusinessRowNumbersOfExpensiveRows()
            assertEquals(1..5, actual)
        }

        @Test
        fun should_return_rows_0f_large_room_with_odd_rows_count() {
            val room = Room(11, 10)
            val actual = room.getBusinessRowNumbersOfExpensiveRows()
            assertEquals(1..5, actual)
        }
    }

    class ExpensiveRowsTest {
        @Test
        fun should_return_expensive_rows_of_even_row_count() {
            val room = Room(10, 10)
            room.bookSeat(Seat(1, 1))
            room.bookSeat(Seat(5, 1))
            val actualRows = room.getExpensiveRows()
            assertEquals(5, actualRows.count())
            assertEquals(2, actualRows.count { row -> row.contains(SeatTypeNumber.Reserved.ordinal) })
        }
    }
}
