package org.example

import java.util.Locale
import java.util.Locale.getDefault

fun main() {

    // Filter
    val numbers: List<Int> = listOf(1,2,3,4,5,6,7,8,9,10)
    val even = numbers.filter { it % 2 == 0 }

    val names = listOf("Anna", "Bob", "Charlotte", "Dave", "Eve", "Alexander")
    val namesGreater4 = names.filter { it.length > 4 }

    println(even)
    println(namesGreater4)

    // Let
    val name: String? = "Anna"
    name?.let { println("Hallo, $it") } // nur wenn name != null ist
    // Ansosten müsste man:
    if (name != null) {
        println("Hallo $name")
    }

    val email: String? = getEmail()
    email?.let { println(it.lowercase()) }

    // Take
    val zahlen1 = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    zahlen1.take(3)          // [1, 2, 3]
    zahlen1.takeWhile { it < 5 } // [1, 2, 3, 4]

    // Ersten 3 geraden Zahlen
    val zahlen2 = listOf(3, 6, 8, 9, 12, 15, 18, 20)
    val newzahlen = zahlen2
        .filter { it % 2 == 0 }
        .take(3)
    println(newzahlen)

    // Apply
    val person = Person().apply {
        personenName = "Anna"
        age = 21
    }
    println(person.personenName)
    println(person.age)

    // Also
    val result = zahlen1
        .filter { it % 2 == 0 }
        .also { println("Gefilterte Liste: $it") }
        .take(3)
    println(result)

    val zahl = 5
    // logge "Verarbeite: 5", dann berechne zahl * zahl und speichere in "quadrat"
    val quadrat = zahl
        .also { println("Verarbeite: $it") }
        .let { it * it }
    println(quadrat)

    // Run
    val person1 = PersonData("Anna", 30)
    val beschreibung = person1.run {
        "$pName ist $age Jahre alt"   // this.name, this.age – kein "this." nötig
    }
    println(beschreibung)

    val ergebnis = run {
        val a = 5
        val b = 10
        a + b
    }
    // a und b existieren außerhalb nicht mehr, nur "ergebnis"
    println(ergebnis)

    val r = Rechteck(4, 5)
    val info = r.run {
        val flaeche = breite * hoehe
        val umfang = 2 * (breite + hoehe)

        "Fläche: $flaeche, Umfang: $umfang"
    }
    println(info)

    // Map
    val zahlen = listOf(1, 2, 3, 4)
    val quadrate = zahlen.map { it * it }
    // [1, 4, 9, 16]
    val verdoppelt = zahlen.map { 2 * it }
    println(verdoppelt)

    // Copy
    val anna = Person2("Anna", 30, "Wien")
    val annaGeburtstag = anna.copy(age = 31)
    // Person(name="Anna", age=31, stadt="Wien") – neue Instanz, name & stadt unverändert übernommen
    println(anna)
    println(annaGeburtstag)

    val produkte = listOf(
        Produkt("Buch", 15.0),
        Produkt("Stift", 2.0),
        Produkt("Laptop", 800.0)
    )

    val produkte10ProzentMehr = produkte.map {
        it.copy(preis = it.preis * 1.1)
    }
    produkte10ProzentMehr.forEach { println(it) }




}

private fun getEmail(): String {
    return "MY EMAIL"
}

class Person {
    var personenName: String = ""
    var age: Int = 0
}

data class PersonData(
    val pName: String,
    val age: Int
)

data class Person2(val name: String, val age: Int, val stadt: String)


data class Rechteck(val breite: Int, val hoehe: Int)

data class Produkt(val name: String, val preis: Double)
