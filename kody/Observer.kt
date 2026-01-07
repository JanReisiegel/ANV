package reisiegel.jan

// 1. Rozhraní Pozorovatele (Observer Interface)
interface StockObserver {
    fun update(newPrice: Double)
}

// 2. Konkrétní Pozorovatelé
class TradingBot : StockObserver {
    override fun update(newPrice: Double) {
        if (newPrice < 100.0) {
            println("[TradingBot] Cena $newPrice, NAKUPUJI!")
        } else {
            println("[TradingBot] Cena $newPrice, ČEKÁM.")
        }
    }
}

class GrafickyReport : StockObserver {
    override fun update(newPrice: Double) {
        println("[GrafickyReport] Aktualizuji graf s cenou: $newPrice")
    }
}

// 3. Subjekt (Subject/Observable)
class StockMarket(initialPrice: Double) {

    // Seznam pozorovatelů
    private val observers = mutableListOf<StockObserver>()

    var price: Double = initialPrice
        set(value) {
            // Při změně hodnoty
            if (field != value) {
                field = value
                notifyObservers(value) // Upozornit všechny pozorovatele
            }
        }

    // Metody pro správu pozorovatelů
    fun registerObserver(observer: StockObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: StockObserver) {
        observers.remove(observer)
    }

    // Metoda pro upozornění pozorovatelů
    private fun notifyObservers(newPrice: Double) {
        observers.forEach { it.update(newPrice) }
    }
}

/* Příklad použití:
val market = StockMarket(105.50)
val bot = TradingBot()
val report = GrafickyReport()

market.registerObserver(bot)
market.registerObserver(report)

println("--- Změna 1 ---")
market.price = 99.80 // Spustí update na botovi i reportu

println("--- Změna 2 ---")
market.price = 110.00 // Spustí update
*/