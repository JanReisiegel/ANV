// 1. Component - Společné rozhraní pro všechny části vozidla
interface VehiclePart {
    fun getDetails(): String
}

// 2. Leaf - Rozhraní pro motor (Engine) a jeho konkrétní implementace
interface Engine : VehiclePart

class PetrolEngine(val horsepower: Int) : Engine {
    override fun getDetails() = "Benzínový motor ($horsepower HP)"
}

class DieselEngine(val horsepower: Int) : Engine {
    override fun getDetails() = "Naftový motor ($horsepower HP)"
}

class ElectricEngine(val kilowatts: Int) : Engine {
    override fun getDetails() = "Elektromotor ($kilowatts kW)"
}

// Další jednoduchá součástka (Leaf)
class Wheel(val size: Int) : VehiclePart {
    override fun getDetails() = "Kolo velikosti R$size"
}

// 3. Composite - Vozidlo, které se skládá z více částí
class Vehicle(val name: String) : VehiclePart {
    private val parts = mutableListOf<VehiclePart>()

    fun addPart(part: VehiclePart) {
        parts.add(part)
    }

    override fun getDetails(): String {
        val sb = StringBuilder()
        sb.append("Vozidlo: $name\n")
        parts.forEach { part ->
            // Rekurzivní volání getDetails() bez ohledu na to, zda jde o motor nebo jinou část
            sb.append(" - ${part.getDetails()}\n")
        }
        return sb.toString()
    }
}

// --- Použití ---
fun main() {
    // Vytvoříme Teslu (Elektromotor)
    val tesla = Vehicle("Tesla Model S")
    tesla.addPart(ElectricEngine(450))
    tesla.addPart(Wheel(21))
    tesla.addPart(Wheel(21))

    // Vytvoříme Kamion (Naftový motor a spousta kol)
    val truck = Vehicle("Tatra Phoenix")
    truck.addPart(DieselEngine(530))
    repeat(8) { truck.addPart(Wheel(22)) }

    // Vytvoříme hybridní systém (Vozidlo v jiném vozidle - ukázka síly Composite)
    val hybridSystem = Vehicle("Hybridní pohonný systém")
    hybridSystem.addPart(PetrolEngine(150))
    hybridSystem.addPart(ElectricEngine(50))

    val hybridCar = Vehicle("Toyota Prius")
    hybridCar.addPart(hybridSystem) // Přidáváme celou skupinu jako jednu součástku
    hybridCar.addPart(Wheel(17))

    // Výpis
    println(tesla.getDetails())
    println(truck.getDetails())
    println(hybridCar.getDetails())
}
