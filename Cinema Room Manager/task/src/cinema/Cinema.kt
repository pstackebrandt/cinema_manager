package cinema

import java.util.*

fun main() {
    val calcIncome = false

    val cinema = Cinema()
    cinema.start()

    // statistics
    // current income
    // total income
    // number of free seats
    // percentage of occupancy
//    "Number of purchased tickets: 0"
//    "Percentage: 0.00%"
//    "Current income: $0"
//    "Total income: $360"

    // buy free seat only
    // "That ticket has already been purchased!"
    // "Wrong input!"
    if (calcIncome) {
        cinema.calcIncome()
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
    private val cheapSeatPrice = 8
    private val expensiveSeatPrice = 10
    private var room: Room = Room(0, 0)

    fun start() {
        createRoom()
        showMenu()
    }

    private fun showMenu() {
        while (true) {
            when (chooseMenu()) {
                1 -> {
                    showRoom()
                }
                2 -> {
                    val seat = getSeat()
                    val price = getSeatPrice(room, seat)

                    showSeatPrice(price)
                    addSeatRequest(room, seat)
                    showRoom()
                }
                else -> { // 0 -> exit
                    return
                }
            }
        }
    }

    private fun chooseMenu(): Int {
        println("\n1. Show the seats")
        println("2. Buy a ticket")
        println("0. Exit")

        val scanner = Scanner(System.`in`)
        return scanner.nextInt()
    }

    private fun showRoom() {
        println("\nCinema:")
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
        println("\nTotal income:")
        println("$${calcIncome(room.rowsCount, room.seatsPerRow)}")
    }

    private fun createRoom() {
        println("\nEnter the number of rows:")
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

    private fun getSeat(): Seat {
        println("\nEnter a row number:")
        val rowNumber = readLine()!!.toInt()
        println("Enter a seat number in that row:")
        val seatNumber = readLine()!!.toInt()
        return Seat(rowNumber, seatNumber)
    }

    private fun showSeatPrice(price: Int) {
        println("Ticket price: $${price}")
    }

    private fun getSeatPrice(room: Room, seat: Seat) =
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
    private fun addSeatRequest(room: Room, seat: Seat) {
        if (seat.isInitialized()) {
            room.rows[seat.rowNumber - 1][seat.seatNumber - 1] = SeatTypeNumber.Reserved.ordinal
        }
    }
}