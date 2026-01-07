package reisiegel.jan

// 1. Rozhraní (Interface) pro Produkty
interface Enemy {
    fun attack()
}

// 2. Konkrétní Produkty
class Goblin : Enemy {
    override fun attack() {
        println("Goblin zaútočil dýkou!")
    }
}

class Orc : Enemy {
    override fun attack() {
        println("Orc zaútočil palicí!")
    }
}

// 3. Tvůrce (Creator/Factory)
class EnemyFactory {

    enum class Level { EASY, HARD }

    fun createEnemy(level: Level): Enemy {
        return when (level) {
            Level.EASY -> Goblin() // Snadná obtížnost = Goblin
            Level.HARD -> Orc()    // Těžká obtížnost = Orc
            // V reálné Factory by zde mohla být i další logika, např. podle náhodného výběru
        }
    }
}

/* Příklad použití:
val factory = EnemyFactory()

val easyEnemy = factory.createEnemy(EnemyFactory.Level.EASY)
easyEnemy.attack() // Goblin zaútočil dýkou!

val hardEnemy = factory.createEnemy(EnemyFactory.Level.HARD)
hardEnemy.attack() // Orc zaútočil palicí!
*/