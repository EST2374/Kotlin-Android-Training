package org.example

fun main() {

    // Aufgabe 0
    val liste = listOf<Int>(1,2,3,4,5,6,7,8,9,10)
    val (odd, even) = splitOddEven(liste)
    println("Odd: $odd\nEven: $even")

    // Aufgabe 1
    println("Verdoppelt: ${verdopple(liste)}")

    // Aufgabe 2
    println("Summe & Durchschnitt: ${summeUndDurchschnitt(liste)}")

    // Aufgabe 3
    val woerter = listOf("Apfel", "Birne", "Kiwi", "Ananas", "Feige")
    println("Nach Länge gruppiert: ${gruppiereNachLaenge(woerter)}")

    // Aufgabe 4
    println("Min & Max: ${minUndMax(liste)}")

    // Aufgabe 5
    val gemischt = listOf(4, null, 7, null, 2, 9, null)
    println("Ohne Null (Elvis-Ersatz 0): ${ersetzeNullDurchNull(gemischt)}")

    // Aufgabe 6
    println("Ist aufsteigend sortiert: ${istSortiert(liste)}")

    val zahlen = listOf(15, 3, 42, 8, 23, 4, 16, 55, 9, 30)
    val namen = listOf("Anna", "Ben", "Clara", "David", "Eva", "Finn", "Greta")
    val bestellungen = listOf(
        Bestellung("Apfel", 3, 0.5),
        Bestellung("Birne", 2, 0.8),
        Bestellung("Apfel", 5, 0.5),
        Bestellung("Kiwi", 1, 1.2),
        Bestellung("Birne", 4, 0.8)
    )

    // Aufgabe 7
    println("Primzahlen: ${findePrimzahlen(zahlen)}")

    // Aufgabe 8
    println("Namen nach Anfangsbuchstabe, nur Anzahl: ${zaehleNachAnfangsbuchstabe(namen)}")

    // Aufgabe 9
    println("Gesamtsumme pro Produkt: ${summeProProdukt(bestellungen)}")

    // Aufgabe 10
    println("Erste Zahl > 20, die durch 4 teilbar ist: ${ersteGrosseDurchVierteilbare(zahlen)}")

    // Aufgabe 11
    println("Enthält nur gerade Zahlen über 10? ${alleGeradeUeber10(listOf(12, 14, 16))}")

    // Aufgabe 12
    println("Teuerste Bestellung (Menge*Preis): ${teuersteBestellung(bestellungen)}")

}

data class Bestellung(val produkt: String, val menge: Int, val preis: Double)


// Aufgabe 0
fun splitOddEven(liste: List<Int>): Pair<List<Int>, List<Int>> {
    val evenList: List<Int> = liste.filter { it % 2 == 0 }
    val oddList: List<Int> = liste.filter { it % 2 == 1 }
    return Pair(oddList, evenList)
}

// Aufgabe 1: Jede Zahl der Liste verdoppeln
fun verdopple(liste: List<Int>): List<Int> {
    return liste.map { 2 * it }
}

// Aufgabe 2: Summe und Durchschnitt als Pair zurückgeben
fun summeUndDurchschnitt(liste: List<Int>): Pair<Int, Double> {
    val sum = liste.sum()
    val durchschnitt = liste.average()
    return Pair(sum, durchschnitt)
}

// Aufgabe 3: Wörter nach ihrer Länge gruppieren (Map<Int, List<String>>)
fun gruppiereNachLaenge(woerter: List<String>): Map<Int, List<String>> {
    return woerter.groupBy { it.length }
}

// Aufgabe 4: kleinstes und größtes Element als Pair zurückgeben
fun minUndMax(liste: List<Int>): Pair<Int, Int> {
    return Pair(liste.min(), liste.max())
}

// Aufgabe 5: Liste mit Int? enthält null-Werte — jeden null durch 0 ersetzen
// (Elvis-Operator nutzen)
fun ersetzeNullDurchNull(liste: List<Int?>): List<Int> {
    return liste.map { it ?: 0 }
}

// Aufgabe 6: prüfen ob die Liste aufsteigend sortiert ist (Boolean)
fun istSortiert(liste: List<Int>): Boolean {
    return liste == liste.sorted()
}

//////////////////////////
//////    HARDER    //////
//////////////////////////

// Aufgabe 7: alle Primzahlen aus der Liste filtern
fun findePrimzahlen(liste: List<Int>): List<Int> {
    return liste.filter { findPrim(it) }
}

private fun findPrim(int: Int): Boolean {
    if (int < 2) return false
    for (i in 2..<int) {
        if (int % i == 0) {
            return false
        }
    }
    return true
}

// Aufgabe 8: Namen nach erstem Buchstaben gruppieren, aber nur die
// Anzahl pro Buchstabe zurückgeben (Map<Char, Int>), nicht die Namen selbst
fun zaehleNachAnfangsbuchstabe(namen: List<String>): Map<Char, Int> {
    val groupedByFirstLetter = namen.groupBy { it[0] }
    return groupedByFirstLetter.mapValues { it.value.size }
}

// Aufgabe 9: pro Produkt die Gesamtsumme (menge * preis) berechnen
// (Map<String, Double>) — mehrere Bestellungen desselben Produkts zusammenzählen
fun summeProProdukt(bestellungen: List<Bestellung>): Map<String, Double> {
    val groupedByProduct = bestellungen.groupBy { it.produkt }
    return groupedByProduct.mapValues { (_, product) ->
        product.sumOf { it.preis * it.menge }
    }
}

// Aufgabe 10: die erste Zahl finden, die > 20 UND durch 4 teilbar ist
// (null falls keine existiert)
fun ersteGrosseDurchVierteilbare(liste: List<Int>): Int? {
    val greater20 = liste.filter { it > 20 }
    val teilbar4 = greater20.find { it % 4 == 0 }
    return teilbar4
}

// Aufgabe 11: prüfen ob ALLE Zahlen gerade UND größer als 10 sind
fun alleGeradeUeber10(liste: List<Int>): Boolean {
    return liste.all { it % 2 == 0 && it > 10 }
}

// Aufgabe 12: die Bestellung mit dem höchsten Gesamtwert (menge * preis) zurückgeben
fun teuersteBestellung(bestellungen: List<Bestellung>): Bestellung? {
    val gesPreise = bestellungen.map { it.menge * it.preis }
    val maxGesPreis = gesPreise.max()
    return bestellungen.find { it.preis * it.menge == maxGesPreis }
}