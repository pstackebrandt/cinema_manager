package cinema

data class Room(val rowsCount: Int, val seatsPerRow: Int) {
    var rows = Array(rowsCount) { Array(seatsPerRow) { 0 } }
}