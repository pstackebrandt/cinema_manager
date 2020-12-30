package cinema

/**
@param businessRowNumber starts with 1.
@param businessSeatNumber starts with 1.
 */
data class Seat(val businessRowNumber: Int, val businessSeatNumber: Int) {
    fun isInitialized() = businessRowNumber > 0 || businessSeatNumber > 0

    /** lowest value is 0 */
    val technicalRowNumber = businessRowNumber - 1

    /** lowest value is 0 */
    val technicalSeatNumber = businessSeatNumber - 1

    companion object {
        /** Get default seat. Such seat can't exist in a room. */
        var default = Seat(0, 0)
    }
}


