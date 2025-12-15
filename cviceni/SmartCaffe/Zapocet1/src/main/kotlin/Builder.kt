package reisiegel.jan

// 1. Produkt (The Product)
class EmailMessage(
    val to: String,            // Povinný
    val subject: String,       // Povinný
    val from: String?,         // Volitelný
    val cc: String?,           // Volitelný
    val body: String?,         // Volitelný
    val attachments: List<String> // Složitý, seznam
) {
    override fun toString(): String {
        return "Email: To: $to, Sub: $subject, Body length: ${body?.length ?: 0}, Attachments: ${attachments.size}"
    }
}

// 2. Stavitel (The Builder)
class EmailMessageBuilder(private val to: String, private val subject: String) {

    // Volitelné parametry mají výchozí hodnoty (null/prázdný seznam)
    private var from: String? = null
    private var cc: String? = null
    private var body: String? = null
    private val attachments = mutableListOf<String>()

    // Metody pro postupné nastavování
    fun setFrom(from: String) = apply { this.from = from }
    fun setCc(cc: String) = apply { this.cc = cc }
    fun setBody(body: String) = apply { this.body = body }
    fun addAttachment(file: String) = apply { this.attachments.add(file) }

    // Finální metoda pro sestavení produktu
    fun build(): EmailMessage {
        // Kontrola povinných polí (pokud je potřeba)
        if (to.isBlank() || subject.isBlank()) {
            throw IllegalStateException("To a Subject jsou povinná pole.")
        }

        return EmailMessage(to, subject, from, cc, body, attachments.toList())
    }
}

/* Příklad použití:
val email = EmailMessageBuilder(
    to = "recipient@example.com",
    subject = "Důležitý Report"
)
.setFrom("sender@company.com")
.setBody("Dobrý den, v příloze zasílám report.")
.addAttachment("report.pdf")
.build()

println(email)
*/