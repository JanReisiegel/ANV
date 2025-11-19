import commands.OrderCommand
import commands.PaymentCommand
import drinks.CustomDrink
import observers.IObserver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import payments.Checkout
import payments.CreditPayment
import reisiegel.jan.CaffeConfig
import kotlin.test.Test
import kotlin.test.assertEquals

class CommandTest {
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
    fun paymentCommandTest(){
        val checkout = Checkout(CreditPayment())
        val command = PaymentCommand(listOf(observer1, observer2), checkout, 375.98, 12)
        command.execute()

        assertEquals(1, observer1.notificationHistory.size)
        assertEquals(1, observer2.notificationHistory.size)
    }

    @Test
    fun orderCommandTest(){
        val drink = CustomDrink.Builder("tea").caramel().cinnamon().build()
        val orderCommand = OrderCommand(listOf(observer1, observer2), drink, TEST_CAFFE_NAME)
        orderCommand.execute()
        assertEquals(1, observer1.notificationHistory.size)
        assertEquals(1, observer2.notificationHistory.size)
    }
}