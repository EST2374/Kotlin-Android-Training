fun main() {

    val acc = BankAccount("Anna", 100.0)
    acc.deposit(50.0)
    acc.withdraw(30.0)
    acc.withdraw(200.0)  // sollte scheitern
    println(acc.balance) // erwarteter Wert?
}

class BankAccount(
    val owner: String,
    val initBalance: Double,
) {
    var balance = initBalance
        private set

    fun deposit(amount: Double) {
        balance += amount
    }

    fun withdraw(amount: Double) {
        if (amount < 0 || balance - amount < 0)  {
            println("Kann nicht ausgezahlt werden")
        } else {
            balance -= amount
        }
    }
}