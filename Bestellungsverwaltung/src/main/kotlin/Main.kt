package org.example

fun main() {

    val bestellungen = listOf(
        Bestellung(1, "Anna", "Laptop", 800.0, 1, "offen"),
        Bestellung(2, "Bob", "Maus", 20.0, 2, "versandt"),
        Bestellung(3, "Anna", "Tastatur", 50.0, 1, "offen"),
        Bestellung(4, "Charlotte", "Monitor", 300.0, 2, "storniert"),
        Bestellung(5, "Bob", "Laptop", 800.0, 1, "offen"),
        Bestellung(6, "Anna", "Webcam", 60.0, 1, "versandt")
    )

    val gesPreisUber50 = bestellungen.filter {
        it.status == "offen" && it.preis * it.menge > 50
    }
    gesPreisUber50.forEach { println(it) }

    println()

    val nachKunde = gesPreisUber50.groupBy { it.kunde }
    nachKunde.forEach { (kunde, liste) -> println("$kunde $liste")  }

    val kundePreis = nachKunde.mapValues { (_, bestellungen) ->
        bestellungen.sumOf { it.preis * it.menge }
    }
    kundePreis.forEach { (kunde,gesPreis) -> println("$kunde: $gesPreis")  }

}