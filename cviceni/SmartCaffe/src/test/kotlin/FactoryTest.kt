import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reisiegel.jan.CaffeConfig
import reisiegel.jan.Drinks.Beer
import reisiegel.jan.Drinks.Caffe
import reisiegel.jan.Drinks.EmptyBottle
import reisiegel.jan.Drinks.Tea

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
    fun teaTest() {
        // Act
        val drink = caffeConfig.createDrink("tea")

        // Assert
        assertTrue(drink is Tea)
    }

    @Test
    fun caffeTest() {
        // Act
        val drink = caffeConfig.createDrink("caffe")

        // Assert
        assertTrue(drink is Caffe)
    }

    @Test
    fun beerTest() {
        // Act
        val drink = caffeConfig.createDrink("beer")

        // Assert
        assertTrue(drink is Beer)
    }

    @Test
    fun emptyBottleTest() {
        // Act
        val drink = caffeConfig.createDrink("whiskey")

        // Assert
        assertTrue(drink is EmptyBottle)
    }

    @Test
    fun serveTeaTest() {
        // Arrange (Tea().serve() vrací "Čaj servírován")
        val expected = "Serving tea in $TEST_CAFFE_NAME"

        // Act
        val result = caffeConfig.serveDrink("tea")

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun serveCaffeTest() {
        // Arrange (Caffe().serve() vrací "Káva servírována")
        val expected = "Serving caffe in $TEST_CAFFE_NAME"

        // Act
        val result = caffeConfig.serveDrink("caffe")

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun serveEmptyBottleTest() {
        // Arrange (EmptyBottle().serve() vrací "Prázdná láhev")
        val expected = "Serving empty bottle in $TEST_CAFFE_NAME"

        // Act
        val result = caffeConfig.serveDrink("džus")

        // Assert
        assertEquals(expected, result)
    }
}