package org.example



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


    // ComponentN
    val u1 = User("Anna", 30)
    val u2 = u1.copy(age = 31)  // User(name=Anna, age=31)

    val (username, age) = u1

    // Sealed Class
    fun describe(result: ApiResult): String = when (result) {
        is Ok -> "Erfolg: ${result.value}"
        is Fail -> "Fehler: ${result.error}"
        Loading -> TODO()
    }

    println(describe(Ok(200)))

    // Enum Class
    Planet.entries.forEach { println(it.surfaceGravity()) }

    // Inline Class
    //fun createUser(userId: String, email: String) { ... }
    //createUser(email, userId) vertauscht! Compiler meckert nicht, beides ist String
    fun createUser(userId: UserId, email: Email): OnlineUser {
        return OnlineUser(
            userId,
            email
        )
    }

    //createUser(Email("a@b.com"), UserId("123"))  // Compiler-Fehler! Typen passen nicht
    val newOnlineUser = createUser(UserId("123"), Email("a@b.com"))
    println(newOnlineUser)


    fun printDistance(distance: Meters) {
        println("Meter: ${distance.value}")
        println("Feet: ${distance.toFeet()}")
    }

    printDistance(Meters(10.0))


    // Class Generics
    class Box<T>(val content: T) {
        fun show(): String = "Box enthält: $content"
    }

    val intBox = Box(42)             // Box<Int>, Typ wird inferiert
    val strBox = Box("Hallo")     // Box<String>


    class Stack<T> {
        private val items = mutableListOf<T>()
        fun push(item: T) { items.add(item) }
        fun pop(): T? = if (items.isEmpty()) null else items.removeAt(items.size - 1)
    }

    class Pair2<A, B>(val first: A,val second: B) {

        fun swap(): Pair2<B, A> {
            return Pair2(second,first)
        }

    }

    val myPair = Pair2("Alter", 25)
    println(myPair.first)
    println(myPair.second)
    println()
    myPair.swap().let { println("${it.first}\n${it.second}") }


    // TypeAlias
    fun printMap(map: StringMap) {
        map.forEach { println(it) }
    }

    val stringMap: StringMap = mapOf("one" to "1", "two" to "2")
    printMap(stringMap)
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

data class User(val name: String, val age: Int)

// Sealed Class
sealed class ApiResult

data class Ok(val value: Int): ApiResult()
data class Fail(val error: String): ApiResult()
object Loading: ApiResult()

// Enum Class
enum class Direction(val degrees: Int) {
    NORTH(0),
    EAST(90),
    SOUTH(180),
    WEST(270);

    fun opposite(): Direction = when (this) {
        NORTH -> SOUTH
        SOUTH -> NORTH
        EAST -> WEST
        WEST -> EAST
    }
}

enum class Planet(val massKg: Double, val radiusM: Double) {
    MERCURY(1.0,2.0),
    EARTH(3.0,4.0),
    MARS(5.0,6.0);

    companion object {
        val G = 6.67 * Math.pow(10.0,-11.0)
    }

    fun surfaceGravity(): Double {
        return G * this.massKg / (Math.pow(this.radiusM,2.0))
    }
}

// Inline Class
@JvmInline
value class UserId(val value: String)

@JvmInline
value class Email(val value: String)

data class OnlineUser(val name: UserId, val age: Email)

@JvmInline
value class Meters(val value: Double) {
    fun toFeet(): Double {
        return value * 3.28084
    }
}



// TypeAlias
typealias StringMap = Map<String, String>

