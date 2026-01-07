// --- ČÁST 1: ITERATOR ---

// Abstraktní rozhraní pro Iterátor
interface MyIterator<T> {
    fun hasNext(): Boolean
    fun next(): T
}

// Konkrétní implementace Iterátoru pro náš seznam knih
class BookIterator(private val items: List<String>) : MyIterator<String> {
    private var position = 0

    override fun hasNext(): Boolean {
        return position < items.size
    }

    override fun next(): String {
        val item = items[position]
        position++
        return item
    }
}

// --- ČÁST 2: AGGREGATE (KOLEKCE) ---

// Abstraktní rozhraní pro Kolekci (Aggregate)
interface MyCollection<T> {
    fun createIterator(): MyIterator<T>
}

// Konkrétní implementace Kolekce
class BookShelf : MyCollection<String> {
    private val books = mutableListOf<String>()

    fun addBook(title: String) {
        books.add(title)
    }

    // Tato metoda vrací konkrétní iterátor pro tuto kolekci
    override fun createIterator(): MyIterator<String> {
        return BookIterator(books)
    }
}

// --- ČÁST 3: KLIENT ---

fun main() {
    // 1. Vytvoříme kolekci a naplníme ji daty
    val shelf = BookShelf()
    shelf.addBook("Design Patterns")
    shelf.addBook("Clean Code")
    shelf.addBook("Kotlin in Action")

    // 2. Získáme iterátor (Klient pracuje s rozhraním MyIterator, ne s konkrétní třídou)
    val iterator: MyIterator<String> = shelf.createIterator()

    // 3. Procházíme kolekci pomocí iterátoru
    println("Knihy v polici:")
    while (iterator.hasNext()) {
        val book = iterator.next()
        println("- $book")
    }
}