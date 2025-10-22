import observers.EmployeeObserver
import observers.IObserver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reisiegel.jan.CaffeConfig
import java.io.PrintStream
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import java.io.ByteArrayOutputStream

class ObserverTest {
    class MockObserver(val id: String): IObserver {
        var notificationHistory: MutableList<String> = mutableListOf()

        override fun update(status: String) {
            notificationHistory.add(status)
        }

        // Pro vyčištění historie před testem
        fun clearHistory() {
            notificationHistory.clear()
        }
    }

    private lateinit var testConfig: CaffeConfig
    private val TEST_CAFFE_NAME = "Testovací Kavárna"
    private lateinit var observer1: MockObserver
    private lateinit var observer2: MockObserver

    @BeforeEach
    fun setUp() {
        testConfig = CaffeConfig.getInstance(TEST_CAFFE_NAME)

        observer1 = MockObserver("Petr")
        observer2 = MockObserver("Jana")
    }

    @AfterEach
    fun tearDown() {
        CaffeConfig.deleteInstance()
    }

    @Test
    fun `addObserver by mel pridat observer do seznamu`() {
        // Act
        testConfig.addObserver(observer1)

        // Assert (Potřebujeme se dostat k privátnímu seznamu 'observers' pomocí reflexe)
        val observersProperty = CaffeConfig::class.memberProperties
            .first { it.name == "observers" } as KProperty1<CaffeConfig, List<IObserver>>

        val observerList = observersProperty.get(testConfig)

        assertEquals(1, observerList.size)
        assertTrue(observerList.contains(observer1))
    }

    @Test
    fun `removeObserver by mel odebrat observer ze seznamu`() {
        // Arrange
        testConfig.addObserver(observer1)
        testConfig.addObserver(observer2)

        // Act
        testConfig.removeObserver(observer1)

        // Assert
        val observersProperty = CaffeConfig::class.memberProperties
            .first { it.name == "observers" } as KProperty1<CaffeConfig, List<IObserver>>

        val observerList = observersProperty.get(testConfig)

        assertEquals(1, observerList.size)
        assertTrue(observerList.contains(observer2))
        assertTrue(!observerList.contains(observer1))
    }

    @Test
    fun `notifyAll by mel notifikovat vsechny pridane observéry se stejnou zpravou`() {
        // Arrange
        testConfig.addObserver(observer1)
        testConfig.addObserver(observer2)
        val testStatus = "Nová objednávka připravena k servírování"

        // Act
        testConfig.notifyAll(testStatus)

        // Assert
        assertEquals(1, observer1.notificationHistory.size)
        assertEquals(testStatus, observer1.notificationHistory[0])

        assertEquals(1, observer2.notificationHistory.size)
        assertEquals(testStatus, observer2.notificationHistory[0])
    }

    @Test
    fun `createDrink by mel automaticky notifikovat observer s kompletní zprávou`() {
        // Arrange
        testConfig.addObserver(observer1)

        // Očekávaná zpráva z createMessage + " in Testovací Kavárna"
        val expectedMessage = "Káva servírována with sugar with caramel in $TEST_CAFFE_NAME"

        // Act: Spuštění Factory metody
        testConfig.createDrink("caffe", false, true, true, false, false)

        // Assert
        assertEquals(1, observer1.notificationHistory.size)
        assertEquals(expectedMessage, observer1.notificationHistory[0])
    }

    @Test
    fun `createDrink by nemel notifikovat observera, ktery byl odebran`() {
        // Arrange
        testConfig.addObserver(observer1)
        testConfig.addObserver(observer2)
        testConfig.removeObserver(observer2)

        // Act
        testConfig.createDrink("tea", true, false, false, false, false)

        // Assert
        // Observer 1 (Petr) byl notifikován
        assertEquals(1, observer1.notificationHistory.size)
        // Observer 2 (Jana) by neměl být notifikován
        assertEquals(0, observer2.notificationHistory.size)
    }

}