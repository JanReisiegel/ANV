package reisiegel.jan.Strategy

// Interface Strategy (definuje operaci)
interface ShippingStrategy {
    fun calculateCost(orderValue: Double): Double
}

// Konkrétní strategie 1: PPL
class PplStrategy : ShippingStrategy {
    override fun calculateCost(orderValue: Double): Double {
        return if (orderValue >= 2000.0) 0.0 else 129.0
    }
}

// Konkrétní strategie 2: Zásilkovna
class ZasilkovnaStrategy : ShippingStrategy {
    override fun calculateCost(orderValue: Double): Double {
        return 69.0 // Pevná cena
    }
}

// Kontext (třída Objednávka) - drží referenci na Strategy
class Order(val value: Double) {
    private lateinit var shippingStrategy: ShippingStrategy

    // Setter pro snadnou runtime zaměnitelnost
    fun setShippingStrategy(strategy: ShippingStrategy) {
        this.shippingStrategy = strategy
    }

    fun getTotalCost(): Double {
        val shippingCost = shippingStrategy.calculateCost(value)
        println("Cena objednávky: $value, Cena dopravy: $shippingCost")
        return value + shippingCost
    }
}

// Příklad použití
fun mainStrategy() {
    val order = Order(value = 1500.0)

    // Záměna strategie za běhu
    order.setShippingStrategy(PplStrategy())
    println("Celková cena s PPL: ${order.getTotalCost()} Kč")

    order.setShippingStrategy(ZasilkovnaStrategy())
    println("Celková cena se Zásilkovnou: ${order.getTotalCost()} Kč")
}