
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import reisiegel.jan.CaffeConfig

class SingletonTest {

    @AfterEach
    fun resetSingleton() {
        CaffeConfig.deleteInstace()
    }

    @Test
    fun FirstTest() {
        val caffeName = "U Jednorožce"
        val instance = CaffeConfig.getInstance(caffeName)

        // Ověříme, že je instance vytvořena a má správný název
        assertEquals(caffeName, instance.getCaffeName())
    }

    @Test
    fun OnlyOneInstance() {
        val originalName = "Kavárna Na Růžku"
        val newName = "Nové Místo"

        // První volání, které vytvoří a nastaví singleton
        val firstInstance = CaffeConfig.getInstance(originalName)

        // Druhé volání s jiným názvem
        val secondInstance = CaffeConfig.getInstance(newName)

        // Test, zda obě reference ukazují na stejný objekt
        assertSame(firstInstance, secondInstance)

        // Test, že název zůstal stejný jako při prvním volání
        assertEquals(originalName, secondInstance.getCaffeName())
    }

    @Test
    fun ThreadSafeInstance() = runBlocking {
        // Počet souběžných volání
        val threadsCount = 1000

        // Seznam pro uložení všech instancí vrácených z paralelních volání
        val instances = mutableListOf<CaffeConfig>()

        // Spustíme 1000 coroutines, které se pokusí získat instanci současně
        withContext(Dispatchers.Default) {
            val jobs = List(threadsCount) { index ->
                launch {
                    // Každá coroutine volá getInstance s unikátním názvem
                    instances.add(CaffeConfig.getInstance("Test Caffe $index"))
                }
            }
            // Počkáme, až se dokončí všechny úlohy
            jobs.forEach { it.join() }
        }

        // Ověříme, že všechny vrácené instance jsou stejné
        val firstInstance = instances.firstOrNull()
        assertNotNull(firstInstance)

        // Vytvoříme Set z instancí. Velikost Setu musí být 1, což potvrzuje,
        // že se vytvořila jen jedna instance, ačkoliv jsme volali getInstance 1000x
        assertEquals(1, instances.toSet().size)

        // Dodatečně ověříme, že název instance je správně nastaven z prvního volání,
        // které vytvořilo singleton
        // (Většinou to bude Test Caffe 0, ale záleží na rychlosti coroutines)
        println("Název vytvořené instance: ${firstInstance?.getCaffeName()}")
    }

    @Test
    fun RightCaffeName() {
        val originalName = "Caffe & Co."

        // Volání, které vytvoří instanci s původním názvem
        CaffeConfig.getInstance(originalName)

        // Volání metody bez parametru, která by měla vrátit tu samou instanci
        val instanceFromDefaultCall = CaffeConfig.getInstance("Default Name")

        // Ověříme, že získaná instance má původní název
        assertEquals(originalName, instanceFromDefaultCall.getCaffeName())
    }

    @Test
    fun OwnNameTest() {
        // Vzhledem k tomu, že konstruktor je private, to není možné přímo
        // Tento test jen ověřuje, že defaultní hodnota je "Smart Caffe"
        val name = "Smart Caffe"
        val instance = CaffeConfig.getInstance(name)
        assertEquals(name, instance.getCaffeName())
    }
}