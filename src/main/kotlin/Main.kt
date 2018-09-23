import java.io.FileWriter
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import kotlin.system.measureTimeMillis

/**
 * Basic optimized implementation of algorithm
 */
fun calc(iterations: Long): BigDecimal {
    var n = BigInteger.valueOf(0)
    var d = BigInteger.valueOf(1)
    println("Start of calculation...")
    for (i in iterations downTo 1) {
        d *= BigInteger.valueOf(i)
        n += d
    }
    println("End calculating n and d...")
    val bdN = BigDecimal(n, 1_000_000)
    println("End converting n to BigDecimal")
    val bdD = BigDecimal(d, 1_000_000)
    println("End converting d to BigDecimal")
    val result = bdN / bdD
    println("End calculating the result")
    return result
}

/**
 * Fast algorithm based on continued fraction
 */
fun calc2(iterations: Long): BigDecimal {
    var n = BigInteger.ONE // Set numerator to 1
    var d = BigInteger.valueOf(4 * iterations + 2) // Set denominator to the last term
    var m: Long // current term multiplier
    var a: BigInteger // temp variable
    println("Start of calculation...")
    for (i in iterations downTo 1) {
        m = 4 * i + 2
        a = BigInteger.valueOf(m) * d + n
        n = d
        d = a
    }
    println("End calculating n and d")
    val precision = 1_000_000
    val mathContext = MathContext(precision)
    val bdN = BigDecimal(n, 0, mathContext).setScale(precision)
    println("End converting n to BigDecimal")
    val bdD = BigDecimal(d, 0, mathContext).setScale(precision)
    println("End converting d to BigDecimal")
    // Calculate 1 + 2 / (1 + n / d)
    val td = bdN / bdD + BigDecimal.ONE
    val result = 1.toBigDecimal() + 2.toBigDecimal().setScale(precision) / td
    println("End calculating the result")
    return result
}

fun pretyPrinter(name: String, f: (i: Long) -> BigDecimal, iterations: Long) {
    println("------------------------------")
    println("Algorithm: $name")
    println("------------------------------")
    var output: BigDecimal = BigDecimal.ONE
    val millis = measureTimeMillis {
        output = f(iterations)
    }
    val escapedName = name.replace("\\W+".toRegex(), "_").toLowerCase()
    val writer = FileWriter("e_$escapedName.txt")
    writer.write(output.toPlainString())
    writer.close()
    println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
    println("Time spent: ${millis / 1000} seconds")
    println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")

}

fun main(args: Array<String>) {
    pretyPrinter("Fast", ::calc2, 97_000)
    pretyPrinter("Basic optimized", ::calc, 250_000)
}
