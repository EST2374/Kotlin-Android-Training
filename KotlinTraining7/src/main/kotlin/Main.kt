package org.example

import java.io.File

fun main() {
    /*
    val file = File("data.txt")

    file.readText()              // ganze Datei als String
    file.readLines()             // List<String>, eine Zeile pro Element
    file.writeText("Hallo")      // überschreibt Datei
    file.appendText("\nWelt")    // hängt an
    file.forEachLine { line -> println(line) }  // zeilenweise, speichereffizient bei großen Dateien
     */

    println(countLines("/home/est/.bashrc"))

}

/**
 * Schaut ob ein String eine valide Email ist
 * @receiver (this) ist der String
 * @return ein Boolean je nachdem ob es eine Valide Email ist oder nicht
 */

fun String.isValidEmail(): Boolean {
    return this.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
}

fun countLines(path: String): Int {
    var count = 0
    val datei = File(path)
    datei.forEachLine { count++ }
    return count
}