package drinks

class DrinkOrder: IPreparableDrink {
    private val drinks: MutableList<IPreparableDrink> = mutableListOf()
    override fun prepare() {
        println("${getCount()} drinks in cart")
        drinks.forEach { drink -> drink.prepare() }
        println("Preparation is done")
    }
    fun add(drinkOrder: IPreparableDrink){
        drinks.add(drinkOrder)
    }
    fun remove(drinkOrder: IPreparableDrink){
        drinks.remove(drinkOrder)
    }
    fun getCount(): Int{
        return drinks.size
    }
}