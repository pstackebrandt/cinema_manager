package cinema

fun main() {
    val cinema = Cinema()
    cinema.calcIncome()

    val show = false
    if (show) {
        cinema.show()
    }
}

class Cinema {
    private val expensiveSeatPrice = 10
    private val cheapSeatPrice = 8

    fun show() {
        println("Cinema:")
        println("""  1 2 3 4 5 6 7 8
1 S S S S S S S S
2 S S S S S S S S
3 S S S S S S S S
4 S S S S S S S S
5 S S S S S S S S
6 S S S S S S S S
7 S S S S S S S S""".trimMargin())
    }

    fun calcIncome() {
        println("Enter the number of rows:")
        val rows = readLine()!!.toInt()
        println("Enter the number of seats in each row:")
        val seatsPerRow = readLine()!!.toInt()
        println("Total income:")
        println("$${calcIncome(rows, seatsPerRow)}")
    }

    private fun calcIncome(rows: Int, seatsPerRow: Int) =
            getCheapSeats(rows, seatsPerRow) * cheapSeatPrice +
                    getExpensiveSeats(rows, seatsPerRow) * expensiveSeatPrice

    internal fun getCheapSeats(rows: Int, seatsPerRow: Int): Int =
            if (rows * seatsPerRow <= 60) { // small room
                0
            } else { // big room
                if (rows % 2 == 0) {
                    (rows / 2) * seatsPerRow
                } else {
                    ((rows + 1) / 2) * seatsPerRow
                }
            }

    internal fun getExpensiveSeats(rows: Int, seatsPerRow: Int): Int =
            if (rows * seatsPerRow <= 60) { // small room
                rows * seatsPerRow
            } else { // big room
                if (rows % 2 == 0) {
                    (rows / 2) * seatsPerRow
                } else {
                    (rows / 2) * seatsPerRow
                }
            }
}
