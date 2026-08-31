package org.example

data class Bestellung(
    val id: Int,
    val kunde: String,
    val artikel: String,
    val preis: Double,
    val menge: Int,
    val status: String  // "offen", "versandt", "storniert"
)
