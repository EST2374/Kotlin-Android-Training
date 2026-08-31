package org.example

import kotlin.system.measureNanoTime

fun main() {

    val bestellungen = listOf(
        Bestellung(1, "Anna", "Laptop", 800.0, 1, "offen"),
        Bestellung(2, "Bob", "Maus", 20.0, 2, "versandt"),
        Bestellung(3, "Anna", "Tastatur", 50.0, 1, "offen"),
        Bestellung(4, "Charlotte", "Monitor", 300.0, 2, "storniert"),
        Bestellung(5, "Bob", "Laptop", 800.0, 1, "offen"),
        Bestellung(6, "Anna", "Webcam", 60.0, 1, "versandt")
    )


    // SortedBy
    val nachPreis = bestellungen.sortedBy { it.preis }        // billigstes zuerst
    val teuersteZuerst = bestellungen.sortedByDescending { it.preis }  // teuerstes zuerst
    println(nachPreis)
    println(teuersteZuerst)
    val teuersteGesPreis = bestellungen.sortedByDescending { it.preis * it.menge }
    println(teuersteGesPreis)

    // FirstOrNull und Find
    val ersteOffene = bestellungen.firstOrNull { it.status == "offen" }
    println(ersteOffene)
    // Bestellung(1, "Anna", ...) – oder null, wenn keine offene existiert
    val bobVersand = bestellungen.firstOrNull { it.status == "versandt" && it.kunde == "Bob" }
    println(bobVersand)

    // Any / All / None
    val hatOffene = bestellungen.any { it.status == "offen" }      // true
    val alleVersandt = bestellungen.all { it.status == "versandt" } // false
    val anyCharlotte = bestellungen.any { it.status == "storniert" && it.kunde == "Charlotte"}
    val allGreater10Euros = bestellungen.all { it.preis * it.menge > 10 }

    // Elvis Operator
    val ergebnis = bobVersand?.kunde ?: "Keine gefunden"

    // Sealed Class
    fun beschreibung(status: Status): String = when (status) {
        is Status.Offen -> "Wird bearbeitet"
        is Status.Versandt -> "Unterwegs"
        is Status.Storniert -> "Storniert"
        // kein "else" nötig!
    }

    fun istAktiv(status: Status): Boolean = when(status) {
        Status.Offen -> true
        Status.Storniert -> false
        Status.Versandt -> true
    }

}

data class Bestellung(val id: Int, val kunde: String, val artikel: String, val preis: Double, val menge: Int, val status: String)

sealed class Status {
    object Offen : Status()
    object Versandt : Status()
    object Storniert : Status()
}

data class Bestellung2(
    val id: Int,
    val kunde: String,
    val preis: Double,
    val menge: Int,
    val status: Status
)