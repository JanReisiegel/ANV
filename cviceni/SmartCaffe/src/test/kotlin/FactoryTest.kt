import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reisiegel.jan.CaffeConfig

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
}