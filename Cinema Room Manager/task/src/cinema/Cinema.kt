package cinema

fun main() {
    val cinema = Cinema()
    cinema.show()
}

class Cinema {
    fun show(){
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
}
