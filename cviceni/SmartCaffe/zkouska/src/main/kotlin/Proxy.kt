// 1. Subject - Rozhraní pro obrázek
interface Image {
    fun display()
}

// 2. RealSubject - Skutečný velký obrázek
class HighResImage(private val fileName: String) : Image {

    init {
        // Simulace drahé operace (načítání z disku/sítě)
        loadFromDisk()
    }

    private fun loadFromDisk() {
        println("Načítám obrovský soubor $fileName z disku... (trvá to 2 sekundy)")
    }

    override fun display() {
        println("Zobrazuji obrázek: $fileName")
    }
}

// 3. Proxy - Zástupce pro Lazy Loading
class ImageProxy(private val fileName: String) : Image {
    // Skutečný objekt je zatím null
    private var realImage: HighResImage? = null

    override fun display() {
        // Lazy Loading: Skutečný objekt vytvoříme až PŘI PRVNÍM zavolání display()
        if (realImage == null) {
            println("Proxy: Obrázek ještě není v paměti, vytvářím RealSubject...")
            realImage = HighResImage(fileName)
        } else {
            println("Proxy: Obrázer už je v paměti, znovu ho nenačítám.")
        }

        // Předání požadavku skutečnému objektu
        realImage?.display()
    }
}

// --- Použití v main ---
fun main() {
    // Vytvoříme seznam proxy objektů (tato operace je blesková, nic se nenačítá)
    val gallery: List<Image> = listOf(
        ImageProxy("dovolena_u_more.jpg"),
        ImageProxy("rodinna_oslava.png"),
        ImageProxy("profilovka.webp")
    )

    println("--- Galerie je vytvořena, ale v paměti nic není ---")

    // Uživatel klikne na první obrázek
    println("\n[Klik na první obrázek]")
    gallery[0].display()

    // Uživatel klikne na první obrázek znovu (již je načten)
    println("\n[Klik na první obrázek znovu]")
    gallery[0].display()

    // Ostatní obrázky (index 1 a 2) v paměti stále nejsou, šetříme zdroje!
}