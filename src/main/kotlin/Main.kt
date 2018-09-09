import java.io.FileWriter
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.system.measureTimeMillis

fun calc(iterations: Long): BigDecimal {
    var n = BigInteger("0")
    var f = BigInteger("1")
    println("Starting...")
    for (i in iterations downTo 1) {
        f *= BigInteger(i.toString())
        n += f
    }
    println("End calculating n and f...")
    val bdN = BigDecimal(n).setScale(1_000_000, RoundingMode.UNNECESSARY)
    println("End converting n to BigDecimal")
    val bdF = BigDecimal(f).setScale(1_000_000, RoundingMode.UNNECESSARY)
    println("End converting f to BigDecimal")
    val result = bdN / bdF
    println("End calculating result")
    return result
}

fun main(args: Array<String>) {
    val millis = measureTimeMillis {
        val e = calc(250_000)
        val writer = FileWriter("e.txt")
        writer.write(e.toPlainString())
        writer.close()
    }
    println("--------------------------")
    println("          Yohooo          ")
    println("It took ${millis / 1000} seconds to complete")
}
