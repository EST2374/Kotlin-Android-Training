fun main() {

    // Double Division bei Kotlin -> Keine Exception
    // Bei Int schon
    println(0.0 / 0.0) // NaN
    println(10.0 / 0.0) // Infinity

    // Try - Catch
    fun sicherTeilen(a: Int, b: Int): Int {
        return try {
            a / b
        } catch (e: Exception) {
            -1
        }
    }

    // Finally meist für Cleanup / Logging
    fun test(): Int {
        try {
            println("try")
            return 1
        } finally {
            println("finally")
        }
    }

    fun test2(): Int {
        try {
            return 1
        } finally {
            return 2
        }
    }

    test()
    println(test2())

    // Eigene Exception
    fun ziehAb(betrag: Int) {
        if (betrag < 0) {
            throw NegativAmountException("Negative Zahl")
        }
        println("Abgezogen: $betrag")
    }

    try {
        ziehAb(-5)
    } catch (e: NegativAmountException) {
        println("Fehler: ${e.message}")
    }

    fun wende2xAn(zahl: Int, operation: (Int) -> Int): Int {
        val erg1 = operation(zahl)
        return operation(erg1)
    }
    println(wende2xAn(3) { it * 2 })

    fun wende2xAn(zahl: Int, wiederholungen: Int, operation: (Int) -> Int): Int {
        return operation(operation(zahl))
    }
    println(wende2xAn(3,4) { it + 2 })
}

// Eigene Exception
class NegativAmountException(message: String): Exception(message)