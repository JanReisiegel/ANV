// 1. Interface Command
// Pro Undo/Redo potřebujeme metodu execute() i undo()
interface Command {
    fun execute()
    fun undo()
}

// 2. Receiver (Příjemce)
// Třída, která skutečně vykonává práci (v našem případě drží text)
class TextEditor {
    private var text = ""

    fun appendText(newText: String) {
        text += newText
    }

    fun removeLastPart(length: Int) {
        text = text.substring(0, text.length - length)
    }

    fun printContent() {
        println("Aktuální text: \"$text\"")
    }
}

// 3. Konkrétní Command
// Drží stav potřebný pro návrat zpět (v tomto případě text, který byl přidán)
class WriteTextCommand(
    private val editor: TextEditor,
    private val textToWrite: String
) : Command {

    override fun execute() {
        editor.appendText(textToWrite)
    }

    override fun undo() {
        editor.removeLastPart(textToWrite.length)
    }
}

// 4. Invoker (Spouštěč) s historií pro Undo/Redo
class CommandManager {
    private val undoStack = mutableListOf<Command>()
    private val redoStack = mutableListOf<Command>()

    fun executeCommand(command: Command) {
        command.execute()
        undoStack.add(command)
        redoStack.clear() // Nový příkaz maže historii Redo
    }

    fun undo() {
        if (undoStack.isNotEmpty()) {
            val command = undoStack.removeAt(undoStack.size - 1)
            command.undo()
            redoStack.add(command)
            println("Akce 'Zpět' provedena.")
        } else {
            println("Není co vrátit.")
        }
    }

    fun redo() {
        if (redoStack.isNotEmpty()) {
            val command = redoStack.removeAt(redoStack.size - 1)
            command.execute()
            undoStack.add(command)
            println("Akce 'Znovu' provedena.")
        } else {
            println("Není co zopakovat.")
        }
    }
}

// --- Použití v main ---
fun main() {
    val editor = TextEditor()
    val manager = CommandManager()

    // 1. Něco napíšeme
    manager.executeCommand(WriteTextCommand(editor, "Ahoj "))
    manager.executeCommand(WriteTextCommand(editor, "všem "))
    manager.executeCommand(WriteTextCommand(editor, "studujícím!"))
    editor.printContent()

    // 2. Zkusíme Undo (Zpět)
    manager.undo() // Vrátí "studujícím!"
    editor.printContent()

    manager.undo() // Vrátí "všem "
    editor.printContent()

    // 3. Zkusíme Redo (Znovu)
    manager.redo() // Znovu napíše "všem "
    editor.printContent()
}