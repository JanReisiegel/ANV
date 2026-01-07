// 1. Target (Cílové rozhraní)
// Toto rozhraní naše aplikace očekává.
interface JsonParser {
    fun getJsonData(): String
}

// 2. Adaptee (Přizpůsobovaný objekt)
// Toto je existující třída (např. stará knihovna), kterou chceme použít,
// ale její rozhraní nám nevyhovuje.
class XmlLibrary {
    fun getSpecificXmlData(): String {
        return "<note><to>User</to><from>System</from><msg>Ahoj z XML!</msg></note>"
    }
}

// 3. Adapter (Adaptér)
// Tato třída propojí oba světy. Implementuje rozhraní, které chceme (JsonParser),
// a uvnitř drží instanci staré knihovny, jejíž výstup transformuje.
class XmlToJsonAdapter(private val xmlLibrary: XmlLibrary) : JsonParser {

    override fun getJsonData(): String {
        // Získáme data ze staré knihovny
        val xmlData = xmlLibrary.getSpecificXmlData()

        // Simulace převodu XML na JSON (ve skutečnosti by tu byla parsovací logika)
        println("Adaptér: Převádím XML na JSON...")
        val jsonData = "{ \"message\": \"Zkonvertováno z XML\" }"

        return jsonData
    }
}

// 4. Client (Klientský kód)
// Třída, která vyžaduje JsonParser.
class ModernApp(private val parser: JsonParser) {
    fun displayData() {
        val data = parser.getJsonData()
        println("Aplikace zobrazuje data: $data")
    }
}

// --- Spuštění (Main) ---
fun main() {
    // Máme starou knihovnu
    val oldLibrary = XmlLibrary()

    // Chceme ji použít v moderní aplikaci, ale ta chce JSON.
    // Vytvoříme adaptér, kterému starou knihovnu předáme.
    val adapter = XmlToJsonAdapter(oldLibrary)

    // Aplikace teď může pracovat s adaptérem, jako by to byl běžný JSON zdroj.
    val app = ModernApp(adapter)
    app.displayData()
}