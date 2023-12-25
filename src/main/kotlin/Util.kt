fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }

data class Point2D(val x: Int, val y: Int)

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

fun Array<CharArray>.transpose(): Array<CharArray> = this.first()
    .mapIndexed { x, _ -> this.map { it[x] }.toCharArray() }.toTypedArray()

fun Array<CharArray>.rotateCW(): Array<CharArray> = this.transpose().map { it.reversedArray() }.toTypedArray()
fun Array<CharArray>.rotateCCW(): Array<CharArray> = this.transpose().reversedArray()
fun Array<CharArray>.tileExists(x: Int, y: Int) = this.getOrNull(y)?.getOrNull(x) != null

fun Array<IntArray>.tileExists(x: Int, y: Int) = this.getOrNull(y)?.getOrNull(x) != null