import observers.EmployeeObserver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class EmployeeObserverTest {

    private val standardOut = System.out
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        // Přesměrujte System.out na náš ByteArrayOutputStream
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        // Obnovte původní System.out
        System.setOut(standardOut)
    }

    @Test
    fun updateTest() {
        val employeeName = "Karel"
        val statusMessage = "Created Caffe with cinnamon"

        // Instance EmployeeObserver (Předpokládá se import z produkčního kódu)
        val employeeObserver = EmployeeObserver(employeeName)

        // Act
        employeeObserver.update(statusMessage)

        // Očekávaný výstup (musí obsahovat přesně ten text, který je v println, plus zalomení řádku \n)
        val expectedOutput = "Employee $employeeName notified: $statusMessage"

        // Assert
        val streamText = outputStreamCaptor.toString().trim()
        assertEquals(expectedOutput, streamText)
    }
}