package reisiegel.jan.Template

// Abstraktní třída definující šablonu algoritmu
abstract class DocumentProcessor {

    // Template Method (neměnná kostra algoritmu)
    fun processDocument(documentName: String) {
        openDocument(documentName)
        val content = readContent(documentName)
        processContent(content)
        closeDocument(documentName)
        println("--- Zpracování dokumentu $documentName dokončeno ---")
    }

    // Společné kroky (konkrétní implementace)
    private fun openDocument(name: String) {
        println("Otevírám dokument: $name (Společná logika)")
    }

    private fun closeDocument(name: String) {
        println("Uzavírám dokument: $name (Společná logika)")
    }

    // Abstraktní kroky (přenechány podtřídám)
    protected abstract fun readContent(name: String): String
    protected abstract fun processContent(content: String)
}

// Konkrétní implementace pro PDF
class PdfProcessor : DocumentProcessor() {
    override fun readContent(name: String): String {
        println("   --> Načítám PDF data: $name (PDF-specifické)")
        return "Obsah PDF"
    }

    override fun processContent(content: String) {
        println("   --> Analyzuji PDF grafiku/text: '$content'")
    }
}

// Konkrétní implementace pro DOCX
class DocxProcessor : DocumentProcessor() {
    override fun readContent(name: String): String {
        println("   --> Načítám DOCX XML strukturu: $name (DOCX-specifické)")
        return "Obsah DOCX"
    }

    override fun processContent(content: String) {
        println("   --> Kontroluji DOCX styly/formátování: '$content'")
    }
}

// Příklad použití
fun mainTemplate() {
    val pdfProcessor = PdfProcessor()
    pdfProcessor.processDocument("Report.pdf")

    val docxProcessor = DocxProcessor()
    docxProcessor.processDocument("Zápis.docx")
}
