package reisiegel.jan

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    System.setOut(java.io.PrintStream(System.out, true, Charsets.UTF_8))

    val instance = CaffeConfig.getInstance("Testovací kavárna")
    println(instance.getCaffeName())
    println(instance.serveDrink("tea", milk = true))
    println(instance.serveDrink("caffe", milk = true, sugar = true, ))
    println(instance.serveDrink("beer", cinnamon = true))
    println(instance.serveDrink("whiskey"))
}