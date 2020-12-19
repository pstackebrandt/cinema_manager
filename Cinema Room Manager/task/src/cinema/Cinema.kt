package cinema

fun main() {
    val show = true
    val calcIncome = false
    val showSeatPrice = true

    val cinema = Cinema()
    cinema.createRoom()
    if (calcIncome) {
        cinema.calcIncome()
    }
    if (show) {
        cinema.show()
    }
    if (showSeatPrice) {
        val seat = cinema.getSeat()
        val price = cinema.getSeatPrice(cinema.room, seat)

        cinema.showSeatPrice(price)
        cinema.addSeatRequest(cinema.room, seat)
        cinema.show()
    }
}

enum class SeatTypeNumber {
    Free,
    Reserved
}


enum class SeatType {
    Expensive,
    Cheap
}

class Cinema {
    private val expensiveSeatPrice = 10
    private val cheapSeatPrice = 8
    var room: Room = Room(0, 0)

    fun show() {
        println("Cinema:")
        print(" ")
        repeat(room.seatsPerRow) { print(" ${it + 1}") }
        println()
        room.rows.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { seatIndex, seatValue ->
                if (seatIndex == 0) {
                    print(rowIndex + 1)
                }
                print(" ")

                val part = when (seatValue) {
                    SeatTypeNumber.Free.ordinal -> "S"
                    1 -> "B"
                    else -> "E"
                }
                print(part)

                if (seatIndex == row.lastIndex) {
                    println()
                }
            }
        }
    }

    fun calcIncome() {
        println("Total income:")
        println("$${calcIncome(room.rowsCount, room.seatsPerRow)}")
    }

    fun createRoom() {
        println("Enter the number of rows:")
        val rows = readLine()!!.toInt()
        println("Enter the number of seats in each row:")
        val seatsPerRow = readLine()!!.toInt()
        room = Room(rows, seatsPerRow)
    }

    private fun calcIncome(rows: Int, seatsPerRow: Int) =
            getCheapSeatsCount(rows, seatsPerRow) * cheapSeatPrice +
                    getExpensiveSeatsCount(rows, seatsPerRow) * expensiveSeatPrice

    internal fun getCheapSeatsCount(rows: Int, seatsPerRow: Int): Int =
            if (isSmallRoom(Room(rows, seatsPerRow))) { // small room
                0
            } else { // big room
                if (rows % 2 == 0) {
                    (rows / 2) * seatsPerRow
                } else {
                    ((rows + 1) / 2) * seatsPerRow
                }
            }

    internal fun getExpensiveSeatsCount(rows: Int, seatsPerRow: Int): Int =
            if (isSmallRoom(Room(rows, seatsPerRow))) { // small room
                rows * seatsPerRow
            } else { // big room
                if (rows % 2 == 0) {
                    (rows / 2) * seatsPerRow
                } else {
                    (rows / 2) * seatsPerRow
                }
            }

    fun getSeat(): Seat {
        println("Enter a row number:")
        val rowNumber = readLine()!!.toInt()
        println("Enter a seat number in that row:")
        val seatNumber = readLine()!!.toInt()
        return Seat(rowNumber, seatNumber)
    }

    fun showSeatPrice(price: Int) {
        println("Ticket price: $${price}")
    }

    fun getSeatPrice(room: Room, seat: Seat) =
            when (getSeatType(room, seat)) {
                SeatType.Expensive -> expensiveSeatPrice
                SeatType.Cheap -> cheapSeatPrice
            }

    private fun getSeatType(room: Room, seat: Seat): SeatType {
        return if (isSmallRoom(room)) {
            SeatType.Expensive
        } else { // big room
            if (seat.rowNumber in getExpensiveRowNumbers(room)) {
                SeatType.Expensive
            } else {
                SeatType.Cheap
            }
        }
    }

    private fun getExpensiveRowNumbers(room: Room): IntRange {
        if (room.rowsCount <= 0) {
            return 0..0
        }

        return if (isSmallRoom(room)) {
            1..room.rowsCount
        } else {
            1..(room.rowsCount / 2) // first half of rows OR first "smaller half"
        }
    }

    private fun isSmallRoom(room: Room) = room.rowsCount * room.seatsPerRow <= 60
    fun addSeatRequest(room: Room, seat: Seat) {
        if (seat.isInitialized()) {
            room.rows[seat.rowNumber - 1][seat.seatNumber - 1] = SeatTypeNumber.Reserved.ordinal
        }
    }
}