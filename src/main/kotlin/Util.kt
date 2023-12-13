fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }

// got this from https://www.baeldung.com/kotlin/lcm
fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

fun List<Long>.findLCM(): Long {
    var result = this[0]
    for (i in 1..<size) {
        result = findLCM(result, this[i])
    }
    return result
}