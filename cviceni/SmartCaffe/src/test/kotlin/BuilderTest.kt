import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reisiegel.jan.CaffeConfig

class BuilderTest {
    private lateinit var caffeConfig: CaffeConfig
    private val TEST_CAFFE_NAME = "Testovací Kavárna"

    @BeforeEach
    fun setUp(){
        caffeConfig = CaffeConfig.getInstance(TEST_CAFFE_NAME)
    }
    @AfterEach
    fun clean() = CaffeConfig.deleteInstance()

    @Test
    fun cinnamonTest() {
        // Act
        val customDrink = caffeConfig.createDrink("caffe", cinnamon = true)

        // Assert: Overeni, ze Factory vratila spravne sestaveny napoj
        assertEquals("Serving caffe with cinnamon", customDrink.toString())
    }
    @Test
    fun milkTest() {
        // Act
        val customDrink = caffeConfig.createDrink("caffe", milk = true)

        // Assert: Overeni, ze Factory vratila spravne sestaveny napoj
        assertEquals("Serving caffe with milk", customDrink.toString())
    }
    @Test
    fun sugarTest() {
        // Act
        val customDrink = caffeConfig.createDrink("caffe", sugar = true)

        // Assert: Overeni, ze Factory vratila spravne sestaveny napoj
        assertEquals("Serving caffe with sugar", customDrink.toString())
    }
    @Test
    fun caramelTest() {
        // Act
        val customDrink = caffeConfig.createDrink("caffe", caramel = true)

        // Assert: Overeni, ze Factory vratila spravne sestaveny napoj
        assertEquals("Serving caffe with caramel", customDrink.toString())
    }
    @Test
    fun honeyTest() {
        // Act
        val customDrink = caffeConfig.createDrink("caffe", honey = true)

        // Assert: Overeni, ze Factory vratila spravne sestaveny napoj
        assertEquals("Serving caffe with honey", customDrink.toString())
    }
}