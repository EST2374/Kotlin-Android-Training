fun main() {

    val namen: List<String?> = listOf("Anna", null, "Ben", null, "Clara")
    begruesseAlle(namen)

    val p1 = Person("Anna", Adresse("Wien"))
    val p2 = Person("Ben", null)
    gibtStadtAus(p1)
    gibtStadtAus(p2)

    val userVorhanden: User? = User("Anna")
    val userNull: User? = null

    val ergebnis1 = userVorhanden?.name ?: "Error oder leeres Feld"
    val ergebnis2 = userNull?.name ?: "Error oder leeres Feld"
    println(ergebnis1)
    println(ergebnis2)

    beschreibe(null)
}

fun begruesseAlle(name: List<String?>) {
    name.forEach { println("Hallo ${it ?: "Unbekannt"}!") }
}

fun gibtStadtAus(p: Person) {
    println(p.adresse?.stadt ?: "Keine Adresse hinterlegt")
}

fun beschreibe(text: String?) {
    if (text == null) {
        println("Kein Text")
    } else {
        println(text.length)
    }
}

class Adresse(val stadt: String?)
class Person(val name: String, val adresse: Adresse?)
data class User(val name: String)
