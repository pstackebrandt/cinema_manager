package cinema

data class Seat(val rowNumber: Int, val seatNumber: Int){
    fun isInitialized() = rowNumber > 0 || seatNumber > 0
}


