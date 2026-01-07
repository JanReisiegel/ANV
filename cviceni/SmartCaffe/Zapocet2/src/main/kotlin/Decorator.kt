package reisiegel.jan

// Komponenta (základní rozhraní pro objekt a jeho úpravy/dekorace)
interface Shape {
    fun draw(): String
    fun getDescription(): String
}

// Konkrétní Komponenta (základní objekt)
class Circle : Shape {
    override fun draw(): String = "Vykreslení základního Kruhu"
    override fun getDescription(): String = "Základní Kruh"
}

// Abstraktní Dekorátor
abstract class ShapeDecorator(private val decoratedShape: Shape) : Shape {
    // Deleguje volání na obalenou komponentu (dekorovaný objekt)
    override fun draw(): String = decoratedShape.draw()
    override fun getDescription(): String = decoratedShape.getDescription()
}

// Konkrétní Dekorátor 1: Přidání rámečku
class BorderDecorator(decoratedShape: Shape) : ShapeDecorator(decoratedShape) {
    override fun draw(): String = super.draw() + " s Rámečkem"
    override fun getDescription(): String = super.getDescription() + ", Rámeček"
}

// Konkrétní Dekorátor 2: Stínování
class ShadowDecorator(decoratedShape: Shape) : ShapeDecorator(decoratedShape) {
    override fun draw(): String = super.draw() + " a Stínováním"
    override fun getDescription(): String = super.getDescription() + ", Stínování"
}

// Příklad použití
fun mainDecorator() {
    // 1. Základní kruh
    var myShape: Shape = Circle()
    println("1. ${myShape.draw()} - ${myShape.getDescription()}")

    // 2. Kruh s Rámečkem
    myShape = BorderDecorator(myShape)
    println("2. ${myShape.draw()} - ${myShape.getDescription()}")

    // 3. Kruh s Rámečkem a Stínováním (stohování/kompozice dekorátorů)
    myShape = ShadowDecorator(myShape)
    println("3. ${myShape.draw()} - ${myShape.getDescription()}")
}