// 1. Společné rozhraní (nebo abstraktní třída) pro všechny články řetězce
abstract class Handler {
    var next: Handler? = null

    fun setNextHandler(handler: Handler): Handler {
        this.next = handler
        return handler
    }

    abstract fun handleRequest(amount: Int)
}

// 2. Konkrétní články řetězce
class JuniorManager : Handler() {
    override fun handleRequest(amount: Int) {
        if (amount <= 1000) {
            println("Junior Manager: Schvaluji výdaj ve výši $amount Kč.")
        } else {
            println("Junior Manager: Na tohle nemám pravomoc, předávám dál...")
            next?.handleRequest(amount)
        }
    }
}

class SeniorManager : Handler() {
    override fun handleRequest(amount: Int) {
        if (amount <= 5000) {
            println("Senior Manager: Schvaluji výdaj ve výši $amount Kč.")
        } else {
            println("Senior Manager: Příliš drahé, posílám řediteli.")
            next?.handleRequest(amount)
        }
    }
}

class CEO : Handler() {
    override fun handleRequest(amount: Int) {
        println("CEO: Schvaluji velký výdaj ve výši $amount Kč.")
    }
}

// 3. Použití
fun main() {
    val junior = JuniorManager()
    val senior = SeniorManager()
    val ceo = CEO()

    // Sestavení řetězce: Junior -> Senior -> CEO
    junior.setNextHandler(senior).setNextHandler(ceo)

    // Testování různých požadavků
    println("--- Požadavek na 500 Kč ---")
    junior.handleRequest(500)

    println("\n--- Požadavek na 2500 Kč ---")
    junior.handleRequest(2500)

    println("\n--- Požadavek na 15000 Kč ---")
    junior.handleRequest(15000)
}