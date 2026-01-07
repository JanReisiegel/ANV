// 1. IMPLEMENTACE (Implementor)
// Rozhraní pro konkrétní zařízení
interface Device {
    fun isEnabled(): Boolean
    fun enable()
    fun disable()
    fun setVolume(percent: Int)
}

// Konkrétní implementace: Televize
class Tv : Device {
    private var on = false
    private var volume = 30

    override fun isEnabled() = on
    override fun enable() { on = true; println("TV: Zapnuto") }
    override fun disable() { on = false; println("TV: Vypnuto") }
    override fun setVolume(percent: Int) {
        volume = percent
        println("TV: Hlasitost nastavena na $volume%")
    }
}

// Konkrétní implementace: Rádio
class Radio : Device {
    private var on = false
    override fun isEnabled() = on
    override fun enable() { on = true; println("Rádio: Hraje") }
    override fun disable() { on = false; println("Rádio: Ticho") }
    override fun setVolume(percent: Int) { println("Rádio: Hlasitost na $percent%") }
}

// 2. ABSTRAKCE (Abstraction)
// Ovladač, který drží odkaz na rozhraní Device (to je ten "MOST")
open class RemoteControl(protected val device: Device) {
    fun togglePower() {
        if (device.isEnabled()) device.disable() else device.enable()
    }

    fun volumeUp() {
        device.setVolume(50)
    }
}

// Rozšířená abstrakce (Refined Abstraction)
// Ovladač s pokročilými funkcemi (např. tlačítko Mute)
class AdvancedRemoteControl(device: Device) : RemoteControl(device) {
    fun mute() {
        println("Ovladač: Aktivuji funkci Mute")
        device.setVolume(0)
    }
}

// --- Použití v main ---
fun main() {
    val tv = Tv()
    val radio = Radio()

    // Použijeme základní ovladač pro TV
    println("--- Testování TV se základním ovladačem ---")
    val basicRemote = RemoteControl(tv)
    basicRemote.togglePower()

    // Použijeme pokročilý ovladač pro Rádio
    println("\n--- Testování Rádia s pokročilým ovladačem ---")
    val advancedRemote = AdvancedRemoteControl(radio)
    advancedRemote.togglePower()
    advancedRemote.mute()

    // Bridge nám umožňuje vzít pokročilý ovladač a ovládat s ním TV
    // aniž bychom měnili kód ovladače nebo TV
    println("\n--- Testování TV s pokročilým ovladačem ---")
    val advancedTvRemote = AdvancedRemoteControl(tv)
    advancedTvRemote.mute()
}