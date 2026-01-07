// 1. Rozhraní pro Návštěvníka (Visitor)
// Musí mít metodu pro každý typ prvku, který bude navštěvovat
interface Visitor {
    fun visit(food: Food): String
    fun visit(electronics: Electronics): String
    fun visit(service: Service): String
}

// 2. Rozhraní pro Prvek (Element)
// Každý prvek musí přijmout návštěvníka
interface Item {
    fun accept(visitor: Visitor): String
}

// 3. Konkrétní Prvky
class Food(val name: String, val price: Double) : Item {
    override fun accept(visitor: Visitor) = visitor.visit(this)
}

class Electronics(val name: String, val price: Double, val weight: Int) : Item {
    override fun accept(visitor: Visitor) = visitor.visit(this)
}

class Service(val name: String, val hourlyRate: Double, val hours: Int) : Item {
    override fun accept(visitor: Visitor) = visitor.visit(this)
}

// 4. Konkrétní Návštěvníci (Algoritmy)

// Návštěvník pro výpočet daně
class TaxVisitor : Visitor {
    override fun visit(food: Food) = "DPH pro ${food.name} (15%): ${food.price * 0.15} Kč"
    override fun visit(electronics: Electronics) = "DPH pro ${electronics.name} (21%): ${electronics.price * 0.21} Kč"
    override fun visit(service: Service) = "DPH pro ${service.name} (21%): ${(service.hourlyRate * service.hours) * 0.21} Kč"
}

// Návštěvník pro generování exportu do prostého textu
class ExportVisitor : Visitor {
    override fun visit(food: Food) = "Potravina: ${food.name}, cena: ${food.price}"
    override fun visit(electronics: Electronics) = "Elektronika: ${electronics.name}, hmotnost: ${electronics.weight}g"
    override fun visit(service: Service) = "Služba: ${service.name}, trvání: ${service.hours}h"
}

// 5. Použití v main
fun main() {
    val items = listOf(
        Food("Jablko", 30.0),
        Electronics("Sluchátka", 2500.0, 200),
        Service("Konzultace", 1000.0, 2)
    )

    val taxVisitor = TaxVisitor()
    val exportVisitor = ExportVisitor()

    println("--- VÝPOČET DANÍ ---")
    items.forEach { println(it.accept(taxVisitor)) }

    println("\n--- EXPORT DAT ---")
    items.forEach { println(it.accept(exportVisitor)) }
}