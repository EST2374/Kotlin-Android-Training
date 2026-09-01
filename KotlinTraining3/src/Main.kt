import kotlin.math.roundToInt

fun main() {


    // Safe Calls
    val name: String? = null
    // val laenge = name.length       // Compile-Error! Geht nicht direkt
    val laenge2 = name?.length     // funktioniert -> laenge2 ist null

    val stadt: String? = ""
    val stadt2: String? = null
    println(stadt?.uppercase()?.length)

    // Not-null
    val stadt3: String? = null
    // val laenge = stadt3!!.length // NullPointerException

    fun ersteZahlUeber(liste: List<Int>, grenze: Int): Int {
        // BESSER
        return liste.find { it > grenze } ?: -1
        /*
        val gefunden = liste.find { it > grenze }
        if (gefunden != null) {
            return gefunden
        }
        return -1
         */
    }

    // Sealed Klassen
    fun zeigeStatus(state: SongGuessUIState): String {
        val status: String = when(state) {
            is SongGuessUIState.Error -> "Error: ${state.message}"
            SongGuessUIState.Loading -> "Lädt..."
            is SongGuessUIState.Success -> "${state.songs.size} songs geladen"
        }
        return status
    }

    // Enums
    fun istWochenende(tag: Wochentag): Boolean {
        return when (tag) {
            Wochentag.SAMSTAG, Wochentag.SONNTAG -> true
            else -> false
        }
    }

    // Extension Functions
    fun String.istPalindrom(): Boolean {
        return this == this.reversed()
    }

    // Aufruf:
    "anna".istPalindrom()

    fun List<Int>.durchschnittGerundet(): Int {
        val avg = this.sum().toDouble() / this.size.toDouble()
        return avg.roundToInt()
    }
    println(listOf(1,2,4,7).durchschnittGerundet())


    // When
    fun bewerteNote(note: Int): String {
        val bewertung = when(note) {
            1 -> "Sehr gut"
            2,3 -> "Gut"
            in 4..5 -> "Befriedigend"
            else -> "Nicht genügend"
        }
        return bewertung
    }

    fun beschreibeZahl(zahl: Int): String {
        val beschreibung = when {
            zahl < 0 -> "negativ"
            zahl % 3 == 0 && zahl % 5 == 0 -> "FizzBuzz"
            zahl > 100 -> "groß"
            else -> "normal"
        }
        return beschreibung
    }

    // Generics
    fun <T> ersteZwei(liste: List<T>): Pair<T,T>? {
        if (liste.size < 2) return null
        val first = liste[0]
        val second = liste[1]
        return Pair(first, second)
    }


}

enum class Wochentag {
    MONTAG, DIENSTAG, MITTWOCH, DONNERSTAG, FREITAG, SAMSTAG, SONNTAG
}