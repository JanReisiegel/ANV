// 1. Rozhraní Mediátora
interface AirTrafficControl {
    fun sendMessage(message: String, sender: Participant)
    fun registerParticipant(participant: Participant)
}

// 2. Abstraktní třída účastníka provozu (Participant)
abstract class Participant(protected val atc: AirTrafficControl, val callsign: String) {
    abstract fun send(message: String)
    abstract fun receive(message: String)
}

// 3. Konkrétní Mediátor (Věž)
class ControlTower : AirTrafficControl {
    private val participants = mutableListOf<Participant>()

    override fun registerParticipant(participant: Participant) {
        participants.add(participant)
        println("Věž: Registruji nový stroj do vzdušného prostoru: ${participant.callsign}")
    }

    override fun sendMessage(message: String, sender: Participant) {
        participants.forEach {
            if (it != sender) {
                it.receive("Od ${sender.callsign}: $message")
            }
        }
    }
}

// --- 4. Různé typy "Kolegů" ---

// Dopravní letadlo - standardní chování
class CommercialJet(atc: AirTrafficControl, callsign: String) : Participant(atc, callsign) {
    override fun send(message: String) {
        println("\n[RADIO] $callsign vysílá: $message")
        atc.sendMessage(message, this)
    }

    override fun receive(message: String) {
        println("   $callsign v kokpitu přijal: $message")
    }
}

// Vrtulník - může mít specifickou reakci (např. visení ve vzduchu)
class Helicopter(atc: AirTrafficControl, callsign: String) : Participant(atc, callsign) {
    override fun send(message: String) {
        println("\n[HELI-COM] $callsign vysílá: $message")
        atc.sendMessage(message, this)
    }

    override fun receive(message: String) {
        println("   $callsign (vrtulník) potvrzuje příjem: $message")
    }
}

// Autonomní Dron - reaguje automaticky (strojově)
class AutonomousDrone(atc: AirTrafficControl, callsign: String) : Participant(atc, callsign) {
    override fun send(message: String) {
        println("\n[DATA-LINK] $callsign posílá telemetrii: $message")
        atc.sendMessage(message, this)
    }

    override fun receive(message: String) {
        println("   $callsign (DRON-AUTO): Zpráva přijata a analyzována.")
    }
}

// --- Použití v main ---
fun main() {
    val tower = ControlTower()

    val boeing = CommercialJet(tower, "Boeing 737")
    val apache = Helicopter(tower, "Apache-1")
    val predator = AutonomousDrone(tower, "Drone-X")

    tower.registerParticipant(boeing)
    tower.registerParticipant(apache)
    tower.registerParticipant(predator)

    // Komunikace probíhá mezi různými typy strojů, ale nikdo nezná nikoho přímo
    boeing.send("Klesám na hladinu 3000 stop.")
    apache.send("Rozumím Boeingu, uvolňuji sektor.")
    predator.send("Baterie 15%, zahajuji automatický návrat.")
}