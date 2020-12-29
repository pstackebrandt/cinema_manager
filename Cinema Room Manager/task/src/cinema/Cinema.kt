package cinema

import java.util.*

fun main() {
    val calcIncome = false

    val cinema = Cinema()
    cinema.start()

//     statistics
//     current income
//     total income
//     number of free seats
//     percentage of occupancy
//    "Number of purchased tickets: 0"
//    "Percentage: 0.00%"
//    "Current income: $0"
//    "Total income: $360"
//
//     buy free seat only
//     "That ticket has already been purchased!"
//     "Wrong input!"
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
                    val price = room.getSeatPrice(seat)
                    showSeatPrice(price)
                    room.bookSeat(seat)
                    showRoom()
                }
                3 -> {
                    showStatistics(room)
                }
                else -> { // 0 -> exit
                    return
                }
            }
        }
    }

    private fun showStatistics(room: Room) {
        println()
        println(getNumberOfPurchasedTicketsMessage(room))
        println(getPercentageOfBookedSeatsMessage(room))
        println(getCurrentIncomeMessage(room))
        println(getTotalIncomeMessage())
    }

    private fun getCurrentIncomeMessage(room: Room) =
            "Current income: \$${room.getCurrentIncome()}"

    private fun getNumberOfPurchasedTicketsMessage(room: Room) =
            "Number of purchased tickets: ${room.bookedSeatsCount}"


    private fun chooseMenu(): Int {
        println("\n1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
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

    private fun showSeatPrice(price: Int) {
        println("Ticket price: $${price}")
    }

    private fun getTotalIncomeMessage() = "\nTotal income: \$${room.calcIncome(room.rowsCount, room.seatsPerRow)}"

    private fun createRoom() {
        println("\nEnter the number of rows:")
        val rows = readLine()!!.toInt()
        println("Enter the number of seats in each row:")
        val seatsPerRow = readLine()!!.toInt()
        room = Room(rows, seatsPerRow)
    }

    private fun getSeat(): Seat {
        println("\nEnter a row number:")
        val rowNumber = readLine()!!.toInt()
        println("Enter a seat number in that row:")
        val seatNumber = readLine()!!.toInt()
        return Seat(rowNumber, seatNumber)
    }

    companion object {
        const val cheapSeatPrice = 8
        const val expensiveSeatPrice = 10

        internal fun getPercentageOfBookedSeatsMessage(room: Room): String {
            val percentage = room.percentageOfBookedSeats
            return "Percentage: %.2f".format(locale = Locale.US, percentage) + "%"
        }
    }
}




