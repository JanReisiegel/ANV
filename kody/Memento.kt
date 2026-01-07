// 1. Memento - Drží stav.
// V Kotlinu je ideální použít 'data class', kterou nastavíme jako imutabilní (val).
data class GameStateMemento(
    val level: Int,
    val health: Int,
    val inventory: List<String>
)

// 2. Originator - Objekt, který chceme ukládat
class PlayerCharacter {
    var level: Int = 1
    var health: Int = 100
    private val inventory = mutableListOf<String>()

    fun collectItem(item: String) {
        inventory.add(item)
    }

    fun takeDamage(damage: Int) {
        health -= damage
    }

    // Vytvoří snímek aktuálního stavu
    fun save(): GameStateMemento {
        println("Hráč: Ukládám stav hry (Level $level, HP $health)...")
        // Posíláme kopii listu, aby se změny v originálu neprojevily v mementu
        return GameStateMemento(level, health, inventory.toList())
    }

    // Obnoví stav ze snímku
    fun restore(memento: GameStateMemento) {
        this.level = memento.level
        this.health = memento.health
        this.inventory.clear()
        this.inventory.addAll(memento.inventory)
        println("Hráč: Stav hry obnoven!")
    }

    fun showStatus() {
        println("Aktuální stav -> Level: $level, HP: $health, Inventář: $inventory")
    }
}

// 3. Caretaker - Správce historie
class GameSaveManager {
    private val history = mutableListOf<GameStateMemento>()

    fun saveCheckpoint(memento: GameStateMemento) {
        history.add(memento)
    }

    fun getLastCheckpoint(): GameStateMemento? {
        return if (history.isNotEmpty()) history.removeAt(history.size - 1) else null
    }
}

// --- Použití v main ---
fun main() {
    val player = PlayerCharacter()
    val saveManager = GameSaveManager()

    // 1. Hráč hraje a získá věc
    player.collectItem("Meč")
    player.showStatus()

    // 2. Uložíme hru (Checkpoint 1)
    saveManager.saveCheckpoint(player.save())

    // 3. Hráč postoupí, ale pak utrpí těžké zranění
    player.level = 2
    player.takeDamage(80)
    player.collectItem("Lektvar many")
    player.showStatus()

    // 4. "Smrt" - chceme se vrátit k poslednímu checkpointu
    println("--- Načítám poslední uloženou pozici ---")
    val lastSave = saveManager.getLastCheckpoint()
    if (lastSave != null) {
        player.restore(lastSave)
    }

    player.showStatus()
}