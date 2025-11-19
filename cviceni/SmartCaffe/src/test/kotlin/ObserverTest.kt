import observers.EmployeeObserver
import observers.IObserver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reisiegel.jan.CaffeConfig
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

class ObserverTest {
    class MockObserver(id: String): IObserver {
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
    fun addObserverTest() {
        // Act
        testConfig.addObserver(observer1)

        // Assert (Potřebujeme se dostat k privátnímu seznamu 'observers' pomocí reflexe)
        val observersProperty = CaffeConfig::class.memberProperties
            .first { it.name == "observers" } as KProperty1<CaffeConfig, MutableList<IObserver>>
        val javaField = observersProperty.javaField!!

        // ** KLÍČOVÝ KROK: Nastavení přístupnosti na true pro bypass 'protected' **
        javaField.isAccessible = true

        val observerList = javaField.get(testConfig) as MutableList<IObserver>

        assertEquals(1, observerList.size)
        assertTrue(observerList.contains(observer1))
    }

    @Test
    fun removeObserverTest() {
        // Arrange
        testConfig.addObserver(observer1)
        testConfig.addObserver(observer2)

        // Act
        testConfig.removeObserver(observer1)

        // Assert (Potřebujeme se dostat k privátnímu seznamu 'observers' pomocí reflexe)
        val observersProperty = CaffeConfig::class.memberProperties
            .first { it.name == "observers" } as KProperty1<CaffeConfig, MutableList<IObserver>>
        val javaField = observersProperty.javaField!!

        // ** KLÍČOVÝ KROK: Nastavení přístupnosti na true pro bypass 'protected' **
        javaField.isAccessible = true

        val observerList = javaField.get(testConfig) as MutableList<IObserver>

        assertEquals(1, observerList.size)
        assertTrue(observerList.contains(observer2))
        assertTrue(!observerList.contains(observer1))
    }

    @Test
    fun createDrinkObserverTest() {
        // Arrange
        testConfig.addObserver(observer1)

        // Očekávaná zpráva z createMessage + " in Testovací Kavárna"
        val expectedMessage = "Created caffe with sugar with caramel in $TEST_CAFFE_NAME"

        // Act: Spuštění Factory metody
        testConfig.createDrink("caffe", false, true, true, false, false)

        // Assert
        assertEquals(1, observer1.notificationHistory.size)
        assertEquals(expectedMessage, observer1.notificationHistory[0])
    }

    @Test
    fun createDrinkTest2() {
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