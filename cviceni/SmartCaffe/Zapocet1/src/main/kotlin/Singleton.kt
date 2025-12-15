package reisiegel.jan

class DatabaseConnectionManager private constructor(private val connectionString: String) {

    // Místo pro fond spojení (pro zjednodušení jen string)
    private val connectionPool: String = "Pool pro $connectionString"

    fun logQuery(query: String) {
        println("[DB LOG] Dotaz: $query")
        // ... Logování
    }

    // Companion Object drží statickou referenci a logiku pro vytvoření
    companion object {
        // Způsob implementace 1: Standardní Eager Initialization
        // private val INSTANCE = DatabaseConnectionManager("jdbc:url:standard")

        // Způsob implementace 2: Kotlin's object declaration (nejčistší a doporučený způsob)
        // Toto je nejjednodušší a thread-safe způsob v Kotlinu
        private var instance: DatabaseConnectionManager? = null

        fun getInstance(): DatabaseConnectionManager {
            if (instance == null) {
                // Použití synchronized bloku pro thread-safe inicializaci
                synchronized(this) {
                    if (instance == null) {
                        // Vytvoření instance, pouze pokud ještě neexistuje
                        instance = DatabaseConnectionManager("jdbc:url:standard")
                    }
                }
            }
            return instance!!
        }
    }
}

// Nejčistší Kotlin řešení pro Singleton (doporučeno pro papírovou skicu)
// object DatabaseConnectionManager {
//     fun logQuery(query: String) {
//         println("[DB LOG] Dotaz: $query")
//     }
// }

/* Příklad použití:
val manager1 = DatabaseConnectionManager.getInstance()
manager1.logQuery("SELECT * FROM users")

val manager2 = DatabaseConnectionManager.getInstance()
// manager1 a manager2 odkazují na stejnou instanci
*/