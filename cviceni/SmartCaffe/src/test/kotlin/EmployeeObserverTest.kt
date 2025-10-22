import observers.EmployeeObserver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class EmployeeObserverFunctionalityTest {

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
    fun `update by mel spravne vypsat jmeno zamestnance a status do konzole`() {
        val employeeName = "Karel"
        val statusMessage = "Objednávka č. 45 byla dokončena"

        // Instance EmployeeObserver (Předpokládá se import z produkčního kódu)
        val employeeObserver = EmployeeObserver(employeeName)

        // Act
        employeeObserver.update(statusMessage)

        // Očekávaný výstup (musí obsahovat přesně ten text, který je v println, plus zalomení řádku \n)
        val expectedOutput = "Employee $employeeName notified: $statusMessage\n"

        // Assert
        assertEquals(expectedOutput, outputStreamCaptor.toString())
    }
}