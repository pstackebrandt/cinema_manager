package cinema

/**
@param businessRowNumber starts with 1.
@param businessSeatNumber starts with 1.
 */
data class Seat(val businessRowNumber: Int, val businessSeatNumber: Int){
    fun isInitialized() = businessRowNumber > 0 || businessSeatNumber > 0
}


