import observers.IObserver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import payments.CashPayment
import payments.CreditPayment
import reisiegel.jan.CaffeConfig
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField
import kotlin.test.assertTrue

class StrategyTest {
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
    private lateinit var observer3: MockObserver
    private lateinit var observer4: MockObserver

    @BeforeEach
    fun setUp() {
        testConfig = CaffeConfig.getInstance(TEST_CAFFE_NAME)

        observer3 = MockObserver("Petr")
        observer4 = MockObserver("Jana")
    }

    @AfterEach
    fun tearDown() {
        CaffeConfig.deleteInstance()
    }

    @Test
    fun addCheckoutObserverTest(){
        testConfig.addCheckoutObserver(observer3)

        val observersProperty = CaffeConfig::class.memberProperties.first{it.name == "checkoutObservers"} as KProperty1<CaffeConfig, MutableList<IObserver>>
        val javaField = observersProperty.javaField!!

        javaField.isAccessible = true

        val observerList = javaField.get(testConfig) as MutableList<IObserver>

        assertEquals(1,observerList.size)
        assertTrue(observerList.contains(observer3))
    }

    @Test
    fun removeCheckoutObserverTest() {
        // Arrange
        testConfig.addCheckoutObserver(observer3)
        testConfig.addCheckoutObserver(observer4)

        // Act
        testConfig.removeCheckoutObserver(observer3)

        // Assert (Potřebujeme se dostat k privátnímu seznamu 'observers' pomocí reflexe)
        val observersProperty = CaffeConfig::class.memberProperties
            .first { it.name == "checkoutObservers" } as KProperty1<CaffeConfig, MutableList<IObserver>>
        val javaField = observersProperty.javaField!!

        // ** KLÍČOVÝ KROK: Nastavení přístupnosti na true pro bypass 'protected' **
        javaField.isAccessible = true

        val observerList = javaField.get(testConfig) as MutableList<IObserver>

        assertEquals(1, observerList.size)
        Assertions.assertTrue(observerList.contains(observer4))
        Assertions.assertTrue(!observerList.contains(observer3))
    }

    @Test
    fun notifyAllTest() {
        // Arrange
        testConfig.addCheckoutObserver(observer3)
        testConfig.addCheckoutObserver(observer4)
        val testStatus = "Nová objednávka připravena k servírování"

        // Act
        testConfig.notifyCheckouts(testStatus)

        // Assert
        assertEquals(1, observer3.notificationHistory.size)
        assertEquals(testStatus, observer3.notificationHistory[0])

        assertEquals(1, observer4.notificationHistory.size)
        assertEquals(testStatus, observer4.notificationHistory[0])
    }

    @Test
    fun creditCardPaymentTest(){
        testConfig.addCheckoutObserver(observer3)

        val expectedMessage = "Customer, on table 3, want to pay 3,55 using credit card."
        testConfig.checkout(CreditPayment(), 3.55, 3)

        assertEquals(1, observer3.notificationHistory.size)
        assertEquals(expectedMessage, observer3.notificationHistory[0])
    }

    @Test
    fun cashPaymentTest(){
        testConfig.addCheckoutObserver(observer3)

        val expectedMessage = "Customer, on table 3, want to pay 3,55 using cash."
        testConfig.checkout(CashPayment(), 3.55, 3)

        assertEquals(1, observer3.notificationHistory.size)
        assertEquals(expectedMessage, observer3.notificationHistory[0])
    }
}