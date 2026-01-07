// 1. Rozhraní State definuje operace, které se mění podle stavu
interface State {
    fun publish()
    fun edit()
}

// 2. Context - Třída Document
class Document(val currentUserRole: String) {
    // Instance stavů (můžeme je držet přímo v dokumentu pro snadný přístup)
    val draft = DraftState(this)
    val moderation = ModerationState(this)
    val published = PublishedState(this)

    // Aktuální stav (výchozí je Draft)
    var state: State = draft

    // Metody pouze delegují práci na aktuální stav
    fun publish() = state.publish()
    fun edit() = state.edit()

    // Pomocná metoda pro výpis (volitelná)
    fun printCurrentStatus() {
        println("Aktuální stav: ${state::class.simpleName}")
    }
}

// 3. Konkrétní implementace stavů

class DraftState(private val doc: Document) : State {
    override fun publish() {
        println("Document moved to moderation.")
        doc.state = doc.moderation
    }

    override fun edit() {
        println("Document edited (Draft).")
    }
}

class ModerationState(private val doc: Document) : State {
    override fun publish() {
        if (doc.currentUserRole == "admin") {
            println("Document published.")
            doc.state = doc.published
        } else {
            println("Only admin can publish.")
        }
    }

    override fun edit() {
        println("Document edited (Moderation).")
    }
}

class PublishedState(private val doc: Document) : State {
    override fun publish() {
        println("Document is already published.")
    }

    override fun edit() {
        println("Cannot edit a published document.")
    }
}

// --- Testování ---
fun main() {
    println("--- Test s uživatelem 'user' ---")
    val userDoc = Document("user")
    userDoc.edit()      // OK
    userDoc.publish()   // Draft -> Moderation
    userDoc.publish()   // Selže (není admin)
    userDoc.printCurrentStatus()

    println("\n--- Test s uživatelem 'admin' ---")
    val adminDoc = Document("admin")
    adminDoc.publish()  // Draft -> Moderation
    adminDoc.publish()  // Moderation -> Published
    adminDoc.edit()     // Selže (již publikováno)
    adminDoc.printCurrentStatus()
}