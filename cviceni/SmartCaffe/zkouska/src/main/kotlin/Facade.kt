// 1. Komplexní subsystém (třídy, které nechceme ovládat ručně)

class Projector {
    fun on() = println("Projektor: Zapnuto")
    fun setInput(source: String) = println("Projektor: Vstup nastaven na $source")
    fun off() = println("Projektor: Vypnuto")
}

class SoundSystem {
    fun on() = println("SoundSystem: Zapnuto")
    fun setVolume(level: Int) = println("SoundSystem: Hlasitost nastavena na $level")
    fun off() = println("SoundSystem: Vypnuto")
}

class StreamingService {
    fun connect() = println("Streaming: Připojeno k serveru")
    fun play(movie: String) = println("Streaming: Přehrávám film '$movie'")
}

// 2. Facade (Fasáda)
// Tato třída sjednocuje ovládání celého subsystému do jednoduchých metod

class HomeTheaterFacade(
    private val projector: Projector,
    private val soundSystem: SoundSystem,
    private val streaming: StreamingService
) {
    fun watchMovie(movie: String) {
        println("--- Příprava na filmové odpoledne ---")
        projector.on()
        projector.setInput("HDMI 1")
        soundSystem.on()
        soundSystem.setVolume(20)
        streaming.connect()
        streaming.play(movie)
        println("--- Užijte si film! ---")
    }

    fun endMovie() {
        println("--- Vypínání kina ---")
        projector.off()
        soundSystem.off()
        println("--- Hotovo ---")
    }
}

// 3. Použití v main
fun main() {
    // Vytvoříme komponenty (v reálu by mohly být skryté nebo injektované)
    val projector = Projector()
    val sound = SoundSystem()
    val stream = StreamingService()

    // Vytvoříme fasádu
    val homeTheater = HomeTheaterFacade(projector, sound, stream)

    // Klient používá pouze jednoduché rozhraní fasády
    homeTheater.watchMovie("Inception")

    println("\n... o dvě hodiny později ...\n")

    homeTheater.endMovie()
}