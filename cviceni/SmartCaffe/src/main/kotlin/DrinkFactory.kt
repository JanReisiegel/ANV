import drinks.Drink

abstract class DrinkFactory {
    abstract fun createDrink(type: String): Drink;
    open fun serveDrink(type: String): String{
        return createDrink(type).serve()
    }
}