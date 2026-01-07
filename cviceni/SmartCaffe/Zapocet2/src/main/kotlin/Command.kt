package reisiegel.jan

// Interface Command (zapouzdřuje požadavek)
interface Command {
    fun execute()
}

// Receiver (Příjemce akce - reálné zařízení)
class Light {
    fun turnOn() = println("Světlo: Zapnuto")
    fun turnOff() = println("Světlo: Vypnuto")
}

// Konkrétní Command 1: Zapnutí světla
class TurnOnLightCommand(private val light: Light) : Command {
    override fun execute() {
        light.turnOn()
        // Zde by mohlo být logování akce
    }
}

// Konkrétní Command 2: Vypnutí světla
class TurnOffLightCommand(private val light: Light) : Command {
    override fun execute() {
        light.turnOff()
        // Zde by mohlo být přidání do Undo stacku
    }
}

// Invoker (Aktivátor - Tlačítko v UI)
class Button(private var command: Command) {
    fun setCommand(command: Command) {
        this.command = command
    }

    fun press() {
        println("Tlačítko stisknuto:")
        command.execute()
    }
}

// Příklad použití
fun mainCommand() {
    val livingRoomLight = Light()

    // Vytvoření příkazů
    val onCommand = TurnOnLightCommand(livingRoomLight)
    val offCommand = TurnOffLightCommand(livingRoomLight)

    // Vytvoření tlačítka a přiřazení příkazu
    val lightButton = Button(onCommand)
    lightButton.press() // Světlo: Zapnuto

    // Změna příkazu tlačítka
    lightButton.setCommand(offCommand)
    lightButton.press() // Světlo: Vypnuto
}