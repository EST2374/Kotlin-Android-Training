import kotlin.math.roundToInt

sealed class Ergebnis {
    object Unentschieden : Ergebnis()
    data class Sieger(val name: String, val punkte: Int) : Ergebnis()
    data class Ungueltig(val grund: String) : Ergebnis()
}

enum class Schwierigkeit { LEICHT, MITTEL, SCHWER }

fun main() {
    val spieler: String? = "Anna"
    val keinSpieler: String? = null

    val ergebnisse = listOf(
        Ergebnis.Sieger("Ben", 87),
        Ergebnis.Unentschieden,
        Ergebnis.Ungueltig("Abbruch"),
        Ergebnis.Sieger("Clara", 95)
    )

    // Aufgabe 1: Safe Call + Elvis
    println("Begrüßung: ${begruesse(spieler)}")
    println("Begrüßung: ${begruesse(keinSpieler)}")

    // Aufgabe 2: Sealed Class + when
    ergebnisse.forEach { println(beschreibeErgebnis(it)) }

    // Aufgabe 3: Enum + when
    println("Punkte-Multiplikator: ${multiplikator(Schwierigkeit.SCHWER)}")

    // Aufgabe 4: Extension Function
    println("Ist stark: ${listOf(10, 90, 30).istStarkesTeam()}")

    // Aufgabe 5: when ohne Argument
    println(bewerteZahl(-5))
    println(bewerteZahl(50))
    println(bewerteZahl(150))

    // Aufgabe 6: Generics
    println("Letztes Element: ${letztes(listOf(1, 2, 3))}")
    println("Letztes Element: ${letztes(listOf<String>())}")
}

// Aufgabe 1: Wenn spieler nicht null ist, gib "Hallo, {name}!" zurück,
// sonst "Hallo, Gast!" — mit ?. und ?: (kein if!)
fun begruesse(spieler: String?): String {
    return "Hallo, ${spieler ?: "Gast"}!"
}

// Aufgabe 2: Für jeden Ergebnis-Typ einen passenden String zurückgeben
// (Unentschieden -> "Unentschieden!", Sieger -> "{name} gewinnt mit {punkte} Punkten",
//  Ungueltig -> "Ungültig: {grund}") — exhaustives when, kein else
fun beschreibeErgebnis(ergebnis: Ergebnis): String {
    val message = when(ergebnis) {
        is Ergebnis.Sieger -> "${ergebnis.name} gewinnt mit ${ergebnis.punkte} Punkten"
        Ergebnis.Unentschieden -> "Unentschieden!"
        is Ergebnis.Ungueltig -> "Ungültig: ${ergebnis.grund}"
    }
    return message
}

// Aufgabe 3: LEICHT -> 1, MITTEL -> 2, SCHWER -> 3 — exhaustives when, kein else
fun multiplikator(schwierigkeit: Schwierigkeit): Int {
    val zahl = when(schwierigkeit) {
        Schwierigkeit.LEICHT -> 1
        Schwierigkeit.MITTEL -> 2
        Schwierigkeit.SCHWER -> 3
    }
    return zahl
}

// Aufgabe 4: Extension Function auf List<Int> — "stark" bedeutet:
// Durchschnitt der Liste ist über 40
fun List<Int>.istStarkesTeam(): Boolean {
    val avg = this.sum().toDouble() / this.size.toDouble()
    return avg > 40
}

// Aufgabe 5: when ohne Argument — negativ -> "negativ", 0..99 -> "normal",
// 100+ -> "hoch"
fun bewerteZahl(zahl: Int): String {
    val message = when {
        zahl < 0 -> "negativ"
        zahl in 0..99 -> "normal"
        else -> "hoch"
    }
    return message
}

// Aufgabe 6: generische Funktion, gibt das letzte Element der Liste zurück,
// oder null bei leerer Liste (KEIN !!)
fun <T> letztes(liste: List<T>): T? {
    if (liste.isEmpty()) return null
    return liste.last()
}