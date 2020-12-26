fun main() {
    readLine()?.run { toInt() + 1 }
            .also { println(it) }
}