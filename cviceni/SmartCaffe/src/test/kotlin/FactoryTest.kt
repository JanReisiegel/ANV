import drinks.CustomDrink
import observers.IObserver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import reisiegel.jan.CaffeConfig
import kotlin.reflect.typeOf

class FactoryTest {

    private lateinit var caffeConfig: CaffeConfig
    private val TEST_CAFFE_NAME = "Testovací Kavárna"

    @BeforeEach
    fun setUp(){
        caffeConfig = CaffeConfig.getInstance(TEST_CAFFE_NAME)
    }
    @AfterEach
    fun clean() = CaffeConfig.deleteInstance()

    @Test
    fun caffeTest() {
        // Act
        val customDrink = caffeConfig.createDrink("caffe")

        // Assert: Overeni, ze Factory vratila spravne sestaveny napoj
        assertEquals("Serving caffe", customDrink.toString())
    }

    @Test
    fun teaTest() {
        // Arrange
        val expected = "Serving tea in $TEST_CAFFE_NAME"

        // Act
        val result = caffeConfig.serveDrink("tea")

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun beerTest() {
        // Arrange
        val expectedDrinkPart = "Serving beer"
        val expected = "$expectedDrinkPart in $TEST_CAFFE_NAME"

        // Act
        val result = caffeConfig.serveDrink("beer")

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun emptyBottleTest() {
        // Arrange
        val expectedDrinkPart = "Serving empty bottle"
        val expected = "$expectedDrinkPart in $TEST_CAFFE_NAME"

        // Act
        val result = caffeConfig.serveDrink("whiskey")

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun serveDrinkUnimplemented(){
        class DrinkUnimplemented : DrinkFactory() {
            override fun createDrink(
                type: String,
                milk: Boolean,
                sugar: Boolean,
                caramel: Boolean,
                honey: Boolean,
                cinnamon: Boolean
            ): CustomDrink {
                TODO("Not yet implemented")
            }

            override fun addObserver(observer: IObserver) {
                TODO("Not yet implemented")
            }

            override fun removeObserver(observer: IObserver) {
                TODO("Not yet implemented")
            }

            override fun notifyAll(status: String) {
                TODO("Not yet implemented")
            }
        }

        val exception = assertThrows<NotImplementedError>{
            DrinkUnimplemented().serveDrink("tea")
        }
    }
}