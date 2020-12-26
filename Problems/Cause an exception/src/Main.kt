fun main() {
    val letters = arrayOf('a', 'b', 'c')
//    for (i in 0..4) { // this code leads to java.lang.ArrayIndexOutOfBoundsException
//        println(letters[i]) //  but not to approval by the tests ;-)
//    }

    println(letters[0].toInt() / 0)
}
