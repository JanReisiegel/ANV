// 1. Flyweight - Sdílený objekt
// Obsahuje data, která jsou společná pro tisíce stromů (textura, barva)
class TreeType(
    val name: String,
    val color: String,
    val textureData: ByteArray // Simulace těžkých dat (např. obrázek)
) {
    fun draw(x: Int, y: Int) {
        println("Vykresluji strom '$name' ($color) na pozici [$x, $y]")
    }
}

// 2. Flyweight Factory - Správce sdílených objektů
// Zajišťuje, že se každý typ stromu vytvoří jen jednou
object TreeFactory {
    private val treeTypes = mutableMapOf<String, TreeType>()

    fun getTreeType(name: String, color: String): TreeType {
        val key = "$name-$color"
        return treeTypes.getOrPut(key) {
            println("--- Vytvářím NOVÝ typ stromu: $name ($color) ---")
            // V reálu by se zde načítala těžká data
            TreeType(name, color, byteArrayOf(1, 0, 1))
        }
    }
}

// 3. Context - Objekt, který obsahuje unikátní (extrinsic) data
// Drží jen souřadnice a odkaz na sdílený "těžký" objekt
class Tree(val x: Int, val y: Int, val type: TreeType) {
    fun draw() {
        type.draw(x, y)
    }
}

// 4. Client - Les
class Forest {
    private val trees = mutableListOf<Tree>()

    fun plantTree(x: Int, y: Int, name: String, color: String) {
        val type = TreeFactory.getTreeType(name, color)
        val tree = Tree(x, y, type)
        trees.add(tree)
    }

    fun display() {
        trees.forEach { it.draw() }
    }
}

// --- Použití v main ---
fun main() {
    val forest = Forest()

    // Sázíme 10 000 dubů, ale v paměti bude objekt TreeType jen jednou!
    repeat(10000) {
        forest.plantTree(it, it * 2, "Dub", "Zelená")
    }

    // Sázíme pár smrků
    forest.plantTree(10, 20, "Smrk", "Tmavě zelená")
    forest.plantTree(15, 25, "Smrk", "Tmavě zelená")

    // forest.display()
    println("\nCelkový počet stromů: 10002")
    println("Počet unikátních typů v paměti (TreeType): 2")
}