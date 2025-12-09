package reisiegel.jan.Composite

// Komponenta (společné rozhraní pro listy a složené objekty)
interface OrganizationComponent {
    val name: String
    val budget: Double
    fun printHierarchy(level: Int = 0)
}

// List (Jednotlivý zaměstnanec)
class Employee(
    override val name: String,
    override val budget: Double // Např. jeho plat
) : OrganizationComponent {
    override fun printHierarchy(level: Int) {
        println("${"  ".repeat(level)}🧑 Zaměstnanec: $name (Rozpočet: $budget)")
    }
}

// Kompozit (Oddělení - může obsahovat další komponenty)
class Department(
    override val name: String
) : OrganizationComponent {
    private val children = mutableListOf<OrganizationComponent>()

    fun add(component: OrganizationComponent) {
        children.add(component)
    }

    fun remove(component: OrganizationComponent) {
        children.remove(component)
    }

    // Celkový rozpočet je součet rozpočtů všech podřízených
    override val budget: Double
        get() = children.sumOf { it.budget }

    override fun printHierarchy(level: Int) {
        println("${"  ".repeat(level)}🏢 Oddělení: $name (Celkový rozpočet: $budget)")
        children.forEach { it.printHierarchy(level + 1) }
    }
}

// Příklad použití
fun mainComposite() {
    val mgr1 = Employee("Alice", 70000.0)
    val dev1 = Employee("Bob", 50000.0)

    val hr = Department("Lidské zdroje")
    hr.add(Employee("Carol", 45000.0))

    val development = Department("Vývoj SW")
    development.add(mgr1)
    development.add(dev1)
    development.add(Employee("Dave", 55000.0))

    // Hlavní oddělení/kořen
    val company = Department("Společnost X")
    company.add(hr)
    company.add(development)

    // Zacházení s celou strukturou i s jednotlivými částmi jednotně
    println("--- Celková hierarchie a rozpočty ---")
    company.printHierarchy()

    println("\nRozpočet vývojového oddělení: ${development.budget} Kč")
}