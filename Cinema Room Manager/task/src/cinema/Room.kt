package cinema

data class Room(val rowsCount: Int, val seatsPerRow: Int) {
    private val seatsCount = rowsCount * seatsPerRow
    var rows = Array(rowsCount) { Array(seatsPerRow) { 0 } }

    internal fun getSeatPrice(seat: Seat) =
            when (getSeatType(this, seat)) {
                SeatType.Expensive -> Cinema.expensiveSeatPrice
                SeatType.Cheap -> Cinema.cheapSeatPrice
            }

    private fun getSeatType(room: Room, seat: Seat): SeatType {
        return if (room.isSmallRoom()) {
            SeatType.Expensive
        } else { // big room
            if (seat.businessRowNumber in getBusinessRowNumbersOfExpensiveRows()) {
                SeatType.Expensive
            } else {
                SeatType.Cheap
            }
        }
    }

    internal fun bookSeat(seat: Seat) {
        if (seat.isInitialized()) {
            this.rows[seat.technicalRowNumber][seat.technicalSeatNumber] = SeatTypeNumber.Reserved.ordinal
        }
    }

    internal val percentageOfBookedSeats: Double
        get() = bookedSeatsCount * 100.0 / seatsCount

    internal val bookedSeatsCount: Int
        get() {
            var tickets = 0

            this.rows.forEach { row ->
                tickets += row.filter { value ->
                    value == SeatTypeNumber.Reserved.ordinal
                }.sum()
            }
            return tickets
        }

    /* Returns 0..0 if no rows found. */
    internal fun getBusinessRowNumbersOfExpensiveRows(): IntRange {
        if (rowsCount <= 0) {
            return 0..0
        }

        return if (isSmallRoom()) {
            1..rowsCount
        } else {
            1..(rowsCount / 2) // even: first half of rows, odd: first "smaller half"
        }
    }

    /* Returns 0..0 if no rows found. */
    internal fun getBusinessRowNumbersOfCheapRows(): IntRange {
        if (rowsCount <= 0) {
            return 0..0
        }

        return if (isSmallRoom()) {
            0..0
        } else {
            ((rowsCount + 2) / 2)..rows.count() // even: second half of rows, odd: second "half" + 1
        }
    }

    fun getCurrentIncome() = getIncomeFromBookedExpensiveSeats() + getIncomeFromBookedCheapSeats()

    private fun getIncomeFromBookedCheapSeats() =
            getBookedCheapSeatsCount(this) * Cinema.cheapSeatPrice

    private fun getIncomeFromBookedExpensiveSeats() =
            getBookedExpensiveSeatsCount(this) * Cinema.expensiveSeatPrice

    internal fun getExpensiveRows(): List<Array<Int>> {
        val rowNumbers = getBusinessRowNumbersOfExpensiveRows().toTechnicalValue()
        val rows = mutableListOf<Array<Int>>()
        if (rowNumbers == -1..-1) {
            return rows
        }
        for (i in rowNumbers) {
            rows.add(this.rows[i])
        }
        return rows
    }

    private fun cheapRows(): List<Array<Int>> {
        val rowNumbers = getBusinessRowNumbersOfCheapRows().toTechnicalValue()
        val rows = mutableListOf<Array<Int>>()
        if (rowNumbers == -1..-1) {
            return rows
        }
        for (i in rowNumbers) {
            rows.add(this.rows[i])
        }
        return rows
    }

    private fun isSmallRoom() = this.rowsCount * this.seatsPerRow <= 60

    fun calcIncome(rows: Int, seatsPerRow: Int) =
            getCheapSeatsCount(rows, seatsPerRow) * Cinema.cheapSeatPrice +
                    getExpensiveSeatsCount(rows, seatsPerRow) * Cinema.expensiveSeatPrice

    fun isSeatFree(seat: Seat) =
            rows[seat.technicalRowNumber][seat.technicalSeatNumber] == SeatTypeNumber.Free.ordinal

    fun isSeatPositionValid(seat: Seat) =
            seat.businessRowNumber in 1..rowsCount &&
                    seat.businessSeatNumber in 1..seatsPerRow

    companion object {
        internal fun getCheapSeatsCount(rows: Int, seatsPerRow: Int): Int =
                if (Room(rows, seatsPerRow).isSmallRoom()) { // small room
                    0
                } else { // big room
                    if (rows % 2 == 0) {
                        (rows / 2) * seatsPerRow
                    } else {
                        ((rows + 1) / 2) * seatsPerRow
                    }
                }

        internal fun getExpensiveSeatsCount(rows: Int, seatsPerRow: Int): Int =
                if (Room(rows, seatsPerRow).isSmallRoom()) { // small room
                    rows * seatsPerRow
                } else { // big room
                    if (rows % 2 == 0) {
                        (rows / 2) * seatsPerRow
                    } else {
                        (rows / 2) * seatsPerRow
                    }
                }

        fun getBookedExpensiveSeatsCount(room: Room): Int {
            var bookedSeatsCount = 0
            room.getExpensiveRows().forEach { row ->
                row.filter { seat ->
                    seat == SeatTypeNumber.Reserved.ordinal
                }.sum()
                        .also { sum -> bookedSeatsCount += sum }
            }
            return bookedSeatsCount
        }

        fun getBookedCheapSeatsCount(room: Room): Int {
            var bookedSeatsCount = 0
            room.cheapRows().forEach { row ->
                row.filter { seat ->
                    seat == SeatTypeNumber.Reserved.ordinal
                }.sum()
                        .also { sum -> bookedSeatsCount += sum }
            }
            return bookedSeatsCount
        }
    }
}

/* Transforms business values (1..n) to technical values (0..n-1) */
fun IntRange.toTechnicalValue() = this.first - 1 until this.last


