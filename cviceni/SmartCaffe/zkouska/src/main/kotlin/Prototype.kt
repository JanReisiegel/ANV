// 1. Prototype - Rozhraní pro klonování
// Kotlin má vestavěné Cloneable, ale pro zkoušku je lepší vlastní interface
interface Prototype {
    fun clone(): Prototype
}

// 2. Konkrétní Prototyp
class NPC(
    var name: String,
    var health: Int,
    val skills: MutableList<String>,
    val textureData: String // Simulace těžkých dat
) : Prototype {

    init {
        // Simulace drahého vytváření
        println("NPC: Provádím drahé načítání textur a databáze pro '$name'...")
    }

    // Implementace klonování (Deep Copy)
    override fun clone(): NPC {
        // Pozor: u kolekcí musíme vytvořit novou kopii (deep copy),
        // jinak by všichni kloni sdíleli stejný seznam dovedností.
        return NPC(name, health, skills.toMutableList(), textureData)
    }

    override fun toString(): String {
        return "NPC(name='$name', health=$health, skills=$skills)"
    }
}

// 3. Client - Registry (volitelný, ale u zkoušky vypadá skvěle)
// Správce prototypů, u kterého si "objednáme" kopii
class NPCRegistry {
    private val prototypes = mutableMapOf<String, NPC>()

    fun addPrototype(key: String, npc: NPC) {
        prototypes[key] = npc
    }

    fun getClone(key: String): NPC? {
        return prototypes[key]?.clone()
    }
}

// --- Použití v main ---
fun main() {
    val registry = NPCRegistry()

    // 1. Vytvoříme a zaregistrujeme "těžký" prototyp (stane se jen jednou)
    val guardPrototype = NPC("Strážce brány", 100, mutableListOf("Útok mečem"), "HD_TEXTURE_001")
    registry.addPrototype("BASIC_GUARD", guardPrototype)

    println("--- Začínám generovat vojáky ---")

    // 2. Klonujeme strážce (operace je blesková, bez init bloku)
    val guard1 = registry.getClone("BASIC_GUARD")
    guard1?.name = "Strážce 1"

    val guard2 = registry.getClone("BASIC_GUARD")
    guard2?.name = "Strážce 2"
    guard2?.skills?.add("Střelba z luku") // Změna u guard2 neovlivní ostatní

    println(guard1)
    println(guard2)
}